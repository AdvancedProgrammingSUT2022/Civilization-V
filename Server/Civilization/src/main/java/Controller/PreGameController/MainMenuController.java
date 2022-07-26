package Controller.PreGameController;

import Controller.GameController.MapControllers.MapGenerator;
import Controller.SavingDataController.DataSaver;
import Model.CivlizationRelated.Civilization;
import Model.MapRelated.GameMap;
import Model.NetworkRelated.NetworkController;
import Model.NetworkRelated.Update;
import Model.NetworkRelated.UpdateType;
import Model.User.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Controller.Controller.Controller;

import javax.xml.crypto.Data;

public class MainMenuController extends Controller{
    private static MainMenuController mainMenuController;
    private MainMenuController(){}
    public static MainMenuController getInstance(){
        if(mainMenuController == null)
            mainMenuController = new MainMenuController();
        return mainMenuController;
    }


    public void gameStart(ArrayList<String> players){
        int mapWidth = Integer.parseInt(players.get(0));
        int mapHeight = Integer.parseInt(players.get(1));
        players.remove(0);
        players.remove(0);
        ArrayList<User> users = new ArrayList<>(){{
            for (String username:players) {
                if(LoginAndRegisterController.getInstance().getUser(username) != null)
                    add(LoginAndRegisterController.getInstance().getUser(username));
            }
        }};
        GameMap gameMap = MapGenerator.getInstance().gameInit(users, mapWidth, mapHeight);
        NetworkController.getInstance().addGame(gameMap);
        startGamesForClients(gameMap);
    }

    @Override
    public String showCurrentMenu() {
        return "Main Menu";
    }

    public String enterMenu(Matcher matcher) {
        if(matcher.group("menu").equals("Profile_Menu"))return "done!";
        return "menu navigation is not possible";
    }

    public String sendInvite(String sender,String receiver) {
        User user = LoginAndRegisterController.getInstance().getUser(receiver);
        if(user == null) return "no such user exists";
        if(LoginAndRegisterController.getInstance().getUser(sender).equals(user))return "you can't invite yourself!";
        System.out.println(user + "    " + user.getUsername());
        for (User online:NetworkController.getInstance().getOnlineUsers()) {
            System.out.println(online + "    " + online.getUsername());
        }
        if(!NetworkController.getInstance().getOnlineUsers().contains(user))return "user is offline";
        ArrayList<String> params = new ArrayList<>(){{
            add(sender);
        }};
        NetworkController.getInstance().sendUpdate(new Update(UpdateType.invitation,params),user);
        return "invite sent";
    }


    public void inviteAcceptation(String acceptanceStatus, String sender, String invitee) {
        Update update = new Update(UpdateType.inviteAcceptance,new ArrayList<>(){{
            add(acceptanceStatus);
            add(invitee);
        }});
        NetworkController.getInstance().sendUpdate(update,LoginAndRegisterController.getInstance().getUser(sender));
    }

    public void startGamesForClients(GameMap gameMap){
        String data = DataSaver.getInstance().makeJson(gameMap);
        Update update = new Update(UpdateType.initializeGame,new ArrayList<>(){{add(data);}});
        for (Civilization civilization:gameMap.getCivilizations()) {
            NetworkController.getInstance().sendUpdate(update,civilization.getUser());
        }
    }
}
