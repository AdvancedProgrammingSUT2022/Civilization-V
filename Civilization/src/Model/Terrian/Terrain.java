package Model.Terrian;

import Model.Units.Unit;

public class Terrain {
    private int x;
    private int y;
    private Unit civilUnit;
    private Unit combatUnit;

    TerrainType terrainType;

    public int getX() {
        return x;
    }

    public int getY() {
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
}
