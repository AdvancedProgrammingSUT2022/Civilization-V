package Controller;
import Controller.PreGameController.LoginAndRegisterController;
import Controller.PreGameController.MainMenuController;
import Model.ChatRelated.AlertDataBase;
import Model.Enums.Menus;
import Model.NetworkRelated.*;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class NetworkController {
    private static NetworkController networkController;
    public Socket socket;
    public Socket listenerSocket;
    public boolean isOnline = true;
    public DataInputStream inputStream;
    public DataOutputStream outputStream;

    public Thread listener;

    private int port = 8000;

    public static NetworkController getInstance(){
        if(networkController == null)
            networkController = new NetworkController();
        return networkController;
    }

    public boolean connect() {
        try {
            this.socket = new Socket("localhost", port);
            this.inputStream = new DataInputStream(socket.getInputStream());
            this.outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ignored) {
            System.out.println("connection Failed!");
            return false;
        }
        return true;
    }

    public void listenForServerUpdates(String username) throws IOException {
        listenerSocket = new Socket("localhost",port);
        DataInputStream inputListenerStream = new DataInputStream(listenerSocket.getInputStream());
        DataOutputStream outputListenerStream = new DataOutputStream(listenerSocket.getOutputStream());
        outputListenerStream.writeUTF(new Request(RequestType.registerReaderSocket,new ArrayList<>(){{
            add(username);
        }}).toJson());
        listener = new Thread(() -> {
            while (isOnline) {
                try {
                    String update = inputListenerStream.readUTF();
                    try {
                        handleUpdate(new Gson().fromJson(update, Update.class));
                    }
                    catch (Exception e){
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        listener.start();
    }

    private void handleUpdate(Update update) {
        switch (update.getUpdateType()) {
            case invitation -> createInvitePopUp(update);
            case inviteAcceptance -> MainMenuController.getInstance().handleInvitation(update);
        }
    }

    private void createInvitePopUp(Update update) {
        Popup popup = new Popup();
        popup.requestFocus();
        Label label = new Label(update.getParams().get(0) + " has invited you to a game!");
        label.setTextFill(Color.rgb(180,0,0,1));
        label.setMinHeight(200);
        label.setMinWidth(600);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setStyle("-fx-font-size: 30; -fx-font-family: 'Tw Cen MT'; -fx-font-weight: bold;" +
                "-fx-background-color: rgba(255,255,255,0.34); -fx-background-radius: 5; -fx-alignment: center;" +
                "-fx-border-color: cyan; -fx-border-width: 4.5; -fx-border-radius: 5;");
        popup.getContent().add(label);
        Button acceptButton = new Button();
        acceptButton.setText("Accept");
        acceptButton.setStyle(" -fx-font-family: 'Britannic Bold'; -fx-background-radius: 10;-fx-background-color: rgba(201, 238, 221, 0.7); -fx-font-size: 18 ;-fx-text-fill: #4f4e4e;");
        acceptButton.setLayoutX(225);
        acceptButton.setLayoutY(150);
        Button declineButton = new Button();
        declineButton.setLayoutX(125);
        declineButton.setLayoutY(150);
        declineButton.setStyle(" -fx-font-family: 'Britannic Bold'; -fx-background-radius: 10;-fx-background-color: rgba(201, 238, 221, 0.7); -fx-font-size: 18 ;-fx-text-fill: #4f4e4e;");
        declineButton.setText("Decline");
        popup.getContent().add(acceptButton);
        popup.getContent().add(declineButton);
        Platform.runLater(()->{popup.show(main.java.Main.scene.getWindow());});
        acceptButton.setOnMouseClicked(mouseEvent -> {
            main.java.Main.changeMenu(Menus.WaitingRoom.value);
            send(new Request(RequestType.inviteAcceptation,new ArrayList<>(){{
                add("accepted");
                add(update.getParams().get(0));
                add(LoginAndRegisterController.getInstance().getLoggedInUser().getUsername());
            }}));
            popup.hide();
        });
        declineButton.setOnMouseClicked(mouseEvent -> {
            send(new Request(RequestType.inviteAcceptation,new ArrayList<>(){{
                add("declined");
                add(update.getParams().get(0));
                add(LoginAndRegisterController.getInstance().getLoggedInUser().getUsername());
            }}));
            popup.hide();
        });
    }

    private void disconnect() {
        isOnline = false;
    }

    public Response send(Request request) {
        try {
            outputStream.writeUTF(request.toJson());
            outputStream.flush();
            String response = this.inputStream.readUTF();
            return new Gson().fromJson(response,Response.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
