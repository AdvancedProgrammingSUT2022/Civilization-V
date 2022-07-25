package Controller.PreGameController;

import Model.Enums.Menus;
import Model.NetworkRelated.Request;
import Model.NetworkRelated.RequestType;
import Model.NetworkRelated.Update;
import Model.User.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Controller.Controller.Controller;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;

public class MainMenuController extends Controller{
    private static MainMenuController mainMenuController;
    private ArrayList<String> players = new ArrayList<>();
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

    public void addPlayer(String username){
        for (String string:players) {
            if(string.equals(username))return;
        }
        players.add(username);
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    //    public String gameStart(Matcher matcher , ArrayList<User> players){
//        String regex = "--player(?<num>\\d+) (?<user>\\S+)";
//        HashMap<Integer,String> numbers = new HashMap<>();
//        Matcher matcher2 = Pattern.compile(regex).matcher(matcher.group("playerData"));
//        while (matcher2.find()){
//            numbers.put(Integer.parseInt(matcher2.group("num")) , matcher2.group("user"));
//        }
//        if(numbers.size() <= 1)return "not a valid command!";
//
//        for (int t = 1; t <= numbers.size() ; t++) {
//            String username;
//            if((username = numbers.get(t)) == null)return "player numbers are not valid";
//            boolean playerExists = false;
//            for (User key: LoginAndRegisterController.getInstance().getUsers()) {
//                if(key.getUsername().equals(username)){
//                    playerExists = true;
//                    for (User key2:players) {
//                        if(key2.getUsername().equals(username))return "repetitive username";
//                    }
//                    players.add(key);
//                }
//            }
//            if(!playerExists)return "not a valid username";
//        }
//         return "game has started!";
//    }

    @Override
    public String showCurrentMenu() {
        return "Main Menu";
    }

    public String enterMenu(Matcher matcher) {
        if(matcher.group("menu").equals("Profile_Menu"))return "done!";
        return "menu navigation is not possible";
    }

    public void handleInvitation(Update update) {
        Popup popup = new Popup();
        popup.requestFocus();
        Label label = new Label(update.getParams().get(1) + " has " + update.getParams().get(0) + " your invitation");
        label.setTextFill(Color.rgb(180,0,0,1));
        label.setMinHeight(200);
        label.setMinWidth(600);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setStyle("-fx-font-size: 30; -fx-font-family: 'Tw Cen MT'; -fx-font-weight: bold;" +
                "-fx-background-color: rgba(255,255,255,0.34); -fx-background-radius: 5; -fx-alignment: center;" +
                "-fx-border-color: cyan; -fx-border-width: 4.5; -fx-border-radius: 5;");
        popup.getContent().add(label);
        Platform.runLater(()->{popup.show(main.java.Main.scene.getWindow());});
        popup.setAutoHide(true);

        if(update.getParams().get(0).equals("accepted")){
            addPlayer(update.getParams().get(1));
        }
    }
}
