package Controller.PreGameController;

import Model.User.User;
import View.GameView.PreGameView.Regex;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class MainMenuControllerTest {
    public Regex regex = new Regex();

    public User user = new User();

    public  User user1 = new User();

    public  User user2 = new User();

    public ArrayList<User> users = new ArrayList<User>();
    public ArrayList<User> players = new ArrayList<User>();

    public  MainMenuController mainMenuController = MainMenuController.getInstance();

    @Before
    public void init(){
        users.add(user);
        users.add(user1);
        users.add(user2);
        user.setUsername("nima");
        user.setPassword("mio");
        user.setNickname("Araash");
        user1.setUsername("sero");
        user1.setPassword("asdf");
        user1.setNickname("enigma");
        user2.setUsername("arash");
        user2.setPassword("man");
        user2.setNickname("man");
        LoginAndRegisterController.getInstance().setUsers(users);
    }

    @Test
    public void logoutTest(){
        assertEquals("user logged out successfully!", mainMenuController.userLogout());
    }
    @Test
    public void gameStartTestOne(){
        Matcher matcher = Pattern.compile(regex.startGame).matcher("play game --player1 nima");
        matcher.find();
        Assert.assertEquals("not a valid command!", mainMenuController.gameStart(matcher,players));
    }
    @Test
    public void gameStartTestTwo(){
        Matcher matcher = Pattern.compile(regex.startGame).matcher("play game --player1 nima --player3 sero");
        matcher.find();
        Assert.assertEquals("player numbers are not valid", mainMenuController.gameStart(matcher,players));
    }
    @Test
    public void gameStartTestThree(){
        Matcher matcher = Pattern.compile(regex.startGame).matcher("play game --player1 nima --player2 nima");
        matcher.find();
        Assert.assertEquals("repetitive username", mainMenuController.gameStart(matcher,players));
    }
    @Test
    public void gameStartTestFour(){
        Matcher matcher = Pattern.compile(regex.startGame).matcher("play game --player1 nima --player2 sero --player3 alskjdaj;");
        matcher.find();
        Assert.assertEquals("not a valid username", mainMenuController.gameStart(matcher,players));
    }
    @Test
    public void gameStartTestFive(){
        Matcher matcher = Pattern.compile(regex.startGame).matcher("play game --player1 nima --player2 sero");
        matcher.find();
        Assert.assertEquals("game has started!", mainMenuController.gameStart(matcher,players));
    }
    @Test
    public void showCurrentMenuTest(){
        Assert.assertEquals("Main Menu", mainMenuController.showCurrentMenu());
    }
    @Test
    public void enterMenu(){
        Matcher matcher = Pattern.compile(regex.enterMenu).matcher("menu enter Profile_Menu");
        matcher.find();
        Assert.assertEquals("done!", mainMenuController.enterMenu(matcher));
    }
    @Test
    public void enterMenuTwo(){
        Matcher matcher = Pattern.compile(regex.enterMenu).matcher("menu enter adfdadf");
        matcher.find();
        Assert.assertEquals("menu navigation is not possible", mainMenuController.enterMenu(matcher));
    }
    @After
    public void After(){
        mainMenuController = null;
        user = null;
        user1 = null;
        user2 = null;
        users = null;
    }
}