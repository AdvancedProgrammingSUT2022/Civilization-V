package Model.Resource;

import Model.Improvement.Improvement;
import Model.Technology.TechnologyType;
import Model.Tile.TileType;
import Model.Feature.FeatureType;

import java.util.ArrayList;

public enum ResourceType {
//    // Bonus Resources
    Bananas(1, 0, 0,null, new ArrayList<FeatureType>(){{   
        add(FeatureType.Forest);
    }}, Improvement.Plantation, null,
    ResourceMainTypes.BonusResources),

    Cattle(1, 0, 0, new ArrayList<TileType>(){{
        add(TileType.Grassland);
    }},null,  Improvement.Pasture, null,
    ResourceMainTypes.BonusResources),

    Deer(1, 0, 0, new ArrayList<TileType>(){{
        add(TileType.Hill);
        add(TileType.Tundra);
    }}, new ArrayList<FeatureType>(){{
        add(FeatureType.Forest);
    }}, Improvement.Camp, null,
    ResourceMainTypes.BonusResources),

    Sheep(2, 0, 0, new ArrayList<TileType>(){{
        add(TileType.Grassland);
        add(TileType.Plains);
        add(TileType.Desert);
        add(TileType.Hill);
    }}, null, Improvement.Pasture, null,
    ResourceMainTypes.BonusResources),

    Wheat(1, 0, 0, new ArrayList<TileType>(){{
        add(TileType.Plains);
    }}, new ArrayList<FeatureType>(){{
        add(FeatureType.FloodPlains);
    }}, Improvement.Farm, null,
    ResourceMainTypes.BonusResources),
//    // Strategic Resources
    Coal(0, 1, 0, new ArrayList<TileType>(){{
        add(TileType.Grassland);
        add(TileType.Hill);
        add(TileType.Plains);
    }}, null, Improvement.Mine, TechnologyType.ScientificTheory,
    ResourceMainTypes.StrategicResources),

    Horses(0, 1, 0, new ArrayList<TileType>(){{
        add(TileType.Plains);
        add(TileType.Tundra);
        add(TileType.Grassland);
    }}, null, Improvement.Pasture, TechnologyType.AnimalHusbandry,
    ResourceMainTypes.StrategicResources),

    Iron(0, 1, 0, new ArrayList<TileType>(){{
        add(TileType.Hill);
        add(TileType.Desert);
        add(TileType.Plains);
        add(TileType.Tundra);
        add(TileType.Grassland);
        add(TileType.Snow);
    }}, null, Improvement.Mine, TechnologyType.IronWorking,ResourceMainTypes.StrategicResources);

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

    public final int Food;
    public final int production;
    public final int Gold;
    public final ResourceMainTypes mainType;
    public final ArrayList<TileType> canBeFoundOn;
    public final ArrayList<FeatureType> canBeFoundOnFeatures;
    public final Improvement improvementNeeded;
    public final TechnologyType raquiredTechology;


    ResourceType(int food, int production, int gold, ArrayList<TileType> canBeFoundOn, ArrayList<FeatureType> canBeFoundOnFeatures, Improvement improvementNeeded, TechnologyType requiredTechnology,ResourceMainTypes mainType) {
        this.Food = food;
        this.Gold = gold;
        this.production = production;
        this.canBeFoundOn = canBeFoundOn;
        this.canBeFoundOnFeatures = canBeFoundOnFeatures;
        this.improvementNeeded = improvementNeeded;
        this.raquiredTechology = requiredTechnology;
        this.mainType = mainType;
    }

}
