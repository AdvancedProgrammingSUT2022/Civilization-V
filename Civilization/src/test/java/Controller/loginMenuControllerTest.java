package Controller;
import Controller.PreGameController.LoginMenuController;
import Model.User.User;
import View.PreGameView.Regex;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ExtendWith(MockitoExtension.class)
public class loginMenuControllerTest {
   public  Regex regex = new Regex();

   public  LoginMenuController loginMenuController = LoginMenuController.getInstance();

   public  User user = new User();

   public  User user1 = new User();

   public  User user2 = new User();

   public  ArrayList<User> users = new ArrayList<User>();

   @Before
   public  void Before(){
      users.add(user);
      users.add(user1);
      user.setUsername("Arash");
      user.setPassword("mio");
      user.setNickname("Araash");
      user1.setUsername("nima2");
      user1.setPassword("nima");
      user1.setNickname("enigma");
      user2.setUsername("man");
      user2.setPassword("man");
      user2.setNickname("man");
      LoginMenuController.getInstance().setUsers(users);
   }

   @Test
   public void UsernameCheckTestOne(){
      String username = "nima";
      Assert.assertNull(loginMenuController.UsernameCheck(username));
   }

   @Test
   public void UsernameCheckTestTwo(){
      String username = "nima1";
      Assert.assertNull(loginMenuController.UsernameCheck(username));
   }


   @Test
   public void showCurrentMenuTest(){
      String expected = "Login menu";
      String result = loginMenuController.showCurrentMenu();
      Assert.assertEquals(expected, result);
   }

   @Test
   public void setLoggedInUserTest(){
      LoginMenuController.getInstance().setLoggedInUser(user);
      Assert.assertEquals(user, LoginMenuController.getInstance().getLoggedInUser());
   }

   @Test
   public void LoginTestOne(){
      String input = "user login --username nima --password nimo";
      Matcher matcher = Pattern.compile(regex.login).matcher(input);
      matcher.find();
      String expected = "Username and password didn’t match!";
      String result = loginMenuController.login(matcher);
      Assert.assertEquals(expected, result);
   }

   @Test
   public void LoginTestTwo(){
      String input = "user login --username nima1 --password nimooooo";
      Matcher matcher = Pattern.compile(regex.login).matcher(input);
      matcher.find();
      String expected = "Username and password didn’t match!";
      String result = loginMenuController.login(matcher);
      Assert.assertEquals(expected, result);
   }

      @Test
   public void LoginTestThree(){
      String input = "user login --username Arash --password mio";
      Matcher matcher = Pattern.compile(regex.login).matcher(input);
      matcher.find();
      String expected = "user logged in successfully!";
      String result = loginMenuController.login(matcher);
      Assert.assertEquals(expected, result);
   }


   @Test
   public void registerTestOne(){
      String input = "user create --username Arash --password hello --nickname Arash";
      Matcher matcher = Pattern.compile(regex.register).matcher(input);
      matcher.find();
      String expected = "user with username Arash already exists";
      String result = loginMenuController.register(matcher);
      Assert.assertEquals(expected, result);
   }

   @Test
   public void registerTestTwo(){
      String input = "user create --username sero --password hello --nickname Araash";
      Matcher matcher = Pattern.compile(regex.register).matcher(input);
      matcher.find();
      String expected = "user with nickname Araash already exists";
      String result = loginMenuController.register(matcher);
      Assert.assertEquals(expected, result);
   }

   @Test
   public void registerTestThree(){
      String input = "user create --username sero --password chaghal --nickname chaghalllll";
      Matcher matcher = Pattern.compile(regex.register).matcher(input);
      matcher.find();
      String expected = "user created successfully";
      String result = loginMenuController.register(matcher);
      Assert.assertEquals(expected, result);
   }

   @Test
   public void setUsersTest(){
      LoginMenuController.getInstance().setUsers(users);
      Assert.assertEquals(LoginMenuController.getInstance().getUsers(), users);
   }
   @Test
   public void enterMenuTestOne() {
      Matcher matcher = Pattern.compile(regex.enterMenu).matcher("menu enter Main_Menu");
      LoginMenuController.getInstance().setLoggedInUser(user);
      matcher.find();
      Assert.assertEquals(LoginMenuController.getInstance().enterMenu(matcher), "done!");
   }
   @Test
   public void enterMenuTestTwo() {
      Matcher matcher = Pattern.compile(regex.enterMenu).matcher("menu enter asdfasd");
      LoginMenuController.getInstance().setLoggedInUser(user);
      matcher.find();
      Assert.assertEquals(LoginMenuController.getInstance().enterMenu(matcher), "menu navigation is not possible");
   }
   @After
   public void After(){
      loginMenuController = null;
      LoginMenuController.getInstance().setLoggedInUser(null);
      LoginMenuController.getInstance().setUsers(null);
      user = null;
      user1 = null;
      user2 = null;
      users = null;
   }
}
