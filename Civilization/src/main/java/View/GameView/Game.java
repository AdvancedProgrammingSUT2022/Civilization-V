package View.GameView;

import Controller.GameController.GameController;
import Model.User;
import View.Menu;
import View.PreGameView.Regex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;

public class Game extends Menu{
    GameController gameController = new GameController();

    private final Consumer<Matcher> printMap = matcher -> System.out.println(gameController.printMap());
    private final Consumer<Matcher> nextTurn = matcher -> System.out.println(gameController.nextTurn());
    private final Consumer<Matcher> getPlayerName = matcher -> System.out.println(gameController.getPlayerTurn().getUser().getUsername());
    //private final Consumer<Matcher> moveUnits = matche;

    public Game(ArrayList<User> players){
        gameController.gameInit(players);
    }

    @Override
    public HashMap<String, Consumer<Matcher>> createCommandsMap(){
        Regex regex = new Regex();
        HashMap<String, Consumer<Matcher>> commandsMap = new HashMap<>();
        commandsMap.put(regex.printMap,this.printMap);
        commandsMap.put(regex.nextTurn,this.nextTurn);
        commandsMap.put(regex.getPlayerName,this.getPlayerName);
        return commandsMap;
    }
}
