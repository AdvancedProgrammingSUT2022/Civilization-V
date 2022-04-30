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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;

public class CityController {
    private static CityController cityController;
    private CityController(){};

    public static CityController getInstance() {
        if (cityController == null)
            cityController = new CityController();
        return cityController;
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
        return GameController.getInstance().getSelectedCity().getBuildingTypesCanBeBuilt().toString();
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
                String howMake = matcher.group(matcher.group("howMake"));
                if(howMake.equals("per turns")) {
                    if(GameController.getInstance().getSelectedCity().getProductionPerTurn() < buildingType.getCost()) return "you can not pay for this building";
                    HashMap<City, BuildingType> buildingsAreBuildingHashMap;
                    if((buildingsAreBuildingHashMap = GameMap.getInstance().getBuildingsAreBuilding()) != null) {
                        for (Map.Entry<City, BuildingType> city : buildingsAreBuildingHashMap.entrySet()) {
                            if(Objects.equals(GameController.getInstance().getSelectedCity(), city)) return "your city is building a building";
                        }
                    }
                    // array  of buildings
                    // handle turn
                    return "construction of your new building has begun";
                }
                if(howMake.equals("now")){
                    if(buildingType.getCost() > GameController.getInstance().getSelectedCity().getCivilization().getGold()) return "you can not pay for this building";
                    buildBuilding(buildingType);
                    return "your new building is built";
                }
            }
        }
        return "not a valid building type";
    }

    private void buildBuilding(BuildingType buildingType){
        Building building = new Building(buildingType);
        GameController.getInstance().getSelectedCity().addBuilding(building);
        GameMap.getInstance().addBuiltBuilding(building);
        int newGoldAmount = GameController.getInstance().getSelectedCity().getCivilization().getGold() - buildingType.getCost();
        GameController.getInstance().getSelectedCity().getCivilization().setGold(newGoldAmount);
    }
}
