package View.GameView;

import Controller.GameController.GameController;
import Controller.GameController.UnitController;
import Model.User.User;
import View.Menu.Menu;
import View.PreGameView.Regex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;

public class Game extends Menu{
    GameController gameController = GameController.getInstance();

    private final Consumer<Matcher> printMap = matcher -> System.out.println(gameController.printMap());
    private final Consumer<Matcher> nextTurn = matcher -> System.out.println(gameController.nextTurn());
    private final Consumer<Matcher> getPlayerName = matcher -> System.out.println(gameController.getPlayerTurn().getUser().getUsername());
    private final Consumer<Matcher> moveUnit = matcher -> System.out.println(gameController.initMoveUnit(matcher));
    private final Consumer<Matcher> selectUnit = matcher -> System.out.println(UnitController.getInstance().selectUnit(matcher));
    private final Consumer<Matcher> info = matcher -> System.out.println();
//    private final Consumer<Matcher> selectCity = matcher -> System.out.println();
    private final Consumer<Matcher> showMap = matcher -> System.out.println();
    private final Consumer<Matcher> moveMap = matcher -> System.out.println();
    private final Consumer<Matcher> build = matcher -> System.out.println();
    private final Consumer<Matcher> sleep = matcher -> System.out.println();
    private final Consumer<Matcher> alert = matcher -> System.out.println();
    private final Consumer<Matcher> fortify = matcher -> System.out.println();
    private final Consumer<Matcher> fortifyHeal = matcher -> System.out.println();
    private final Consumer<Matcher> garrison = matcher -> System.out.println();
    private final Consumer<Matcher> setup = matcher -> System.out.println();
    private final Consumer<Matcher> attack = matcher -> System.out.println();
    private final Consumer<Matcher> foundCity = matcher -> System.out.println(UnitController.getInstance().checkAndBuildCity());
    private final Consumer<Matcher> cancel = matcher -> System.out.println();
    private final Consumer<Matcher> wake = matcher -> System.out.println();
    private final Consumer<Matcher> delete = matcher -> System.out.println();
    private final Consumer<Matcher> removeObjects = matcher -> System.out.println();
    private final Consumer<Matcher> repair = matcher -> System.out.println();


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
        commandsMap.put(regex.selectUnit,this.selectUnit);
        commandsMap.put(regex.moveUnit,this.moveUnit);
        commandsMap.put(regex.info,info);
//        commandsMap.put(regex.selectCity,selectCity);
        commandsMap.put(regex.showMap,showMap);
        commandsMap.put(regex.moveMap,moveMap);
        commandsMap.put(regex.build,build);
        commandsMap.put(regex.sleep,sleep);
        commandsMap.put(regex.alert,alert);
        commandsMap.put(regex.fortify,fortify);
        commandsMap.put(regex.fortifyHeal,fortifyHeal);
        commandsMap.put(regex.garrison,garrison);
        commandsMap.put(regex.setup,setup);
        commandsMap.put(regex.attack,attack);
        commandsMap.put(regex.foundCity,foundCity);
        commandsMap.put(regex.cancel,cancel);
        commandsMap.put(regex.wake,wake);
        commandsMap.put(regex.delete,delete);
        commandsMap.put(regex.removeObjects,removeObjects);
        commandsMap.put(regex.repair,repair);
        return commandsMap;
    }
}
