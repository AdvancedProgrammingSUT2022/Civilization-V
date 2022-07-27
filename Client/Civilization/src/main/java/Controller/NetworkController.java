package Controller;
import Controller.GameController.GameController;
import Controller.PreGameController.LoginAndRegisterController;
import Controller.PreGameController.MainMenuController;
import Controller.SavingDataController.DataSaver;
import Model.ChatRelated.AlertDataBase;
import Model.Enums.Menus;
import Model.MapRelated.GameMap;
import Model.NetworkRelated.*;
import View.GraphicViewController.GameplayGraphicController;
import View.GraphicViewController.MainPageController;
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
import java.nio.charset.StandardCharsets;
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
        sendMessage(new Request(RequestType.registerReaderSocket,new ArrayList<>(){{
            add(username);
        }}).toJson(),outputListenerStream);
        listener = new Thread(() -> {
            try {
                while (isOnline) {
                    int length = inputListenerStream.readInt();
                    byte [] data = new byte[length];
                    inputListenerStream.readFully(data);
                    String update = new String(data, StandardCharsets.UTF_8);
                    System.out.println("i got your update!");
                    try {
                        if(!update.equals("{}"))
                            handleUpdate(new Gson().fromJson(update, Update.class));
                    }catch ( com.google.gson.JsonSyntaxException e){
                        System.out.println("not a valid update");
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
                inputListenerStream.close();
                listenerSocket.close();
            } catch (Exception e) {
                System.out.println("disconnected");
                disconnect();
            }
        });
        listener.start();
    }

    private void handleUpdate(Update update) throws IOException {
        switch (update.getUpdateType()) {
            case invitation -> MainPageController.createInvitePopUp(update);
            case inviteAcceptance -> MainMenuController.getInstance().handleInvitation(update);
            case initializeGame -> MainMenuController.getInstance().initializeGame(update);
            case UpdateGame -> GameController.getInstance().setGameMap(update);
            case peaceRequest -> GameController.getInstance().makePeacePopup(update);
            case demandRequest -> GameController.getInstance().demandPopup(update);
            case declareWar -> GameController.getInstance().declareWarPopup(update);
        }
    }



    private void disconnect() {
        isOnline = false;
    }

    public Response send(Request request) {
        try {
            sendMessage(request.toJson(),outputStream);
            String response = getMessage(inputStream);
            return new Gson().fromJson(response,Response.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void sendMessage(String message, DataOutputStream outputStream) throws IOException {
        byte [] updateBytes = message.getBytes(StandardCharsets.UTF_8);
        outputStream.writeInt(updateBytes.length);
        outputStream.write(updateBytes);
        outputStream.flush();
    }

    public String getMessage(DataInputStream inputStream) throws IOException {
        int length = inputStream.readInt();
        byte [] data = new byte[length];
        inputStream.readFully(data);
        return new String(data, StandardCharsets.UTF_8);
    }
}
