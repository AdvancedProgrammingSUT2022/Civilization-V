package Controller.PreGameController;

import Model.User.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Controller.Controller.Controller;

public class MainMenuController extends Controller{
    private static MainMenuController mainMenuController;
    private MainMenuController(){}
    public static MainMenuController getInstance(){
        if(mainMenuController == null)
            mainMenuController = new MainMenuController();
        return mainMenuController;
    }
    public String userLogout(){
        LoginAndRegisterController.getInstance().setLoggedInUser(null);
        return "user logged out successfully!";
    }


    public String gameStart(Matcher matcher , ArrayList<User> players){
        String regex = "--player(?<num>\\d+) (?<user>\\S+)";
        HashMap<Integer,String> numbers = new HashMap<>();
        Matcher matcher2 = Pattern.compile(regex).matcher(matcher.group("playerData"));
        while (matcher2.find()){
            numbers.put(Integer.parseInt(matcher2.group("num")) , matcher2.group("user"));
        }
        if(numbers.size() <= 1)return "not a valid command!";

        for (int t = 1; t <= numbers.size() ; t++) {
            String username;
            if((username = numbers.get(t)) == null)return "player numbers are not valid";
            boolean playerExists = false;
            for (User key: LoginAndRegisterController.getInstance().getUsers()) {
                if(key.getUsername().equals(username)){
                    playerExists = true;
                    for (User key2:players) {
                        if(key2.getUsername().equals(username))return "repetitive username";
                    }
                    players.add(key);
                }
            }
            if(!playerExists)return "not a valid username";
        }
         return "game has started!";
    }

    @Override
    public String showCurrentMenu() {
        return "Main Menu";
    }

    public String enterMenu(Matcher matcher) {
        if(matcher.group("menu").equals("Profile_Menu"))return "done!";
        return "menu navigation is not possible";
    }

}
