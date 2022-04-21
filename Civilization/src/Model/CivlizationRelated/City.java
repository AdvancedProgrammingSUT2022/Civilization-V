package Model.CivlizationRelated;
import java.util.ArrayList;

import Model.TileRelated.Building.Building;
import Model.TileRelated.Improvement.Improvement;
import Model.TileRelated.Tile.Tile;
import Model.Units.Unit;

public class City {
    Civilization civilization;
    private int hitPoint = 20;
    private int foodProductionRate;
    private int population;
    private ArrayList<Building> building;
    private ArrayList<Tile> cityTiles;
    private ArrayList<Unit> garrisonUnits;
    private ArrayList<Unit> units;
    private ArrayList<Citizen> citizens;
    private ArrayList<Improvement> improvements;
    public void feedPopulation(){

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
}
