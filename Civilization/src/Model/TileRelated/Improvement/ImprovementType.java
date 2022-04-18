package Model.TileRelated.Improvement;

import Model.Technology.Technology;
import Model.Technology.TechnologyType;
import Model.TileRelated.Feature.Feature;
import Model.TileRelated.Feature.FeatureType;
import Model.TileRelated.Resource.Resource;
import Model.TileRelated.Resource.ResourceType;
import Model.TileRelated.Terraine.Terrain;
import Model.TileRelated.Terraine.TerrainType;
import com.sun.source.tree.NewArrayTree;

import java.util.AbstractMap;
import java.util.ArrayList;

public enum ImprovementType {

    // workerImprovements
    Camp(null, 0, new ArrayList<Resource>(){{
        Resource Furs = new Resource(ResourceType.Furs);
        Resource Ivory = new Resource(ResourceType.Ivory);
        Resource Deer = new Resource(ResourceType.Deer);
        add(Furs);
        add(Ivory);
        add(Deer);
    }}, new Technology(TechnologyType.Trapping), new ArrayList<Feature>(){{
        Feature Forest = new Feature(FeatureType.Forest);
        add(Forest);
    }}, new ArrayList<Terrain>(){{
        Terrain Tundra = new Terrain(TerrainType.Tundra);
        Terrain Plains = new Terrain(TerrainType.Plains);
        Terrain Hill = new Terrain(TerrainType.Hill);
        add(Tundra);
        add(Plains);
        add(Hill);
    }}),
    Farm("food", 1, new ArrayList<Resource>(){{
        Resource Wheat = new Resource(ResourceType.Wheat);
        add(Wheat);
    }}, new Technology(TechnologyType.Agriculture), null, new ArrayList<Terrain>(){{
        Terrain Desert = new Terrain(TerrainType.Desert);
        Terrain Plains = new Terrain(TerrainType.Plains);
        Terrain Grassland = new Terrain(TerrainType.Grassland);
        add(Desert);
        add(Plains);
        add(Grassland);
    }}),
    LumberMill("food", 1, null, new Technology(TechnologyType.Engineering),
            new ArrayList<Feature>(){{
                Feature  Forest = new Feature(FeatureType.Forest);
                add(Forest);
            }}, null),
    Mine("food", 1, new ArrayList<Resource>(){{
        Resource Gem = new Resource(ResourceType.Gems);
        Resource GoldResource = new Resource(ResourceType.GoldResource);
        Resource Silver = new Resource(ResourceType.Silver);
        Resource Coal = new Resource(ResourceType.Coal);
        Resource Iron = new Resource(ResourceType.Iron);
        add(Gem);
        add(GoldResource);
        add(Silver);
        add(Coal);
        add(Iron);
    }}, new Technology(TechnologyType.Mining), new ArrayList<Feature>(){{
        Feature Forest = new Feature(FeatureType.Forest);
        Feature Jungle = new Feature(FeatureType.Jungle);
        Feature Marsh = new Feature(FeatureType.Marsh);
        add(Forest);
        add(Jungle);
        add(Marsh);
    }}, new ArrayList<Terrain>(){{
        Terrain Plains = new Terrain(TerrainType.Plains);
        Terrain Desert = new Terrain(TerrainType.Desert);
        Terrain Grassland = new Terrain(TerrainType.Grassland);
        Terrain Tundra = new Terrain(TerrainType.Tundra);
        Terrain Snow = new Terrain(TerrainType.Snow);
        Terrain Hill = new Terrain(TerrainType.Hill);
        add(Plains);
        add(Desert);
        add(Grassland);
        add(Tundra);
        add(Snow);
        add(Hill);
    }}),
    Pasture(null, 0, new ArrayList<Resource>(){{
        Resource Horse = new Resource(ResourceType.Horses);
        Resource Cattle = new Resource(ResourceType.Cattle);
        Resource Sheep = new Resource(ResourceType.Sheep);
        add(Horse);
        add(Cattle);
        add(Sheep);
    }}, new Technology(TechnologyType.AnimalHusbandry), null, new ArrayList<Terrain>(){{
        Terrain Plains = new Terrain(TerrainType.Plains);
        Terrain Desert = new Terrain(TerrainType.Desert);
        Terrain Grassland = new Terrain(TerrainType.Grassland);
        Terrain Tundra = new Terrain(TerrainType.Tundra);
        Terrain Hill = new Terrain(TerrainType.Hill);
        add(Plains);
        add(Desert);
        add(Grassland);
        add(Tundra);
        add(Hill);
    }}),
    Plantation(null, 0, new ArrayList<Resource>(){{
        Resource Bananas = new Resource(ResourceType.Bananas);
        Resource Cotton = new Resource(ResourceType.Cotton);
        Resource Dyes = new Resource(ResourceType.Dyes);
        Resource Incense = new Resource(ResourceType.Incense);
        Resource Silk = new Resource(ResourceType.Silk);
        Resource Sugar = new Resource(ResourceType.Sugar);
        add(Bananas);
        add(Cotton);
        add(Dyes);
        add(Incense);
        add(Silk);
        add(Sugar);
    }}, new Technology(TechnologyType.Calendar), new ArrayList<Feature>(){{
        Feature Jungle = new Feature(FeatureType.Jungle);
        Feature Forest = new Feature(FeatureType.Forest);
        Feature Marsh = new Feature(FeatureType.Marsh);
        Feature FloodPlains = new Feature(FeatureType.FloodPlains);
        add(Marsh);
        add(Forest);
        add(Jungle);
        add(FloodPlains);
    }}, new ArrayList<Terrain>(){{
        Terrain Plains = new Terrain(TerrainType.Plains);
        Terrain Desert = new Terrain(TerrainType.Desert);
        Terrain Grassland = new Terrain(TerrainType.Grassland);
        Terrain Tundra = new Terrain(TerrainType.Tundra);
        add(Plains);
        add(Desert);
        add(Grassland);
        add(Tundra);
    }}),
    Quarry(null, 0, new ArrayList<Resource>(){{
        Resource Marble = new Resource(ResourceType.Marble);
        add(Marble);
    }}, new Technology(TechnologyType.Masonry), null, new ArrayList<Terrain>(){{
        Terrain Plains = new Terrain(TerrainType.Plains);
        Terrain Desert = new Terrain(TerrainType.Desert);
        Terrain Grassland = new Terrain(TerrainType.Grassland);
        Terrain Tundra = new Terrain(TerrainType.Tundra);
        Terrain Hill = new Terrain(TerrainType.Hill);
        add(Plains);
        add(Desert);
        add(Grassland);
        add(Tundra);
        add(Hill);
    }}),
    TradingPost("Gold", 1, null, new Technology(TechnologyType.Trapping), null,
            new ArrayList<Terrain>(){{
                Terrain Plains = new Terrain(TerrainType.Plains);
                Terrain Desert = new Terrain(TerrainType.Desert);
                Terrain Grassland = new Terrain(TerrainType.Grassland);
                Terrain Tundra = new Terrain(TerrainType.Tundra);
                add(Plains);
                add(Desert);
                add(Grassland);
                add(Tundra);
            }}),
    ManuFactory("Production", 2, null, new Technology(TechnologyType.Engineering),
            null, new ArrayList<Terrain>(){{
        Terrain Plains = new Terrain(TerrainType.Plains);
        Terrain Desert = new Terrain(TerrainType.Desert);
        Terrain Grassland = new Terrain(TerrainType.Grassland);
        Terrain Tundra = new Terrain(TerrainType.Tundra);
        Terrain Snow = new Terrain(TerrainType.Snow);
        add(Plains);
        add(Desert);
        add(Grassland);
        add(Tundra);
        add(Snow);
    }});

    public final int TileYields;
    public final String name;
    public final ArrayList<Resource> ImprovesThisResources;
    public final Technology PrerequisiteTechnology;
    public final ArrayList<Feature> FeaturesCanBeBuiltOn;
    public final ArrayList<Terrain> TerrainCanBeBuiltOn;
    ImprovementType(String name, int tileYields, ArrayList<Resource> improvesThisResources, Technology prerequisiteTechnology, ArrayList<Feature> featuresCanBeBuiltOn, ArrayList<Terrain> terrainCanBeBuiltOn) {
        this.name = name;
        this.TileYields = tileYields;
        ImprovesThisResources = improvesThisResources;
        PrerequisiteTechnology = prerequisiteTechnology;
        FeaturesCanBeBuiltOn = featuresCanBeBuiltOn;
        TerrainCanBeBuiltOn = terrainCanBeBuiltOn;
    }
}
