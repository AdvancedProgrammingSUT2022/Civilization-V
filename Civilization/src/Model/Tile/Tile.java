package Model.Tile;

import Model.Feature.FeatureType;
import Model.Resource.ResourceType;
import Model.Units.Unit;

public class Tile {
    private int x;
    private int y;
    private Unit civilUnit;
    private Unit combatUnit;
    FeatureType featureType;
    ResourceType resourceType;
    int mp;
    
    TileType terrainType;

    public int getX() {
        return x;
    }

    public int getY() {
        int a = ResourceType.Bananas.Food;
        return y;
    }

    public int unitCount(){
        if(civilUnit != null && combatUnit != null)return 2;
        else if(civilUnit == null && combatUnit == null)return 0;
        return 1;
    }

    public boolean hasCombatUnit(){
        return true;
    }

    public boolean hasCivilUnit(){
        return true;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setCivilUnit(Unit civilUnit) {
        this.civilUnit = civilUnit;
    }

    public void setCombatUnit(Unit combatUnit) {
        this.combatUnit = combatUnit;
    }

    public Unit getCivilUnit() {
        return civilUnit;
    }

    public Unit getCombatUnit() {
        return combatUnit;
    }

    public int calculateMp(){
        return 0;
    }
}
