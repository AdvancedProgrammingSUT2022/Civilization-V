package Controller.GameController;

import Model.CivlizationRelated.Civilization;
import Model.Enums.MapEnum;
import Model.TileRelated.Feature.Feature;
import Model.TileRelated.Feature.FeatureType;
import Model.TileRelated.Resource.Resource;
import Model.TileRelated.Resource.ResourceType;
import Model.TileRelated.Terraine.Terrain;
import Model.TileRelated.Terraine.TerrainType;
import Model.TileRelated.Tile.Tile;
import Model.TileRelated.Tile.TileVisibility;
import Model.Units.NonCombat.Settler;
import Model.Units.TypeEnums.UnitType;
import Model.Units.Unit;
import Model.User;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.regex.Matcher;

public class GameController {
    private LinkedHashMap<Integer, ArrayList<Tile>> seedsForTiles = new LinkedHashMap<Integer, ArrayList<Tile>>();
    private LinkedHashMap<Integer, ArrayList<Civilization>> seedForCivilization = new LinkedHashMap<Integer, ArrayList<Civilization>>();
    private LinkedHashMap<Integer, ArrayList<Unit>> seedForUnits = new LinkedHashMap<Integer, ArrayList<Unit>>();
    private ArrayList<Unit> units = new ArrayList<Unit>();
    private ArrayList<Civilization> civilizations = new ArrayList<Civilization>();
    private Civilization playerTurn;
    private ArrayList<Tile> tiles = new ArrayList<Tile>();
    private Unit selectedUnit;
    public void generateMap(int mapX, int mapY){ // map[x][y]
        Tile currentTile = new Tile();
        Random randomSeed = new Random();
        int MapSeed = randomSeed.nextInt(1000);
        Random random = new Random(MapSeed);
        for (int i = 0; i < mapX; i++) {
            for (int j = 0; j < mapY; j++) {
                Tile tile = new Tile();
                tile.setX(j);
                tile.setY(i);
                TerrainType terrainType = getARandomTerrainType();
                tile.setTerrain(terrainType);
                if(!tile.checkType(TerrainType.Snow) && !tile.checkType(TerrainType.Mountain) && !tile.checkType(TerrainType.Ocean) && !tile.checkType(TerrainType.Tundra)){
                    Resource resource = getARandomResource();
                    Feature feature = getARandomFeature();
                    int test = random.nextInt(10);
                    if (test < 3) tile.setFeature(feature);
                    else if (test > 7) tile.setResource(resource);
                    else {
                        tile.setFeature(feature);
                        tile.setResource(resource);
                    }
                } else if(tile.checkType(TerrainType.Snow) || tile.checkType(TerrainType.Tundra)){
                    Resource resource =getARandomResource();
                    tile.setResource(resource);
                }
                tile.setTileVisibility(TileVisibility.FOGOFWAR);
                this.tiles.add(tile);
            }
        }
        this.seedsForTiles.put(MapSeed, this.tiles);
    }

    public Resource getARandomResource(){
        int pickResource = new Random().nextInt(ResourceType.values().length);
        return new Resource(ResourceType.values()[pickResource]);
    }

    public TerrainType getARandomTerrainType(){
        int pickTerrain = new Random().nextInt(TerrainType.values().length);
        return TerrainType.values()[pickTerrain];
    }

    public Feature getARandomFeature(){
        int pickFeature = new Random().nextInt(FeatureType.values().length);
        return new Feature(FeatureType.values()[pickFeature]);
    }

    public void setCivilizations(int civilizationCount, int mapX, int mapY){
        Random randomSeed = new Random();
        int MapSeed = randomSeed.nextInt(1000);
        Random random = new Random(MapSeed);
        int count = 0;
        while(count < civilizationCount){
            Civilization civilization = new Civilization();
            int testX = random.nextInt(mapX);
            int testY = random.nextInt(mapY);
            boolean availabilityTile = true;
            for(Civilization value : this.civilizations){
                int valueX = value.getX();
                int valueY = value.getY();
                availabilityTile = checkAvailabilityOfTile(mapX, mapY, testX, testY, valueX, valueY);
                if(!availabilityTile) break;
            }
            if(availabilityTile) {
                Tile tile = new Tile();
                for (Tile value: this.tiles) {
                    if(testX == value.getX() && testY == value.getY()){
                        tile = value;
                        break;
                    }
                }
                Settler settler = new Settler(civilization, null, tile);
                Unit unit = new Unit(civilization, null, tile, UnitType.Warrior);
                this.units.add(unit);
                this.seedForUnits.put(MapSeed, this.units);
                this.civilizations.add(civilization);
                count++;
            }
        }
        this.seedForCivilization.put(MapSeed, this.civilizations);
    }

    public boolean checkAvailabilityOfTile(int mapX, int mapY, int testX, int testY, int valueX, int valueY){
        if(valueX == testX && valueY == testY)
            return false;
        else if(valueX == testX && (valueY + 1 == testY || (valueY > 0 && valueY - 1 == testY)))
            return false;
        else if(valueY == testY && (valueX + 1 == testX || (valueX > 0 && valueX - 1 == testX)))
            return false;
        else if(valueX - 1 == testX && (valueY - 1 == testY || valueY + 1 == testY))
            return false;
        else if(valueX + 1 == testX && (valueY - 1 == testY || valueY + 1 == testY))
            return false;
        else if(valueY - 1 == testY && (valueX - 1 == testX || valueX + 1 == testX))
            return false;
        else return valueY + 1 != testY || (valueX - 1 != testX && valueX + 1 != testX);
    }


    public Tile getTerrain(int x , int y){
        return null;
    }

    public String changeTurn(){
        return "";
    }

    public String move(Matcher matcher){
        return "";
    }

    public String moveUnit(){
        return "";
    }

    public String attack(Tile terrain){
        return "";
    }

    public String selectUnit(){
        return "";
    }

    public String assignTerrainState(Tile terrain){
        return "";
    }

    public String sleep(){
        return "";
    }
    public void updateData(){

    }

    public void updateCurrentBuildingProject(){

    }

    public void updateCurrentTechnologyProject(){

    }

    public String printResearchPanel(){
        return null;
    }
}
