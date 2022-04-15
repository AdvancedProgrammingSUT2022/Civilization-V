package Model.Units.Combat.NonRanged;

import java.util.ArrayList;

import Model.City;
import Model.Civilization;
import Model.Technology.Technology;
import Model.TileAndFeatures.Resource.Resource;
import Model.TileAndFeatures.Tile.Tile;
import Model.Units.Combat.Combat;

public class Scout extends Combat{

    public Scout(Civilization civilization, City city, Tile tile, int movement, int cost, Technology technologyRequired,
            ArrayList<Resource> resourcesRequired, int maxCombatStrength) {
        super(civilization, city, tile, movement, cost, technologyRequired, resourcesRequired, maxCombatStrength);
        //TODO Auto-generated constructor stub
    }
    
}