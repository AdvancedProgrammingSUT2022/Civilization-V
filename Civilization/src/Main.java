import Controller.GameController.GameController;

public class Main {

    public static void main(String[] args) {
	    GameController gameController = new GameController();
        gameController.generateRandomMap(0, 0);
        System.out.println(gameController.printMap());
    }
}
