package Model.TileRelated.Resource;

import Model.Technology.TechnologyType;
import Model.TileRelated.Feature.FeatureType;
import Model.TileRelated.Improvement.ImprovementType;
import Model.TileRelated.Terraine.TerrainType;

import java.util.ArrayList;

public enum ResourceType {
    // Bonus Resources
    Bananas(1, 0, 0,null, new ArrayList<FeatureType>(){{   
        add(FeatureType.Forest);
    }}, ImprovementType.Plantation, null,
            ResourceMainTypes.BonusResources),

    Cattle(1, 0, 0, new ArrayList<TerrainType>(){{
        add(TerrainType.Grassland);
    }},null,  ImprovementType.Pasture, null,
            ResourceMainTypes.BonusResources),

    Deer(1, 0, 0, new ArrayList<TerrainType>(){{
        add(TerrainType.Hill);
        add(TerrainType.Tundra);
    }}, new ArrayList<FeatureType>(){{
        add(FeatureType.Forest);
    }}, ImprovementType.Camp, null,
            ResourceMainTypes.BonusResources),

    Sheep(2, 0, 0, new ArrayList<TerrainType>(){{
        add(TerrainType.Grassland);
        add(TerrainType.Plains);
        add(TerrainType.Desert);
        add(TerrainType.Hill);
    }}, null, ImprovementType.Pasture, null,
            ResourceMainTypes.BonusResources),

    Wheat(1, 0, 0, new ArrayList<TerrainType>(){{
        add(TerrainType.Plains);
    }}, new ArrayList<FeatureType>(){{
        add(FeatureType.FloodPlains);
    }}, ImprovementType.Farm, null,
            ResourceMainTypes.BonusResources),
    // Strategic Resources
    Coal(0, 1, 0, new ArrayList<TerrainType>(){{
        add(TerrainType.Grassland);
        add(TerrainType.Hill);
        add(TerrainType.Plains);
    }}, null, ImprovementType.Mine, TechnologyType.ScientificTheory,
            ResourceMainTypes.StrategicResources),

    Horses(0, 1, 0, new ArrayList<TerrainType>(){{
        add(TerrainType.Plains);
        add(TerrainType.Tundra);
        add(TerrainType.Grassland);
    }}, null, ImprovementType.Pasture, TechnologyType.AnimalHusbandry,
            ResourceMainTypes.StrategicResources),

    Iron(0, 1, 0, new ArrayList<TerrainType>(){{
        add(TerrainType.Hill);
        add(TerrainType.Desert);
        add(TerrainType.Plains);
        add(TerrainType.Tundra);
        add(TerrainType.Grassland);
        add(TerrainType.Snow);
    }}, null, ImprovementType.Mine, TechnologyType.IronWorking,
            ResourceMainTypes.StrategicResources),
    // Luxury Resources
    Cotton(0, 0,2,new ArrayList<TerrainType>(){{
        add(TerrainType.Grassland);
        add(TerrainType.Plains);
        add(TerrainType.Desert);
    }}, null, ImprovementType.Plantation, null,
            ResourceMainTypes.LuxuryResources),
    Dyes(0, 0, 2, null, new ArrayList<FeatureType>(){{
        add(FeatureType.Forest);
        add(FeatureType.Jungle);
    }}, ImprovementType.Plantation, null,
            ResourceMainTypes.LuxuryResources),
    Furs(0, 0, 2, new ArrayList<TerrainType>(){{
        add(TerrainType.Tundra);
    }}, new ArrayList<FeatureType>(){{
        add(FeatureType.Forest);
    }}, ImprovementType.Camp, null,
            ResourceMainTypes.LuxuryResources),
    Gems(0, 0, 3, new ArrayList<TerrainType>(){{
        add(TerrainType.Grassland);
        add(TerrainType.Tundra);
        add(TerrainType.Desert);
        add(TerrainType.Hill);
        add(TerrainType.Plains);
    }}, new ArrayList<FeatureType>(){{
        add(FeatureType.Jungle);
    }}, ImprovementType.Mine, null,
            ResourceMainTypes.LuxuryResources),
    GoldResource(0, 0, 2, new ArrayList<TerrainType>(){{
        add(TerrainType.Plains);
        add(TerrainType.Hill);
        add(TerrainType.Grassland);
        add(TerrainType.Desert);
    }}, null, ImprovementType.Mine, null,
            ResourceMainTypes.LuxuryResources),
    Incense(0, 0, 2, new ArrayList<TerrainType>(){{
        add(TerrainType.Plains);
        add(TerrainType.Hill);
    }}, null, ImprovementType.Plantation, null,
            ResourceMainTypes.LuxuryResources),
    Ivory(0, 0, 2, new ArrayList<TerrainType>(){{
        add(TerrainType.Plains);
    }}, null, ImprovementType.Camp, null,
            ResourceMainTypes.LuxuryResources),
    Marble(0, 0, 2, new ArrayList<TerrainType>(){{
        add(TerrainType.Plains);
        add(TerrainType.Hill);
        add(TerrainType.Tundra);
        add(TerrainType.Desert);
        add(TerrainType.Grassland);
    }}, null, ImprovementType.Quarry, null,
            ResourceMainTypes.LuxuryResources),
    Silk(0, 0, 2, null, new ArrayList<FeatureType>(){{
        add(FeatureType.Forest);
    }}, ImprovementType.Plantation, null,
            ResourceMainTypes.LuxuryResources),
    Silver(0 , 0, 2, new ArrayList<TerrainType>(){{
        add(TerrainType.Desert);
        add(TerrainType.Hill);
        add(TerrainType.Tundra);
    }}, null, ImprovementType.Plantation, null,
            ResourceMainTypes.LuxuryResources),
    Spices(0, 0, 2, null, new ArrayList<FeatureType>(){{
        add(FeatureType.Jungle);
    }}, ImprovementType.Plantation, null,
            ResourceMainTypes.LuxuryResources),
    Sugar(0, 0, 2, null, new ArrayList<FeatureType>(){{
        add(FeatureType.FloodPlains);
        add(FeatureType.Marsh);
    }}, ImprovementType.Plantation, null,
            ResourceMainTypes.LuxuryResources);

    public final int Food;
    public final int production;
    public final int Gold;
    public final ResourceMainTypes mainType;
    public final ArrayList<TerrainType> canBeFoundOn;
    public final ArrayList<FeatureType> canBeFoundOnFeatures;
    public final ImprovementType improvementNeeded;
    public final TechnologyType requiredTechnology;


    ResourceType(int food, int production, int gold, ArrayList<TerrainType> canBeFoundOn, ArrayList<FeatureType> canBeFoundOnFeatures, ImprovementType improvementNeeded, TechnologyType requiredTechnology,ResourceMainTypes mainType) {
        this.Food = food;
        this.Gold = gold;
        this.production = production;
        this.canBeFoundOn = canBeFoundOn;
        this.canBeFoundOnFeatures = canBeFoundOnFeatures;
        this.improvementNeeded = improvementNeeded;
        this.requiredTechnology = requiredTechnology;
        this.mainType = mainType;
    }

}
