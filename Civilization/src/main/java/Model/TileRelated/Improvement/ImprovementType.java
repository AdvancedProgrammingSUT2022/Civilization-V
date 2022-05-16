package Model.TileRelated.Improvement;

import Model.Technology.Technology;
import Model.Technology.TechnologyType;
import Model.TileRelated.Feature.Feature;
import Model.TileRelated.Feature.FeatureType;
import Model.TileRelated.Resource.Resource;
import Model.TileRelated.Resource.ResourceType;
import Model.TileRelated.Terraine.Terrain;
import Model.TileRelated.Terraine.TerrainType;
import java.util.ArrayList;

public enum ImprovementType {

    // workerImprovements
    Camp("",6, 0, new Technology(TechnologyType.Trapping)),
    Farm("food",6, 1, new Technology(TechnologyType.Agriculture)),
    LumberMill("food",6, 1,  new Technology(TechnologyType.Engineering)),
    Mine("production",6, 1,  new Technology(TechnologyType.Mining)),
    Pasture("",7, 0, new Technology(TechnologyType.AnimalHusbandry)),
    Plantation("",5, 0, new Technology(TechnologyType.Calendar)),
    Quarry("",7, 0, new Technology(TechnologyType.Masonry)),
    TradingPost("Gold",8, 1,  new Technology(TechnologyType.Trapping)),
    ManuFactory("Production",7, 2,  new Technology(TechnologyType.Engineering));

    public final int constructionTime;
    public final int TileYields;
    public final String product;
    public  ArrayList<ResourceType> ImprovesThisResources;
    public final Technology PrerequisiteTechnology;
    public  ArrayList<FeatureType> FeaturesCanBeBuiltOn;
    public  ArrayList<TerrainType> TerrainCanBeBuiltOn;

    static {
        Camp.setter(new ArrayList<ResourceType>(){{
            add(ResourceType.Furs);
            add(ResourceType.Ivory);
            add(ResourceType.Deer);
        }},new ArrayList<FeatureType>(){{
            add(FeatureType.Forest);
        }}, new ArrayList<TerrainType>(){{
            add(TerrainType.Tundra);
            add(TerrainType.Plains);
            add(TerrainType.Hill);
        }});
        Farm.setter(new ArrayList<ResourceType>(){{
            add(ResourceType.Wheat);
        }},null, new ArrayList<TerrainType>(){{
            add(TerrainType.Desert);
            add(TerrainType.Plains);
            add(TerrainType.Grassland);
        }});
        LumberMill.setter(null,new ArrayList<FeatureType>(){{
            add(FeatureType.Forest);
        }}, null);
        Mine.setter(new ArrayList<ResourceType>(){{
            add(ResourceType.Gems);
            add(ResourceType.GoldResource);
            add(ResourceType.Silver);
            add(ResourceType.Coal);
            add(ResourceType.Iron);
        }}, new ArrayList<FeatureType>(){{
            add(FeatureType.Forest);
            add(FeatureType.Jungle);
            add(FeatureType.Marsh);
        }}, new ArrayList<TerrainType>(){{
            add(TerrainType.Plains);
            add(TerrainType.Desert);
            add(TerrainType.Grassland);
            add(TerrainType.Tundra);
            add(TerrainType.Snow);
            add(TerrainType.Hill);
        }});
        Pasture.setter(new ArrayList<ResourceType>(){{
            add(ResourceType.Horses);
            add(ResourceType.Cattle);
            add(ResourceType.Sheep);
        }},null, new ArrayList<TerrainType>(){{
            add(TerrainType.Plains);
            add(TerrainType.Desert);
            add(TerrainType.Grassland);
            add(TerrainType.Tundra);
            add(TerrainType.Hill);
        }});
        Plantation.setter(new ArrayList<ResourceType>(){{
            add(ResourceType.Bananas);
            add(ResourceType.Cotton);
            add(ResourceType.Dyes);
            add(ResourceType.Incense);
            add(ResourceType.Silk);
            add(ResourceType.Sugar);
        }},new ArrayList<FeatureType>(){{
            add(FeatureType.Marsh);
            add(FeatureType.Forest);
            add(FeatureType.Jungle);
            add(FeatureType.FloodPlains);
        }}, new ArrayList<TerrainType>(){{
            add(TerrainType.Plains);
            add(TerrainType.Desert);
            add(TerrainType.Grassland);
            add(TerrainType.Tundra);
        }});
        Quarry.setter(new ArrayList<ResourceType>(){{
            add(ResourceType.Marble);
        }}, null, new ArrayList<TerrainType>(){{
            add(TerrainType.Plains);
            add(TerrainType.Desert);
            add(TerrainType.Grassland);
            add(TerrainType.Tundra);
            add(TerrainType.Hill);
        }});
        TradingPost.setter(null, null,
                new ArrayList<TerrainType>(){{
                    add(TerrainType.Plains);
                    add(TerrainType.Desert);
                    add(TerrainType.Grassland);
                    add(TerrainType.Tundra);
                }});
        ManuFactory.setter(null ,null, new ArrayList<TerrainType>(){{
            add(TerrainType.Plains);
            add(TerrainType.Desert);
            add(TerrainType.Grassland);
            add(TerrainType.Tundra);
            add(TerrainType.Snow);
        }});
    }

    private void setter(ArrayList<ResourceType> improvesThisResources,ArrayList<FeatureType> featuresCanBeBuiltOn, ArrayList<TerrainType> terrainCanBeBuiltOn){
        this.FeaturesCanBeBuiltOn = featuresCanBeBuiltOn;
        this.TerrainCanBeBuiltOn = terrainCanBeBuiltOn;
        this.ImprovesThisResources = improvesThisResources;
    }

    ImprovementType(String product,int constructionTime , int tileYields,  Technology prerequisiteTechnology) {
        this.product = product;
        this.constructionTime = constructionTime;
        this.TileYields = tileYields;
        this.PrerequisiteTechnology = prerequisiteTechnology;
    }
}
