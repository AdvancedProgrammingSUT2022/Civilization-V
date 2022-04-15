package Controller;

import Model.User;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class LoginController extends Controller{
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
        LoginController.loggedInUser = loggedInUser;
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
