package Model.CivlizationRelated;

import java.util.ArrayList;

import Model.TileAndFeatures.Building.Building;
import Model.TileAndFeatures.Tile.Tile;
import Model.Units.Unit;

public class City {
    Civilization civilization;
    private int hitPoint = 20;
    private int foodProductionRate;
    private int citizenPopulation;
    private Building building;
    private ArrayList<Tile> tiles;
    private ArrayList<Unit> garrisonUnits;
    public void feedPopulation(){

    }
    
    public int getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public ArrayList<Unit> getGarrisonUnits() {
        return garrisonUnits;
    }

    public void setGarrisonUnits(ArrayList<Unit> garrisonUnits) {
        this.garrisonUnits = garrisonUnits;
    }

    public int getCitizenPopulation() {
        return citizenPopulation;
    }
    public void setCitizenPopulation(int citizenPopulation) {
        this.citizenPopulation = citizenPopulation;
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
    public void attacEnemyUnit(Unit unit){

    }
}
