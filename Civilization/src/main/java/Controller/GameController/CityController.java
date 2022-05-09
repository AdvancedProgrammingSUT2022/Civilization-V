package Controller.GameController;

import Controller.GameController.MapControllers.MapFunctions;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.Enums.MapEnum;
import Model.MapRelated.GameMap;
import Model.Technology.Technology;
import Model.TileRelated.Building.Building;
import Model.TileRelated.Building.BuildingType;
import Model.TileRelated.Resource.Resource;
import Model.TileRelated.Tile.Tile;
import Model.Units.TypeEnums.UnitType;
import Model.Units.Unit;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;

public class CityController {
    private static CityController cityController;
    private BuildingType selectedBuildingType;
    private UnitType selectedUnitType;
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
                return "city selected :" + cityOutput();

            }
        }
        return "city not found";
    }

    private String cityOutput(){
        City city;
        if((city = GameController.getInstance().getSelectedCity()) == null) return "no city is selected";
        StringBuilder output = new StringBuilder();
        output.append("\n");
        output.append("Food : ").append((city.getFoodPerTurn() > 0 ? "+" : "") + city.getFoodPerTurn() + "\n");
        output.append("Gold : ").append((city.getGoldPerTurn() > 0 ? "+" : "") + city.getGoldPerTurn() + "\n");
        output.append("Production : ").append((city.getProductionPerTurn() > 0 ? "+" : "" ) + city.getProductionPerTurn() + "\n");
        output.append("Science : ").append((GameController.getInstance().getPlayerTurn().getSciencePerTurn() > 0 ? "+" : "") + GameController.getInstance().getPlayerTurn().getSciencePerTurn() + "\n");
        output.append("population growth turns : ").append(/*population growth turns*/"\n");
        output.append("turns until city's border increases : ");
        return output.toString();
    }

    public String buyTile(Matcher matcher){
        if(GameController.getInstance().getSelectedCity() == null)return "no city is selected";
        Tile tile = MapFunctions.getInstance().getTile(Integer.parseInt(matcher.group("x")),Integer.parseInt(matcher.group("y")));
        if(tile == null)return "no such tile exists";
        return GameController.getInstance().getSelectedCity().buyTile(tile);
    }

    public void calculateProducts(){
        for (Civilization civilization: GameMap.getInstance().getCivilizations()) {
            civilization.setGoldPerTurn(0);
            civilization.setSciencePerTurn(0);
            for (City city:civilization.getCities()) {
                city.calculateProduction();
                city.calculateSciencePerTurn();
                city.calculateGold();
                city.calculateFood();
                city.populationGrowth();
                city.calculateBuildingBonuses();
                civilization.changeSciencePerTurn(city.getSciencePerTurn());
                civilization.changeGold(city.getGoldPerTurn());
                civilization.changeGold(city.getGoldPerTurn());
            }
        }
    }

    public String showValidBuildingTypes() {
        if(GameController.getInstance().getSelectedCity() == null) return "no city is selected";
        BuildingType[] buildingTypes = BuildingType.values();
        for(BuildingType buildingType : buildingTypes){
            if(hasRequiredTechnologyForBuilding(buildingType)){
                GameController.getInstance().getSelectedCity().addCanBeBuiltBuildingType(buildingType);
            }
        }
        removeBuiltBuildings(GameController.getInstance().getSelectedCity().getBuildingTypesCanBeBuilt());
        if(GameController.getInstance().getSelectedCity().getBuildingTypesCanBeBuilt() == null) return "this city can not build building now";
        String returnString  = "your valid buildings are:" + GameController.getInstance().getSelectedCity().getBuildingTypesCanBeBuilt().toString();
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

    private boolean hasRequiredTechnologyForBuilding(BuildingType buildingType){
        Technology requiredTechnology = buildingType.getTechnologyRequired();
        if(requiredTechnology == null) return true;
        ArrayList<Technology> validTechnologies;
        if((validTechnologies = GameController.getInstance().getSelectedCity().getCivilization().getTechnologies()) == null) return false;
        for(Technology technology : validTechnologies){
            if(Objects.equals(technology, requiredTechnology)) return true;
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
        String buildNowOrPerTurns = matcher.group("typeOfPay");
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
                return "your construction of building is canceled";
            }
        }
        return "your city is not building any Buildings";
        // TODO create stop method
    }
    // --------- build units ------------------------------

    public String showValidUnits(){
        if(GameController.getInstance().getSelectedCity() == null) return "no city is selected";
        UnitType[] unitTypes = UnitType.values();
        for (UnitType unitType : unitTypes) {
            if(hasRequiredTechnologyForUnit(unitType) && hasRequiredResourcesForUnit(unitType)) {
                GameController.getInstance().getSelectedCity().addUnitsToCanBeBuilt(unitType);
            }
        }
        removeBuiltUnits(GameController.getInstance().getSelectedCity().getUnitsCanBeBuilt());
        if(GameController.getInstance().getSelectedCity().getUnitsCanBeBuilt() == null) return "this city can not build units now";
        String returnString  = "your valid units are:" + GameController.getInstance().getSelectedCity().getUnitsCanBeBuilt().toString();
        String returnString1 = "";
        for(Map.Entry<City, Object[]> cityEntry : GameMap.getInstance().getUnitsUnderConstruction().entrySet()){
            if(GameController.getInstance().getSelectedCity() == cityEntry.getKey()){
                returnString1 = "\nand your city is building:" + cityEntry.getValue()[0] + " now.\nso you can not build a building per turns,\nunless you cancel building";
                break;
            }
        }
        return returnString + (returnString1 == "" ? "\nyour city is not build a unit right now" : returnString1);
    }

    private void removeBuiltUnits(ArrayList<UnitType> validUnitTypes){
        ArrayList<Unit> UnitsInCity;
        if((UnitsInCity = GameController.getInstance().getSelectedCity().getUnits()) != null){
            for (Unit unit : UnitsInCity) {
                validUnitTypes.remove(unit.getUnitType());
            }
        }
    }

    public String chooseUnitType(Matcher matcher){
        if(GameController.getInstance().getSelectedCity() == null) return "no city is selected";
        String chosenUnitType = matcher.group("unitType");
        for(UnitType unitType : GameController.getInstance().getSelectedCity().getUnitsCanBeBuilt()){
            if(unitType.name().equals(chosenUnitType)){
                this.selectedUnitType = unitType;
                return "your unit type is selected. build per turns ? or build now ?";
            }
        }
        return "not a valid unit type";
    }

    public String buildNowOrPerTurnsForUnit(Matcher matcher){
        if(GameController.getInstance().getSelectedCity() == null) return "no city is selected";
        for(Map.Entry<City, Object[]> cityUnitTypeEntry : GameMap.getInstance().getUnitsUnderConstruction().entrySet()){
            if(cityUnitTypeEntry.getKey() == GameController.getInstance().getSelectedCity()){
                return "\nand your city is building:" + cityUnitTypeEntry.getValue()[0] + " now.\nso you can not build a unit per turns,\nunless you cancel construction of unit";
            }
        }
        if(this.selectedUnitType == null) return "no selected unit type";
        String buildNowOrPerTurns = matcher.group("typeOfPay");
        return buildNowOrPerTurns.equals("per turns") ? buildUnitPerTurns() : buildUnitNow();
    }

    private String buildUnitPerTurns(){
        if(GameController.getInstance().getSelectedCity().getProductionPerTurn() <= 0) return "you can not pay for this unit";
        int moneyRemaining = this.selectedUnitType.cost;
        GameMap.getInstance().addUnitISUnderConstruction(GameController.getInstance().getSelectedCity(), this.selectedUnitType, moneyRemaining);
        this.selectedBuildingType = null;
        return "construction of your new building has begun";
    }

    private String buildUnitNow(){
        if(GameController.getInstance().getSelectedCity().getCivilization().getGold() < this.selectedUnitType.cost) return "you don not have enough gold";
        buildUnit();
        this.selectedUnitType = null;
        return "your new unit is built";
    }

    private void buildUnit(){
        Civilization civilization = GameController.getInstance().getSelectedCity().getCivilization();
        Tile tile = GameController.getInstance().getSelectedCity().getCityTiles().get(0);
        Unit unit = new Unit(civilization , tile, this.selectedUnitType);
        GameController.getInstance().getSelectedCity().addUnit(unit);
        GameMap.getInstance().addUnit(unit);
        int newGoldAmount = GameController.getInstance().getSelectedCity().getCivilization().getGold() - this.selectedUnitType.cost;
        GameController.getInstance().getSelectedCity().getCivilization().setGold(newGoldAmount);
    }

    public String cancelBuildingUnit(){
        if(GameController.getInstance().getSelectedCity() == null) return "no city is selected";
        for(Map.Entry<City, Object[]> cityEntry : GameMap.getInstance().getUnitsUnderConstruction().entrySet()){
            if(cityEntry.getKey() == GameController.getInstance().getSelectedCity()){
                GameMap.getInstance().getUnitsUnderConstruction().remove(cityEntry);
                return "your construction of unit is canceled";
            }
        }
        return "your city is not building any units";
        // TODO create stop method
    }

    private boolean hasRequiredTechnologyForUnit(UnitType unitType){
        Technology requiredTechnology = new Technology(unitType.technologyRequired);
        if(requiredTechnology == null) return true;
        ArrayList<Technology> validTechnologies;
        if((validTechnologies = GameController.getInstance().getSelectedCity().getCivilization().getTechnologies()) == null) return false;
        for (Technology technology : validTechnologies) {
            if(Objects.equals(technology, requiredTechnology)) return true;
        }
        return false;
    }

    private boolean hasRequiredResourcesForUnit(UnitType unitType){
        Resource requiredResource = new Resource(unitType.resourcesRequired);
        if(requiredResource == null) return true;
        ArrayList<Resource> validResources;
        if((validResources = GameController.getInstance().getSelectedCity().getCivilization().getResources()) == null) return false;
        for (Resource resource : validResources) {
            if(Objects.equals(resource, requiredResource)) return true;
        }
        return false;
    }
    // ---------------------------------------------------

    public String assignCitizen(Matcher matcher){
        if(GameController.getInstance().getSelectedCity() == null)return "no city is selected";
        Tile tile = MapFunctions.getInstance().getTile(Integer.parseInt(matcher.group("x")),Integer.parseInt(matcher.group("y")));
        if(tile == null)return "no such tile exists";
        return GameController.getInstance().getSelectedCity().assignCitizen(tile);
    }
    public String removeCitizen(Matcher matcher){
        if(GameController.getInstance().getSelectedCity() == null)return "no city is selected";
        Tile tile = MapFunctions.getInstance().getTile(Integer.parseInt(matcher.group("x")),Integer.parseInt(matcher.group("y")));
        if(tile == null)return "no such tile exists";
        return GameController.getInstance().getSelectedCity().removeCitizenFromWork(tile);
    }
}
