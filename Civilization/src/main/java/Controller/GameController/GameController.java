package Controller.GameController;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.MapRelated.GameMap;
import Model.Technology.Technology;
import Model.Technology.TechnologyType;
import Model.TileRelated.Building.Building;
import Model.TileRelated.Building.BuildingType;
import Model.TileRelated.Improvement.Improvement;
import Model.TileRelated.Resource.Resource;
import Model.Units.TypeEnums.UnitType;
import Model.User.User;
import Model.Units.Unit;
import Controller.GameController.MapControllers.MapGenerator;
import java.util.*;
import java.util.regex.Matcher;

public class GameController{
    private int turn = 1;
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
        UnitController.getInstance().updateAllUnitData();
        restoreMovementLefts();
        reducingTurnOfTheUnitsAndBuildings();
        CityController.getInstance().calculateProducts(playerTurn);
        reduceTurnOfImprovements();
        reducingTurnOfTheTechnologies();
        selectedUnit = null;
        selectedCity = null;
        if(playerTurn.equals(this.map.getCivilizations().get(0))) turn++;
        return "next player turn!";
    }

    public void reduceTurnOfImprovements(){
        for (Improvement improvement:playerTurn.getImprovementsUnderConstruction()) {
            improvement.changeDaysToComplete(-1);
            if(improvement.getDaysToComplete() == 0){
                if(improvement.getTile().getResource() != null) {
                    for (Resource resource : improvement.getImprovementType().ImprovesThisResources) {
                        if(improvement.getTile().getResource().equals(resource))resource.setAvailable(true);
                    }
                }
                playerTurn.removeFromImprovementsUnderConstruction(improvement);
            }
        }
    }

    public void reducingTurnOfTheTechnologies(){
        if(getPlayerTurn().getCurrentResearchProject() != null){
            int turn = GameController.getInstance().playerTurn.getResearchTurns() - 1;
            getPlayerTurn().setResearchTurns(turn);
            TechnologyType technologyType = getPlayerTurn().getCurrentResearchProject();
            if(turn <= 0){
                Technology technology = new Technology(technologyType);
                getPlayerTurn().addTechnology(technology);
                getPlayerTurn().setCurrentResearchProject(null);
            }
        }
    }
    public void reducingTurnOfTheUnitsAndBuildings(){
        if(getPlayerTurn().getCities() != null){
            for(City city : GameController.getInstance().getPlayerTurn().getCities()){
                reducingTurnOFUnits(city);
                reducingTurnOfTheBuildings(city);
            }
        }
    }
    private void reducingTurnOFUnits(City city){
        if(city.getUnderConstructionUnit() != null && city.getUnitTurn() != 0){
            int turn = city.getUnitTurn() - 1;
            city.setUnitTurn(turn);
        }
        if(city.getUnderConstructionUnit() != null && city.getUnitTurn() == 0){
            Unit unit = new Unit();
            unit.setUnitType(city.getUnderConstructionUnit());
            city.addUnit(unit);
            GameMap.getInstance().addUnit(unit);
            city.setUnderConstructionUnit(null);
            city.setUnitTurn(0);
        }
    }
    public void reducingTurnOfTheBuildings(City city){
        if(city.getUnderConstructionBuilding() != null && city.getBuildingTurn() != 0){
            int turn = city.getBuildingTurn() - 1;
            city.setBuildingTurn(turn);
        }
        if(city.getUnderConstructionBuilding() != null && city.getBuildingTurn() == 0){
            Building building = new Building(city.getUnderConstructionBuilding());
            city.addBuilding(building);
            GameMap.getInstance().addBuiltBuilding(building);
            city.setUnderConstructionBuilding(null);
            city.setBuildingTurn(0);
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
    public String attack(Matcher matcher){
        return UnitController.getInstance().combat(matcher);
    }

    public int getTurn() {
        return turn;
    }
}
