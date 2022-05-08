package Controller.GameController.MapControllers;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import Controller.GameController.GameController;
import Controller.GameController.Movement;
import Controller.SavingDataController.UserDataController;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.Enums.MapEnum;
import Model.MapRelated.GameMap;
import Model.TileRelated.Feature.Feature;
import Model.TileRelated.Feature.River;
import Model.TileRelated.Resource.Resource;
import Model.TileRelated.Terraine.TerrainType;
import Model.TileRelated.Tile.Tile;
import Model.Units.Unit;
import Model.Units.Combat.Combat;
import Model.Units.Combat.Ranged;
import Model.Units.Combat.Siege;
import Model.Units.TypeEnums.MainType;
import Model.Units.TypeEnums.UnitType;
import Model.User.User;

public class MapGenerator {
    private static MapGenerator mapGenerator;
    private MapGenerator(){}
    public static MapGenerator getInstance(){
        if(mapGenerator == null)
            mapGenerator = new MapGenerator();
        return mapGenerator;
    }
    public void generateMap(int mapX, int mapY){ // map[x][y]
        Random randomSeed = new Random();
        int MapSeed = randomSeed.nextInt(1000);
        GameMap.getInstance().setRandom(new Random(MapSeed));
        int mountainCount = 0;
        for (int i = 0; i < mapY; i++) {
            for (int j = 0; j < mapX; j++) {
                Tile tile = new Tile();
                tile.setX(j);
                tile.setY(i);
                if( i== 0 || j == 0 || j == mapX - 1 || i == mapY - 1){
                    tile.setTerrain(TerrainType.Ocean);
                } else {
                    TerrainType terrainType = getARandomTerrainType();
                    int testMountain = GameMap.getInstance().getRandom().nextInt(10);
                    if(testMountain < 1 && mountainCount < mapY / 2) {
                        terrainType = TerrainType.Mountain;
                        mountainCount++;
                    } else{
                        while(Objects.equals(terrainType , TerrainType.Ocean) || Objects.equals(terrainType, TerrainType.Mountain)) {
                            terrainType = getARandomTerrainType();
                        }
                    }
                    tile.setTerrain(terrainType);
                    if (!tile.checkType(TerrainType.Mountain) && !tile.checkType(TerrainType.Ocean)) {
                        if(terrainType.getPossibleResources() != null) {
                            int test = GameMap.getInstance().getRandom().nextInt(terrainType.getPossibleResources().size());
                            Resource resource = new Resource(terrainType.getPossibleResources().get(test).getResourceType());
                            tile.setResource(resource);
                            if(terrainType.getPossibleFeatures() != null) {
                                for(Feature featureTest : terrainType.getPossibleFeatures()){
                                    if(featureTest.getFeatureType().getPossibleResources() != null && featureTest.getFeatureType().getPossibleResources().contains(resource)){
                                        Feature feature = new Feature(featureTest.getFeatureType());
                                        tile.setFeature(feature);
                                    }
                                }
                            }
                        }
                    }
                }
                GameMap.getInstance().getTiles().add(tile);
            }
        }
        setRivers(mapX, mapY);
    }

