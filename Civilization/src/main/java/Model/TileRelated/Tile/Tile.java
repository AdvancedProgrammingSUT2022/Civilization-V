package Model.TileRelated.Tile;
import java.util.ArrayList;

import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.TileRelated.Building.Building;
import Model.TileRelated.Feature.Feature;
import Model.TileRelated.Feature.River;
import Model.TileRelated.Improvement.Improvement;
import Model.TileRelated.Resource.Resource;
import Model.TileRelated.Terraine.TerrainType;
import Model.Units.Unit;
import Model.Units.Combat.Combat;
import Model.Units.TypeEnums.MainType;

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
    private City city;
    private boolean isCapital;
    
    public int getX() {
        return x;
    }
    public boolean isCapital() {
        return isCapital;
    }
    public void setCapital(boolean isCapital) {
        this.isCapital = isCapital;
    }
    public City getCity() {
        return city;
    }
    public void setCity(City city) {
        this.city = city;
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
    public Combat getCombatUnitOnTile(){
        for(int i = 0;i < this.getUnits().size();i++){
            if(this.getUnits().get(i) != null){
                if(this.getUnits().get(i) != null && this.getUnits().get(i).getUnitType().mainType != MainType.NONCOMBAT){
                    return (Combat) this.getUnits().get(i);
                }
            }
        }
        return null;
    }

}
