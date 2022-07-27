package Controller.GameController;
import Controller.SavingDataController.DataSaver;
import Model.ChatRelated.Alert;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.MapRelated.GameMap;
import Model.NetworkRelated.NetworkController;
import Model.NetworkRelated.Update;
import Model.NetworkRelated.UpdateType;
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
import java.io.IOException;
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

    public int getGameTurn(GameMap gameMap) {
        return gameMap.getGameTurn();
    }

    public void setGameTurn(GameMap gameMap , int gameTurn) {
        gameMap.setGameTurn(gameTurn);
    }

    private Unit selectedUnit;
    private City selectedCity;
    private City selectedCityToAttack;
    private static GameController gameController;
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
    public Civilization getPlayerTurn(GameMap gameMap) {
        return gameMap.getPlayerTurn();
    }
    public void setPlayerTurn(GameMap gameMap ,Civilization playerTurn) {
        gameMap.setPlayerTurn(playerTurn);
    }
    public GameMap getMap(GameMap gameMap){
        return gameMap;
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
    public String nextTurn(ArrayList<String> params) throws IOException {
        String output;
        NetworkController.getInstance().setGame(params.get(1),DataSaver.getInstance().loadGame(params.get(0)));
        GameMap gameMap = NetworkController.getInstance().getGame(params.get(1));
        changePlayer(gameMap);
        if(gameMap.getGameTurn() == 2050) {
            output = "Game Over";
            output = endGameConditions(output,gameMap);
            ArrayList<String> message = new ArrayList<>();
            message.add(output);
            for (Civilization civilization : gameMap.getCivilizations()) {
                NetworkController.getInstance().sendUpdate(new Update(UpdateType.EndGame, message), civilization.getUser());
            }
        }
        UnitController.getInstance().updateAllUnitData(gameMap);
        restoreMovementLefts(gameMap);
        reducingTurnOfTheUnitsAndBuildings(gameMap);
        CivilizationController.getInstance().calculateProducts(getPlayerTurn(gameMap));
        reduceTurnOfImprovements(gameMap);
        reduceTurnOfFeaturesBeingCleared(gameMap);
        reduceTurnOfRoads(gameMap);
        reducingTurnOfTheTechnologies(gameMap);
        selectedUnit = null;
        selectedCity = null;
        // graph init is a heavy method
        updateGame(gameMap);
        output = "next player turn!";
        output = endGameConditions(output,gameMap);
        if (output.startsWith("game is over!")) {
            ArrayList<String> message = new ArrayList<>();
            message.add(output);
            for (Civilization civilization : gameMap.getCivilizations()) {
                NetworkController.getInstance().sendUpdate(new Update(UpdateType.EndGame, message), civilization.getUser());
            }
        }
        return output;
    }

    private String endGameConditions(String nextTurnOutput,GameMap gameMap){
        Civilization winner = gameWinConditionMet(gameMap);
        if(winner != null){
            GameController.getInstance().setWinner(winner);
            return "game is over! the winner is " + winner.getUserName();
        }else if(nextTurnOutput.equals("Game Over")){
            return "game is over! the winner is " + announceWinner(gameMap);
        }
        return "next turn!";
    }

    private Civilization gameWinConditionMet(GameMap gameMap) {
        int eliminatedPlayers = 0;
        Civilization winner = null;
        for (Civilization civ:gameMap.getCivilizations()) {
            if(civ.getCities().size() == 0 &&
                    civ.getSettler() == null)
                eliminatedPlayers++;
            else
                winner = civ;
        }
        System.out.println("eliminated player count : " + eliminatedPlayers);
        if(gameMap.getCivilizations().size() - 1 == eliminatedPlayers && winner != null)
            return winner;
        return null;
    }

    private String announceWinner(GameMap gameMap) {
        Civilization maxGold = null;
        int maxGoldAmount = -1;
        for (Civilization civilization:gameMap.getCivilizations()) {
            if(civilization.getGold() >= maxGoldAmount) {
                maxGoldAmount = civilization.getGold();
                maxGold = civilization;
            }
        }
        assert maxGold != null;
        GameController.getInstance().setWinner(maxGold);
        return maxGold.getUserName();
    }

    public void updateGame(GameMap gameMap){
        String data = DataSaver.getInstance().makeJson(gameMap);
        Update update = new Update(UpdateType.UpdateGame,new ArrayList<>(){{add(data);}});
        for (Civilization civilization:gameMap.getCivilizations()) {
            NetworkController.getInstance().sendUpdate(update,civilization.getUser());
        }
    }

    public void reduceTurnOfFeaturesBeingCleared(GameMap gameMap){
        ArrayList<Feature> Constructions = getPlayerTurn(gameMap).getFeaturesBeingCleared();
        for (int i=0;i<Constructions.size() ; i++) {
            if(Constructions.get(i).getWorker() == null)return;
            Constructions.get(i).changeDaysToClear(-1);
            Constructions.get(i).getWorker().setMovementsLeft(0);
            if(Constructions.get(i).getDaysToClear() == 0){
                if(Constructions.get(i) != null &&Constructions.get(i).getFeatureType().equals(FeatureType.Marsh))Constructions.get(i).getWorker().getTile().setFeature(null);
                if(Constructions.get(i) != null && Constructions.get(i).getFeatureType().equals(FeatureType.Jungle))Constructions.get(i).getWorker().getTile().setFeature(null);
                if(Constructions.get(i) != null && Constructions.get(i).getFeatureType().equals(FeatureType.Forest))Constructions.get(i).getWorker().getTile().setFeature(null);
                Constructions.get(i).setWorker(null);
                getPlayerTurn(gameMap).removeFeaturesBeingCleared(Constructions.get(i));
                i--;
            }
        }
    }

    public void reduceTurnOfImprovements(GameMap gameMap){
        ArrayList<Improvement> Constructions = getPlayerTurn(gameMap).getImprovementsUnderConstruction();
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
                            luxuryAndStrategicRecourses(gameMap , resource);
                            getPlayerTurn(gameMap).addLuxuryResourceCount(resource);
                        }
                    }
                }
                getPlayerTurn(gameMap).removeFromImprovementsUnderConstruction(Constructions.get(i));
                i--;
            }
        }
    }

    public void reduceTurnOfRoads(GameMap gameMap){
        ArrayList<Road> Constructions = getPlayerTurn(gameMap).getRoadsUnderConstruction();
        for (int i=0;i<Constructions.size() ; i++) {
            if(Constructions.get(i).getWorker() == null)return;
            Constructions.get(i).changeDaysToComplete(-1);
            Constructions.get(i).getWorker().setMovementsLeft(0);
            if(Constructions.get(i).getDaysToComplete() == 0){
                Constructions.get(i).setWorker(null);
                getPlayerTurn(gameMap).removeFromRoadsUnderConstruction(Constructions.get(i));
                i--;
                getPlayerTurn(gameMap).changeRoadMaintenance(1);
            }
        }
    }

    public String civilizationOutPut(GameMap gameMap){
        return "Gold : " + getPlayerTurn(gameMap).getGold() + " + " + getPlayerTurn(gameMap).getGoldPerTurn() + "\n" +
                "Science Per Turn : " + getPlayerTurn(gameMap).getSciencePerTurn() + "\n" +
                "Happiness : " + getPlayerTurn(gameMap).getHappiness() + "\n" +
                "Iron : " + getPlayerTurn(gameMap).getTotalIron() + " (used : " + (getPlayerTurn(gameMap).getTotalIron() - getPlayerTurn(gameMap).getCurrentIron()) + ")" + "\n" +
                "Horse : " + getPlayerTurn(gameMap).getTotalHorses() + " (used : " + (getPlayerTurn(gameMap).getTotalHorses() - getPlayerTurn(gameMap).getCurrentHorses()) + ")" + "\n" +
                "Coal : " + getPlayerTurn(gameMap).getTotalCoal() + " (used : " + (getPlayerTurn(gameMap).getTotalCoal() - getPlayerTurn(gameMap).getCurrentCoal()) + ")";
    }

    public void luxuryAndStrategicRecourses(GameMap gameMap,ResourceType resource){
        if(resource.equals(ResourceType.Iron)){
            getPlayerTurn(gameMap).changeTotalIron(2);
            getPlayerTurn(gameMap).changeCurrentIron(2);
        }
        if(resource.equals(ResourceType.Horses)){
            getPlayerTurn(gameMap).changeTotalHorses(2);
            getPlayerTurn(gameMap).changeCurrentHorses(2);
        }
        if(resource.equals(ResourceType.Coal)){
            getPlayerTurn(gameMap).changeTotalCoal(2);
            getPlayerTurn(gameMap).changeCurrentCoal(2);
        }
        if(resource.mainType.equals(ResourceMainTypes.LuxuryResources)){
            boolean alreadyHaveIt = false;
            for (ResourceType founded:getPlayerTurn(gameMap).getFoundedLuxuryRecourses()) {
                if(resource.equals(founded)){
                    alreadyHaveIt = true;
                    break;
                }
            }
            if(!alreadyHaveIt){
                getPlayerTurn(gameMap).addLuxuryRecourse(resource);
                getPlayerTurn(gameMap).changeHappiness(2);
            }
        }
    }

    public void reducingTurnOfTheTechnologies(GameMap gameMap){
        if(getPlayerTurn(gameMap).getCurrentResearchProject() != null){
            int turn = GameController.getInstance().getPlayerTurn(gameMap).getResearchTurns() - GameController.getInstance().getPlayerTurn(gameMap).getSciencePerTurn();
            getPlayerTurn(gameMap).setResearchTurns(turn);
            TechnologyType technologyType = getPlayerTurn(gameMap).getCurrentResearchProject();
            if(turn <= 0){
                Technology technology = new Technology(technologyType);
                getPlayerTurn(gameMap).addTechnology(technology);
                getPlayerTurn(gameMap).setCurrentResearchProject(null);
            }
        }
    }
    public void reducingTurnOfTheUnitsAndBuildings(GameMap gameMap){
        if(getPlayerTurn(gameMap).getCities() != null){
            for(City city : GameController.getInstance().getPlayerTurn(gameMap).getCities()){
                reducingTurnOFUnits(gameMap , city);
                reducingTurnOfTheBuildings(city);
            }
        }
    }

    private void reducingTurnOFUnits(GameMap gameMap , City city){
        if(city.getUnderConstructionUnit() != null && city.getUnitTurn() != 0){
            int turn = city.getUnitTurn() - city.getProductionPerTurn();
            city.setUnitTurn(turn);
        }
        if(city.getUnderConstructionUnit() != null && city.getUnitTurn() <= 0){
            //              city.addUnit(unit);
            Tile tile = city.getCityTiles().get(0);
            UnitController.getInstance().makeUnit(gameMap , city.getUnderConstructionUnit(), GameController.getInstance().getPlayerTurn(gameMap), tile);
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
    private void changePlayer(GameMap gameMap){
        int turnIndex = gameMap.getCivilizations().indexOf(getPlayerTurn(gameMap));
        if(turnIndex == gameMap.getCivilizations().size() - 1){
            gameMap.setGameTurn(gameMap.getGameTurn() + 50);
            turnIndex = 0;}
        else turnIndex++;
        setPlayerTurn(gameMap , gameMap.getCivilizations().get(turnIndex));
    }

    private void restoreMovementLefts(GameMap gameMap){
        UnitController.getInstance().restoreUnitMovementLeft(gameMap);
    }
    public String printMap(GameMap gameMap){
        if(selectedCity != null ){
            System.out.println(selectedCity.getPopulation());
            System.out.println(selectedCity.getGoldPerTurn());
            System.out.println(selectedCity.getFoodPerTurn());
            System.out.println(selectedCity.getProductionPerTurn());
        }
        return gameMap.printMap(gameMap);
    }
    public String initMoveUnit(GameMap gameMap ,Tile tile) {
        return UnitController.getInstance().initMoveUnit(gameMap , tile);
    }
    public String attack(GameMap gameMap ,Tile tile){
        return UnitController.getInstance().combat(gameMap , tile);
    }

    public int getTurn(GameMap gameMap) {
        return gameMap.getTurn(gameMap);
    }
    public String deleteUnit(GameMap gameMap){
        return UnitController.getInstance().removeUnitFromGame(gameMap , selectedUnit);
    }
    public String wake(GameMap gameMap) {
        selectedUnit.setUnitStateType(UnitStateType.NORMAL);
        return "unit is awake";
    }
}
