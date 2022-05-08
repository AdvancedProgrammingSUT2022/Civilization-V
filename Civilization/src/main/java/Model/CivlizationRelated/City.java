package Model.CivlizationRelated;
import java.util.ArrayList;

import Model.TileRelated.Building.Building;
import Model.TileRelated.Improvement.Improvement;
import Model.TileRelated.Tile.Tile;
import Model.Units.Unit;

public class City {
    private Civilization civilization;
    private double hitPoint = 20;
    private double strength = 15;
    private int gold;
    private int science;
    private int foodProductionRate;
    private int population;
    private ArrayList<Building> building;
    private ArrayList<Tile> cityTiles;
    private Unit garrisonUnit;
    private ArrayList<Unit> units;
    private ArrayList<Citizen> citizens;
    private ArrayList<Improvement> improvements;

    public City() {
        this.civilization = new Civilization();
        this.population = 0;
        this.gold = 0;
        this.science = 0;
        this.foodProductionRate = 0;
        this.building = new ArrayList<Building>();
        this.cityTiles = new ArrayList<Tile>();
        this.garrisonUnit = new Unit();
        this.units = new ArrayList<Unit>();
        this.citizens = new ArrayList<Citizen>();
        this.improvements = new ArrayList<Improvement>();

    }
    public Unit getGarrisonUnit() {
        return garrisonUnit;
    }
    public void setGarrisonUnit(Unit garrisonUnit) {
        this.garrisonUnit = garrisonUnit;
    }
    public void feedPopulation(){

    }
    
    public Civilization getCivilization() {
        return civilization;
    }

    public void setCivilization(Civilization civilization) {
        this.civilization = civilization;
    }
    public double calculateMaxCityDamage(){
        double maxDamage = strength;
        if(garrisonUnit != null)    
            maxDamage *= 120.0/100.0;
        maxDamage += population * 0.5;
        return maxDamage;
    }
    public ArrayList<Unit> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<Unit> units) {
        this.units = units;
    }

    public ArrayList<Building> getBuilding() {
        return building;
    }

    public void setBuilding(ArrayList<Building> building) {
        this.building = building;
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

    public double getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(double hitPoint) {
        this.hitPoint = hitPoint;
    }
    public void changeHitPoint(double hitPoint) {
        this.hitPoint = hitPoint;
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
