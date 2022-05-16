package Model.Units.NonCombat;

import Controller.GameController.GameController;
import Controller.GameController.UnitController;
import Model.CivlizationRelated.Civilization;
import Model.Technology.Technology;
import Model.Technology.TechnologyType;
import Model.TileRelated.Feature.Feature;
import Model.TileRelated.Feature.FeatureType;
import Model.TileRelated.Improvement.Improvement;
import Model.TileRelated.Improvement.ImprovementType;
import Model.TileRelated.Road.Road;
import Model.TileRelated.Road.RoadType;
import Model.TileRelated.Terraine.Terrain;
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
            if(!properFeature)return "not a proper Feature";
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
        GameController.getInstance().setSelectedUnit(null);
        return "construction has been started!";
    }


    public String stop(){
        if(tile.getImprovement() != null && tile.getImprovement().getWorker() != null){
            tile.getImprovement().setWorker(null);
            return "action has stopped";
        }
        else if(tile.getRoad() != null && tile.getRoad().getWorker() != null){
            tile.getRoad().setWorker(null);
            return "action has stopped";
        }
        else return "no action to stop!";
    }

    public String resumeBuildingOrRepairOfImprovements(){
        if(tile.getImprovement() == null)return "no improvement here!";
        if(tile.getImprovement().getWorker() != null)return "already being worked on";
        if(tile.getImprovement().getDaysToComplete() == 0 && !tile.getImprovement().isRuined())return "improvement is working properly!";
        tile.getImprovement().setWorker(this);
        if(tile.getImprovement().isRuined()){
            tile.getImprovement().setRuined(false);
            tile.getImprovement().changeDaysToComplete(3);
        }
        civilization.addImprovementUnderConstruction(tile.getImprovement());
        movementsLeft = 0;
        GameController.getInstance().setSelectedUnit(null);
        return "construction resumed";
    }

    public String buildRoad(RoadType roadType){
        if(tile.getRoad() != null)return"there is already a road here!";
        if(tile.isCapital())return "you can't build roads on your city center";
        if(!civilization.hasTechnology(roadType.PrerequisiteTechnology))return "you don't have access to the required technology";
        Road road = new Road(roadType,3,civilization);
        road.setWorker(this);
        tile.setRoad(road);
        civilization.addRoadUnderConstruction(road);
        movementsLeft = 0;
        GameController.getInstance().setSelectedUnit(null);
        return "construction has been started!";
    }

    public String resumeBuildingOrRepairOfRoads(){
        if(tile.getRoad() == null)return "no road here!";
        if(tile.getRoad().getWorker() != null)return "already being worked on";
        if(tile.getRoad().getDaysToComplete() == 0 && !tile.getRoad().isRuined())return "road is working properly!";
        tile.getRoad().setWorker(this);
        if(tile.getRoad().isRuined()){
            tile.getRoad().setRuined(false);
            tile.getRoad().changeDaysToComplete(3);
        }
        civilization.addRoadUnderConstruction(tile.getRoad());
        movementsLeft = 0;
        GameController.getInstance().setSelectedUnit(null);
        return "construction resumed";
    }

    public String destroyRoad(){
        if(tile.getRoad() == null)return "no road here!";
        if(tile.getRoad().getDaysToComplete() == 0 && !tile.getRoad().isRuined())civilization.changeRoadMaintenance(-1);
        tile.setRoad(null);
        movementsLeft = 0;
        GameController.getInstance().setSelectedUnit(null);
        return "destroyed";
    }

    public String clearFeature(){
        if(tile.getFeature() != null && tile.getFeature().getFeatureType().equals(FeatureType.Jungle)){
            if(!civilization.hasTechnology(TechnologyType.BronzeWorking))return "you need BronzeWorking technology";
            tile.getFeature().changeDaysToClear(6);
            tile.getFeature().setWorker(this);
            civilization.addFeaturesBeingCleared(tile.getFeature());
            return "on work";
        }
        if(tile.getFeature() != null && tile.getFeature().getFeatureType().equals(FeatureType.Forest)){
            if(!civilization.hasTechnology(TechnologyType.Mining))return "you need mining technology";
            tile.getFeature().changeDaysToClear(3);
            tile.getFeature().setWorker(this);
            civilization.addFeaturesBeingCleared(tile.getFeature());
            return "on work";
        }
        if(tile.getFeature() != null && tile.getFeature().getFeatureType().equals(FeatureType.Marsh)) {
            if (!civilization.hasTechnology(TechnologyType.Masonry)) return "you need Masonry technology";
            tile.getFeature().changeDaysToClear(5);
            tile.getFeature().setWorker(this);
            civilization.addFeaturesBeingCleared(tile.getFeature());
            return "on work";
        }
        return "no allowed feature";
    }
}

