package Model.Units.Combat.Ranged.SiegeWeapons;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.TileAndFeatures.Tile.Tile;
import Model.Units.UnitType;
import Model.Units.Combat.Ranged.Ranged;

public class Trebuchet extends Ranged {
    public Trebuchet(Civilization civilization, City city, Tile tile) {
        super(civilization, city, tile, UnitType.Trebuchet);
    }
}
