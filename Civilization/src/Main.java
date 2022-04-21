import java.util.Map;
import Controller.GameController.GameController;
import Model.Enums.MapEnum;
import Model.TileRelated.Tile.Tile;

public class Main {

    public static void main(String[] args) {
	    GameController gameController = new GameController();
        gameController.generateRandomMap(0, 0);
        System.out.println(gameController.printMap());
    }
}
