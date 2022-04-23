import Controller.GameController.GameController;
import Model.Enums.MapEnum;
import Model.Units.Unit;


public class Main {

    public static void main(String[] args) {
        GameController gameController = new GameController();
        gameController.generateMap(MapEnum.MAPWIDTH.amount , MapEnum.MAPHEIGHT.amount);
        gameController.gameInit(2);
        gameController.playerTurn = gameController.civilizations.get(0);
            for (Unit key:gameController.civilizations.get(0).getUnits()) {
                System.out.println(key.getTile().getY() + "   " + key.getTile().getX());
            }
            for (Unit key:gameController.civilizations.get(1).getUnits()) {
                System.out.println(key.getTile().getY() + "   " + key.getTile().getX());
            }
        System.out.println(gameController.printMap());
        gameController.getTile(1, 1);
    }
}