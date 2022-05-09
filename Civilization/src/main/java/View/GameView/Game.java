package View.GameView;
import Controller.GameController.CityController;
import Controller.GameController.CivilizationController;
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
    private final Consumer<Matcher> selectCity = matcher -> System.out.println(CityController.getInstance().selectCity(matcher));
    private final Consumer<Matcher> showMap = matcher -> System.out.println();
    private final Consumer<Matcher> moveMap = matcher -> System.out.println();
    private final Consumer<Matcher> build = matcher -> System.out.println(CityController.getInstance().chooseBuilding(matcher)); // Arash changes
    private final Consumer<Matcher> showValidBuildings = matcher -> System.out.println(CityController.getInstance().showValidBuildingTypes()); // Arash changes
    private final Consumer<Matcher> nowOrPerTurns = matcher -> System.out.println(CityController.getInstance().buildNowOrPerTurns(matcher)); // Arash changes
    private final Consumer<Matcher> cancelBuilding = matcher -> System.out.println(CityController.getInstance().cancelBuilding());  // Arash changes
    private final Consumer<Matcher> showValidUnits = matcher -> System.out.println(CityController.getInstance().showValidUnits()); // Arash changes build unit
    private final Consumer<Matcher> BuildUnit = matcher -> System.out.println(CityController.getInstance().chooseUnitType(matcher)); // Arash changes build unit
    private final Consumer<Matcher> typeOFPayForUnit = matcher -> System.out.println(CityController.getInstance().buildNowOrPerTurnsForUnit(matcher)); // Arash changes build unit
    private final Consumer<Matcher> cancelBuildUnit = matcher -> System.out.println(CityController.getInstance().cancelBuildingUnit()); // Arash changes build unit
    private final Consumer<Matcher> chooseTechnology = matcher -> System.out.println(CivilizationController.getInstance().chooseTechnologyMenu()); // Arash changes technology
    private final Consumer<Matcher> studyTechnology = matcher -> System.out.println(CivilizationController.getInstance().createTechnologyForStudy(matcher)); // Arash changes technology
    private final Consumer<Matcher> changeResearchProject = matcher -> System.out.println(CivilizationController.getInstance().changeStudyingTechnology(matcher)); // Arash changes technology
    private final Consumer<Matcher> sleep = matcher -> System.out.println();
    private final Consumer<Matcher> alert = matcher -> System.out.println();
    private final Consumer<Matcher> fortify = matcher -> System.out.println();
    private final Consumer<Matcher> fortifyHeal = matcher -> System.out.println();
    private final Consumer<Matcher> garrison = matcher -> System.out.println();
    private final Consumer<Matcher> setup = matcher -> System.out.println();
    private final Consumer<Matcher> foundCity = matcher -> System.out.println(UnitController.getInstance().checkAndBuildCity(matcher));
    private final Consumer<Matcher> attack = matcher -> System.out.println(gameController.attack(matcher));
    private final Consumer<Matcher> cancel = matcher -> System.out.println();
    private final Consumer<Matcher> wake = matcher -> System.out.println();
    private final Consumer<Matcher> delete = matcher -> System.out.println();
    private final Consumer<Matcher> removeObjects = matcher -> System.out.println();
    private final Consumer<Matcher> repair = matcher -> System.out.println();
    private final Consumer<Matcher> buyTile = matcher -> System.out.println(CityController.getInstance().buyTile(matcher));
    private final Consumer<Matcher> assignCitizen = matcher -> System.out.println(CityController.getInstance().assignCitizen(matcher));
    private final Consumer<Matcher> removeCitizen = matcher -> System.out.println(CityController.getInstance().removeCitizen(matcher));
    private final Consumer<Matcher> siegePreAttack = matcher -> System.out.println(UnitController.getInstance().siegePreAttack());

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
        commandsMap.put(regex.selectCity,selectCity);
        commandsMap.put(regex.showMap,showMap);
        commandsMap.put(regex.moveMap,moveMap);
        commandsMap.put(regex.build,build); // Arash changes
        commandsMap.put(regex.showValidBuilding, showValidBuildings); // Arash changes
        commandsMap.put(regex.typeOfPayForBuilding, nowOrPerTurns); // Arash changes
        commandsMap.put(regex.cancelBuilding, cancelBuilding); // Arash changes
        commandsMap.put(regex.showValidUnits, showValidUnits); // Arash changes
        commandsMap.put(regex.buildUnit, BuildUnit); // Arash changes
        commandsMap.put(regex.typeOfPayForUnit, typeOFPayForUnit); // Arash changes
        commandsMap.put(regex.cancelBuildUnit, cancelBuildUnit); // Arash changes
        commandsMap.put(regex.chooseTechnologyMenu, chooseTechnology); // Arash changes
        commandsMap.put(regex.studyTechnology, studyTechnology); // Arash changes
        commandsMap.put(regex.changeResearchProject, changeResearchProject); // Arash changes
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
        commandsMap.put(regex.buyTile,buyTile);
        commandsMap.put(regex.assignCitizen,assignCitizen);
        commandsMap.put(regex.removeCitizen,removeCitizen);
        commandsMap.put(regex.siegePreAttack,siegePreAttack);
        return commandsMap;
    }
}
