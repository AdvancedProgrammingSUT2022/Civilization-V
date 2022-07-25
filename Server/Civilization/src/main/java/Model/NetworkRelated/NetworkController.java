package Model.NetworkRelated;

import Model.User.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class NetworkController {
    private static NetworkController instance;
    private static HashMap<User,Socket> onlineUsers = new HashMap<>();

    private NetworkController() {}
    public static NetworkController getInstance() {
        if (instance == null) instance = new NetworkController();
        return instance;
    }

    public void addOnlineUser(User user,Socket socket){
        onlineUsers.put(user,socket);
    }

    public void removeOnlineUser(User user,Socket socket){
        onlineUsers.remove(user);
    }

    public static HashMap<User,Socket> getOnlineUsers() {
        return onlineUsers;
    }

    private ServerSocket serverSocket;
    public void initializeServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }
    public void listenForClients() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            new SocketHandler(socket).start();
        }
    }

}
