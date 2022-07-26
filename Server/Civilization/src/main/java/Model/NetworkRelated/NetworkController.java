package Model.NetworkRelated;

import Model.CivlizationRelated.Civilization;
import Model.MapRelated.GameMap;
import Model.User.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class NetworkController {
    private static NetworkController instance;
    private final static ArrayList<User> onlineUsers = new ArrayList<>();
    private ServerSocket serverSocket;
    private ArrayList<GameMap> gamesInProgress = new ArrayList<>();

    private NetworkController() {}
    public static NetworkController getInstance() {
        if (instance == null) instance = new NetworkController();
        return instance;
    }

    public void sendUpdate(Update update,User receiver){
        User orig = getOnlineUser(receiver.getUsername());
        try {
            if(receiver.getDataOutputStream() == null)
                System.out.println("ya bruh");
            sendMessage(update.toJson(),orig.getDataOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getOnlineUser(String username){
        for (User user:onlineUsers) {
            if(user.getUsername().equals(username))
                return user;
        }
        return null;
    }
    public void sendMessage(String message, DataOutputStream outputStream) throws IOException {
        byte [] updateBytes = message.getBytes(StandardCharsets.UTF_8);
        outputStream.writeInt(updateBytes.length);
        outputStream.write(updateBytes);
        outputStream.flush();
    }

    public void addGame(GameMap gameMap){
        gamesInProgress.add(gameMap);
    }

    public GameMap getGame(String username){
        for (GameMap game:gamesInProgress) {
            for (Civilization civilization:game.getCivilizations()) {
                if(civilization.getUser().getUsername().equals(username))
                    return game;
            }
        }
        return null;
    }
    public GameMap getGame(User user){
        for (GameMap game:gamesInProgress) {
            for (Civilization civilization:game.getCivilizations()) {
                if(civilization.getUser().getUsername().equals(user.getUsername()))
                    return game;
            }
        }
        return null;
    }

    public void setGame(String username,GameMap gameMap){
        GameMap gameIntended = null;
        outer:
        for (GameMap game:gamesInProgress) {
            for (Civilization civilization:game.getCivilizations()) {
                if(civilization.getUser().getUsername().equals(username)) {
                    gameIntended = game;
                    break outer;
                }
            }
        }
        if(gameIntended != null)
            gamesInProgress.set(gamesInProgress.indexOf(gameIntended),gameMap);
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
