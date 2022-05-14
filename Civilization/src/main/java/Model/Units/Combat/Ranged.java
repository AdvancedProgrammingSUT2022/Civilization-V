package Model.Units.Combat;
import Model.CivlizationRelated.Civilization;
import Model.TileRelated.Tile.Tile;
import Model.Units.TypeEnums.UnitType;

public class Ranged extends Combat {
    
    public Ranged(Civilization civilization, Tile tile, UnitType unitType) {
        super(civilization, tile, unitType);
    }
    int rangedStrength;
}
