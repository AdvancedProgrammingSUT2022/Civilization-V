package View.PreGameView;
import Controller.PreGameController.ProfileMenuController;
import View.Menu.Menu;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;

public class ProfileMenuView extends Menu{
    private ProfileMenuController profileMenuController = ProfileMenuController.getInstance();

    private final Consumer<Matcher> showMenu = matcher -> System.out.println(profileMenuController.showCurrentMenu());
    private final Consumer<Matcher> enterMenu = matcher -> System.out.println(profileMenuController.enterMenu(matcher));
    private final Consumer<Matcher> changeNickName = matcher -> System.out.println(profileMenuController.changeNickname(matcher));
    private final Consumer<Matcher> changePassword = matcher -> System.out.println(profileMenuController.changeCurrentPassword(matcher));

    @Override
    public HashMap<String, Consumer<Matcher>> createCommandsMap(){
        Regex regex = new Regex();
        HashMap<String, Consumer<Matcher>> commandsMap = new HashMap<>();
        commandsMap.put(regex.showMenu,this.showMenu);
        commandsMap.put(regex.enterMenu,enterMenu);
        commandsMap.put(regex.changeNickName,changeNickName);
        commandsMap.put(regex.changeNickName2,changeNickName);
        commandsMap.put(regex.changePass,changePassword);
        commandsMap.put(regex.changePass2,changePassword);
        return commandsMap;
    }
}
