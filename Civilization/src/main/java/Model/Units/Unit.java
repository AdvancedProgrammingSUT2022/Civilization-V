package Model.Units;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.TileRelated.Tile.Tile;
import Model.Units.TypeEnums.UnitStateType;
import Model.Units.TypeEnums.UnitType;

public class Unit {
    protected Civilization civilization;
    protected City city;
    protected Tile tile;
    protected int movementsLeft;
    protected UnitType unitType;
    protected UnitStateType unitStateType;
    protected int maxDamage;

    public Unit(Civilization civilization, City city, Tile tile, UnitType unitType) {
        this.civilization = civilization;
        this.civilization.addUnit(this);
        this.city = city;
        this.tile = tile;
        this.setUnitType(unitType);
        this.setUnitStateType(UnitStateType.NORMAL);
        this.setMovementsLeft(unitType.movement);
    }

    public int getMovementsLeft() {
        return movementsLeft;
    }

    public void setMovementsLeft(int movementsLeft) {
        this.movementsLeft = movementsLeft;
    }

    public UnitStateType getUnitStateType() {
        return unitStateType;
    }

    public void setUnitStateType(UnitStateType unitStateType) {
        this.unitStateType = unitStateType;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    public void setAlerted(){

    }

    public void move(Tile dest){

    }

    public void setSleep(){

    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public void specialAbility(){

    }
}
