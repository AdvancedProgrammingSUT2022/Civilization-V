package Model.TileRelated.Tile;
import java.util.ArrayList;

import javax.sound.sampled.ReverbType;

import Model.CivlizationRelated.Civilization;
import Model.TileRelated.Building.Building;
import Model.TileRelated.Feature.Feature;
import Model.TileRelated.Feature.River;
import Model.TileRelated.Improvement.Improvement;
import Model.TileRelated.Resource.Resource;
import Model.TileRelated.Terraine.TerrainType;
import Model.Units.Unit;

public class Tile {
    private int x;
    private int y;
    private int mpCost;
    private Building building;
    private ArrayList<Unit> units = new ArrayList<>();
    private Feature feature;
    private Resource resource;
    private ArrayList<River> rivers = new ArrayList<>();
    private TerrainType terrain;
    private Improvement improvement;
    private Civilization civilization;
    
    public int getX() {
        return x;
    }
    public void addUnit(Unit unit){
        units.add(unit);
    }
    public Civilization getCivilization() {
        return civilization;
    }

    public void setCivilization(Civilization civilization) {
        this.civilization = civilization;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<Unit> units) {
        this.units = units;
    }

    public TerrainType getTerrain() {
        return terrain;
    }

    public void setTerrain(TerrainType terrain) {
        this.terrain = terrain;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    public Improvement getImprovement() {
        return improvement;
    }

    public void setImprovement(Improvement improvement) {
        this.improvement = improvement;
    }

    public int getMpCost() {
        return mpCost;
    }

    public void setMpCost(int mpCost) {
        this.mpCost = mpCost;
    }

    public int getY() {
        return y;
    }

    public boolean hasCombatUnit(){
        return true;
    }

    public boolean hasNonCombatUnit(){
        return true;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    

    public int calculateMp(){
        return 0;
    }

    public boolean checkType(TerrainType terrainType){
        return this.terrain == terrainType;
    } 
    public void addRiver(River river){
        rivers.add(river);
    }
    public ArrayList<River> getRivers(){
        return rivers;
    }

}