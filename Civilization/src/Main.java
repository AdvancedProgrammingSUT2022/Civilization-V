import Controller.GameController.GameController;
import Model.Enums.MapEnum;
<<<<<<< HEAD
import Model.TileRelated.Tile.Tile;
import Model.Units.Unit;
import View.PreGameView.LoginMenuView;

import java.util.HashMap;
=======
import Model.Units.Unit;
>>>>>>> f7d80bdd35fb3dc30136a8a6865eb6d3c3a8362a


public class Main {

    public static void main(String[] args) {
        GameController gameController = new GameController();
        gameController.generateMap(MapEnum.MAPWIDTH.amount , MapEnum.MAPHEIGHT.amount);
        gameController.gameInit(2);
        gameController.playerTurn = gameController.civilizations.get(0);
<<<<<<< HEAD
        for (Unit key:gameController.civilizations.get(0).getUnits()) {
            System.out.println(key.getTile().getX() + "   " + key.getTile().getY());
        }
=======
            for (Unit key:gameController.civilizations.get(0).getUnits()) {
                System.out.println(key.getTile().getY() + "   " + key.getTile().getX());
            }
            for (Unit key:gameController.civilizations.get(1).getUnits()) {
                System.out.println(key.getTile().getY() + "   " + key.getTile().getX());
            }
>>>>>>> f7d80bdd35fb3dc30136a8a6865eb6d3c3a8362a
        System.out.println(gameController.printMap());
    }
}