package Model.TileAndFeatures.Tile;
import Model.TileAndFeatures.Feature.Feature;
import Model.TileAndFeatures.Feature.River;
import Model.TileAndFeatures.Improvement.Improvement;
import Model.TileAndFeatures.Resource.Resource;
import Model.TileAndFeatures.Terraine.TerraineType;
import Model.Units.Unit;

public class Tile {
    private int x;
    private int y;
    private int mpCost;
    private Unit nonCombatUnit;
    private Unit combatUnit;
    private Feature featureType;
    private Resource resourceType;
    private River river;
    private TerraineType terrainType;
    private TileVisibility tileVisibility;
    private Improvement improvement;
    
    public int getX() {
        return x;
    }

    public Improvement getImprovement() {
        return improvement;
    }

    public void setImprovement(Improvement improvement) {
        this.improvement = improvement;
    }

    public TileVisibility getTileVisibility() {
        return tileVisibility;
    }

    public void setTileVisibility(TileVisibility tileVisibility) {
        this.tileVisibility = tileVisibility;
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

    public int unitCount(){
        if(nonCombatUnit != null && combatUnit != null)return 2;
        else if(nonCombatUnit == null && combatUnit == null)return 0;
        return 1;
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

    public void setCivilUnit(Unit civilUnit) {
        this.nonCombatUnit = civilUnit;
    }

    public void setCombatUnit(Unit combatUnit) {
        this.combatUnit = combatUnit;
    }

    public Unit getCivilUnit() {
        return nonCombatUnit;
    }

    public Unit getCombatUnit() {
        return combatUnit;
    }

    public int calculateMp(){
        return 0;
    }

    public Feature getFeatureType() {
        return featureType;
    }

    public void setFeatureType(Feature featureType) {
        this.featureType = featureType;
    }

    public Resource getResourceType() {
        return resourceType;
    }

    public void setResourceType(Resource resourceType) {
        this.resourceType = resourceType;
    }

    public River getRiver() {
        return river;
    }

    public void setRiver(River river) {
        this.river = river;
    }

    public TerraineType getTerrainType() {
        return terrainType;
    }

    public void setTerrainType(TerraineType terrainType) {
        this.terrainType = terrainType;
    }
}
