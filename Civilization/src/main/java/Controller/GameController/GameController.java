package Controller.GameController;
import Controller.GameController.MapControllers.MapFunctions;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.Enums.MapEnum;
import Model.MapRelated.GameMap;
import Model.TileRelated.Building.Building;
import Model.TileRelated.Building.BuildingType;
import Model.User.User;
import Model.Units.Unit;
import Controller.GameController.MapControllers.MapGenerator;
import java.util.*;
import java.util.regex.Matcher;

public class GameController{
    private Civilization playerTurn;
    private Unit selectedUnit;
    private City selectedCity;
    private static GameController gameController; 
    private GameMap map = GameMap.getInstance();
    private GameController(){}
    public static GameController getInstance(){
        if(gameController == null)
            gameController = new GameController();
        return gameController;
    }
    public Civilization getPlayerTurn() {
        return playerTurn;
    }
    public void setPlayerTurn(Civilization playerTurn) {
        this.playerTurn = playerTurn;
    }
    public GameMap getMap(){
        return this.map;
    }
    public Unit getSelectedUnit(){
        return selectedUnit;
    }
    public void setSelectedUnit(Unit selectedUnit){
        this.selectedUnit = selectedUnit;
    }
    public City getSelectedCity() {
        return selectedCity;
    }

    public void setSelectedCity(City selectedCity) {
        this.selectedCity = selectedCity;
    }
    public String nextTurn(){
        changePlayer();
        restoreMovementLefts();
        reducingTurnOfTheBuildings();
        CityController.getInstance().calculateProducts();
        return "next player turn!";
    }

    public void reducingTurnOfTheBuildings(){
        for(Map.Entry<City, Object[]> cityEntry : GameMap.getInstance().getBuildingsAreBuilding().entrySet()) {
            int remainingMoney;
            if((remainingMoney = (int) cityEntry.getValue()[1] - cityEntry.getKey().getProductionPerTurn()) <= 0){
                Building building = new Building((BuildingType) cityEntry.getValue()[0]);
                cityEntry.getKey().addBuilding(building);
                GameMap.getInstance().addBuiltBuilding(building);
                GameMap.getInstance().getBuildingsAreBuilding().remove(cityEntry);
            } else{
                cityEntry.getValue()[1] = remainingMoney;
            }
            // TODO set production of cities
        }
    }
    private void changePlayer(){
        int turnIndex = this.map.getCivilizations().indexOf(playerTurn);
        if(turnIndex == this.map.getCivilizations().size() - 1){turnIndex = 0;}
        else turnIndex++;
        playerTurn = this.map.getCivilizations().get(turnIndex);
    }

    private void restoreMovementLefts(){
        UnitController.getInstance().restoreUnitMovementLeft();
    }
    public String printMap(){
        if(selectedCity != null ){
            System.out.println(selectedCity.getPopulation());
            System.out.println(selectedCity.getGoldPerTurn());
            System.out.println(selectedCity.getFoodPerTurn());
            System.out.println(selectedCity.getProductionPerTurn());
        }
        return this.map.printMap();
    }
    public void gameInit(ArrayList<User> players) {
        MapGenerator.getInstance().gameInit(players);
    }
    public String initMoveUnit(Matcher matcher) {
        return UnitController.getInstance().initMoveUnit(matcher); 
    }

}
