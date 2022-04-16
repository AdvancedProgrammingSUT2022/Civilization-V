package Model.Units.NonCombat;

import java.util.ArrayList;

import Model.City;
import Model.Civilization;
import Model.Technology.Technology;
import Model.TileAndFeatures.Improvement.Improvement;
import Model.TileAndFeatures.Resource.Resource;
import Model.TileAndFeatures.Tile.Tile;

public class Worker extends NonCombat{
    private Improvement improvement;
    public Worker(Civilization civilization, City city, Tile tile, int movement, int cost,
            Technology technologyRequired, ArrayList<Resource> resourcesRequired, boolean isSleep, boolean isAlerted) {
        super(civilization, city, tile, movement, cost, technologyRequired, resourcesRequired);
    }
    public Improvement getImprovement() {
        return improvement;
    }
    public void setImprovement(Improvement improvement) {
        this.improvement = improvement;
    }
    public void queueImprovementBuilding(Improvement improvement,Civilization civilization){

    }
    @Override
    public void updateDataAfterAction(City city){

    }
}
