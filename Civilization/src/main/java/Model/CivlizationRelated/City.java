package Model.CivlizationRelated;
import java.util.ArrayList;
import java.util.regex.Matcher;

import Controller.GameController.GameController;
import Controller.GameController.MapControllers.MapFunctions;
import Controller.GameController.MapControllers.MapGenerator;
import Model.TileRelated.Building.Building;
import Model.TileRelated.Building.BuildingType;
import Model.TileRelated.Feature.FeatureType;
import Model.TileRelated.Improvement.Improvement;
import Model.TileRelated.Terraine.TerrainType;
import Model.TileRelated.Tile.Tile;
import Model.Units.TypeEnums.UnitType;
import Model.Units.Unit;

public class City {
    private Civilization civilization;
    boolean isCapital = false;
    private int storedFood;
    private int hitPoint = 20;
    private int goldPerTurn;
    private int sciencePerTurn;
    private int foodPerTurn;
    private int productionPerTurn;
    private int population;
    private ArrayList<BuildingType> BuildingTypesCanBeBuilt;
    private ArrayList<Building> buildings;
    private ArrayList<Tile> cityTiles;
    private ArrayList<Unit> garrisonUnits;
    private ArrayList<UnitType> unitsCanBeBuilt;
    private ArrayList<Unit> units;
    private ArrayList<Citizen> citizens;
    private ArrayList<Improvement> improvements;

    public City(Civilization civilization) {
        this.civilization = civilization;
        this.population = 1;
        this.buildings = new ArrayList<Building>();
        this.cityTiles = new ArrayList<Tile>();
        this.garrisonUnits = new ArrayList<Unit>();
        this.units = new ArrayList<Unit>();
        this.unitsCanBeBuilt = new ArrayList<UnitType>();
        this.citizens = new ArrayList<Citizen>();
        this.improvements = new ArrayList<Improvement>();
        this.BuildingTypesCanBeBuilt = new ArrayList<BuildingType>();
        Citizen citizen = new Citizen(this);
        citizens.add(citizen);
    }

    public void calculateFood(){
        foodPerTurn = 0;
        foodPerTurn += cityTiles.get(0).calculateFood();
        for (Citizen citizen:citizens) {
            foodPerTurn -= 2;
            if(citizen.getTile() == null)continue;
            foodPerTurn += citizen.getTile().calculateFood();
        }
        storedFood += foodPerTurn;
    }

    public ArrayList<BuildingType> getBuildingTypesCanBeBuilt(){
        return this.BuildingTypesCanBeBuilt;
    }
    public void addCanBeBuiltBuildingType(BuildingType buildingType){
        this.BuildingTypesCanBeBuilt.add(buildingType);
    }
    public ArrayList<UnitType> getUnitsCanBeBuilt(){
        return this.unitsCanBeBuilt;
    }
    public void addUnitsToCanBeBuilt(UnitType unitType){
        this.unitsCanBeBuilt.add(unitType);
    }
    public void calculateProduction(){
        if(isCapital)productionPerTurn = 3;
        else productionPerTurn = 0;
        productionPerTurn += cityTiles.get(0).calculateProduction();
        for (Citizen citizen:citizens) {
            if(citizen.getTile() == null){
                productionPerTurn ++;
                continue;
            }
            productionPerTurn += citizen.getTile().calculateProduction();
        }
    }

    public int getProductionPerTurn() {
        return productionPerTurn;
    }

    public void addBuilding(Building building){
        buildings.add(building);
    }

    public void calculateGold(){
        if(isCapital)goldPerTurn = 3;
        else goldPerTurn = 0;
        goldPerTurn += cityTiles.get(0).calculateGold();
        for (Citizen citizen:citizens) {
            if(citizen.getTile() == null)continue;
            goldPerTurn += citizen.getTile().calculateGold();
        }
        for (Building building:buildings) {
            goldPerTurn -= building.getBuildingType().Maintenance;
        }
    }

    public void calculateSciencePerTurn(){
        sciencePerTurn = population;
    }

    public String assignCitizen(Tile tile){
        if(!cityTiles.contains(tile))return "this tile doesnt belong to you";
        if(tile.equals(cityTiles.get(0))) return "cant work on cityCenter";
        if(tile.getCitizen() != null) return "tile has citizen";
        for (Citizen citizen:citizens) {
            if(citizen.getTile() == null){
                citizen.setTile(tile);
                tile.setCitizen(citizen);
                calculateFood();
                calculateProduction();
                calculateGold();
                if(tile.getFeature() != null)System.out.println(tile.getFeature().getFeatureType().name());
                return "done";
            }
        }
        return "all citizens are on work";
    }

    public String removeCitizenFromWork(Tile tile){
        if(tile.getCitizen() == null)return "no citizen works here";
        tile.getCitizen().setTile(null);
        calculateFood();
        calculateProduction();
        calculateGold();
        tile.setCitizen(null);
        return "done";
    }

