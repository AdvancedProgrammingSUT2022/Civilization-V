package View.PreGameView;
import Controller.PreGameController.LoginMenuController;
import View.Menu.Menu;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;

public class LoginMenuView extends Menu{
    private LoginMenuController loginController = LoginMenuController.getInstance();

    private final Consumer<Matcher> register = matcher -> System.out.println(loginController.register(matcher));
    private final Consumer<Matcher> login = matcher ->  System.out.println(loginController.login(matcher));
    private final Consumer<Matcher> showMenu = matcher -> System.out.println(loginController.showCurrentMenu());
    private final Consumer<Matcher> enterMenu = matcher -> {
        String output;
        System.out.println(output = loginController.enterMenu(matcher));
        if(output.equals("done!")){
            MainMenuView mainMenuView = new MainMenuView();
            mainMenuView.run();
        }
    };

    @Override
    public HashMap <String, Consumer<Matcher>> createCommandsMap(){
        Regex regex = new Regex();
        HashMap<String, Consumer<Matcher>> commandsMap = new HashMap<>();
        commandsMap.put(regex.register,this.register);
        commandsMap.put(regex.login,this.login);
        commandsMap.put(regex.showMenu,this.showMenu);
        commandsMap.put(regex.enterMenu,enterMenu);
        return commandsMap;
    }

    public LoginMenuController getLoginController() {
        return loginController;
    }
}
