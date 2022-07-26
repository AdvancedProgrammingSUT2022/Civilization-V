package Model.TileRelated.Terraine;

import Model.TileRelated.Feature.Feature;
import Model.TileRelated.Feature.FeatureType;
import Model.TileRelated.Resource.Resource;
import Model.TileRelated.Resource.ResourceType;
import java.util.ArrayList;

public enum TerrainType {
    Desert(0, 0, 0, -33, 1),
    Grassland(2, 0, 0, -33, 1),
    Hill(0, 2, 0, 25, 2),
    Mountain(0, 0, 0, 25),
    Ocean(0, 0, 0, 25),
    Plains(1, 1, 0, -33, 1 ),
    Snow(0, 0, 0, -33, 1),
    Tundra(1, 0, 0, -33, 1);
    public final int food;
    public final int production;
    public final int gold;
    public final int combatModifier;
    public int movementCost;
    public ArrayList<Feature> possibleFeatures;
    public ArrayList<Resource> possibleResources;

    static {
        Desert.setter(new ArrayList<>() {{
            Feature Oasis = new Feature(FeatureType.Oasis);
            Feature FloodPlains = new Feature(FeatureType.FloodPlains);
            add(Oasis);
            add(FloodPlains);
        }}, new ArrayList<>() {{
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
        }});
        Grassland.setter(new ArrayList<>() {{
            Feature Forest = new Feature(FeatureType.Forest);
            Feature Marsh = new Feature(FeatureType.Marsh);
            add(Forest);
            add(Marsh);
        }}, new ArrayList<>() {{
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
        }});
        Hill.setter(new ArrayList<>() {{
            Feature Forest = new Feature(FeatureType.Forest);
            Feature Jungle = new Feature(FeatureType.Jungle);
            add(Forest);
            add(Jungle);
        }}, new ArrayList<>() {{
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
        }});
        Plains.setter(new ArrayList<>() {{
            Feature Forest = new Feature(FeatureType.Forest);
            Feature Jungle = new Feature(FeatureType.Jungle);
            add(Forest);
            add(Jungle);
        }}, new ArrayList<>() {{
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
        }});
        Snow.setter( null, new ArrayList<>() {{
            Resource Iron = new Resource(ResourceType.Iron);
            add(Iron);
        }});
        Tundra.setter(new ArrayList<>() {{
            Feature Forest = new Feature(FeatureType.Forest);
            add(Forest);
        }}, new ArrayList<>() {{
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
    }

    private void setter(ArrayList<Feature> possibleFeatures, ArrayList<Resource> possibleResources){
        this.possibleFeatures = possibleFeatures;
        this.possibleResources = possibleResources;
    }

    TerrainType(int food, int production, int gold, int combatModifier, int movementCost) {
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.combatModifier = combatModifier;
        this.movementCost = movementCost;

    }

    TerrainType(int food, int production, int gold, int combatModifier) {
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.combatModifier = combatModifier;
    }
    public ArrayList<Resource> getPossibleResources(){
        return this.possibleResources;
    }
    
    public ArrayList<Feature> getPossibleFeatures(){
        return this.possibleFeatures;
    }
}
