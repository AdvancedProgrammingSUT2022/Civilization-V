package View.PreGameView;

import Controller.PreGameController.LoginMenuController;
import Controller.PreGameController.MainMenuController;
import View.Menu;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;

public class MainMenuView extends Menu {
    private MainMenuController mainMenuController = new MainMenuController();

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
        return commandsMap;
    }
}