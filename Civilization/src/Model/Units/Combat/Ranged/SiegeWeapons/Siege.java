package Model.Units.Combat.Ranged.SiegeWeapons;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.TileAndFeatures.Tile.Tile;
import Model.Units.UnitType;
import Model.Units.Combat.Ranged.Ranged;

public class Siege extends Ranged {
    public Siege(Civilization civilization, City city, Tile tile, UnitType unitType) {
        super(civilization, city, tile, unitType);
        //TODO Auto-generated constructor stub
    }
    protected boolean preAttackDone;
    
    public boolean isPreAttackDone() {
        return preAttackDone;
    }
    public void setPreAttackDone(boolean preAttackDone) {
        this.preAttackDone = preAttackDone;
    }
    public void doPreAttack(){
        preAttackDone = true;
    }
}
