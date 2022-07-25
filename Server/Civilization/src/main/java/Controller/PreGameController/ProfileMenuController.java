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
    public String changeNickname(String newNickname,User loggedInUser){
        if(newNickname.equals(""))return "field is empty";
        for (User key: LoginAndRegisterController.getInstance().getUsers()) {
            if(key.getNickname().equals(newNickname))
                return "user with nickname "+ key.getNickname() +" already exists";
        }
        loggedInUser.setNickname(newNickname);
        DataSaver.getInstance().saveUsers();
        return "nickname changed successfully!";
    }
    public void increaseImageIndex(int size,User loggedInUser){
        int index = loggedInUser.getProfPicIndex();
        if(index + 1 == size)index = -1;
        loggedInUser.setProfPicIndex(index + 1);
        DataSaver.getInstance().saveUsers();
    }
    public String changeCurrentPassword(String old,String newPass,User loggedInUser) {
        if(!old.equals(loggedInUser.getPassword()))return "current password is invalid";
        if(old.equals(newPass))return "please enter a new password";
        loggedInUser.setPassword(newPass);
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

    public void decreaseImageIndex(int size,User loggedInUser) {
        int index = loggedInUser.getProfPicIndex();
        if(index - 1 == -1)index = size;
        loggedInUser.setProfPicIndex(index - 1);
        DataSaver.getInstance().saveUsers();
    }
}
