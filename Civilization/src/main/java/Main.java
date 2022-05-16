import Controller.SavingDataController.UserDataController;
import View.PreGameView.LoginMenuView;

public class Main {
    public static void main(String[] args){

        LoginMenuView loginMenuView = LoginMenuView.getInstance();
        UserDataController.getInstance().loadUsers();
        loginMenuView.run();
        UserDataController.getInstance().saveUsers();
    }
}
