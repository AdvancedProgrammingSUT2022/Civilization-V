package Model.CivlizationRelated;
import java.util.ArrayList;

import Model.TileRelated.Building.Building;
import Model.TileRelated.Building.BuildingType;
import Model.TileRelated.Improvement.Improvement;
import Model.TileRelated.Tile.Tile;
import Model.Units.Unit;

public class City {
    private Civilization civilization;
    private int hitPoint = 20;
    private int gold;
    private int science;
    private int foodProductionRate;
    private int population;
    private ArrayList<BuildingType> BuildingTypesCanBeBuilt;
    private ArrayList<Building> buildings;
    private ArrayList<Tile> cityTiles;
    private ArrayList<Unit> garrisonUnits;
    private ArrayList<Unit> units;
    private ArrayList<Citizen> citizens;
    private ArrayList<Improvement> improvements;

    public City() {
        this.civilization = new Civilization();
        this.population = 0;
        this.gold = 0;
        this.science = 0;
        this.foodProductionRate = 0;
        this.buildings = new ArrayList<Building>();
        this.cityTiles = new ArrayList<Tile>();
        this.garrisonUnits = new ArrayList<Unit>();
        this.units = new ArrayList<Unit>();
        this.citizens = new ArrayList<Citizen>();
        this.improvements = new ArrayList<Improvement>();
        this.BuildingTypesCanBeBuilt = new ArrayList<BuildingType>();
    }
    public void feedPopulation(){

    }
    public ArrayList<BuildingType> getBuildingTypesCanBeBuilt(){
        return this.BuildingTypesCanBeBuilt;
    }
    public void addCanBeBuiltBuildingType(BuildingType buildingType){
        this.BuildingTypesCanBeBuilt.add(buildingType);
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

    public void setUnits(ArrayList<Unit> units) {
        this.units = units;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void addBuilding(Building building){
        this.buildings.add(building);
    }

    public void setBuilding(ArrayList<Building> building) {
        this.buildings = building;
    }

    public int getPopulation() {
        return population;
    }
    public void setPopulation(int population) {
        this.population = population;
    }
    public ArrayList<Tile> getCityTiles() {
        return cityTiles;
    }
    public void setCityTiles(ArrayList<Tile> cityTiles) {
        this.cityTiles = cityTiles;
    }
    public ArrayList<Improvement> getImprovements() {
        return improvements;
    }
    public void setImprovements(ArrayList<Improvement> improvements) {
        this.improvements = improvements;
    }
    public ArrayList<Citizen> getCitizens() {
        return citizens;
    }
    public void setCitizens(ArrayList<Citizen> citizens) {
        this.citizens = citizens;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public ArrayList<Unit> getGarrisonUnits() {
        return garrisonUnits;
    }

    public void setGarrisonUnits(ArrayList<Unit> garrisonUnits) {
        this.garrisonUnits = garrisonUnits;
    }
    public int getFoodProductionRate() {
        return foodProductionRate;
    }
    public void setFoodProductionRate(int foodProductionRate) {
        this.foodProductionRate = foodProductionRate;
    }

    public int collectFoodProduction(){
        return 0;
    }

    public int collectProduction(){
        return 0;
    }

    public void initGarrisonUnits(){

    }
    public void attackEnemyUnit(Unit unit){

    }

    public void addCityTiles(Tile tile) {
        this.cityTiles.add(tile);
    }
}
