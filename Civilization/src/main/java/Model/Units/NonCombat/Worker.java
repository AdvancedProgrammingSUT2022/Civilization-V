package Model.Units.NonCombat;

import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.TileRelated.Improvement.Improvement;
import Model.TileRelated.Tile.Tile;
import Model.Units.TypeEnums.UnitType;

public class Worker extends NonCombat{
    public Worker(Civilization civilization, Tile tile) {
        super(civilization, tile,UnitType.Worker);
    }
    private Improvement improvement;
    
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