    private void setRivers(int mapX, int mapY){
        for(Tile value : GameMap.getInstance().getTiles()){
            if(value.checkType(TerrainType.Ocean)) setRiversIntoOcean(value, mapX, mapY);
        }
        ArrayList<Tile> doesNotHaveRiver = new ArrayList<Tile>();
        int riverCount = 0;
        for(Tile value : GameMap.getInstance().getTiles()){
            if(value.getRivers().size() == 0) doesNotHaveRiver.add(value);
            else riverCount++;
        }
        int setRiver = (mapX*mapY) / 3 - riverCount;
        while(setRiver > 0){
            int randomTileIndex = (int)(GameMap.getInstance().getRandom().nextInt() % doesNotHaveRiver.size());
            int setRandom = GameMap.getInstance().getRandom().nextInt( 6);
            while(MapFunctions.getInstance().getSurroundings(doesNotHaveRiver.get(randomTileIndex)).get(setRandom) == null)
                setRandom = GameMap.getInstance().getRandom().nextInt( 6);
            setRiverTile(doesNotHaveRiver.get(randomTileIndex), MapFunctions.getInstance().getSurroundings(doesNotHaveRiver.get(randomTileIndex)).get(setRandom));
            for(Tile value : doesNotHaveRiver){
                if(Objects.equals(value,MapFunctions.getInstance().getSurroundings(doesNotHaveRiver.get(randomTileIndex)).get(setRandom))){
                    doesNotHaveRiver.remove(MapFunctions.getInstance().getSurroundings(doesNotHaveRiver.get(randomTileIndex)).get(setRandom));
                    break;
                }
            }
            doesNotHaveRiver.remove(randomTileIndex);
            setRiver--;
        }
    }

    private void setRiversIntoOcean(Tile OceanTile, int mapX, int mapY){
        if(OceanTile.getX() % 2 == 1){
            if(OceanTile.getX() + 1 < mapX && OceanTile.getY() - 1 > 0) {
                if(!MapFunctions.getInstance().getTile(OceanTile.getX() + 1, OceanTile.getY() - 1).checkType(TerrainType.Ocean) && !MapFunctions.getInstance().getTile(OceanTile.getX(), OceanTile.getY() - 1).checkType(TerrainType.Ocean))
                    setRiverTile(MapFunctions.getInstance().getTile(OceanTile.getX() + 1, OceanTile.getY() - 1), MapFunctions.getInstance().getTile(OceanTile.getX(), OceanTile.getY() - 1));
            } else if(OceanTile.getX() - 1 > 0 && OceanTile.getY() - 1 > 0) {
                if(!MapFunctions.getInstance().getTile(OceanTile.getX() - 1, OceanTile.getY()).checkType(TerrainType.Ocean) && !MapFunctions.getInstance().getTile(OceanTile.getX() - 1, OceanTile.getY() - 1).checkType(TerrainType.Ocean))
                    setRiverTile(MapFunctions.getInstance().getTile(OceanTile.getX() - 1, OceanTile.getY()), MapFunctions.getInstance().getTile(OceanTile.getX() - 1, OceanTile.getY() - 1));
            }
        } else {
            if(OceanTile.getX() - 1 > 0 && OceanTile.getY() - 1 > 0){
                if(!MapFunctions.getInstance().getTile(OceanTile.getX() - 1, OceanTile.getY()).checkType(TerrainType.Ocean) && !MapFunctions.getInstance().getTile(OceanTile.getX(), OceanTile.getY() - 1).checkType(TerrainType.Ocean))
                    setRiverTile(MapFunctions.getInstance().getTile(OceanTile.getX() - 1, OceanTile.getY()),MapFunctions.getInstance(). getTile(OceanTile.getX(), OceanTile.getY() - 1));
            } else if(OceanTile.getY() + 1 < mapY && OceanTile.getX() - 1 > 0) {
                if(!MapFunctions.getInstance().getTile(OceanTile.getX(), OceanTile.getY() + 1).checkType(TerrainType.Ocean) && !MapFunctions.getInstance().getTile(OceanTile.getX() - 1, OceanTile.getY() + 1).checkType(TerrainType.Ocean))
                    setRiverTile(MapFunctions.getInstance().getTile(OceanTile.getX(), OceanTile.getY() + 1), MapFunctions.getInstance().getTile(OceanTile.getX() - 1, OceanTile.getY() + 1));
            } else if(OceanTile.getX() + 1 < mapX && OceanTile.getY() + 1 < mapY) {
                if(!MapFunctions.getInstance().getTile(OceanTile.getX() + 1, OceanTile.getY()).checkType(TerrainType.Ocean) && !MapFunctions.getInstance().getTile(OceanTile.getX() + 1, OceanTile.getY() + 1).checkType(TerrainType.Ocean))
                    setRiverTile(MapFunctions.getInstance().getTile(OceanTile.getX() + 1, OceanTile.getY()), MapFunctions.getInstance().getTile(OceanTile.getX() + 1, OceanTile.getY() + 1));
            }
        }
    }

