import View.PreGameView.LoginMenuView;
import View.PreGameView.Regex;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        LoginMenuView loginMenuView = new LoginMenuView();
        Regex regex = new Regex();
        loginMenuView.run();
    }
}
