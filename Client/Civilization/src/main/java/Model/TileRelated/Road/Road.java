package Model.TileRelated.Road;

import Model.CivlizationRelated.Civilization;
import Model.Units.NonCombat.Worker;
import com.google.gson.annotations.Expose;

public class Road {
    @Expose
    private RoadType roadType;
    @Expose
    private int daysToComplete;
    @Expose
    private boolean ruined = false;
    private Civilization civilization;
    @Expose
    private Worker worker;


    public Road(RoadType roadType,int daysToComplete , Civilization civilization){
        this.roadType = roadType;
        this.daysToComplete = daysToComplete;
        this.civilization = civilization;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public RoadType getRoadType() {
        return roadType;
    }

    public int getDaysToComplete() {
        return daysToComplete;
    }

    public boolean isRuined() {
        return ruined;
    }

    public Civilization getCivilization() {
        return civilization;
    }

    public void setRoadType(RoadType roadType) {
        this.roadType = roadType;
    }

    public void changeDaysToComplete(int daysToComplete) {
        this.daysToComplete += daysToComplete;
    }

    public void setRuined(boolean ruined) {
        this.ruined = ruined;
    }

    public void setCivilization(Civilization civilization) {
        this.civilization = civilization;
    }
}
