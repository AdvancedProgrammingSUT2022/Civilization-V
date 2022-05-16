package Model.Units.NonCombat;
import Model.CivlizationRelated.Civilization;
import Model.Technology.TechnologyType;
import Model.TileRelated.Feature.FeatureType;
import Model.TileRelated.Improvement.Improvement;
import Model.TileRelated.Improvement.ImprovementType;
import Model.TileRelated.Terraine.TerrainType;
import Model.TileRelated.Tile.Tile;
import Model.Units.TypeEnums.UnitType;

public class Worker extends NonCombat {
    public Worker(Civilization civilization, Tile tile) {
        super(civilization, tile, UnitType.Worker);
    }


    public String buildImprovement(ImprovementType improvement){
        if(tile.getImprovement() != null)return"there is an improvement here!";
        if(!civilization.equals(tile.getCivilization()))return "this is not your territory";
        if(tile.isCapital())return "you can't build improvement on your city center";
        boolean properFeature = false;
        boolean properTerrain = false;
        if(!civilization.hasTechnology(improvement.PrerequisiteTechnology.getTechnologyType()))return "you don't have access to the required technology";
        if(tile.getFeature() != null && improvement.FeaturesCanBeBuiltOn != null) {
            for (FeatureType feature : improvement.FeaturesCanBeBuiltOn) {
                if(feature.equals(tile.getFeature().getFeatureType())){
                    properFeature = true;
                    break;
                }
            }
        }
        if(improvement.TerrainCanBeBuiltOn != null) {
            for (TerrainType terrain : improvement.TerrainCanBeBuiltOn) {
                if (terrain.equals(tile.getTerrain())) {
                    properTerrain = true;
                    break;
                }
            }
            if (!properTerrain) return "not a proper Terrain";
        }
        int daysToComplete = improvement.constructionTime;
        if(tile.getFeature() != null && tile.getFeature().getFeatureType().equals(FeatureType.Jungle)){
            if(!civilization.hasTechnology(TechnologyType.BronzeWorking))return "you need BronzeWorking technology";
            daysToComplete += 7;
        }
        if(tile.getFeature() != null && tile.getFeature().getFeatureType().equals(FeatureType.Forest)){
            if(!civilization.hasTechnology(TechnologyType.Mining))return "you need mining technology";
            daysToComplete += 4;
        }
        if(tile.getFeature() != null && tile.getFeature().getFeatureType().equals(FeatureType.Marsh)){
            if(!civilization.hasTechnology(TechnologyType.Masonry))return "you need Masonry technology";
            daysToComplete += 6;
        }
        Improvement newImprovement = new Improvement(improvement);
        newImprovement.setTile(tile);
        newImprovement.changeDaysToComplete(daysToComplete);
        newImprovement.setWorker(this);
        tile.setImprovement(newImprovement);
        civilization.addImprovementUnderConstruction(newImprovement);
        movementsLeft = 0;
        return "construction has been started!";
    }
}

