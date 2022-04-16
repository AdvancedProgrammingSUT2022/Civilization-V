package Model.Units.NonCombat;



import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.TileAndFeatures.Tile.Tile;
import Model.Units.Unit;
import Model.Units.UnitType;

public class NonCombat extends Unit {
    
    public NonCombat(Civilization civilization, City city, Tile tile, UnitType unitType) {
        super(civilization, city, tile, unitType);
        //TODO Auto-generated constructor stub
    }

    public void updateDataAfterAction(City city){

    }
}
