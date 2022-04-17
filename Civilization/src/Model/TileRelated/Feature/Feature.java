package Model.TileRelated.Feature;

import Model.TileRelated.Resource.Resource;

import java.util.ArrayList;

public class Feature {
    public int mpCost;
    public int defenseBonus;
    public ArrayList<Resource> possibleResources;
    private FeatureType featureType;
    public Feature(FeatureType featureType){
        this.featureType = featureType;
    }
    //penalties
}
