package Model.Units.Combat.Ranged.SiegeWeapons;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.TileAndFeatures.Tile.Tile;
import Model.Units.UnitType;

public class Catapult extends Siege {

    public Catapult(Civilization civilization, City city, Tile tile) {
        super(civilization, city, tile, UnitType.Catapult);
    }
}
