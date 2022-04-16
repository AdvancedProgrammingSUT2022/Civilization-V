package Model.Units.NonCombat;

import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.TileAndFeatures.Tile.Tile;
import Model.Units.UnitType;

public class Settler extends NonCombat {

    
    public Settler(Civilization civilization, City city, Tile tile) {
        super(civilization, city, tile,UnitType.Settler);
    }
    public void buildCity(){
        
    }
    public int calculateProductionAfterBuildingCity(){
        return 0;
    }
    public int calculateGoldAfterBuildingCity(){
        return 0;
    }
}
