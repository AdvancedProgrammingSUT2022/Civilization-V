package Controller.GameController;
import Controller.GameController.MapControllers.MapFunctions;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.Enums.MapEnum;
import Model.MapRelated.GameMap;
import Model.Technology.Technology;
import Model.Technology.TechnologyType;
import Model.TileRelated.Building.Building;
import Model.TileRelated.Building.BuildingType;
import Model.Units.TypeEnums.UnitType;
import Model.User.User;
import Model.Units.Unit;
import Controller.GameController.MapControllers.MapGenerator;
import View.GameView.Game;

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
        reducingTurnOfTheUnits();
        CityController.getInstance().calculateProducts();
        reducingTurnOfTheTechnologies();
        return "next player turn!";
    }
    public void reducingTurnOfTheTechnologies(){
        for(Map.Entry<Civilization, Object[]> technologyEntry : GameMap.getInstance().getSearchingTechnologies().entrySet()){
            TechnologyType technologyType = null;
            if((int) technologyEntry.getValue()[1] > 0){
                technologyEntry.getValue()[1]  = (int) technologyEntry.getValue()[1] - 1;
            }  else {
                for (TechnologyType technologyType1 : TechnologyType.values()) {
                    if (technologyEntry.getValue()[0].equals(technologyType1.name())) {
                        technologyType = technologyType1;
                    }
                }
                Technology technology = new Technology(technologyType);
                technologyEntry.getKey().setCurrentStudyingTechnology(null);
                technologyEntry.getKey().addTechnology(technology);
                GameMap.getInstance().getSearchingTechnologies().remove(technologyEntry);
            }
        }
    }
    public void reducingTurnOfTheUnits(){
        for(Map.Entry<City, Object[]> cityEntry : GameMap.getInstance().getUnitsUnderConstruction().entrySet()){
            int remainingMoney;
            if((remainingMoney = (int) cityEntry.getValue()[1] - cityEntry.getKey().getProductionPerTurn()) <= 0) {
                Unit unit = new Unit(cityEntry.getKey().getCivilization(), cityEntry.getKey().getCityTiles().get(0), (UnitType) cityEntry.getValue()[1]);
                cityEntry.getKey().addUnit(unit);
                GameMap.getInstance().addUnit(unit);
                cityEntry.getKey().getCivilization().addUnit(unit);
                GameMap.getInstance().getUnitsUnderConstruction().remove(cityEntry);
            } else{
                cityEntry.getValue()[1] = remainingMoney;
            }
            // TODO set production of cities
            // TODO how many turns left by dividing remaining money to production per turn
        }

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
            // TODO how many turns left by dividing remaining money to production per turn
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
