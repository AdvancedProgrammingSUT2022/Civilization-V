package Model.Units.Combat.NonRanged;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.TileAndFeatures.Tile.Tile;
import Model.Units.UnitType;
import Model.Units.Combat.Combat;

public class Pikeman extends Combat {
    public Pikeman(Civilization civilization, City city, Tile tile) {
        super(civilization, city, tile, UnitType.Pikeman);
        //TODO Auto-generated constructor stub
    }
}
