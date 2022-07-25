package Controller.GameController;
import Controller.SavingDataController.DataSaver;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.MapRelated.GameMap;
import Model.Technology.Technology;
import Model.Technology.TechnologyType;
import Model.TileRelated.Building.Building;
import Model.TileRelated.Feature.Feature;
import Model.TileRelated.Feature.FeatureType;
import Model.TileRelated.Improvement.Improvement;
import Model.TileRelated.Resource.ResourceMainTypes;
import Model.TileRelated.Resource.ResourceType;
import Model.TileRelated.Tile.Tile;
import Model.TileRelated.Road.Road;
import Model.Units.TypeEnums.UnitStateType;
import Model.User.User;
import Model.Units.Unit;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;

public class GameController{



    private Civilization winner;

    public Civilization getWinner() {
        return winner;
    }

    public void setWinner(Civilization winner) {
        this.winner = winner;
    }

    public int getGameTurn() {
        return GameMap.getInstance().getGameTurn();
    }

    public void setGameTurn(int gameTurn) {
        GameMap.getInstance().setGameTurn(gameTurn);
    }

    private Unit selectedUnit;
    private City selectedCity;
    private City selectedCityToAttack;
    private static GameController gameController; 
    private GameMap map = GameMap.getInstance();
    private GameController(){}
    public City getSelectedCityToAttack() {
        return selectedCityToAttack;
    }
    public void setSelectedCityToAttack(City selectdCityToAttack) {
        this.selectedCityToAttack = selectdCityToAttack;
    }
    public static GameController getInstance(){
        if(gameController == null)
            gameController = new GameController();
        return gameController;
    }
    public Civilization getPlayerTurn() {
        return GameMap.getInstance().getPlayerTurn();
    }
    public void setPlayerTurn(Civilization playerTurn) {
        GameMap.getInstance().setPlayerTurn(playerTurn);
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
    public String nextTurn() {
        changePlayer();
        if(GameMap.getInstance().getGameTurn() == 2050)
            return "Game Over";
        UnitController.getInstance().updateAllUnitData();
        restoreMovementLefts();
        reducingTurnOfTheUnitsAndBuildings();
        CivilizationController.getInstance().calculateProducts(getPlayerTurn());
        reduceTurnOfImprovements();
        reduceTurnOfFeaturesBeingCleared();
        reduceTurnOfRoads();
        reducingTurnOfTheTechnologies();
        selectedUnit = null;
        selectedCity = null;
        // graph init is a heavy method
        GameMap.getInstance().setInitialGraph(Movement.getInstance().graphInit());
        if(getPlayerTurn().equals(this.map.getCivilizations().get(0))) GameMap.getInstance().setTurn(GameMap.getInstance().getTurn() + 1);
        return "next player turn!";
    }


    public void reduceTurnOfFeaturesBeingCleared(){
        ArrayList<Feature> Constructions = getPlayerTurn().getFeaturesBeingCleared();
        for (int i=0;i<Constructions.size() ; i++) {
            if(Constructions.get(i).getWorker() == null)return;
            Constructions.get(i).changeDaysToClear(-1);
            Constructions.get(i).getWorker().setMovementsLeft(0);
            if(Constructions.get(i).getDaysToClear() == 0){
                if(Constructions.get(i) != null &&Constructions.get(i).getFeatureType().equals(FeatureType.Marsh))Constructions.get(i).getWorker().getTile().setFeature(null);
                if(Constructions.get(i) != null && Constructions.get(i).getFeatureType().equals(FeatureType.Jungle))Constructions.get(i).getWorker().getTile().setFeature(null);
                if(Constructions.get(i) != null && Constructions.get(i).getFeatureType().equals(FeatureType.Forest))Constructions.get(i).getWorker().getTile().setFeature(null);
                Constructions.get(i).setWorker(null);
                getPlayerTurn().removeFeaturesBeingCleared(Constructions.get(i));
                i--;
            }
        }
    }

    public void reduceTurnOfImprovements(){
        ArrayList<Improvement> Constructions = getPlayerTurn().getImprovementsUnderConstruction();
        for (int i=0;i<Constructions.size() ; i++) {
            if(Constructions.get(i).getWorker() == null)return;
            Constructions.get(i).changeDaysToComplete(-1);
            Constructions.get(i).getWorker().setMovementsLeft(0);
            if(Constructions.get(i).getDaysToComplete() == 0){
                Constructions.get(i).setWorker(null);
                if(Constructions.get(i).getTile().getFeature() != null &&Constructions.get(i).getTile().getFeature().getFeatureType().equals(FeatureType.Marsh))Constructions.get(i).getTile().setFeature(null);
                if(Constructions.get(i).getTile().getFeature() != null && Constructions.get(i).getTile().getFeature().getFeatureType().equals(FeatureType.Jungle))Constructions.get(i).getTile().setFeature(null);
                if(Constructions.get(i).getTile().getFeature() != null && Constructions.get(i).getTile().getFeature().getFeatureType().equals(FeatureType.Forest))Constructions.get(i).getTile().setFeature(null);
                if(Constructions.get(i).getTile().getResource() != null) {
                    for (ResourceType resource : Constructions.get(i).getImprovementType().ImprovesThisResources) {
                        if(Constructions.get(i).getTile().getResource().getResourceType().equals(resource)){
                            Constructions.get(i).getTile().getResource().setAvailable(true);
                            luxuryAndStrategicRecourses(resource);
                            getPlayerTurn().addLuxuryResourceCount(resource);
                        }
                    }
                }
                getPlayerTurn().removeFromImprovementsUnderConstruction(Constructions.get(i));
                i--;
            }
        }
    }

    public void reduceTurnOfRoads(){
        ArrayList<Road> Constructions = getPlayerTurn().getRoadsUnderConstruction();
        for (int i=0;i<Constructions.size() ; i++) {
            if(Constructions.get(i).getWorker() == null)return;
            Constructions.get(i).changeDaysToComplete(-1);
            Constructions.get(i).getWorker().setMovementsLeft(0);
            if(Constructions.get(i).getDaysToComplete() == 0){
                Constructions.get(i).setWorker(null);
                getPlayerTurn().removeFromRoadsUnderConstruction(Constructions.get(i));
                i--;
                getPlayerTurn().changeRoadMaintenance(1);
            }
        }
    }

    public String civilizationOutPut(){
        return "Gold : " + getPlayerTurn().getGold() + " + " + getPlayerTurn().getGoldPerTurn() + "\n" +
                "Science Per Turn : " + getPlayerTurn().getSciencePerTurn() + "\n" +
                "Happiness : " + getPlayerTurn().getHappiness() + "\n" +
                "Iron : " + getPlayerTurn().getTotalIron() + " (used : " + (getPlayerTurn().getTotalIron() - getPlayerTurn().getCurrentIron()) + ")" + "\n" +
                "Horse : " + getPlayerTurn().getTotalHorses() + " (used : " + (getPlayerTurn().getTotalHorses() - getPlayerTurn().getCurrentHorses()) + ")" + "\n" +
                "Coal : " + getPlayerTurn().getTotalCoal() + " (used : " + (getPlayerTurn().getTotalCoal() - getPlayerTurn().getCurrentCoal()) + ")";
    }

    public void luxuryAndStrategicRecourses(ResourceType resource){
        if(resource.equals(ResourceType.Iron)){
            getPlayerTurn().changeTotalIron(2);
            getPlayerTurn().changeCurrentIron(2);
        }
        if(resource.equals(ResourceType.Horses)){
            getPlayerTurn().changeTotalHorses(2);
            getPlayerTurn().changeCurrentHorses(2);
        }
        if(resource.equals(ResourceType.Coal)){
            getPlayerTurn().changeTotalCoal(2);
            getPlayerTurn().changeCurrentCoal(2);
        }
        if(resource.mainType.equals(ResourceMainTypes.LuxuryResources)){
            boolean alreadyHaveIt = false;
            for (ResourceType founded:getPlayerTurn().getFoundedLuxuryRecourses()) {
                if(resource.equals(founded)){
                    alreadyHaveIt = true;
                    break;
                }
            }
            if(!alreadyHaveIt){
                getPlayerTurn().addLuxuryRecourse(resource);
                getPlayerTurn().changeHappiness(2);
            }
        }
    }

    public void reducingTurnOfTheTechnologies(){
        if(getPlayerTurn().getCurrentResearchProject() != null){
            int turn = GameController.getInstance().getPlayerTurn().getResearchTurns() - GameController.getInstance().getPlayerTurn().getSciencePerTurn();
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
            int turn = city.getUnitTurn() - city.getProductionPerTurn();
            city.setUnitTurn(turn);
        }
        if(city.getUnderConstructionUnit() != null && city.getUnitTurn() <= 0){
            //              city.addUnit(unit);
            Tile tile = city.getCityTiles().get(0);
            UnitController.getInstance().makeUnit(city.getUnderConstructionUnit(), GameController.getInstance().getPlayerTurn(), tile);
            city.setUnderConstructionUnit(null);
            city.setUnitTurn(0);
        }
    }
    public void reducingTurnOfTheBuildings(City city){
        if(city.getUnderConstructionBuilding() != null && city.getBuildingTurn() != 0){
            int turn = city.getBuildingTurn() - city.getProductionPerTurn();
            city.setBuildingTurn(turn);
        }
        if(city.getUnderConstructionBuilding() != null && city.getBuildingTurn() <= 0){
            Building building = new Building(city.getUnderConstructionBuilding());
            city.addBuilding(building);
            city.setUnderConstructionBuilding(null);
            city.setBuildingTurn(0);
        }
    }
    private void changePlayer(){
        int turnIndex = this.map.getCivilizations().indexOf(getPlayerTurn());
        if(turnIndex == this.map.getCivilizations().size() - 1){
            GameMap.getInstance().setGameTurn(GameMap.getInstance().getGameTurn() + 50);
            turnIndex = 0;}
        else turnIndex++;
        setPlayerTurn(this.map.getCivilizations().get(turnIndex));
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
    public String initMoveUnit(Tile tile) {
        return UnitController.getInstance().initMoveUnit(tile);
    }
    public String attack(Tile tile){
        return UnitController.getInstance().combat(tile);
    }

    public int getTurn() {
        return GameMap.getInstance().getTurn();
    }
    public String deleteUnit(){
        return UnitController.getInstance().removeUnitFromGame(selectedUnit);
    }
    public String wake() {
        selectedUnit.setUnitStateType(UnitStateType.NORMAL);
        return "unit is awake";
    }
}
