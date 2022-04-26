package Controller.PreGameController;

import java.util.ArrayList;
import java.util.regex.Matcher;

import Controller.Controller.Controller;
import Model.User.User;

public class LoginMenuController extends Controller{
    private static ArrayList<User> users = new ArrayList<>();
    private static User loggedInUser;

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        LoginMenuController.loggedInUser = loggedInUser;
    }

    public String register(Matcher matcher){
        if(UsernameCheck(matcher.group("username")) == null) {
            for (User key:users) {
                if(key.getNickname().equals(matcher.group("nickname")))
                    return "user with nickname " + key.getNickname() +" already exists";
            }
            User user = new User();
            user.setNickname(matcher.group("nickname"));
            user.setUsername(matcher.group("username"));
            user.setPassword(matcher.group("password"));
            users.add(user);
            return "user created successfully";
        }
        else return "user with username " + matcher.group("username") + " already exists";
    }

    public User UsernameCheck(String username){
        for (User key:users) {
            if(key.getUsername().equals(username))
                return key;
        }
        return null;
    }

    public String login(Matcher matcher){
        User user = UsernameCheck(matcher.group("username"));
        if(user == null)return "Username and password didn’t match!";
        if(!user.getPassword().equals(matcher.group("password")))return "Username and password didn’t match!";
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

    public String checkEnterMenuErrors(String menu){
        return "";
    }
}
