package View.PreGameView;

import Controller.PreGameController.LoginMenuController;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;

public class LoginMenuViewTest {
    Regex regex = new Regex();
    public  LoginMenuView loginMenuView  = LoginMenuView.getInstance();
    HashMap<String, Consumer<Matcher>> commandsMap = new HashMap<>();
    private final Consumer<Matcher> register = matcher -> System.out.println(LoginMenuController.getInstance().register(matcher));
    private final Consumer<Matcher> login = matcher ->  System.out.println(LoginMenuController.getInstance().login(matcher));
    private final Consumer<Matcher> showMenu = matcher -> System.out.println(LoginMenuController.getInstance().showCurrentMenu());
    private final Consumer<Matcher> enterMenu = matcher -> {
        String output;
        System.out.println(output = LoginMenuController.getInstance().enterMenu(matcher));
        if(output.equals("done!")){
            MainMenuView mainMenuView = new MainMenuView();
            mainMenuView.run();
        }
    };
    @Before
    public void Before(){
        commandsMap.put(regex.register,this.register);
        commandsMap.put(regex.login,this.login);
        commandsMap.put(regex.showMenu,this.showMenu);
        commandsMap.put(regex.enterMenu,this.enterMenu);
    }
    @Test
    void createCommandsMap() {
        Assert.assertEquals(loginMenuView.createCommandsMap(), loginMenuView.createCommandsMap());
    }

    @Test
    void getLoginController() {
        Assert.assertEquals(LoginMenuController.getInstance(),loginMenuView.getLoginController());
    }
    @After
    public void After(){
        commandsMap = null;
    }
}