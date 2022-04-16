package Model.Units.Combat.Ranged.SiegeWeapons;

import java.util.ArrayList;

import Model.City;
import Model.Civilization;
import Model.Technology.Technology;
import Model.TileAndFeatures.Resource.Resource;
import Model.TileAndFeatures.Tile.Tile;

public class Catapult extends Siege {

    public Catapult(Civilization civilization, City city, Tile tile, int movement, int cost,
            Technology technologyRequired, ArrayList<Resource> resourcesRequired, int maxCombatStrength,
            int rangedStrength, boolean preAttackDone) {
        super(civilization, city, tile, movement, cost, technologyRequired, resourcesRequired, maxCombatStrength,
                rangedStrength, preAttackDone);
        //TODO Auto-generated constructor stub
    }

    

    
}
