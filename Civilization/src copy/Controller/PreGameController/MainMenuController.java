package Controller.PreGameController;

import Controller.Controller;

import java.util.regex.Matcher;

public class MainMenuController extends Controller{

    public String userLogout(){
        LoginMenuController.setLoggedInUser(null);
        return "user logged out successfully!";
    }

    @Override
    public String showCurrentMenu() {
        return "Main Menu";
    }

    public String enterMenu(Matcher matcher) {
        if(matcher.group("menu").equals("Profile_Menu"))return "done!";
        return "menu navigation is not possible";
    }

    public String checkEnterMenuErrors(String menu){
        return "";
    }

}
