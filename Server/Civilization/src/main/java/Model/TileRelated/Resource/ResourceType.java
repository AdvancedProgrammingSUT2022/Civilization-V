package Model.TileRelated.Resource;

import Model.Technology.TechnologyType;
import Model.TileRelated.Feature.FeatureType;
import Model.TileRelated.Improvement.ImprovementType;
import Model.TileRelated.Terraine.TerrainType;

import java.util.ArrayList;

public enum ResourceType {
    // Bonus Resources

    Bananas(1, 0, 0, null,
            ResourceMainTypes.BonusResources),
    Cattle(1, 0, 0, null,
            ResourceMainTypes.BonusResources),
    Deer(1, 0, 0, null,
            ResourceMainTypes.BonusResources),
    Sheep(2, 0, 0, null,
            ResourceMainTypes.BonusResources),
    Wheat(1, 0, 0, null,
            ResourceMainTypes.BonusResources),
    // Strategic Resources
    Coal(0, 1, 0, TechnologyType.ScientificTheory,
            ResourceMainTypes.StrategicResources),
    Horses(0, 1, 0, TechnologyType.AnimalHusbandry,
            ResourceMainTypes.StrategicResources),
    Iron(0, 1, 0, TechnologyType.IronWorking,
            ResourceMainTypes.StrategicResources),
    // Luxury Resources
    Cotton(0, 0,2, null,
            ResourceMainTypes.LuxuryResources),
    Dyes(0, 0, 2, null,
            ResourceMainTypes.LuxuryResources),
    Furs(0, 0, 2, null,
            ResourceMainTypes.LuxuryResources),
    Gems(0, 0, 3, null,
            ResourceMainTypes.LuxuryResources),
    GoldResource(0, 0, 2, null,
            ResourceMainTypes.LuxuryResources),
    Incense(0, 0, 2, null,
            ResourceMainTypes.LuxuryResources),
    Ivory(0, 0, 2, null,
            ResourceMainTypes.LuxuryResources),
    Marble(0, 0, 2, null,
            ResourceMainTypes.LuxuryResources),
    Silk(0, 0, 2, null,
            ResourceMainTypes.LuxuryResources),
    Silver(0 , 0, 2, null,
            ResourceMainTypes.LuxuryResources),
    Spices(0, 0, 2, null,
            ResourceMainTypes.LuxuryResources),
    Sugar(0, 0, 2, null,
            ResourceMainTypes.LuxuryResources);

    static {

        Bananas.setter(null, new ArrayList<>() {{
            add(FeatureType.Forest);
        }}, ImprovementType.Plantation);
        Cattle.setter(new ArrayList<>() {{
            add(TerrainType.Grassland);
        }},null,  ImprovementType.Pasture);
        Deer.setter(new ArrayList<>() {{
            add(TerrainType.Hill);
            add(TerrainType.Tundra);
        }}, new ArrayList<>() {{
            add(FeatureType.Forest);
        }},  ImprovementType.Camp);
        Sheep.setter(new ArrayList<>() {{
            add(TerrainType.Grassland);
            add(TerrainType.Plains);
            add(TerrainType.Desert);
            add(TerrainType.Hill);
        }}, null, ImprovementType.Pasture);
        Wheat.setter(new ArrayList<>() {{
            add(TerrainType.Plains);
        }}, new ArrayList<>() {{
            add(FeatureType.FloodPlains);
        }},  ImprovementType.Farm);
        Coal.setter(new ArrayList<>() {{
            add(TerrainType.Grassland);
            add(TerrainType.Hill);
            add(TerrainType.Plains);
        }}, null,  ImprovementType.Mine);
        Horses.setter(new ArrayList<>() {{
            add(TerrainType.Plains);
            add(TerrainType.Tundra);
            add(TerrainType.Grassland);
        }}, null,  ImprovementType.Pasture);
        Iron.setter(new ArrayList<>() {{
            add(TerrainType.Hill);
            add(TerrainType.Desert);
            add(TerrainType.Plains);
            add(TerrainType.Tundra);
            add(TerrainType.Grassland);
            add(TerrainType.Snow);
        }}, null, ImprovementType.Mine);
        Cotton.setter(new ArrayList<>() {{
            add(TerrainType.Grassland);
            add(TerrainType.Plains);
            add(TerrainType.Desert);
        }}, null, ImprovementType.Plantation);
        Dyes.setter(null, new ArrayList<>() {{
            add(FeatureType.Forest);
            add(FeatureType.Jungle);
        }},  ImprovementType.Plantation);
        Furs.setter(new ArrayList<>() {{
            add(TerrainType.Tundra);
        }}, new ArrayList<>() {{
            add(FeatureType.Forest);
        }},  ImprovementType.Camp);
        Gems.setter(new ArrayList<>() {{
            add(TerrainType.Grassland);
            add(TerrainType.Tundra);
            add(TerrainType.Desert);
            add(TerrainType.Hill);
            add(TerrainType.Plains);
        }}, new ArrayList<>() {{
            add(FeatureType.Jungle);
        }},  ImprovementType.Mine);
        GoldResource.setter(new ArrayList<>() {{
            add(TerrainType.Plains);
            add(TerrainType.Hill);
            add(TerrainType.Grassland);
            add(TerrainType.Desert);
        }}, null,  ImprovementType.Mine);
        Incense.setter(new ArrayList<>() {{
            add(TerrainType.Plains);
            add(TerrainType.Hill);
        }}, null,  ImprovementType.Plantation);
        Ivory.setter(new ArrayList<>() {{
            add(TerrainType.Plains);
        }}, null,  ImprovementType.Camp);
        Marble.setter(new ArrayList<>() {{
            add(TerrainType.Plains);
            add(TerrainType.Hill);
            add(TerrainType.Tundra);
            add(TerrainType.Desert);
            add(TerrainType.Grassland);
        }}, null,  ImprovementType.Quarry);
        Silk.setter(null, new ArrayList<>() {{
            add(FeatureType.Forest);
        }},  ImprovementType.Plantation);
        Silver.setter(new ArrayList<>() {{
            add(TerrainType.Desert);
            add(TerrainType.Hill);
            add(TerrainType.Tundra);
        }}, null, ImprovementType.Plantation);
        Spices.setter(null, new ArrayList<>() {{
            add(FeatureType.Jungle);
        }},  ImprovementType.Plantation);
        Sugar.setter(null, new ArrayList<>() {{
            add(FeatureType.FloodPlains);
            add(FeatureType.Marsh);
        }},  ImprovementType.Plantation);
    }

    public final int Food;
    public final int production;
    public final int Gold;
    public final ResourceMainTypes mainType;
    public ArrayList<TerrainType> canBeFoundOn;
    public ArrayList<FeatureType> canBeFoundOnFeatures;
    public  ImprovementType improvementNeeded;
    public final TechnologyType requiredTechnology;


    private void setter(ArrayList<TerrainType> canBeFoundOn, ArrayList<FeatureType> canBeFoundOnFeatures, ImprovementType improvementNeeded){
        this.canBeFoundOn = canBeFoundOn;
        this.canBeFoundOnFeatures = canBeFoundOnFeatures;
        this.improvementNeeded = improvementNeeded;
    }

    ResourceType(int food, int production, int gold, TechnologyType requiredTechnology, ResourceMainTypes mainType) {
        this.Food = food;
        this.Gold = gold;
        this.production = production;
        this.requiredTechnology = requiredTechnology;
        this.mainType = mainType;
    }
    public ArrayList<FeatureType> getCanBeFoundOnFeatures(){
        return getCanBeFoundOnFeatures();
    }

}
