package Model.Units.Combat.Ranged.SiegeWeapons;

import java.util.ArrayList;

import Model.City;
import Model.Civilization;
import Model.Technology.Technology;
import Model.TileAndFeatures.Resource.Resource;
import Model.TileAndFeatures.Tile.Tile;
import Model.Units.Combat.Ranged.Ranged;

public class Trebuchet extends Ranged {

    public Trebuchet(Civilization civilization, City city, Tile tile, int movement, int cost,
            Technology technologyRequired, ArrayList<Resource> resourcesRequired, int maxCombatStrength,
            int rangedStrength) {
        super(civilization, city, tile, movement, cost, technologyRequired, resourcesRequired, maxCombatStrength,
                rangedStrength);
        //TODO Auto-generated constructor stub
    }

    
    
}
