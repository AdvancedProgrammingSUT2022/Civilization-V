package Controller;

import Controller.PreGameController.LoginMenuController;
import Model.User.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.when;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ExtendWith(MockitoExtension.class)
public class loginMenuControllerTest {
   String regex = "user create (?=.*--username (?<username>\\S+))(?=.*--password (?<password>\\S+))(?=.*--nickname (?<nickname>\\S+))";

   public static LoginMenuController loginMenuController = new LoginMenuController();

   public static User user = new User();

   public static User user1 = new User();

   public static ArrayList<User> users = new ArrayList<>();

   @BeforeEach
   public void BeforeAll(){
      users.add(user);
      users.add(user1);
      user.setUsername("Arash");
      user.setPassword("ziyaei21");
      user.setNickname("Araash");
      user1.setUsername("nima1");
      user1.setPassword("nimo");
      user1.setNickname("enigma");
   }

   @AfterEach
   public void AfterAll(){
      loginMenuController = null;
      user = null;
      user1 = null;
      users = null;
   }

   @Test
   public void UsernameCheckTestOne(){
      String input = "user create --username nima --password nimo --nickname enigma";
      Matcher matcher = Pattern.compile(regex).matcher(input);
      User result = loginMenuController.UsernameCheck(matcher.group("username"));
      Assert.assertNull(result);
   }

   @Test
   public void UsernameCheckTestTwo(){
      String input = "user create --username nima1 --password nimo --nickname enigma";
      Matcher matcher = Pattern.compile(regex).matcher(input);
      User result = loginMenuController.UsernameCheck(matcher.group("username"));
      Assert.assertNotNull(result);
   }

}
