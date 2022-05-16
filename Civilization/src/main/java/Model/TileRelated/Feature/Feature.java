package Model.TileRelated.Feature;

import Model.Units.NonCombat.Worker;



public class Feature {
    private FeatureType featureType;
    private Worker worker;
    private int daysToClear;
    public Feature(FeatureType featureType){
        this.setFeatureType(featureType);
    }
    //penalties
    public FeatureType getFeatureType() {
        return featureType;
    }
    public void setFeatureType(FeatureType featureType) {
        this.featureType = featureType;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public int getDaysToClear() {
        return daysToClear;
    }

    public void changeDaysToClear(int daysToClear) {
        this.daysToClear += daysToClear;
    }

    public Worker getWorker() {
        return worker;
    }
}
