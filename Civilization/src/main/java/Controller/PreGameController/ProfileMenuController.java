package Controller.PreGameController;

import Controller.SavingDataController.UserDataController;
import Model.User.User;

import java.util.regex.Matcher;

import Controller.Controller.Controller;

public class ProfileMenuController extends Controller{
    public String changeNickname(Matcher matcher){
        for (User key:LoginMenuController.getUsers()) {
            if(key.getNickname().equals(matcher.group("nickname")))
                return "user with nickname "+ key.getNickname() +" already exists";
        }
        LoginMenuController.getLoggedInUser().setNickname(matcher.group("nickname"));
        UserDataController.getInstance().saveUsers();
        return "nickname changed successfully!";
    }
    public String changeCurrentPassword(Matcher matcher) {
        if(!matcher.group("current").equals(LoginMenuController.getLoggedInUser().getPassword()))return "current password is invalid";
        if(matcher.group("current").equals(matcher.group("new")))return "please enter a new password";
        LoginMenuController.getLoggedInUser().setPassword(matcher.group("new"));
        UserDataController.getInstance().saveUsers();
        return "password changed successfully!";
    }

    @Override
    public String showCurrentMenu() {
        return "Profile Menu";
    }

    @Override
    public String enterMenu(Matcher matcher) {
        return "menu navigation is not possible";
    }
}
