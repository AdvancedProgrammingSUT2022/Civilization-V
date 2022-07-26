package Controller.GameController.MapControllers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import Controller.GameController.GameController;
import Controller.GameController.Movement;
import Controller.GameController.UnitController;
import Model.CivlizationRelated.Civilization;
import Model.Enums.MapEnum;
import Model.MapRelated.GameMap;
import Model.TileRelated.Feature.Feature;
import Model.TileRelated.Feature.River;
import Model.TileRelated.Resource.Resource;
import Model.TileRelated.Ruin.Ruin;
import Model.TileRelated.Terraine.TerrainType;
import Model.TileRelated.Tile.Tile;
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

    public Random random = new Random();
    private void setFeatures(GameMap gameMap ,int mapX, int mapY, int mapSeed){
        for(int i = 1; i < mapY - 1; i++){
            for(int j = 1; j < mapX - 1; j++){
                int random = gameMap.getRandom().nextInt(10);
                if(random >= 5) {
                    Tile tile = gameMap.getATile(j, i);
                    if (tile.getTerrain().getPossibleFeatures() != null) {
                        Feature feature;
                        int index = gameMap.getRandom().nextInt(tile.getTerrain().getPossibleFeatures().size());
                        feature = new Feature(tile.getTerrain().getPossibleFeatures().get(index).getFeatureType());
                        tile.setFeature(feature);
                    }
                }
            }
        }
    }
    private void setResources(GameMap gameMap , int mapX, int mapY, int mapSeed){
        for(int i = 1; i < mapY - 1; i++){
            for(int j = 1; j < mapX - 1; j++){
                int random = gameMap.getRandom().nextInt(10);
                if(random >= 5) {
                    Tile tile = gameMap.getATile(j, i);
                    if (tile.getFeature() != null && tile.getFeature().getFeatureType().getPossibleResources() != null) {
                        int index = gameMap.getRandom().nextInt(tile.getFeature().getFeatureType().getPossibleResources().size());
                        Resource resource = new Resource(tile.getFeature().getFeatureType().getPossibleResources().get(index).getResourceType());
                        tile.setResource(resource);
                    }
                    if (tile.getTerrain().getPossibleResources() != null) {
                        int index = gameMap.getRandom().nextInt(tile.getTerrain().getPossibleResources().size());
                        Resource resource = new Resource(tile.getTerrain().getPossibleResources().get(index).getResourceType());
                        tile.setResource(resource);
                    }
                }
            }
        }
    }
    private void setTerrainTypes(GameMap gameMap ,int mapX, int mapY, int mapSeed){
        for(int i = 1; i < mapY - 1; i++){
            for (int j = 1; j < mapX - 1; j++) {
                Tile tile = gameMap.getATile(j ,i);
                TerrainType terrainType = getARandomTerrainType(gameMap);
                while(Objects.equals(terrainType , TerrainType.Ocean) || Objects.equals(terrainType, TerrainType.Mountain)) {
                    terrainType = getARandomTerrainType(gameMap);
                }
                tile.setTerrain(terrainType);
            }
        }
    }

    private void setMountains(GameMap gameMap ,int mapX, int mapY, int mapSeed){
        int mountainCount = mapX /2 ;
        for (int i = 1; i < mapY; i++) {
            for (int j = 1; j < mapX; j++) {
                if(mountainCount == 0) break;
                int random = gameMap.getRandom().nextInt(1000);
                if(random % ( mapY - 2 ) == (mapY - 3)){
                    gameMap.getATile(j, i).setTerrain(TerrainType.Mountain);
                    mountainCount--;
                }
            }
        }
    }

    private void setOceans(GameMap gameMap ,int mapX, int mapY){
        for(int i = 0; i < mapY; i++){
            for(int j = 0; j < mapX; j++){
                Tile tile = new Tile();
                tile.setX(j);
                tile.setY(i);
                if( i== 0 || j == 0 || j == mapX - 1 || i == mapY - 1){
                    tile.setTerrain(TerrainType.Ocean);
                }
                gameMap.getTiles().add(tile);
            }
        }
    }

    public GameMap generateMap(int mapX, int mapY){ // map[x][y]
        GameMap gameMap = new GameMap();
        Random randomSeed = new Random();
        int MapSeed = randomSeed.nextInt(1000);
        setRandom(new Random(MapSeed));
        setOceans(gameMap, mapX, mapY);
        setTerrainTypes(gameMap , mapX, mapY, MapSeed);
        setMountains(gameMap ,mapX, mapY, MapSeed);
        setFeatures(gameMap ,mapX, mapY, MapSeed);
        setResources(gameMap , mapX, mapY, MapSeed);
        //setRivers(mapX, mapY);
        setRuins(gameMap , mapX,mapY);
        setWaterSideTiles(gameMap ,mapX , mapY);
        return gameMap;
    }
    public void setRandom(Random random) {
        this.random = random;
    }

    private void setRuins(GameMap gameMap ,int mapX, int mapY) {
        for(int i = 1; i < mapY - 1; i++){
            for(int j = 1; j < mapX - 1; j++){
                int random = gameMap.getRandom().nextInt(25);
                if(random >= 24) {
                    Tile tile = gameMap.getATile(j, i);
                    Ruin ruin = new Ruin(gameMap , tile);
                    tile.setRuin(ruin);
                }
            }
        }
    }

    private void setWaterSideTiles(GameMap gameMap ,int mapX, int mapY){
        for (Tile tile:gameMap.getTiles()) {
            int x = tile.getX();
            int y = tile.getY();
            if(!tile.getRivers().isEmpty())tile.setHasRiverOrOcean(true);
            if(x == 1 || y == 1 || x == mapX - 2 || y == mapY - 2)tile.setHasRiverOrOcean(true);
        }
    }

    private void setRivers(GameMap gameMap ,int mapX, int mapY){
        for(Tile value : gameMap.getTiles()){
            if(value.checkType(TerrainType.Ocean)) setRiversIntoOcean(gameMap , value, mapX, mapY);
        }
        ArrayList<Tile> doesNotHaveRiver = new ArrayList<Tile>();
        int riverCount = 0;
        for(Tile value : gameMap.getTiles()){
            if(value.getRivers().size() == 0) doesNotHaveRiver.add(value);
            else riverCount++;
        }
        int setRiver = (mapX*mapY) / 3 - riverCount;
        while(setRiver > 0){
            int randomTileIndex = gameMap.getRandom().nextInt(doesNotHaveRiver.size());
            int setRandom = 0;
            for (Tile tile:(MapFunctions.getInstance().getSurroundings(gameMap , doesNotHaveRiver.get(randomTileIndex)))) {
                if(tile != null)
                    setRandom = (MapFunctions.getInstance().getSurroundings(gameMap , doesNotHaveRiver.get(randomTileIndex))).indexOf(tile);
            }
            setRiverTile(doesNotHaveRiver.get(randomTileIndex), MapFunctions.getInstance().getSurroundings(gameMap , doesNotHaveRiver.get(randomTileIndex)).get(setRandom));
            ArrayList<Tile> removables = new ArrayList<>();
            Tile tileRiver = doesNotHaveRiver.get(randomTileIndex);
            doesNotHaveRiver.remove(randomTileIndex);
            for(Tile value : doesNotHaveRiver){
                if(Objects.equals(value,MapFunctions.getInstance().getSurroundings(gameMap , tileRiver).get(setRandom))){
                    removables.add(MapFunctions.getInstance().getSurroundings(gameMap , tileRiver).get(setRandom));
                    break;
                }
            }
            for (Tile tile:removables) {
                doesNotHaveRiver.remove(tile);
            }
            setRiver--;
        }
    }

    private void setRiversIntoOcean(GameMap gameMap , Tile OceanTile, int mapX, int mapY){
        if(OceanTile.getX() % 2 == 1){
            if(OceanTile.getX() + 1 < mapX && OceanTile.getY() - 1 > 0) {
                if(!MapFunctions.getInstance().getTile(gameMap , OceanTile.getX() + 1, OceanTile.getY() - 1).checkType(TerrainType.Ocean) && !MapFunctions.getInstance().getTile(gameMap ,OceanTile.getX(), OceanTile.getY() - 1).checkType(TerrainType.Ocean))
                    setRiverTile(MapFunctions.getInstance().getTile(gameMap ,OceanTile.getX() + 1, OceanTile.getY() - 1), MapFunctions.getInstance().getTile(gameMap ,OceanTile.getX(), OceanTile.getY() - 1));
            } else if(OceanTile.getX() - 1 > 0 && OceanTile.getY() - 1 > 0) {
                if(!MapFunctions.getInstance().getTile(gameMap ,OceanTile.getX() - 1, OceanTile.getY()).checkType(TerrainType.Ocean) && !MapFunctions.getInstance().getTile(gameMap ,OceanTile.getX() - 1, OceanTile.getY() - 1).checkType(TerrainType.Ocean))
                    setRiverTile(MapFunctions.getInstance().getTile(gameMap ,OceanTile.getX() - 1, OceanTile.getY()), MapFunctions.getInstance().getTile(gameMap ,OceanTile.getX() - 1, OceanTile.getY() - 1));
            }
        } else {
            if(OceanTile.getX() - 1 > 0 && OceanTile.getY() - 1 > 0){
                if(!MapFunctions.getInstance().getTile(gameMap ,OceanTile.getX() - 1, OceanTile.getY()).checkType(TerrainType.Ocean) && !MapFunctions.getInstance().getTile(gameMap ,OceanTile.getX(), OceanTile.getY() - 1).checkType(TerrainType.Ocean))
                    setRiverTile(MapFunctions.getInstance().getTile(gameMap ,OceanTile.getX() - 1, OceanTile.getY()),MapFunctions.getInstance(). getTile(gameMap ,OceanTile.getX(), OceanTile.getY() - 1));
            } else if(OceanTile.getY() + 1 < mapY && OceanTile.getX() - 1 > 0) {
                if(!MapFunctions.getInstance().getTile(gameMap ,OceanTile.getX(), OceanTile.getY() + 1).checkType(TerrainType.Ocean) && !MapFunctions.getInstance().getTile(gameMap ,OceanTile.getX() - 1, OceanTile.getY() + 1).checkType(TerrainType.Ocean))
                    setRiverTile(MapFunctions.getInstance().getTile(gameMap ,OceanTile.getX(), OceanTile.getY() + 1), MapFunctions.getInstance().getTile(gameMap ,OceanTile.getX() - 1, OceanTile.getY() + 1));
            } else if(OceanTile.getX() + 1 < mapX && OceanTile.getY() + 1 < mapY) {
                if(!MapFunctions.getInstance().getTile(gameMap ,OceanTile.getX() + 1, OceanTile.getY()).checkType(TerrainType.Ocean) && !MapFunctions.getInstance().getTile(gameMap ,OceanTile.getX() + 1, OceanTile.getY() + 1).checkType(TerrainType.Ocean))
                    setRiverTile(MapFunctions.getInstance().getTile(gameMap ,OceanTile.getX() + 1, OceanTile.getY()), MapFunctions.getInstance().getTile(gameMap ,OceanTile.getX() + 1, OceanTile.getY() + 1));
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
    private TerrainType getARandomTerrainType(GameMap gameMap){
        int pickTerrain = gameMap.getRandom().nextInt(TerrainType.values().length);
        return TerrainType.values()[pickTerrain];
    }

    public GameMap gameInit(ArrayList<User> players,int mapWidth,int mapHeight){
        GameMap gameMap = generateMap(mapWidth , mapHeight);
        gameMap.setMapHeight(mapHeight);
        gameMap.setMapWidth(mapWidth);
        int playersCount = players.size();
        ArrayList<Tile> availableMapTiles = new ArrayList<>(gameMap.getTiles());
        gameMap.setInitialGraph(Movement.getInstance().graphInit(gameMap));
        for (int i = 0; i < playersCount ; i++) {
            Tile settlerDeploy = new Tile();
            Tile warriorDeploy = new Tile();
            Civilization civilization = new Civilization();
            civilization.setUser(players.get(i));
            TileVisibilityController.getInstance().seenByInit(gameMap ,civilization.getSeenBy());
            gameMap.getCivilizations().add(civilization);
            outer:
            while (availableMapTiles.size() != 0) {
                Tile center = availableMapTiles.get(gameMap.getRandom().nextInt(availableMapTiles.size()));
                if(center.getTerrain().equals(TerrainType.Mountain) || center.getTerrain().equals(TerrainType.Ocean))continue;
                ArrayList <Tile> availableSurroundings = MapFunctions.getInstance().getSurroundings(gameMap ,center);
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
                int index = gameMap.getRandom().nextInt(availableSurroundings.size());
                warriorDeploy = availableSurroundings.get(index);
                settlerDeploy = center;
                break outer;
            }
            UnitController.getInstance().makeUnit(gameMap ,UnitType.Settler,civilization,settlerDeploy);
            UnitController.getInstance().makeUnit(gameMap , UnitType.Warrior,civilization,warriorDeploy);
        }
        GameController.getInstance().setPlayerTurn(gameMap , gameMap.getCivilizations().get(0));
//        UserDataController.getInstance().saveGame();
        return gameMap;
    }
}
