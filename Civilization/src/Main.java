
import Controller.GameController.GameController;
import Model.TileRelated.Tile.Tile;
import View.PreGameView.LoginMenuView;

import java.util.HashMap;


public class Main {

    public static void main(String[] args) {
        GameController gameController = new GameController();
        LoginMenuView loginMenuView = new LoginMenuView();
        loginMenuView.run();
    }
}
