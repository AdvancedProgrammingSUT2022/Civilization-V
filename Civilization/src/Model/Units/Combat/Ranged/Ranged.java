package Model.Units.Combat.Ranged;
import java.util.ArrayList;

import Model.City;
import Model.Civilization;
import Model.Technology.Technology;
import Model.TileAndFeatures.Resource.Resource;
import Model.TileAndFeatures.Tile.Tile;
import Model.Units.Combat.Combat;

public class Ranged extends Combat {
    

    public Ranged(Civilization civilization, City city, Tile tile, int movement, int cost,
            Technology technologyRequired, ArrayList<Resource> resourcesRequired, int maxCombatStrength,int rangedStrength) {
        super(civilization, city, tile, movement, cost, technologyRequired, resourcesRequired, maxCombatStrength);
        this.rangedStrength = rangedStrength;
        //TODO Auto-generated constructor stub
    }

    private int rangedStrength;

    public int getRangedStrength() {
        return rangedStrength;
    }

    public void setRangedStrength(int rangedStrength) {
        this.rangedStrength = rangedStrength;
    }

    public void doRangedAttack(){
        
    }
}
