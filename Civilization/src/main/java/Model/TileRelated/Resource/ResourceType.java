package Model.TileRelated.Resource;

import Model.Technology.TechnologyType;
import Model.TileRelated.Feature.FeatureType;
import Model.TileRelated.Improvement.ImprovementType;
import Model.TileRelated.Terraine.TerrainType;
import javafx.scene.image.Image;

import java.util.ArrayList;

public enum ResourceType {
    // Bonus Resources


    Bananas(1, 0, 0, ImprovementType.Plantation, null,
            ResourceMainTypes.BonusResources),
//
    Cattle(1, 0, 0,  ImprovementType.Pasture, null,
            ResourceMainTypes.BonusResources),
//
    Deer(1, 0, 0,  ImprovementType.Camp, null,
            ResourceMainTypes.BonusResources),
//
    Sheep(2, 0, 0, ImprovementType.Pasture, null,
            ResourceMainTypes.BonusResources),
//
    Wheat(1, 0, 0,  ImprovementType.Farm, null,
            ResourceMainTypes.BonusResources),
    // Strategic Resources
    Coal(0, 1, 0,  ImprovementType.Mine, TechnologyType.ScientificTheory,
            ResourceMainTypes.StrategicResources),

    Horses(0, 1, 0,  ImprovementType.Pasture, TechnologyType.AnimalHusbandry,
            ResourceMainTypes.StrategicResources),

    Iron(0, 1, 0, ImprovementType.Mine, TechnologyType.IronWorking,
            ResourceMainTypes.StrategicResources),
    // Luxury Resources
    Cotton(0, 0,2, ImprovementType.Plantation, null,
            ResourceMainTypes.LuxuryResources),
    Dyes(0, 0, 2,  ImprovementType.Plantation, null,
            ResourceMainTypes.LuxuryResources),
    Furs(0, 0, 2,  ImprovementType.Camp, null,
            ResourceMainTypes.LuxuryResources),
    Gems(0, 0, 3,  ImprovementType.Mine, null,
            ResourceMainTypes.LuxuryResources),
    GoldResource(0, 0, 2,  ImprovementType.Mine, null,
            ResourceMainTypes.LuxuryResources),
    Incense(0, 0, 2,  ImprovementType.Plantation, null,
            ResourceMainTypes.LuxuryResources),
    Ivory(0, 0, 2,  ImprovementType.Camp, null,
            ResourceMainTypes.LuxuryResources),
    Marble(0, 0, 2,  ImprovementType.Quarry, null,
            ResourceMainTypes.LuxuryResources),
    Silk(0, 0, 2,  ImprovementType.Plantation, null,
            ResourceMainTypes.LuxuryResources),
    Silver(0 , 0, 2, ImprovementType.Plantation, null,
            ResourceMainTypes.LuxuryResources),
    Spices(0, 0, 2,  ImprovementType.Plantation, null,
            ResourceMainTypes.LuxuryResources),
    Sugar(0, 0, 2,  ImprovementType.Plantation, null,
            ResourceMainTypes.LuxuryResources);

