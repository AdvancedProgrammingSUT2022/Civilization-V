package Controller;
import Model.NetworkRelated.Request;
import Model.NetworkRelated.Response;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Scanner;

public class NetworkController {
    private static NetworkController networkController;
    public Socket socket;
    public Socket listenerSocket;
    public boolean isOnline = true;
    public DataInputStream inputStream;
    public DataOutputStream outputStream;

    public Thread listener;
    public Scanner scanner;

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
            ignored.printStackTrace();
            return false;
        }
        return true;
    }

    public void listenForServerUpdates() throws IOException {
        listenerSocket = new Socket("localhost",port);
        DataInputStream inputListenerStream = new DataInputStream(listenerSocket.getInputStream());
        DataOutputStream outputListenerStream = new DataOutputStream(listenerSocket.getOutputStream());
        listener = new Thread(() -> {
            while (isOnline) {
                try {
                    String response = inputListenerStream.readUTF();
                    handleResponse(new Gson().fromJson(response,Response.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        listener.start();
    }

    private void handleResponse(Response fromJson) {

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
