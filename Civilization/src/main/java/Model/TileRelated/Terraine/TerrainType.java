package Model.TileRelated.Terraine;

import Model.TileRelated.Feature.Feature;
import Model.TileRelated.Feature.FeatureType;
import Model.TileRelated.Resource.Resource;
import Model.TileRelated.Resource.ResourceType;

import java.util.ArrayList;

public enum TerrainType {
    Desert(0, 0, 0, -33, 1, new ArrayList<Feature>(){{
        Feature Oasis = new Feature(FeatureType.Oasis);
        Feature FloodPlains = new Feature(FeatureType.FloodPlains);
        add(Oasis);
        add(FloodPlains);
    }}, new ArrayList<Resource>(){{
        Resource Iron = new Resource(ResourceType.Iron);
        Resource GoldResource = new Resource(ResourceType.GoldResource);
        Resource Marble = new Resource(ResourceType.Marble);
        Resource Silver = new Resource(ResourceType.Silver);
        Resource Gem = new Resource(ResourceType.Gems);
        Resource Cotton = new Resource(ResourceType.Cotton);
        Resource Incense = new Resource(ResourceType.Incense);
        Resource Sheep = new Resource(ResourceType.Sheep);
        add(Iron);
        add(GoldResource);
        add(Marble);
        add(Silver);
        add(Gem);
        add(Cotton);
        add(Incense);
        add(Sheep);
    }}),
    Grassland(2, 0, 0, -33, 1, new ArrayList<Feature>(){{
        Feature Forest = new Feature(FeatureType.Forest);
        Feature Marsh = new Feature(FeatureType.Marsh);
        add(Forest);
        add(Marsh);
    }}, new ArrayList<Resource>(){{
        Resource Iron = new Resource(ResourceType.Iron);
        Resource Horse = new Resource(ResourceType.Horses);
        Resource Coal = new Resource(ResourceType.Coal);
        Resource Cattle = new Resource(ResourceType.Cattle);
        Resource GoldResource = new Resource(ResourceType.GoldResource);
        Resource Gem = new Resource(ResourceType.Gems);
        Resource Marble = new Resource(ResourceType.Marble);
        Resource Cotton = new Resource(ResourceType.Cotton);
        Resource Sheep = new Resource(ResourceType.Sheep);
        add(Iron);
        add(Horse);
        add(Coal);
        add(Cattle);
        add(GoldResource);
        add(Gem);
        add(Marble);
        add(Cotton);
        add(Sheep);
    }}),
    Hill(0, 2, 0, 25, 2, new ArrayList<Feature>(){{
        Feature Forest = new Feature(FeatureType.Forest);
        Feature Jungle = new Feature(FeatureType.Jungle);
        add(Forest);
        add(Jungle);
    }}, new ArrayList<Resource>(){{
        Resource Iron = new Resource(ResourceType.Iron);
        Resource Coal = new Resource(ResourceType.Coal);
        Resource Deer = new Resource(ResourceType.Deer);
        Resource GoldResource = new Resource(ResourceType.GoldResource);
        Resource Silver = new Resource(ResourceType.Silver);
        Resource Gem = new Resource(ResourceType.Gems);
        Resource Marble = new Resource(ResourceType.Marble);
        Resource Sheep = new Resource(ResourceType.Sheep);
        add(Iron);
        add(Coal);
        add(Deer);
        add(GoldResource);
        add(Silver);
        add(Gem);
        add(Marble);
        add(Sheep);
    }}),
    Mountain(0, 0, 0, 25, null, null),
    Ocean(0, 0, 0, 25, null, null),
    Plains(1, 1, 0, -33, 1 , new ArrayList<Feature>(){{
        Feature Forest = new Feature(FeatureType.Forest);
        Feature Jungle = new Feature(FeatureType.Jungle);
        add(Forest);
        add(Jungle);
    }}, new ArrayList<Resource>(){{
        Resource Iron = new Resource(ResourceType.Iron);
        Resource Horse = new Resource(ResourceType.Horses);
        Resource Coal = new Resource(ResourceType.Coal);
        Resource GoldResource = new Resource(ResourceType.GoldResource);
        Resource Gem = new Resource(ResourceType.Gems);
        Resource Marble = new Resource(ResourceType.Marble);
        Resource Ivory = new Resource(ResourceType.Ivory);
        Resource Cotton = new Resource(ResourceType.Cotton);
        Resource Incense = new Resource(ResourceType.Incense);
        Resource Sheep = new Resource(ResourceType.Sheep);
        add(Iron);
        add(Horse);
        add(Coal);
        add(GoldResource);
        add(Gem);
        add(Marble);
        add(Ivory);
        add(Cotton);
        add(Incense);
        add(Sheep);
    }}),
    Snow(0, 0, 0, -33, 1, null, new ArrayList<Resource>(){{
        Resource Iron = new Resource(ResourceType.Iron);
        add(Iron);
    }}),
    Tundra(1, 0, 0, -33, 1, new ArrayList<Feature>(){{
        Feature Forest = new Feature(FeatureType.Forest);
        add(Forest);
    }}, new ArrayList<Resource>(){{
        Resource Iron = new Resource(ResourceType.Iron);
        Resource Horse = new Resource(ResourceType.Horses);
        Resource Deer = new Resource(ResourceType.Deer);
        Resource Silver = new Resource(ResourceType.Silver);
        Resource Gem = new Resource(ResourceType.Gems);
        Resource Marble = new Resource(ResourceType.Marble);
        add(Iron);
        add(Horse);
        add(Deer);
        add(Silver);
        add(Gem);
        add(Marble);
    }});
    public final int food;
    public final int production;
    public final int gold;
    public final int combatModifier;
    public int movementCost;
    public final ArrayList<Feature> possibleFeatures;
    public final ArrayList<Resource> possibleResources;

    TerrainType(int food, int production, int gold, int combatModifier, int movementCost, ArrayList<Feature> possibleFeatures, ArrayList<Resource> possibleResources) {
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.combatModifier = combatModifier;
        this.movementCost = movementCost;
        this.possibleFeatures = possibleFeatures;
        this.possibleResources = possibleResources;
    }
    TerrainType(int food, int production, int gold, int combatModifier, ArrayList<Feature> possibleFeatures, ArrayList<Resource> possibleResources) {
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.combatModifier = combatModifier;
        this.possibleFeatures = possibleFeatures;
        this.possibleResources = possibleResources;
    }
    public ArrayList<Resource> getPossibleResources(){
        return this.possibleResources;
    }
    
    public ArrayList<Feature> getPossibleFeatures(){
        return this.possibleFeatures;
    }
}
