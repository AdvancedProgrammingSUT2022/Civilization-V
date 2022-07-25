package Model.NetworkRelated;

import Model.User.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class NetworkController {
    private static NetworkController instance;
    private final static ArrayList<User> onlineUsers = new ArrayList<>();
    private ServerSocket serverSocket;

    private NetworkController() {}
    public static NetworkController getInstance() {
        if (instance == null) instance = new NetworkController();
        return instance;
    }

    public void sendUpdate(Update update,User receiver){

    }

    public void addOnlineUser(User user){
        onlineUsers.add(user);
    }

    public void removeOnlineUser(User user){
        onlineUsers.remove(user);
    }

    public ArrayList<User> getOnlineUsers() {
        return onlineUsers;
    }


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
