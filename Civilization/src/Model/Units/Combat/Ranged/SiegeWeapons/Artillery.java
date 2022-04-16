package Model.Units.Combat.Ranged.SiegeWeapons;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.TileAndFeatures.Tile.Tile;
import Model.Units.UnitType;
import Model.Units.Combat.Ranged.Ranged;

public class Artillery extends Ranged {

    public Artillery(Civilization civilization, City city, Tile tile) {
        super(civilization, city, tile, UnitType.Artillery);
    }

    
}
