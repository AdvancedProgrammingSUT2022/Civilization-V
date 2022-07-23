package View.GameView.PreGameView;
import Controller.PreGameController.MainMenuController;
import Model.User.User;
import View.GameView.Game;
import View.Menu.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;

public class MainMenuView extends Menu {
    private MainMenuController mainMenuController = MainMenuController.getInstance();

    private final Consumer<Matcher> logout = matcher -> {
        System.out.println(mainMenuController.userLogout());
    };
    private final Consumer<Matcher> showMenu = matcher -> System.out.println(mainMenuController.showCurrentMenu());
    private final Consumer<Matcher> enterMenu = matcher -> {
        String output;
        System.out.println(output = mainMenuController.enterMenu(matcher));
        if(output.equals("done!")){
            ProfileMenuView profileMenuView = new ProfileMenuView();
            profileMenuView.run();
        }
    };
    private final Consumer<Matcher> enterGame = matcher -> {
        String output;
        ArrayList<User> players = new ArrayList<>();
        System.out.println(output = mainMenuController.gameStart(matcher , players));
        if(output.equals("game has started!")){
            Game game = new Game(players);
            game.run();
        }
    };

    @Override
    public void run(){
        while (true){
            String input = Menu.scanner.nextLine();
            if(input.equals("menu exit")){
                break;
            }
            checkCommand(createCommandsMap(),input);
            if(input.equals("user logout")){
                break;
            }
        }
    }

    @Override
    public HashMap<String, Consumer<Matcher>> createCommandsMap(){
        Regex regex = new Regex();
        HashMap<String, Consumer<Matcher>> commandsMap = new HashMap<>();
        commandsMap.put(regex.logout,logout);
        commandsMap.put(regex.showMenu,this.showMenu);
        commandsMap.put(regex.enterMenu,enterMenu);
        commandsMap.put(regex.startGame,this.enterGame);
        return commandsMap;
    }
}