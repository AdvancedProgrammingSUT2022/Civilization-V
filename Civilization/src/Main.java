
import Controller.GameController.GameController;
import Model.CivlizationRelated.Civilization;
import Model.Enums.MapEnum;
import Model.TileRelated.Tile.Tile;
import View.PreGameView.LoginMenuView;

import java.util.HashMap;


public class Main {

    public static void main(String[] args) {
        GameController gameController = new GameController();
//        gameController.generateRandomMap(0, 0);
//        gameController.playerTurn = new Civilization();
//        gameController.test();
//        System.out.println(gameController.printMap());
        //LoginMenuView loginMenuView = new LoginMenuView();
        //loginMenuView.run();
        gameController.generateMap(MapEnum.MAPWIDTH.amount , MapEnum.MAPHEIGHT.amount);
        gameController.gameInit(2);
        gameController.playerTurn = gameController.civilizations.get(0);
        System.out.println(gameController.printMap());
    }
}
