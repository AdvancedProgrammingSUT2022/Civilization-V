package Model.NetworkRelated;

import Controller.GameController.GameController;
import Controller.PreGameController.LoginAndRegisterController;
import Controller.PreGameController.MainMenuController;
import Controller.PreGameController.ProfileMenuController;
import Controller.SavingDataController.DataSaver;
import Model.User.User;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public class SocketHandler extends Thread {
    private boolean working = true;
    private final Socket socket;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;

    private User loggedInUser;

    public SocketHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        while (working) {
            try {
                String message = getMessage();
                Request request =  new Gson().fromJson(message,Request.class);
                Response response = handleRequest(request);
                if(response != null) {
                    sendMessage(response.toJson());
                }
            } catch (SocketException e) {
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("im out");
    }
    public void sendMessage(String message) throws IOException {
        byte [] updateBytes = message.getBytes(StandardCharsets.UTF_8);
        dataOutputStream.writeInt(updateBytes.length);
        dataOutputStream.write(updateBytes);
        dataOutputStream.flush();
    }
    public String getMessage() throws IOException {
        int length = dataInputStream.readInt();
        byte [] data = new byte[length];
        dataInputStream.readFully(data);
        return new String(data, StandardCharsets.UTF_8);
    }

    private Response handleRequest(Request request) throws IOException {
        String response = null;
        switch (request.getRequestType()){
            case Login -> response = login(request.getParams().get(0),request.getParams().get(1));
            case Register -> response = LoginAndRegisterController.getInstance().register(request.getParams().get(0),request.getParams().get(1),request.getParams().get(2));
            case Logout -> response = userLogout();
            case Users -> response = DataSaver.getInstance().loadUsers();
            case ChangeNickname -> response = ProfileMenuController.getInstance().changeNickname(request.getParams().get(0),loggedInUser);
            case ChangePassword -> response = ProfileMenuController.getInstance().changeCurrentPassword(request.getParams().get(0),request.getParams().get(1),loggedInUser);
            case NextProfilePic -> ProfileMenuController.getInstance().increaseImageIndex(Integer.parseInt(request.getParams().get(0)),loggedInUser);
            case PrevProfilePic -> ProfileMenuController.getInstance().decreaseImageIndex(Integer.parseInt(request.getParams().get(0)),loggedInUser);
            case ChoosePic ->  loggedInUser.setProfPicIndex(Integer.parseInt(request.getParams().get(0)));
            case registerReaderSocket -> {registerReaderSocket(request.getParams().get(0));}
            case sendInvite -> response = MainMenuController.getInstance().sendInvite(loggedInUser.getUsername(),request.getParams().get(0));
            case inviteAcceptation -> MainMenuController.getInstance().inviteAcceptation(request.getParams().get(0),request.getParams().get(1),request.getParams().get(2));
            case startGame -> MainMenuController.getInstance().gameStart(request.getParams());
            case Friendship -> { response = MainMenuController.getInstance().friendship(request);}
            case ShowFriendshipRequests -> response = updateFriendshipRequests(loggedInUser);
            case AcceptFriendship, RejectFriendship -> updateFriendship(request);
            case UpdateGame -> {
                GameController.getInstance().nextTurn(request.getParams());
            }
        }
        return new Response(response);
    }

    private void registerReaderSocket(String username) {
        LoginAndRegisterController.getInstance().getUser(username).setUpdateSocket(socket);
        working = false;
    }

    public String login(String username , String password){
        if(username.equals(""))return "enter username!";
        User user = LoginAndRegisterController.getInstance().getUser(username);
        if(user == null)return "Username and password didn’t match!";
        if(!user.getPassword().equals(password))return "Username and password didn’t match!";
        loggedInUser = user;
        loggedInUser.setOnline(true);
        DataSaver.getInstance().saveUsers();
        DataSaver.getInstance().loadUsers();
        NetworkController.getInstance().addOnlineUser(user);
        return "user logged in successfully!";
    }

    public String userLogout(){
        String lastSeen = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        LoginAndRegisterController.getInstance().getUser(loggedInUser.getUsername()).setLastSeen(lastSeen);
        LoginAndRegisterController.getInstance().getUser(loggedInUser.getUsername()).setOnline(false);
        DataSaver.getInstance().saveUsers();
        DataSaver.getInstance().loadUsers();
        NetworkController.getInstance().removeOnlineUser(loggedInUser);
        loggedInUser = null;
        return "user logged out successfully!";
    }
    public String updateFriendshipRequests(User user){
        ArrayList<String> names = new ArrayList<>();
        for (Map.Entry<String , ArrayList<String>> entry : NetworkController.getInstance().getFriendshipRequests().entrySet()){
            if(entry.getValue().contains(user.getUsername())){
                System.out.println(entry.getKey());
                names.add(entry.getKey());
            }
        }
        return new Gson().toJson(names);
    }
    public void updateFriendship(Request request){
        if(request.getAction().equals("accept")) {
            LoginAndRegisterController.getInstance().getUser(request.getParams().get(0)).getFriendsName().add(request.getParams().get(1));
            LoginAndRegisterController.getInstance().getUser(request.getParams().get(1)).getFriendsName().add(request.getParams().get(0));
            DataSaver.getInstance().saveUsers();
        }
        NetworkController.getInstance().getFriendshipRequests().get(request.getParams().get(0)).remove(request.getParams().get(1));
    }
}
