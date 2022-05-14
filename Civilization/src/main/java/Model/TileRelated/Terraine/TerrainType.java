package Model.TileRelated.Terraine;

import Model.TileRelated.Feature.Feature;
import Model.TileRelated.Feature.FeatureType;
import Model.TileRelated.Resource.Resource;
import Model.TileRelated.Resource.ResourceType;

import java.util.ArrayList;

public enum TerrainType {
    Desert(0, 0, 0, -33, 1, new ArrayList<FeatureType>(){{
        add(FeatureType.Oasis);
        add(FeatureType.FloodPlains);
    }}, new ArrayList<ResourceType>(){{
        add(ResourceType.Iron);
        add(ResourceType.GoldResource);
        add(ResourceType.Marble);
        add(ResourceType.Silver);
        add(ResourceType.Gems);
        add(ResourceType.Cotton);
        add(ResourceType.Incense);
        add(ResourceType.Sheep);
    }}),
    Grassland(2, 0, 0, -33, 1, new ArrayList<FeatureType>(){{
        add(FeatureType.Forest);
        add(FeatureType.Marsh);
    }}, new ArrayList<ResourceType>(){{
        add(ResourceType.Iron);
        add(ResourceType.Horses);
        add(ResourceType.Coal);
        add(ResourceType.Cattle);
        add(ResourceType.GoldResource);
        add(ResourceType.Gems);
        add(ResourceType.Marble);
        add(ResourceType.Cotton);
        add(ResourceType.Sheep);
    }}),
    Hill(0, 2, 0, 25, 2, new ArrayList<FeatureType>(){{
        add(FeatureType.Forest);
        add(FeatureType.Jungle);
    }}, new ArrayList<ResourceType>(){{
        add(ResourceType.Iron);
        add(ResourceType.Coal);
        add(ResourceType.Deer);
        add(ResourceType.GoldResource);
        add(ResourceType.Silver);
        add(ResourceType.Gems);
        add(ResourceType.Marble);
        add(ResourceType.Sheep);
    }}),
    Mountain(0, 0, 0, 25, null, null),
    Ocean(0, 0, 0, 25, null, null),
    Plains(1, 1, 0, -33, 1 , new ArrayList<FeatureType>(){{
        add(FeatureType.Forest);
        add(FeatureType.Jungle);
    }}, new ArrayList<ResourceType>(){{
        add(ResourceType.Iron);
        add(ResourceType.Horses);
        add(ResourceType.Coal);
        add(ResourceType.GoldResource);
        add(ResourceType.Gems);
        add(ResourceType.Marble);
        add(ResourceType.Ivory);
        add(ResourceType.Cotton);
        add(ResourceType.Incense);
        add(ResourceType.Sheep);
    }}),
    Snow(0, 0, 0, -33, 1, null, new ArrayList<ResourceType>(){{
        add(ResourceType.Iron);
    }}),
    Tundra(1, 0, 0, -33, 1, new ArrayList<FeatureType>(){{
        add(FeatureType.Forest);
    }}, new ArrayList<ResourceType>(){{
        add(ResourceType.Iron);
        add(ResourceType.Horses);
        add(ResourceType.Deer);
        add(ResourceType.Silver);
        add(ResourceType.Gems);
        add(ResourceType.Marble);
    }});
    public final int food;
    public final int production;
    public final int gold;
    public final int combatModifier;
    public int movementCost;
    public final ArrayList<FeatureType> possibleFeatures;
    public final ArrayList<ResourceType> possibleResources;

    TerrainType(int food, int production, int gold, int combatModifier, int movementCost, ArrayList<FeatureType> possibleFeatures, ArrayList<ResourceType> possibleResources) {
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.combatModifier = combatModifier;
        this.movementCost = movementCost;
        this.possibleFeatures = possibleFeatures;
        this.possibleResources = possibleResources;
    }
    TerrainType(int food, int production, int gold, int combatModifier, ArrayList<FeatureType> possibleFeatures, ArrayList<ResourceType> possibleResources) {
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.combatModifier = combatModifier;
        this.possibleFeatures = possibleFeatures;
        this.possibleResources = possibleResources;
    }
    public ArrayList<ResourceType> getPossibleResources(){
        return this.possibleResources;
    }
    
    public ArrayList<FeatureType> getPossibleFeatures(){
        return this.possibleFeatures;
    }
}
