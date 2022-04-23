
import Controller.GameController.GameController;
import Model.CivlizationRelated.Civilization;
import Model.Enums.MapEnum;
import Model.TileRelated.Tile.Tile;
import Model.Units.Unit;
import View.PreGameView.LoginMenuView;

import java.util.HashMap;


public class Main {

    public static void main(String[] args) {
        GameController gameController = new GameController();
        gameController.generateMap(MapEnum.MAPWIDTH.amount , MapEnum.MAPHEIGHT.amount);
        gameController.gameInit(2);
        gameController.playerTurn = gameController.civilizations.get(0);
        for (Unit key:gameController.civilizations.get(0).getUnits()) {
            System.out.println(key.getTile().getX() + "   " + key.getTile().getY());
        }
        System.out.println(gameController.printMap());
    }
}
