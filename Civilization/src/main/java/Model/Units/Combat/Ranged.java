package Model.Units.Combat;
import Controller.GameController.UnitController;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.TileRelated.Tile.Tile;
import Model.Units.TypeEnums.UnitType;

public class Ranged extends Combat {
    int rangedStrength;
    public Ranged(Civilization civilization, City city, Tile tile, UnitType unitType) {
        super(civilization, city, tile, unitType);
        //TODO Auto-generated constructor stub
    }
    public double cityAttackDamage(City city){
        return UnitController.getInstance().calculateDamageDeltToCity(this, city);
    }
}
