package Controller.PreGameController;

import java.util.ArrayList;
import java.util.regex.Matcher;

import Controller.Controller.Controller;
import Controller.SavingDataController.DataSaver;
import Model.User.User;

public class LoginAndRegisterController extends Controller{
    private static LoginAndRegisterController loginAndRegisterController;
    private LoginAndRegisterController(){}
    public static LoginAndRegisterController getInstance(){
        if(loginAndRegisterController == null)
            loginAndRegisterController = new LoginAndRegisterController();
        return loginAndRegisterController;
    }
    private ArrayList<User> users = new ArrayList<>();
    private  User loggedInUser;

    public User getUser(String username){
        for (User key:users) {
            if(key.getUsername().equals(username))
                return key;
        }
        return null;
    }
    public ArrayList<User> getUsers() {
        return users;
    }
    public void setUsers(ArrayList<User> createdUsers){
        users = createdUsers;
    }
    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public String register(String username , String password , String nickname){
        if(usernameCheck(username) == null) {
            for (User key:users) {
                if(key.getNickname().equals(nickname))
                    return "user with nickname " + key.getNickname() +" already exists";
            }
            User user = new User();
            user.setNickname(nickname);
            user.setUsername(username);
            user.setPassword(password);
            users.add(user);
            DataSaver.getInstance().saveUsers();
            return "user created successfully";
        }
        else return "user with username " + username + " already exists";
    }

    public User usernameCheck(String username){
        for (User key:users) {
            if(key.getUsername().equals(username))
                return key;
        }
        return null;
    }

    public String login(String username , String password){
        if(username.equals(""))return "enter username!";
        User user = usernameCheck(username);
        if(user == null)return "Username and password didn’t match!";
        if(!user.getPassword().equals(password))return "Username and password didn’t match!";
        setLoggedInUser(user);
        return "user logged in successfully!";
    }

    @Override
    public String showCurrentMenu() {
        return "Login menu";
    }
    @Override
    public String enterMenu(Matcher matcher) {
        if(loggedInUser == null)return "please login first";
        if(matcher.group("menu").equals("Main_Menu"))return "done!";
        return "menu navigation is not possible";
    }
}
