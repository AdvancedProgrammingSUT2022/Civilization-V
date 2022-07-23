package Model.Units.Combat;
import Model.CivlizationRelated.Civilization;
import Model.TileRelated.Tile.Tile;
import Model.Units.TypeEnums.UnitType;
import com.google.gson.annotations.Expose;

public class Siege extends Ranged {
    public Siege(Civilization civilization, Tile tile, UnitType unitType) {
        super(civilization, tile, unitType);
        //TODO Auto-generated constructor stub
    }
    public Siege() {
    }
    @Expose
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
