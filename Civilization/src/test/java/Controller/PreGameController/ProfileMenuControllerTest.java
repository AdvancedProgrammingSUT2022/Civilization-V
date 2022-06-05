package Controller.PreGameController;

import Model.User.User;
import View.GameView.PreGameView.Regex;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileMenuControllerTest {
    public Regex regex = new Regex();

    public User user = new User();

    public  User user1 = new User();

    public  User user2 = new User();

    public ArrayList<User> users = new ArrayList<User>();
    public ArrayList<User> players = new ArrayList<User>();

    public  ProfileMenuController profileMenuController = ProfileMenuController.getInstance();

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
        LoginAndRegisterController.getInstance().setLoggedInUser(user1);
        LoginAndRegisterController.getInstance().setUsers(users);
    }

    @Test
    public void changeNicknameTestOne(){
        Matcher matcher = Pattern.compile(regex.changeNickName).matcher("profile change --nickname man");
        matcher.find();
        Assert.assertEquals("user with nickname man already exists", profileMenuController.changeNickname(matcher));
    }
    @Test
    public void changeNicknameTestTwo(){
        Matcher matcher = Pattern.compile(regex.changeNickName).matcher("profile change --nickname nmd");
        matcher.find();
        Assert.assertEquals("nickname changed successfully!", profileMenuController.changeNickname(matcher));
    }
    @Test
    public void changePasswordTestOne(){
        Matcher matcher = Pattern.compile(regex.changePass).matcher("profile change --password --current asdf --new sadf");
        matcher.find();
        Assert.assertEquals("password changed successfully!", profileMenuController.changeCurrentPassword(matcher));
    }
    @Test
    public void changePasswordTestTwo(){
        Matcher matcher = Pattern.compile(regex.changePass).matcher("profile change --password --current asdf --new asdf");
        matcher.find();
        Assert.assertEquals("please enter a new password", profileMenuController.changeCurrentPassword(matcher));
    }
    @Test
    public void changePasswordTestThree(){
        Matcher matcher = Pattern.compile(regex.changePass).matcher("profile change --password --current asdfasdf --new asdf");
        matcher.find();
        Assert.assertEquals("current password is invalid", profileMenuController.changeCurrentPassword(matcher));
    }
    @Test
    public void showCurrentMenuTest(){
        Assert.assertEquals("Profile Menu", profileMenuController.showCurrentMenu());
    }
    @Test
    public void enterMenuTwo(){
        Matcher matcher = Pattern.compile(regex.enterMenu).matcher("menu enter assd");
        matcher.find();
        Assert.assertEquals("menu navigation is not possible", profileMenuController.enterMenu(matcher));
    }
    @After
    public void After(){
        profileMenuController = null;
        LoginAndRegisterController.getInstance().setLoggedInUser(null);
        LoginAndRegisterController.getInstance().setUsers(null);
        user = null;
        user1 = null;
        user2 = null;
        users = null;
    }
}