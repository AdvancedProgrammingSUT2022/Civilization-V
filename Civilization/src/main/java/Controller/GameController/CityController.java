package Controller.GameController;
import Controller.GameController.MapControllers.MapFunctions;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.Enums.MapEnum;
import Model.MapRelated.GameMap;
import Model.Technology.Technology;
import Model.TileRelated.Building.Building;
import Model.TileRelated.Building.BuildingType;
import Model.TileRelated.Resource.ResourceType;
import Model.TileRelated.Tile.Tile;
import Model.Units.TypeEnums.UnitType;
import Model.Units.Unit;
import java.util.ArrayList;
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

    public String selectCity(String name){
//        if(name == null) {
//            int x = Integer.parseInt(matcher.group("x"));
//            int y = Integer.parseInt(matcher.group("y"));
//            if (x > GameMap.getInstance().getMapWidth() - 1 || y > GameMap.getInstance().getMapHeight() - 1) return "invalid coordinates";
//            Civilization civilization;
//            if ((civilization = MapFunctions.getInstance().getTile(x, y).getCivilization()) == null)
//                return "this tile does not belong to anyone";
//            if (civilization != GameController.getInstance().getPlayerTurn())
//                return "this tile does not belong to your civilization";
//            ArrayList<City> playerCities = GameController.getInstance().getPlayerTurn().getCities();
//            if (playerCities == null) return "no cities on your civilization";
//            for (City city : playerCities) {
//                if (Objects.equals(city.getCityTiles().get(0), MapFunctions.getInstance().getTile(x, y))) {
//                    GameController.getInstance().setSelectedCity(city);
//                    return "city selected :" + cityOutput();
//
//                }
//            }
//        } else {
            ArrayList<City> playerCities = GameController.getInstance().getPlayerTurn().getCities();
            if (playerCities == null) return "no cities on your civilization";
            for (City city : playerCities) {
                if (name.equals(city.getName())) {
                    GameController.getInstance().setSelectedCity(city);
                    return "city selected :" + cityOutput();
                }
            }
        //}
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
        output.append("population : ").append(city.getPopulation()).append("\n");
        if(city.getFoodPerTurn() <= 0)output.append("city is not growing !");
        else output.append("population growth turns : " + (int)((Math.pow(2,city.getPopulation())) - city.getStoredFood())/(city.getFoodPerTurn())+1);
        return output.toString();
    }



    public String buyTile(Matcher matcher){
        if(GameController.getInstance().getSelectedCity() == null)return "no city is selected";
        Tile tile = MapFunctions.getInstance().getTile(Integer.parseInt(matcher.group("x")),Integer.parseInt(matcher.group("y")));
        if(tile == null)return "no such tile exists";
        City city = GameController.getInstance().getSelectedCity();
        GameController.getInstance().getPlayerTurn().addNotification(city.getName() + "->buying Tile( y->" + tile.getY()+ ",x->"+ tile.getX() +") :");
        return GameController.getInstance().getSelectedCity().buyTile(tile);
    }

    public String showValidBuildingTypes() {
        if(GameController.getInstance().getSelectedCity() == null) return "no city is selected";
        ArrayList<BuildingType> buildingTypesCanBuilt = new ArrayList<BuildingType>();
        BuildingType[] buildingTypes = BuildingType.values();
        for(BuildingType buildingType : buildingTypes){
            if(hasRequiredTechnologyForBuilding(buildingType)){
                buildingTypesCanBuilt.add(buildingType);
            }
        }
        removeBuiltBuildings(buildingTypesCanBuilt);
        if(buildingTypesCanBuilt.size() == 0) return "this city can not build building now";
        String returnString  = "your valid buildings are:" + buildingTypesCanBuilt.toString();
        String returnString1 = "";
        BuildingType buildingType = GameController.getInstance().getSelectedCity().getUnderConstructionBuilding();
        UnitType unitType = GameController.getInstance().getSelectedCity().getUnderConstructionUnit();
        if (unitType!= null && buildingType == null)
            returnString1 = "\nand your city is building:" + unitType.name() + " now.\nso you can not build a building per turns,\nunless you cancel building";
        if (buildingType != null && unitType == null)
            returnString1 = "\nand your city is building:" + buildingType.name() + " now.\nso you can not build a building per turns,\nunless you cancel building";
        return returnString + (returnString1 == "" ? "\nyour city is not build a unit or a building right now" : returnString1);
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
        BuildingType buildingType;
        if((buildingType = GameController.getInstance().getSelectedCity().getUnderConstructionBuilding()) != null)
            return "\nand your city is building:" + buildingType.name() + " now.\nso you can not build a building per turns,\nunless you cancel building";
        if(this.selectedBuildingType == null) return "no selected building type";
        String buildNowOrPerTurns = matcher.group("typeOfPay");
        return buildNowOrPerTurns.equals("per turns") ? buildPerTurns() : buildNow();
    }
    private String buildNow(){
        GameController.getInstance().getPlayerTurn().addNotification("turn : " + GameController.getInstance().getTurn() + ")" +"build building :");
        if(GameController.getInstance().getSelectedCity().getCivilization().getGold() < this.selectedBuildingType.getCost()){
            GameController.getInstance().getPlayerTurn().addNotification("you do not have enough gold");
            return "you do not have enough gold";
        }
        GameController.getInstance().getSelectedCity().setUnderConstructionUnit(this.selectedUnitType);
        buildBuildingNow();
        GameController.getInstance().getSelectedCity().setUnderConstructionBuilding(null);
        this.selectedBuildingType = null;
        GameController.getInstance().getPlayerTurn().addNotification("your new building is built");
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
        GameController.getInstance().getPlayerTurn().addNotification("turn : " + GameController.getInstance().getTurn() + ")" +"build building :");
        if(GameController.getInstance().getSelectedCity().getProductionPerTurn() <= 0) {
            GameController.getInstance().getPlayerTurn().addNotification("you can not pay for this building");
            return "you can not pay for this building";
        }
        int turn = this.selectedBuildingType.getCost() / GameController.getInstance().getSelectedCity().getProductionPerTurn();
        GameController.getInstance().getSelectedCity().setBuildingTurn(turn);
        BuildingType buildingType =  this.selectedBuildingType;
        this.selectedBuildingType = null;
        if(turn ==0 ) {
            Building building = new Building(buildingType);
            GameController.getInstance().getSelectedCity().addBuilding(building);
            GameMap.getInstance().addBuiltBuilding(building);
            GameController.getInstance().getPlayerTurn().addNotification("your new building is built");
            return "your new building is built";
        }
        GameController.getInstance().getPlayerTurn().addNotification("construction of your new building has begun");
        return "construction of your new building has begun";
    }

    public String cancelBuilding(){
        if(GameController.getInstance().getSelectedCity() == null) return "no city is selected";
        GameController.getInstance().getPlayerTurn().addNotification("turn : " + GameController.getInstance().getTurn() + ")" +
                GameController.getInstance().getSelectedCity().getName() + "->" + "cancel build building :");
        if(GameController.getInstance().getSelectedCity().getUnderConstructionBuilding() != null) {
            GameController.getInstance().getSelectedCity().setUnderConstructionBuilding(null);
            GameController.getInstance().getPlayerTurn().addNotification("your construction of building is canceled");
            return "your construction of building is canceled";
        }
        GameController.getInstance().getPlayerTurn().addNotification("your city is not build any buildings");
        return "your city is not building any Buildings";
    }
    // --------- build units ------------------------------
    public String showValidUnits(){
        if(GameController.getInstance().getSelectedCity() == null) return "no city is selected";
        ArrayList<UnitType> unitTypesCanBeBuilt = new ArrayList<UnitType>();
        UnitType[] unitTypes = UnitType.values();
        for (UnitType unitType : unitTypes) {
            if(hasRequiredTechnologyForUnit(unitType) && hasRequiredResourcesForUnit(unitType)) {
                unitTypesCanBeBuilt.add(unitType);
            }
        }
        if(unitTypesCanBeBuilt.size() == 0) return "this city can not build units now";
        showBuiltUnits();
        GameController.getInstance().getSelectedCity().setUnitsCanBeBuilt(unitTypesCanBeBuilt);
        String returnString  = "your valid units are:" + unitTypesCanBeBuilt.toString();
        String returnString1 = "";
        BuildingType buildingType = GameController.getInstance().getSelectedCity().getUnderConstructionBuilding();
        UnitType unitType = GameController.getInstance().getSelectedCity().getUnderConstructionUnit();
        if (unitType!= null && buildingType == null)
            returnString1 = "\nand your city is building:" + unitType.name() + " now.\nso you can not build a unit per turns,\nunless you cancel construction";
        if (buildingType != null && unitType == null)
            returnString1 = "\nand your city is building:" + buildingType.name() + " now.\nso you can not build a building per turns,\nunless you cancel construction";
        return returnString + (returnString1 == "" ? "\nyour city is not build a unit or a building right now" : returnString1);
    }

    private void showBuiltUnits(){
        ArrayList<Unit> units;
        if((units = GameController.getInstance().getPlayerTurn().getUnits()) != null){
            for (int i = 0; i < units.size(); i++) {
                System.out.println(i + 1 + " : " + units.get(i).getUnitType().name());
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
        UnitType unitType;
        if(this.selectedUnitType == null) return "no selected unit type";
        if((unitType = GameController.getInstance().getSelectedCity().getUnderConstructionUnit()) != null){
            return "\nand your city is building:" + unitType.name() + " now.\nso you can not build a building per turns,\nunless you cancel building";
        }
        String buildNowOrPerTurns = matcher.group("typeOfPay");
        return buildNowOrPerTurns.equals("per turns") ? buildUnitPerTurns() : buildUnitNow();
    }

    private String buildUnitPerTurns(){
        GameController.getInstance().getPlayerTurn().addNotification("turn : " + GameController.getInstance().getTurn() + ")" +"build unit :");
        if(GameController.getInstance().getSelectedCity().getProductionPerTurn() <= 0) {
            GameController.getInstance().getPlayerTurn().addNotification("you can not pay for this unit");
            return "you can not pay for this unit";
        }
        String checkCenterTile = checkUnitsInCenterTile();
        if(checkCenterTile != null) return checkCenterTile;
        GameController.getInstance().getSelectedCity().setUnderConstructionUnit(this.selectedUnitType);
        int turn = this.selectedUnitType.cost / GameController.getInstance().getSelectedCity().getProductionPerTurn();
        if(turn ==0 ) return buildUnitNow();
        GameController.getInstance().getSelectedCity().setUnitTurn(turn);
        this.selectedUnitType = null;
        GameController.getInstance().getPlayerTurn().addNotification("construction of your new unit has begun");
        return "construction of your new unit has begun";
    }

    private String buildUnitNow(){
        GameController.getInstance().getPlayerTurn().addNotification("turn : " + GameController.getInstance().getTurn() + ")" +"build unit :");
        if(GameController.getInstance().getSelectedCity().getCivilization().getGold() < this.selectedUnitType.cost) {
            GameController.getInstance().getPlayerTurn().addNotification("you do not have enough gold");
            return "you do not have enough gold";
        }
        String checkCenterTile = checkUnitsInCenterTile();
        if(checkCenterTile != null) return checkCenterTile;
        buildUnit();
        GameController.getInstance().getSelectedCity().setUnderConstructionUnit(null);
        this.selectedUnitType = null;
        GameController.getInstance().getPlayerTurn().addNotification("your new unit is built");
        return "your new unit is built";
    }

    private String checkUnitsInCenterTile(){
        if(GameController.getInstance().getSelectedCity().getCityTiles().get(0).getUnits().size()!=0) {
            for(Unit unit :GameController.getInstance().getSelectedCity().getCityTiles().get(0).getUnits()) {
                if (unit.getUnitType().mainType.equals(selectedUnitType.mainType)) {
                    return "first move your units with " + unit.getUnitType().name() + " main type from center tile in city";
                }
            }
        } return null;
    }


    private void buildUnit(){
        Tile tile = GameController.getInstance().getSelectedCity().getCityTiles().get(0);
        UnitController.getInstance().makeUnit(selectedUnitType, GameController.getInstance().getPlayerTurn(), tile);
        need();
        int newGoldAmount = GameController.getInstance().getSelectedCity().getCivilization().getGold() - this.selectedUnitType.cost;
        GameController.getInstance().getSelectedCity().getCivilization().setGold(newGoldAmount);
    }

    private void need() {
        if (selectedUnitType.resourcesRequired != null) {
            if (selectedUnitType.resourcesRequired.equals(ResourceType.Horses))
                GameController.getInstance().getPlayerTurn().changeCurrentHorses(-1);
            else if (selectedUnitType.resourcesRequired.equals(ResourceType.Iron))
                GameController.getInstance().getPlayerTurn().changeCurrentIron(-1);
            else if (selectedUnitType.resourcesRequired.equals(ResourceType.Coal))
                GameController.getInstance().getPlayerTurn().changeCurrentCoal(-1);
        }
    }



    public String cancelBuildingUnit(){
        if(GameController.getInstance().getSelectedCity() == null) return "no city is selected";
        GameController.getInstance().getPlayerTurn().addNotification("turn : " + GameController.getInstance().getTurn() + ")" + GameController.getInstance().getSelectedCity().getName()+"->"+"cancel build unit :");
        if(GameController.getInstance().getSelectedCity().getUnderConstructionUnit() != null) {
            GameController.getInstance().getSelectedCity().setUnderConstructionUnit(null);
            GameController.getInstance().getPlayerTurn().addNotification("your construction of unit is canceled");
            return "your construction of unit is canceled";
        }
        GameController.getInstance().getPlayerTurn().addNotification("your city is not building any units");
        return "your city is not building any units";
    }

    private boolean hasRequiredTechnologyForUnit(UnitType unitType){
        if(unitType.technologyRequired == null) return true;
        ArrayList<Technology> validTechnologies;
        if((validTechnologies = GameController.getInstance().getSelectedCity().getCivilization().getTechnologies()) == null) return false;
        for (Technology technology : validTechnologies) {
            if(Objects.equals(technology.getTechnologyType(), unitType.technologyRequired)) return true;
        }
        return false;
    }

    private boolean hasRequiredResourcesForUnit(UnitType unitType){
        if(unitType.resourcesRequired == null) return true;
        if(unitType.resourcesRequired.equals(ResourceType.Iron)){
            if(GameController.getInstance().getPlayerTurn().getCurrentIron() == 0) return false;
            return true;
        }
        if(unitType.resourcesRequired.equals(ResourceType.Horses)){
            if(GameController.getInstance().getPlayerTurn().getCurrentHorses() == 0) return false;
            return true;
        }
        if(unitType.resourcesRequired.equals(ResourceType.Coal)){
            if(GameController.getInstance().getPlayerTurn().getCurrentCoal() == 0) return false;
            return true;
        }
        return false;
    }

    public String changeConstruction(Matcher matcher){
        if(GameController.getInstance().getSelectedCity() == null) return "no city is selected";
        boolean hasUnitUnderConstruction = GameController.getInstance().getSelectedCity().getUnderConstructionUnit() == null;
        boolean hasBuildingUnderConstruction = GameController.getInstance().getSelectedCity().getUnderConstructionBuilding() == null;
        GameController.getInstance().getPlayerTurn().addNotification("turn : "+ GameController.getInstance().getTurn() + ")"+ "changing construction of city :");
        if(hasUnitUnderConstruction && hasBuildingUnderConstruction) {
            GameController.getInstance().getPlayerTurn().addNotification("your city has not any under construction object");
            return "your city has not any under construction object";
        }
        String unitType = matcher.group("unitType");
        String buildingType = matcher.group("buildingType");
        if(unitType != null && buildingType == null) {
            GameController.getInstance().getPlayerTurn().addNotification(changeConstructionToUnit(unitType));
            return changeConstructionToUnit(unitType);
        }
        if(buildingType != null && unitType == null) {
            GameController.getInstance().getPlayerTurn().addNotification(changeConstructionToBuilding(buildingType));
            return changeConstructionToBuilding(buildingType);
        }
        GameController.getInstance().getPlayerTurn().addNotification("not a valid building type or unit type");
        return "not a valid building type or unit type";
    }

    private String changeConstructionToUnit(String unitType){
        for(UnitType unitType1 : UnitType.values()){
            if(unitType1.name().equals(unitType)){
                if(!hasRequiredResourcesForUnit(unitType1) && !hasRequiredTechnologyForUnit(unitType1)) return "you do not have requirements";
                int turn = 0;
                if(GameController.getInstance().getSelectedCity().getUnderConstructionBuilding() != null){
                    GameController.getInstance().getSelectedCity().setUnitUnderConstruction(unitType1);
                    turn = GameController.getInstance().getSelectedCity().getBuildingTurn();
                    GameController.getInstance().getSelectedCity().setUnitTurn(turn);
                    GameController.getInstance().getSelectedCity().setUnderConstructionBuilding(null);
                } else if(GameController.getInstance().getSelectedCity().getUnderConstructionUnit() != null){
                    GameController.getInstance().getSelectedCity().setUnitUnderConstruction(unitType1);
                    turn = GameController.getInstance().getSelectedCity().getUnitTurn();
                    GameController.getInstance().getSelectedCity().setUnitTurn(turn);
                    GameController.getInstance().getSelectedCity().setUnitUnderConstruction(null);
                }
                return "your city construction is changed";
            }
        } return "not a valid unit type";
    }

    private String changeConstructionToBuilding(String buildingType){
        for(BuildingType buildingType1 : BuildingType.values()){
            if(buildingType1.name().equals(buildingType)){
                if(!hasRequiredTechnologyForBuilding(buildingType1)) return "you do not have required technologies";
                int turn = 0;
                if(GameController.getInstance().getSelectedCity().getUnderConstructionBuilding() != null){
                    GameController.getInstance().getSelectedCity().setUnderConstructionBuilding(buildingType1);
                    turn = GameController.getInstance().getSelectedCity().getBuildingTurn();
                    GameController.getInstance().getSelectedCity().setBuildingTurn(turn);
                } else if(GameController.getInstance().getSelectedCity().getUnderConstructionUnit() != null){
                    GameController.getInstance().getSelectedCity().setUnderConstructionBuilding(buildingType1);
                    turn = GameController.getInstance().getSelectedCity().getUnitTurn();
                    GameController.getInstance().getSelectedCity().setBuildingTurn(turn);
                    GameController.getInstance().getSelectedCity().setUnitUnderConstruction(null);
                }
                return "your city construction is changed";
            }
        } return "not a valid building type";
    }

    // ---------------------------------------------------

//    public String assignCitizen(Tile tile){
//        if(GameController.getInstance().getSelectedCity() == null)return "no city is selected";
//        GameController.getInstance().getPlayerTurn().addNotification("turn : "+ GameController.getInstance().getTurn() + ")"+ GameController.getInstance().getSelectedCity().getName() + "->assign work to citizen :");
//        if(tile == null){
//            GameController.getInstance().getPlayerTurn().addNotification("no such tile exist");
//            return "no such tile exists";
//        }
//        return GameController.getInstance().getSelectedCity().assignCitizen(tile);
//    }

    public String removeCitizen(Matcher matcher){
        if(GameController.getInstance().getSelectedCity() == null)return "no city is selected";
        GameController.getInstance().getPlayerTurn().addNotification("turn : "+ GameController.getInstance().getTurn() + ")"+ GameController.getInstance().getSelectedCity().getName() + "->remove citizen from work :");
        Tile tile = MapFunctions.getInstance().getTile(Integer.parseInt(matcher.group("x")),Integer.parseInt(matcher.group("y")));
        if(tile == null){
            GameController.getInstance().getPlayerTurn().addNotification("no such tile exist");
            return "no such tile exists";
        }
        return GameController.getInstance().getSelectedCity().removeCitizenFromWork(tile);
    }

}