    public void calculateBuildingBonuses(){
        for (Building building:buildings) {
            if(building.getBuildingType().equals(BuildingType.Granary)){
                foodPerTurn += 2;
            }
            else if(building.getBuildingType().equals(BuildingType.Library)){
                sciencePerTurn += population/2 ;
            }
            else if(building.getBuildingType().equals(BuildingType.WaterMill)){
                foodPerTurn += 2;
            }
            else if(building.getBuildingType().equals(BuildingType.Market)){
                goldPerTurn += goldPerTurn/4;
            }
            else if(building.getBuildingType().equals(BuildingType.Mint)){
                for (Tile tile:cityTiles) {
                    if(tile.calculateGold() != 0)goldPerTurn += 3;
                }
            }
            else if(building.getBuildingType().equals(BuildingType.University)){
                for (Tile tile:cityTiles) {
                    if(tile.getFeature().getFeatureType().equals(FeatureType.Forest) && tile.getCitizen() != null)sciencePerTurn +=2;
                }
                sciencePerTurn += sciencePerTurn/2;
            }
            else if(building.getBuildingType().equals(BuildingType.Bank)){
                goldPerTurn += goldPerTurn/4;
            }
            else if(building.getBuildingType().equals(BuildingType.PublicSchool)){
                sciencePerTurn += sciencePerTurn/2;
            }
            else if(building.getBuildingType().equals(BuildingType.SatrapsCourt)){
                goldPerTurn += goldPerTurn/4;
            }
            else if(building.getBuildingType().equals(BuildingType.StockExchange)){
                goldPerTurn += goldPerTurn/3;
            }
            else if(building.getBuildingType().equals(BuildingType.Factory)){
                productionPerTurn += productionPerTurn/2 ;
            }
            else if(building.getBuildingType().equals(BuildingType.Windmill)){
                if(!cityTiles.get(0).getTerrain().equals(TerrainType.Hill))productionPerTurn += (15*productionPerTurn)/100 ;
            }
        }
    }

    public void populationGrowth(){
        int neededFood = (int)Math.pow(2,population);
        for (Building building:buildings) {
            if(building.getBuildingType().equals(BuildingType.Hospital)){
                neededFood /= 2;
                break;
            }
        }
        if(storedFood > neededFood){
            population ++;
            Citizen citizen = new Citizen(this);
            citizens.add(citizen);
            storedFood -= Math.pow(2,population);
            productionPerTurn ++;
            sciencePerTurn ++;
            foodPerTurn -= 2;
        }
    }

    public String buyTile(Tile tile){
        if(!findCitySurroundings().contains(tile))return "you can't buy this tile";
        if(this.civilization.getGold() < 50)return "you don't have enough gold";
        this.civilization.changeGold(-50);
        cityTiles.add(tile);
        this.civilization.getTiles().add(tile);
        tile.setCivilization(this.civilization);
        return "done!";
    }

    public ArrayList<Tile> findCitySurroundings(){
        ArrayList <Tile> surroundings = new ArrayList<>();
        for (Tile cityTile:cityTiles) {
            for (Tile surrounding:MapFunctions.getInstance().getSurroundings(cityTile)) {
                if(surrounding != null && !surrounding.getTerrain().equals(TerrainType.Ocean) && !cityTiles.contains(surrounding) &&
                        !surrounding.getTerrain().equals(TerrainType.Mountain) && !surroundings.contains(surrounding)){
                    surroundings.add(surrounding);
                }
            }
        }
        return surroundings;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void addUnit(Unit unit){
        this.units.add(unit);
    }

    public void setCapital(boolean capital) {
        isCapital = capital;
    }

    public Civilization getCivilization() {
        return civilization;
    }

    public void setCivilization(Civilization civilization) {
        this.civilization = civilization;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public ArrayList<Tile> getCityTiles() {
        return cityTiles;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public int getGoldPerTurn() {
        return goldPerTurn;
    }

    public int getSciencePerTurn() {
        return sciencePerTurn;
    }

    public int getPopulation() {
        return population;
    }

    public ArrayList<Building> getBuilding() {
        return buildings;
    }

    public ArrayList<Unit> getGarrisonUnits() {
        return garrisonUnits;
    }

    public ArrayList<Citizen> getCitizens() {
        return citizens;
    }

    public ArrayList<Improvement> getImprovements() {
        return improvements;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public void setGoldPerTurn(int goldPerTurn) {
        this.goldPerTurn += goldPerTurn;
    }

    public void setSciencePerTurn(int sciencePerTurn) {
        this.sciencePerTurn += sciencePerTurn;
    }

    public int getFoodPerTurn() {
        return foodPerTurn;
    }

    public void setPopulation(int population) {
        this.population += population;
    }

    public void setCityTiles(ArrayList<Tile> cityTiles) {
        this.cityTiles = cityTiles;
    }

    public void setGarrisonUnits(ArrayList<Unit> garrisonUnits) {
        this.garrisonUnits = garrisonUnits;
    }

    public void setCitizens(ArrayList<Citizen> citizens) {
        this.citizens = citizens;
    }

    public void setImprovements(ArrayList<Improvement> improvements) {
        this.improvements = improvements;
    }

    public void addCityTiles(Tile tile) {
        this.cityTiles.add(tile);
    }
}
