import Controller.GameController.GameController;

public class Main {

    public static void main(String[] args) {
	// write your code here
        GameController gameController = new GameController();
        gameController.generateMap(2);
    }
}
