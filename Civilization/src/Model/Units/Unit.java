package Model.Units;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.TileAndFeatures.Tile.Tile;

public class Unit {
    protected Civilization civilization;
    protected City city;
    protected Tile tile;
    protected int movementsLeft;
    protected UnitType unitType;
    protected UnitStateType unitStateType;

    public Unit(Civilization civilization, City city, Tile tile, UnitType unitType) {
        this.civilization = civilization;
        this.city = city;
        this.tile = tile;
        this.unitType = unitType;
        this.unitStateType = UnitStateType.NORMAL;
        this.movementsLeft = unitType.movement;
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
