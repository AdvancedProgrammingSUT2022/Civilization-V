package Model.Units.NonCombat;



import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.TileRelated.Tile.Tile;
import Model.Units.Unit;
import Model.Units.TypeEnums.UnitType;

public class NonCombat extends Unit {
    
    public NonCombat(Civilization civilization, Tile tile, UnitType unitType) {
        super(civilization, tile, unitType);
        //TODO Auto-generated constructor stub
    }

    public void updateDataAfterAction(City city){

    }
}