    private void setRiverTile(Tile tile1, Tile tile2){
        ArrayList<Tile> adjacentTiles = new ArrayList<Tile>();
        adjacentTiles.add(tile1);
        adjacentTiles.add(tile2);
        River river = new River(adjacentTiles);
        tile1.addRiver(river);
        tile2.addRiver(river);
    }
    private TerrainType getARandomTerrainType(){
        int pickTerrain = GameMap.getInstance().getRandom().nextInt(TerrainType.values().length);
        return TerrainType.values()[pickTerrain];
    }

    public void gameInit(ArrayList<User> players){
        generateMap(MapEnum.MAPWIDTH.amount , MapEnum.MAPHEIGHT.amount);
        int playersCount = players.size();
        ArrayList<Tile> availableMapTiles = new ArrayList<>(GameMap.getInstance().getTiles());
        GameMap.getInstance().setInitialGraph(Movement.getInstance().graphInit());
        for (int i = 0; i < playersCount ; i++) {
            Tile settlerDeploy = new Tile();
            Tile warriorDeploy = new Tile();
            Civilization civilization = new Civilization();
            civilization.setUser(players.get(i));
            TileVisibilityController.getInstance().seenByInit(civilization.getSeenBy());
            GameMap.getInstance().getCivilizations().add(civilization);
            outer:
            while (availableMapTiles.size() != 0) {
                Tile center = availableMapTiles.get(GameMap.getInstance().getRandom().nextInt(availableMapTiles.size()));
                if(center.getTerrain().equals(TerrainType.Mountain) || center.getTerrain().equals(TerrainType.Ocean))continue;
                ArrayList <Tile> availableSurroundings = MapFunctions.getInstance().getSurroundings(center);
                for (Tile surrounding:availableSurroundings) {
                    if(surrounding == null || surrounding.getUnits().size() != 0){
                        availableMapTiles.remove(center);
                        continue outer;
                    }
                }
                for (int j = 0; j <availableSurroundings.size() ; j++) {
                    if(availableSurroundings.get(j).getTerrain().equals(TerrainType.Ocean) || availableSurroundings.get(j).getTerrain().equals(TerrainType.Mountain)){
                        availableSurroundings.remove(availableSurroundings.get(j));
                        j--;
                    }
                }
                if(availableSurroundings.size() == 0){
                    availableMapTiles.remove(center);
                    continue outer;
                }
                int index = GameMap.getInstance().getRandom().nextInt(availableSurroundings.size());
                warriorDeploy = availableSurroundings.get(index);
                settlerDeploy = center;
                break outer;
            }
            makeUnit(UnitType.Settler,civilization,null,settlerDeploy);
            makeUnit(UnitType.Warrior,civilization,null,warriorDeploy);
        }
        GameController.getInstance().setPlayerTurn(GameMap.getInstance().getCivilizations().get(0));
        UserDataController.getInstance().saveUsersInformation();
    }

    private void makeUnit(UnitType unitType, Civilization civilization , City city, Tile tile){
        Unit unit;
        if(unitType.mainType == MainType.NONCOMBAT)
            unit = new Unit(civilization, city, tile, unitType);
        else if(unitType.mainType == MainType.NONRANGED) 
            unit = new Combat(civilization,city,tile,unitType);
        else if(unitType.mainType == MainType.RANGED)
            unit = new Ranged(civilization, city, tile, unitType);
        else
            unit = new Siege(civilization, city, tile, unitType);
        civilization.addUnit(unit);
        tile.getUnits().add(unit);
        GameMap.getInstance().getUnits().add(unit);
        //city.units.add(unit);
        TileVisibilityController.getInstance().changeVision(tile,civilization.getSeenBy(),1,2);
    }
}