    static {
        for (ResourceType resourceType: ResourceType.values()) {
                resourceType.image = new Image("/images/Map/resource/" + resourceType.name().toLowerCase() + ".png");
        }

        Bananas.setter(null, new ArrayList<FeatureType>(){{
            add(FeatureType.Forest);
        }});
        Cattle.setter(new ArrayList<TerrainType>(){{
            add(TerrainType.Grassland);
        }},null);
        Deer.setter(new ArrayList<>() {{
            add(TerrainType.Hill);
            add(TerrainType.Tundra);
        }}, new ArrayList<FeatureType>(){{
            add(FeatureType.Forest);
        }});
        Sheep.setter(new ArrayList<TerrainType>(){{
            add(TerrainType.Grassland);
            add(TerrainType.Plains);
            add(TerrainType.Desert);
            add(TerrainType.Hill);
        }}, null);
        Wheat.setter(new ArrayList<TerrainType>(){{
            add(TerrainType.Plains);
        }}, new ArrayList<FeatureType>(){{
            add(FeatureType.FloodPlains);
        }});
        Coal.setter(new ArrayList<TerrainType>(){{
            add(TerrainType.Grassland);
            add(TerrainType.Hill);
            add(TerrainType.Plains);
        }}, null);
        Horses.setter(new ArrayList<TerrainType>(){{
            add(TerrainType.Plains);
            add(TerrainType.Tundra);
            add(TerrainType.Grassland);
        }}, null);
        Iron.setter(new ArrayList<TerrainType>(){{
            add(TerrainType.Hill);
            add(TerrainType.Desert);
            add(TerrainType.Plains);
            add(TerrainType.Tundra);
            add(TerrainType.Grassland);
            add(TerrainType.Snow);
        }}, null);
        Cotton.setter(new ArrayList<TerrainType>(){{
            add(TerrainType.Grassland);
            add(TerrainType.Plains);
            add(TerrainType.Desert);
        }}, null);
        Dyes.setter(null, new ArrayList<FeatureType>(){{
            add(FeatureType.Forest);
            add(FeatureType.Jungle);
        }});
        Furs.setter(new ArrayList<TerrainType>(){{
            add(TerrainType.Tundra);
        }}, new ArrayList<FeatureType>(){{
            add(FeatureType.Forest);
        }});
        Gems.setter(new ArrayList<TerrainType>(){{
            add(TerrainType.Grassland);
            add(TerrainType.Tundra);
            add(TerrainType.Desert);
            add(TerrainType.Hill);
            add(TerrainType.Plains);
        }}, new ArrayList<FeatureType>(){{
            add(FeatureType.Jungle);
        }});
        GoldResource.setter(new ArrayList<TerrainType>(){{
            add(TerrainType.Plains);
            add(TerrainType.Hill);
            add(TerrainType.Grassland);
            add(TerrainType.Desert);
        }}, null);
        Incense.setter(new ArrayList<TerrainType>(){{
            add(TerrainType.Plains);
            add(TerrainType.Hill);
        }}, null);
        Ivory.setter(new ArrayList<TerrainType>(){{
            add(TerrainType.Plains);
        }}, null);
        Marble.setter(new ArrayList<TerrainType>(){{
            add(TerrainType.Plains);
            add(TerrainType.Hill);
            add(TerrainType.Tundra);
            add(TerrainType.Desert);
            add(TerrainType.Grassland);
        }}, null);
        Silk.setter(null, new ArrayList<FeatureType>(){{
            add(FeatureType.Forest);
        }});
        Silver.setter( new ArrayList<TerrainType>(){{
            add(TerrainType.Desert);
            add(TerrainType.Hill);
            add(TerrainType.Tundra);
        }}, null);
        Spices.setter(null, new ArrayList<FeatureType>(){{
            add(FeatureType.Jungle);
        }});
        Sugar.setter(null, new ArrayList<FeatureType>(){{
            add(FeatureType.FloodPlains);
            add(FeatureType.Marsh);
        }});
    }

    public final int Food;
    public final int production;
    public final int Gold;
    public final ResourceMainTypes mainType;
    public ArrayList<TerrainType> canBeFoundOn;
    public ArrayList<FeatureType> canBeFoundOnFeatures;
    public final ImprovementType improvementNeeded;
    public final TechnologyType requiredTechnology;

    public Image image;

    private void setter(ArrayList<TerrainType> canBeFoundOn, ArrayList<FeatureType> canBeFoundOnFeatures){
        this.canBeFoundOn = canBeFoundOn;
        this.canBeFoundOnFeatures = canBeFoundOnFeatures;
    }

    ResourceType(int food, int production, int gold, ImprovementType improvementNeeded, TechnologyType requiredTechnology, ResourceMainTypes mainType) {
        this.Food = food;
        this.Gold = gold;
        this.production = production;
        this.improvementNeeded = improvementNeeded;
        this.requiredTechnology = requiredTechnology;
        this.mainType = mainType;
    }
    public ArrayList<FeatureType> getCanBeFoundOnFeatures(){
        return getCanBeFoundOnFeatures();
    }

}
