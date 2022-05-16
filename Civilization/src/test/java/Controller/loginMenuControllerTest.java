package Controller;
import Controller.PreGameController.LoginMenuController;
import Model.User.User;
import View.PreGameView.Regex;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ExtendWith(MockitoExtension.class)
public class loginMenuControllerTest {
   public static Regex regex = new Regex();

   public static LoginMenuController loginMenuController = new LoginMenuController();

   public static User user = new User();

   public static User user1 = new User();

   public static ArrayList<User> users = new ArrayList<User>();

   @BeforeEach
   public void BeforeEach(){
      users.add(user);
      users.add(user1);
      user.setUsername("Arash");
      user.setPassword("mio");
      user.setNickname("Araash");
      user1.setUsername("nima1");
      user1.setPassword("nimo");
      user1.setNickname("enigma");
   }

   @AfterEach
   public void AfterEach(){
      loginMenuController = null;
      user = null;
      user1 = null;
      users = null;
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
      LoginMenuController.setLoggedInUser(user);
      Assert.assertEquals(user, LoginMenuController.getLoggedInUser());
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

}
