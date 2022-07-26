package Model.TileRelated.Feature;

import Model.TileRelated.Resource.Resource;
import Model.TileRelated.Resource.ResourceType;
import Model.TileRelated.Terraine.TerrainType;

import java.util.ArrayList;

public enum FeatureType {
    FloodPlains(2, 0, 0, -33, 1),
    Forest(1, 1, 0, 25, 2),
    Ice(0, 0 , 0, 0),
    Jungle(1, -1, 0, 25, 2),
    Marsh(-1, 0, 0, -33, 2),
    Oasis(3, 0, 1, -33, 1);
    //    Rivers(0, 0, 1, 0, 0/* I IIIIIII*/, null);
    public final int Food;
    public final int production;
    public final int Gold;
    public final int combatModifier;
    public int movementCost;
    public  ArrayList<Resource> possibleResources;

    static {
        FloodPlains.setter(new ArrayList<Resource>(){{
            Resource Wheat = new Resource(ResourceType.Wheat);
            Resource Sugar = new Resource(ResourceType.Sugar);
            add(Wheat);
            add(Sugar);
        }});
        Jungle.setter( new ArrayList<Resource>(){{
            Resource Banana = new Resource(ResourceType.Bananas);
            Resource Gem = new Resource(ResourceType.Gems);
            Resource Dyes = new Resource(ResourceType.Dyes);
            Resource Spices = new Resource(ResourceType.Spices);
            add(Banana);
            add(Gem);
            add(Dyes);
            add(Spices);
        }});
        Marsh.setter( new ArrayList<Resource>(){{
            Resource Sugar = new Resource(ResourceType.Sugar);
            add(Sugar);
        }});
    }

    private void setter(ArrayList<Resource> possibleResources){
        this.possibleResources = possibleResources;
    }

    FeatureType(int food, int production, int gold, int combatModifier, int movementCost) {
        Food = food;
        this.production = production;
        Gold = gold;
        this.combatModifier = combatModifier;
        this.movementCost = movementCost;
    }
    FeatureType(int food, int production, int gold, int combatModifier) {
        Food = food;
        this.production = production;
        Gold = gold;
        this.combatModifier = combatModifier;
    }
    public ArrayList<Resource> getPossibleResources(){
        return this.possibleResources;
    }

}

