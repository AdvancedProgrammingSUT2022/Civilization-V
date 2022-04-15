package Model.Resource;

import Model.Improvement.Improvement;
import Model.Technology.Technology;
import Model.Technology.TechnologyType;
import Model.TerrainFeature.TerrainFeatureType;
import Model.Terrian.Terrain;
import Model.Terrian.TerrainType;

import java.util.ArrayList;

public enum ResourceType {
//    // Bonus Resources
    Bananas(1, 0, 0,null, new ArrayList<TerrainFeatureType>(){{
        add(TerrainFeatureType.Forest);
    }}, Improvement.Plantation, null),

    Cattle(1, 0, 0, new ArrayList<TerrainType>(){{
        add(TerrainType.Grassland);
    }},null,  Improvement.Pasture, null),
    Deer(1, 0, 0, new ArrayList<TerrainType>(){{
        add(TerrainType.Hill);
        add(TerrainType.Tundra);
    }}, new ArrayList<TerrainFeatureType>(){{
        add(TerrainFeatureType.Forest);
    }}, Improvement.Camp, null),

    Sheep(2, 0, 0, new ArrayList<TerrainType>(){{
        add(TerrainType.Grassland);
        add(TerrainType.Plains);
        add(TerrainType.Desert);
        add(TerrainType.Hill);
    }}, null, Improvement.Pasture, null),

    Wheat(1, 0, 0, new ArrayList<TerrainType>(){{
        add(TerrainType.Plains);
    }}, new ArrayList<TerrainFeatureType>(){{
        add(TerrainFeatureType.FloodPlains);
    }}, Improvement.Farm, null),
//    // Strategic Resources
    Coal(0, 1, 0, new ArrayList<TerrainType>(){{
        add(TerrainType.Grassland);
        add(TerrainType.Hill);
        add(TerrainType.Plains);
    }}, null, Improvement.Mine, TechnologyType.ScientificTheory),

    Horses(0, 1, 0, new ArrayList<TerrainType>(){{
        add(TerrainType.Plains);
        add(TerrainType.Tundra);
        add(TerrainType.Grassland);
    }}, null, Improvement.Pasture, TechnologyType.AnimalHusbandry),

    Iron(0, 1, 0, new ArrayList<TerrainType>(){{
        add(TerrainType.Hill);
        add(TerrainType.Desert);
        add(TerrainType.Plains);
        add(TerrainType.Tundra);
        add(TerrainType.Grassland);
        add(TerrainType.Snow);
    }}, null, Improvement.Mine, TechnologyType.IronWorking);

//    // Luxury Resources
//    Cotton,
//    Dyes,
//    Furs,
//    Gems,
//    Gold,
//    Incense,
//    Ivory,
//    Marble,
//    Silk,
//    Silver,
//    Sugar

    private final int Food;
    private final int production;
    private final int Gold;
    private final ArrayList<TerrainType> canBeFoundOn;
    private final ArrayList<TerrainFeatureType> canBeFoundOnFeatures;
    private final Improvement improvementNeeded;
    private final TechnologyType raquiredTechology;


    ResourceType(int food, int production, int gold, ArrayList<TerrainType> canBeFoundOn, ArrayList<TerrainFeatureType> canBeFoundOnFeatures, Improvement improvementNeeded, TechnologyType requiredTechnology) {
        this.Food = food;
        this.Gold = gold;
        this.production = production;
        this.canBeFoundOn = canBeFoundOn;
        this.canBeFoundOnFeatures = canBeFoundOnFeatures;
        this.improvementNeeded = improvementNeeded;
        this.raquiredTechology = requiredTechnology;
    }

}
