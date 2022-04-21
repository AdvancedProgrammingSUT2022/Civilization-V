package Controller.PreGameController;

import java.util.ArrayList;
import java.util.regex.Matcher;

import Controller.Controller;
import Model.User;

public class LoginMenuController extends Controller{
    private ArrayList<User> users;
    private static User loggedInUser;

    public ArrayList<User> getUsers() {
        return users;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public static void setLoggedInUser(User loggedInUser) {
        LoginMenuController.loggedInUser = loggedInUser;
    }

    public String register(Matcher matcher){
        return "";
    }

    public String login(Matcher matcher){
        return "";
    }

    public String createUser(Matcher matcher){
        return "";
    }

    public void userLogout(){

    }
    public String checkEnterMenuErrors(String menu){
        return "";
    }
}
