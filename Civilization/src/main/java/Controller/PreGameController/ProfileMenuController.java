package Controller.PreGameController;

import Controller.SavingDataController.DataSaver;
import Model.User.User;

import java.util.regex.Matcher;

import Controller.Controller.Controller;

public class ProfileMenuController extends Controller{

    private static ProfileMenuController profileMenuController;
    private ProfileMenuController(){}
    public static ProfileMenuController getInstance(){
        if(profileMenuController == null)
            profileMenuController = new ProfileMenuController();
        return profileMenuController;
    }
    public String changeNickname(String newNickname){
        if(newNickname.equals(""))return "field is empty";
        for (User key: LoginAndRegisterController.getInstance().getUsers()) {
            if(key.getNickname().equals(newNickname))
                return "user with nickname "+ key.getNickname() +" already exists";
        }
        LoginAndRegisterController.getInstance().getLoggedInUser().setNickname(newNickname);
        DataSaver.getInstance().saveUsers();
        return "nickname changed successfully!";
    }

    public String changeCurrentPassword(String old,String newPass) {
        if(!old.equals(LoginAndRegisterController.getInstance().getLoggedInUser().getPassword()))return "current password is invalid";
        if(old.equals(newPass))return "please enter a new password";
        LoginAndRegisterController.getInstance().getLoggedInUser().setPassword(newPass);
        DataSaver.getInstance().saveUsers();
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
