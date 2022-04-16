package Model.TileAndFeatures.Resource;

import Model.Technology.TechnologyType;
import Model.TileAndFeatures.Feature.FeatureType;
import Model.TileAndFeatures.Improvement.ImprovementType;
import Model.TileAndFeatures.Terraine.TerraineType;

import java.util.ArrayList;

public enum ResourceType {
//    // Bonus Resources
    Bananas(1, 0, 0,null, new ArrayList<FeatureType>(){{   
        add(FeatureType.Forest);
    }}, ImprovementType.Plantation, null,
    ResourceMainTypes.BonusResources),

    Cattle(1, 0, 0, new ArrayList<TerraineType>(){{
        add(TerraineType.Grassland);
    }},null,  ImprovementType.Pasture, null,
    ResourceMainTypes.BonusResources),

    Deer(1, 0, 0, new ArrayList<TerraineType>(){{
        add(TerraineType.Hill);
        add(TerraineType.Tundra);
    }}, new ArrayList<FeatureType>(){{
        add(FeatureType.Forest);
    }}, ImprovementType.Camp, null,
    ResourceMainTypes.BonusResources),

    Sheep(2, 0, 0, new ArrayList<TerraineType>(){{
        add(TerraineType.Grassland);
        add(TerraineType.Plains);
        add(TerraineType.Desert);
        add(TerraineType.Hill);
    }}, null, ImprovementType.Pasture, null,
    ResourceMainTypes.BonusResources),

    Wheat(1, 0, 0, new ArrayList<TerraineType>(){{
        add(TerraineType.Plains);
    }}, new ArrayList<FeatureType>(){{
        add(FeatureType.FloodPlains);
    }}, ImprovementType.Farm, null,
    ResourceMainTypes.BonusResources),
//    // Strategic Resources
    Coal(0, 1, 0, new ArrayList<TerraineType>(){{
        add(TerraineType.Grassland);
        add(TerraineType.Hill);
        add(TerraineType.Plains);
    }}, null, ImprovementType.Mine, TechnologyType.ScientificTheory,
    ResourceMainTypes.StrategicResources),

    Horses(0, 1, 0, new ArrayList<TerraineType>(){{
        add(TerraineType.Plains);
        add(TerraineType.Tundra);
        add(TerraineType.Grassland);
    }}, null, ImprovementType.Pasture, TechnologyType.AnimalHusbandry,
    ResourceMainTypes.StrategicResources),

    Iron(0, 1, 0, new ArrayList<TerraineType>(){{
        add(TerraineType.Hill);
        add(TerraineType.Desert);
        add(TerraineType.Plains);
        add(TerraineType.Tundra);
        add(TerraineType.Grassland);
        add(TerraineType.Snow);
    }}, null, ImprovementType.Mine, TechnologyType.IronWorking,ResourceMainTypes.StrategicResources);

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
    public final ArrayList<TerraineType> canBeFoundOn;
    public final ArrayList<FeatureType> canBeFoundOnFeatures;
    public final ImprovementType improvementNeeded;
    public final TechnologyType raquiredTechology;


    ResourceType(int food, int production, int gold, ArrayList<TerraineType> canBeFoundOn, ArrayList<FeatureType> canBeFoundOnFeatures, ImprovementType improvementNeeded, TechnologyType requiredTechnology,ResourceMainTypes mainType) {
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
