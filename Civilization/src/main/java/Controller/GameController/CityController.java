package Controller.GameController;

import Controller.GameController.MapControllers.MapFunctions;
import Controller.GameController.MapControllers.MapGenerator;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.Enums.MapEnum;
import Model.MapRelated.GameMap;
import Model.Technology.Technology;
import Model.TileRelated.Building.Building;
import Model.TileRelated.Building.BuildingType;
import Model.TileRelated.Tile.Tile;
import View.GameView.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;

public class CityController {
    private static CityController cityController;
    private BuildingType selectedBuildingType;
    private CityController(){};

    public static CityController getInstance() {
        if (cityController == null)
            cityController = new CityController();
        return cityController;
    }

    public BuildingType getSelectedBuildingType() {
        return selectedBuildingType;
    }

    public String selectCity(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if(x > MapEnum.MAPWIDTH.amount -1 || y > MapEnum.MAPHEIGHT.amount -1) return "invalid coordinates";
        Civilization civilization;
        if(( civilization = MapFunctions.getInstance().getTile(x, y).getCivilization()) == null) return "this tile does not belong to anyone";
        if(civilization != GameController.getInstance().getPlayerTurn()) return "this tile does not belong to your civilization";
        ArrayList<City> playerCities = GameController.getInstance().getPlayerTurn().getCities();
        if(playerCities == null) return "no cities on your civilization";
        for(City city : playerCities){
            if(Objects.equals(city.getCityTiles().get(0), MapFunctions.getInstance().getTile(x, y))){
                GameController.getInstance().setSelectedCity(city);
                return "city selected";
            }
        }
        return "city not found";
    }

    public String buyTile(Matcher matcher){
        if(GameController.getInstance().getSelectedCity() == null)return "no city is selected";
        Tile tile = MapFunctions.getInstance().getTile(Integer.parseInt(matcher.group("x")),Integer.parseInt(matcher.group("y")));
        if(tile == null)return "no such tile exists";
        return GameController.getInstance().getSelectedCity().buyTile(tile);
    }

    public void calculateProducts(){
        for (Civilization civilization: GameMap.getInstance().getCivilizations()) {
            for (City city:civilization.getCities()) {
                city.calculateProduction();
                city.calculateGold();
                city.calculateFood();
                civilization.changeGold(city.getGoldPerTurn());
                city.populationGrowth();
            }
        }
    }

    public String showValidBuildingTypes() {
        if(GameController.getInstance().getSelectedCity() == null) return "no city is selected";
        BuildingType[] buildingTypes = BuildingType.values();
        for(BuildingType buildingType : buildingTypes){
            if(hasRequiredTechnology(buildingType)){
                GameController.getInstance().getSelectedCity().addCanBeBuiltBuildingType(buildingType);
            }
        }
        removeBuiltBuildings(GameController.getInstance().getSelectedCity().getBuildingTypesCanBeBuilt());
        if(GameController.getInstance().getSelectedCity().getBuildingTypesCanBeBuilt() == null) return "this city can not build building now";
        String returnString  = "your valid building are:" + GameController.getInstance().getSelectedCity().getBuildingTypesCanBeBuilt().toString();
        String returnString1 = "";
        for(Map.Entry<City, Object[]> cityBuildingTypeEntry : GameMap.getInstance().getBuildingsAreBuilding().entrySet()){
            if(cityBuildingTypeEntry.getKey() == GameController.getInstance().getSelectedCity()){
                returnString1 = "\nand your city is building:" + cityBuildingTypeEntry.getValue()[0] + " now.\nso you can not build a building per turns,\nunless you cancel building";
                break;
            }
        }
        return returnString + (returnString1 == "" ? "\nyour city is not build a building right now" : returnString1);
    }

    private void removeBuiltBuildings(ArrayList<BuildingType> validBuildingTypes){
        ArrayList<Building> buildingsInCity;
        if((buildingsInCity = GameController.getInstance().getSelectedCity().getBuildings()) != null){
            for (Building building : buildingsInCity) {
                validBuildingTypes.remove(building.getBuildingType());
            }
        }
    }

    private boolean hasRequiredTechnology(BuildingType buildingType){
        Technology requiredTechnology = buildingType.getTechnologyRequired();
        ArrayList<Technology> validTechnologies;
        if((validTechnologies = GameController.getInstance().getSelectedCity().getCivilization().getTechnologies()) == null) return false;
        for(Technology technology : validTechnologies){
            if(Objects.equals(technology, requiredTechnology)){
                return true;
            }
        }
        return false;
    }

    public String chooseBuilding(Matcher matcher){
        if(GameController.getInstance().getSelectedCity() == null) return "no city is selected";
        String chosenBuildingType = matcher.group("buildingType");
        for(BuildingType buildingType : GameController.getInstance().getSelectedCity().getBuildingTypesCanBeBuilt()){
            if(buildingType.name().equals(chosenBuildingType)){
                this.selectedBuildingType = buildingType;
                return "your building type is selected. build per turns ? or build now ?";
            }
        }
        return "not a valid building type";
    }

    public String buildNowOrPerTurns(Matcher matcher){
        if(GameController.getInstance().getSelectedCity() == null) return "no city is selected";
        for(Map.Entry<City, Object[]> cityBuildingTypeEntry : GameMap.getInstance().getBuildingsAreBuilding().entrySet()){
            if(cityBuildingTypeEntry.getKey() == GameController.getInstance().getSelectedCity()){
                return "\nand your city is building:" + cityBuildingTypeEntry.getValue()[0] + " now.\nso you can not build a building per turns,\nunless you cancel building";
            }
        }
        if(this.selectedBuildingType == null) return "no selected building type";
        String buildNowOrPerTurns = matcher.group("typeOFPay");
        return buildNowOrPerTurns.equals("per turns") ? buildPerTurns() : buildNow();
    }
    private String buildNow(){
        if(GameController.getInstance().getSelectedCity().getCivilization().getGold() < this.selectedBuildingType.getCost()) return "you don not have enough gold";
        buildBuildingNow();
        this.selectedBuildingType = null;
        return "your new building is built";
    }

    private void buildBuildingNow(){
        Building building = new Building(this.selectedBuildingType);
        GameController.getInstance().getSelectedCity().addBuilding(building);
        GameMap.getInstance().addBuiltBuilding(building);
        int newGoldAmount = GameController.getInstance().getSelectedCity().getCivilization().getGold() - this.selectedBuildingType.getCost();
        GameController.getInstance().getSelectedCity().getCivilization().setGold(newGoldAmount);
    }

    private String buildPerTurns(){
        if(GameController.getInstance().getSelectedCity().getProductionPerTurn() <= 0) return "you can not pay for this building";
        int moneyRemaining = this.selectedBuildingType.Cost;
        GameMap.getInstance().addBuildingIsBuilding(GameController.getInstance().getSelectedCity(), this.selectedBuildingType, moneyRemaining);
        this.selectedBuildingType = null;
        return "construction of your new building has begun";
    }

    public String cancelBuilding(){
        if(GameController.getInstance().getSelectedCity() == null) return "no city is selected";
        for(Map.Entry<City, Object[]> cityEntry : GameMap.getInstance().getBuildingsAreBuilding().entrySet()){
            if(cityEntry.getKey() == GameController.getInstance().getSelectedCity()){
                GameMap.getInstance().getBuildingsAreBuilding().remove(cityEntry);
                return "your construction has stopped";
            }
        }
        return "this method is not complete";
    }


}
