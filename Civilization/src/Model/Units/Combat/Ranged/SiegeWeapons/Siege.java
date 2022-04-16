package Model.Units.Combat.Ranged.SiegeWeapons;

import java.util.ArrayList;

import Model.City;
import Model.Civilization;
import Model.Technology.Technology;
import Model.TileAndFeatures.Resource.Resource;
import Model.TileAndFeatures.Tile.Tile;
import Model.Units.Combat.Ranged.Ranged;

public class Siege extends Ranged {
    protected boolean preAttackDone;
    public Siege(Civilization civilization, City city, Tile tile, int movement, int cost, Technology technologyRequired,
            ArrayList<Resource> resourcesRequired, int maxCombatStrength, int rangedStrength,boolean preAttackDone) {
        super(civilization, city, tile, movement, cost, technologyRequired, resourcesRequired, maxCombatStrength,
                rangedStrength);
        this.setPreAttackDone(preAttackDone);
    }
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
