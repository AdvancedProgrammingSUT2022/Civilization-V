package Model.TileRelated.Feature;

import Model.TileRelated.Resource.Resource;
import Model.TileRelated.Resource.ResourceType;

import java.util.ArrayList;

public enum FeatureType {
    FloodPlains(2, 0, 0, -33, 1, new ArrayList<Resource>(){{
        Resource Wheat = new Resource(ResourceType.Wheat);
        Resource Sugar = new Resource(ResourceType.Sugar);
        add(Wheat);
        add(Sugar);
    }}),
    Forest(1, 1, 0, 25, 2,null),
    Ice(0, 0 , 0, 0, 0, null),
    Jungle(1, -1, 0, 25, 2, new ArrayList<Resource>(){{
        Resource Banana = new Resource(ResourceType.Bananas);
        Resource Gem = new Resource(ResourceType.Gems);
        Resource Dyes = new Resource(ResourceType.Dyes);
        Resource Spices = new Resource(ResourceType.Spices);
        add(Banana);
        add(Gem);
        add(Dyes);
        add(Spices);
    }}),
    Marsh(-1, 0, 0, -33, 2, new ArrayList<Resource>(){{
        Resource Sugar = new Resource(ResourceType.Sugar);
        add(Sugar);
    }}),
    Oasis(3, 0, 1, -33, 1, null),
    Rivers(0, 0, 1, 0, 0/* I IIIIIII*/, null);
    public final int Food;
    public final int production;
    public final int Gold;
    public final int combatModifier;
    public final int movementCost;
    public final ArrayList<Resource> possibleResources;


    FeatureType(int food, int production, int gold, int combatModifier, int movementCost, ArrayList<Resource> possibleResources) {
        Food = food;
        this.production = production;
        Gold = gold;
        this.combatModifier = combatModifier;
        this.movementCost = movementCost;
        this.possibleResources = possibleResources;
    }

}
