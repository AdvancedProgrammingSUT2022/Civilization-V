package Controller.GameController;

import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.Enums.Color;
import Model.Enums.Direction;
import Model.Enums.MapEnum;
import Model.Movement.Graph;
import Model.Movement.Node;
import Model.TileRelated.Feature.Feature;
import Model.TileRelated.Feature.FeatureType;
import Model.TileRelated.Feature.River;
import Model.TileRelated.Resource.Resource;
import Model.TileRelated.Resource.ResourceType;
import Model.TileRelated.Terraine.TerrainType;
import Model.TileRelated.Tile.Tile;
import Model.TileRelated.Tile.TileVisibility;
import Model.Units.TypeEnums.MainType;
import Model.Units.TypeEnums.UnitType;
import Model.Units.Unit;
import Model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;

public class GameController {
    private final ArrayList<Civilization> civilizations = new ArrayList<>();
    private Civilization playerTurn;
    private static ArrayList<Tile> tiles = new ArrayList<>();
    private Unit selectedUnit;
    private final ArrayList<Unit> units = new ArrayList<Unit>();
    private Random random = new Random();
    private Graph initialGraph;
    private ArrayList<Unit> movingUnits = new ArrayList<>();

    public Civilization getPlayerTurn() {
        return playerTurn;
    }

    public String printMap(){
        String[][] map = new String[MapEnum.MAPHEIGHT.amount * MapEnum.HEXSIDESHORT.amount * 2 + MapEnum.HEXSIDESHORT.amount + 1][MapEnum.MAPWIDTH.amount * (MapEnum.HEXSIDELONG.amount + MapEnum.HEXSIDESHORT.amount) + MapEnum.HEXSIDESHORT.amount];
        blankMap(map);
        for (int i = 0; i < tiles.size(); i++) {
            fillTileInfo(tiles.get(i), map);
        }
        if(selectedUnit != null)
        System.out.println(selectedUnit.getUnitType());
        return printArray(map);
    }
    public String printArray(String array[][]){
        String returnString = new String();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                returnString += array[i][j];
            }
            if(i != array.length - 1)
                returnString += "\n";
        }
        return returnString;
    }
    public void blankMap(String map[][]){
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = " ";
            }
        }
    }

    public String assignCharToCivilization(Civilization civilization){
        int asciiA = (int)'A';
        char returnChar = (char)(asciiA + civilizations.indexOf(civilization));
        return Character.toString(returnChar);
    }

    public void nullify(String map[][],int startIndex,int length,int y){
        for (int i = startIndex; i < startIndex + length; i++) {
            map[y][i] = "";
        }
    }

    public void fillTileInfo(Tile tile,String map[][]){
        int x = NonConventionalCoordinatesX(tile);
        int y = NonConventionalCoordinatesY(tile);
        createHex(map, getBackGroundColor(tile), Color.ANSI_RESET, x, y,tile);
        printInfo(map, x, y, tile);
    }

    private Boolean hasRiverBetween(Tile one,Tile two){
        for (River river : one.getRivers()) {
            if(river.otherTile(one) == two)
                return true;
        }
        return false;
    }

    private void createHex(String map[][],String backgroundColor,String reset,int x,int y,Tile tile){
        ArrayList<String> hasColor = hasRiverBorders(tile);
        if(y == 0 || y == MapEnum.HEXSIDESHORT.amount){for (int i = MapEnum.HEXSIDESHORT.amount; i < MapEnum.HEXSIDELONG.amount + MapEnum.HEXSIDESHORT.amount ; i++){if(i == 0)map[y][x] = hasColor.get(Direction.NORTH.ordinal()) + "_";if(i == MapEnum.HEXSIDELONG.amount + MapEnum.HEXSIDESHORT.amount - 1)map[y][x + i] = "_" + reset;else map[y][x + i] = "_";}}
        for (int i = 1; i < MapEnum.HEXSIDESHORT.amount * 2 + 1; i++) {
            String chap,rast;
            int tmp;
            if(i <= MapEnum.HEXSIDESHORT.amount){tmp = Math.abs(MapEnum.HEXSIDESHORT.amount - i);chap = hasColor.get(Direction.NORTHWEST.ordinal()) + "/" + reset;rast = hasColor.get(Direction.NORTHEAST.ordinal()) + "\\" + reset;}
            else{tmp = Math.abs(MapEnum.HEXSIDESHORT.amount - i) - 1;chap = hasColor.get(Direction.SOUTHWEST.ordinal()) +"\\" + reset;rast = hasColor.get(Direction.SOUTHEAST.ordinal()) +"/" + reset;}
            map[y + i][x + tmp] = chap;map[y + i][x + tmp + 1] = backgroundColor + " ";map[y + i][x + MapEnum.HEXSIDELONG.amount + MapEnum.HEXSIDESHORT.amount * 2 - tmp - 2] = " " + reset;map[y + i][x + MapEnum.HEXSIDELONG.amount + MapEnum.HEXSIDESHORT.amount * 2 - tmp - 1] = rast;
        }
        map[y + MapEnum.HEXSIDESHORT.amount * 2][x + MapEnum.HEXSIDESHORT.amount - 1]= hasColor.get(Direction.SOUTHWEST.ordinal()) + "\\" + reset;map[y + MapEnum.HEXSIDESHORT.amount * 2][x + MapEnum.HEXSIDESHORT.amount] = backgroundColor + hasColor.get(Direction.SOUTH.ordinal()) + "_";for (int i = MapEnum.HEXSIDESHORT.amount + 1; i < MapEnum.HEXSIDELONG.amount + MapEnum.HEXSIDESHORT.amount  - 1; i++) {map[y + MapEnum.HEXSIDESHORT.amount * 2][x + i] = "_";}map[y + MapEnum.HEXSIDESHORT.amount * 2][x + MapEnum.HEXSIDELONG.amount + MapEnum.HEXSIDESHORT.amount - 1] = "_" + reset;map[y + MapEnum.HEXSIDESHORT.amount * 2][x + MapEnum.HEXSIDELONG.amount + MapEnum.HEXSIDESHORT.amount] = hasColor.get(Direction.SOUTHEAST.ordinal()) + "/" + reset;
    }
    

    private ArrayList<String> hasRiverBorders(Tile tile){
        ArrayList<String> hasRivers = new ArrayList<>(){{for(int i = 0;i < 6;i++)add("");}};
        if(getVisibility(tile).equals(TileVisibility.FOGOFWAR) == false)
            for (River borderRiver : tile.getRivers()) {
                hasRivers.set(findNeighborDirection(tile, borderRiver.otherTile(tile)).ordinal(), Color.ANSI_CYAN_BACKGROUND);
            }
        return hasRivers;
    }

    private String getBackGroundColor(Tile tile){
        if(getVisibility(tile).equals(TileVisibility.FOGOFWAR))
            return Color.ANSI_WHITE_BACKGROUND;
        else if(tile.getTerrain().name().equals(TerrainType.Desert.name()))
            return Color.ANSI_YELLOW_BACKGROUND;
        else if(tile.getTerrain().name().equals(TerrainType.Grassland.name()))
            return Color.ANSI_GREEN_BACKGROUND;
        else if(tile.getTerrain().name().equals(TerrainType.Hill.name()))
            return Color.ANSI_PURPLE_BACKGROUND;
        else if(tile.getTerrain().name().equals(TerrainType.Mountain.name()))
            return Color.ANSI_BLACK_BACKGROUND;
        else if(tile.getTerrain().name().equals(TerrainType.Ocean.name()))
            return Color.ANSI_BLUE_BACKGROUND;
        else if(tile.getTerrain().name().equals(TerrainType.Plains.name()))
            return Color.ANSI_GREEN_BACKGROUND;
        else if(tile.getTerrain().name().equals(TerrainType.Snow.name()))
            return Color.ANSI_PURPLE_BACKGROUND;
        else if(tile.getTerrain().name().equals(TerrainType.Tundra.name()))
            return Color.ANSI_RED_BACKGROUND;
        return Color.getBackgroundColor(tile.getTerrain().ordinal());
    }

    private int NonConventionalCoordinatesY(Tile tile){
        if(tile.getX() % 2 == 0){
            return (MapEnum.HEXSIDESHORT.amount * 2) * tile.getY();
        }
        return tile.getY() * MapEnum.HEXSIDESHORT.amount * 2 + MapEnum.HEXSIDESHORT.amount;

    }

    private int NonConventionalCoordinatesX(Tile tile){
        if(tile.getX() % 2 == 0){
            return (MapEnum.HEXSIDESHORT.amount * 2 + MapEnum.HEXSIDELONG.amount * 2) * tile.getX() / 2;
        }
        return (tile.getX() - 1) * (MapEnum.HEXSIDESHORT.amount * 2 + MapEnum.HEXSIDELONG.amount * 2) / 2 + (MapEnum.HEXSIDESHORT.amount + MapEnum.HEXSIDELONG.amount);
    }

    private void printInfo(String map[][],int x,int y,Tile tile){
        if(isFogOfWar(tile) == false){
            ArrayList<String> texts = new ArrayList<>();
            fillTextsForTilePrint(texts, tile);
            int textDistance = MapEnum.HEXSIDESHORT.amount / texts.size(),distance = MapEnum.HEXSIDESHORT.amount * 3 / texts.size();
            if(tile.getCivilization() != null)
                printCivilizationChar(map, x, y, distance, tile);
            for (int i = 0; i < texts.size(); i++) {
                distance += textDistance;
                if(texts.get(i) != null)
                    printInfoTile(map, distance, texts.get(i), x, y);
            }
        }
    }

    private boolean isFogOfWar(Tile tile){
        return (getVisibility(tile) == TileVisibility.FOGOFWAR);
    }
    private void printCivilizationChar(String map[][],int x,int y,int textDistance,Tile tile){
        map[y + textDistance][x + (MapEnum.HEXSIDESHORT.amount * 2 + MapEnum.HEXSIDELONG.amount) / 2] = assignCharToCivilization(tile.getCivilization());
    }
    private void fillTextsForTilePrint(ArrayList<String> texts,Tile tile){
        if(getVisibility(tile) == TileVisibility.VISIBLE){
            addVisibleTypeTexts(tile, texts);
        }
        else{
            addRevealedTypeTexts(tile, texts);
        }
        texts.add("T: " + tile.getTerrain().name());
    }
    private void addVisibleTypeTexts(Tile tile,ArrayList<String> texts){
        if(tile.getFeature() != null)texts.add("F:" + tile.getFeature().getFeatureType().name());else{texts.add(null);}
        if(tile.getResource() != null)texts.add("R:" + tile.getResource().getResourceType().name());else{texts.add(null);}
        //if(tile.getImprovement() != null)texts.add("I:" + tile.getImprovement().getImprovementType().name());else{texts.add(null);}
        texts.add("y:" + tile.getY() + " " + "x:" + tile.getX());
        //if(tile.getBuilding() != null)texts.add("B:" + tile.getBuilding().getBuildingType().name());else{texts.add(null);}
        if(tile.getUnits().size() == 1){texts.add("U:" + tile.getUnits().get(0).getUnitType().name());}else{texts.add(null);}
        if(tile.getUnits().size() == 2){texts.add("U:" + tile.getUnits().get(1).getUnitType().name());}else{texts.add(null);}
    }
    private void addRevealedTypeTexts(Tile tile,ArrayList<String> texts){
        texts.add("REVEALED");
        if(playerTurn.getRevealedFeatures().get(tile) != null)texts.add("F:" + playerTurn.getRevealedFeatures().get(tile).getFeatureType().name());else{texts.add(null);}
        if(playerTurn.getRevealedResources().get(tile) != null)texts.add("R:" + playerTurn.getRevealedResources().get(tile).getResourceType().name());else{texts.add(null);}
        //if(playerTurn.getRevealedImprovements().get(tile) != null)texts.add("I:" + playerTurn.getRevealedImprovements().get(tile).getImprovementType().name());else{texts.add(null);}
        texts.add("y:" + tile.getY() + " " + "x:" + tile.getX());
       // if(playerTurn.getRevealedBuildings().get(tile) != null)texts.add("B:" + playerTurn.getRevealedBuildings().get(tile).getBuildingType().name());else{texts.add(null);}
    }
    private void printInfoTile(String map[][],int textDistance,String infoString,int x,int y){
        nullify(map, x + ((MapEnum.HEXSIDESHORT.amount + MapEnum.HEXSIDESHORT.amount * 2) / 2) -  infoString.length() / 2 + 1,infoString.length(), y + textDistance);
        map[y + textDistance][x + ((MapEnum.HEXSIDESHORT.amount + MapEnum.HEXSIDESHORT.amount * 2) / 2) -  infoString.length() / 2 + 1] = infoString;
    }
    //print map finish


    public void generateMap(int mapX, int mapY){ // map[x][y]
        Random randomSeed = new Random();
        int MapSeed = randomSeed.nextInt(1000);
        this.random = new Random(MapSeed);
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
                    int testMountain = random.nextInt(10);
                    if(testMountain < 2 && mountainCount < mapY / 2) {
                        terrainType = TerrainType.Mountain;
                        mountainCount++;
                    } else{
                        while(Objects.equals(terrainType , TerrainType.Ocean) || Objects.equals(terrainType, TerrainType.Mountain)) {
                            terrainType = getARandomTerrainType();
                        }
                    }
                    tile.setTerrain(terrainType);
                    if (!tile.checkType(TerrainType.Snow) && !tile.checkType(TerrainType.Mountain) && !tile.checkType(TerrainType.Ocean) && !tile.checkType(TerrainType.Tundra)) {
                        Resource resource = getARandomResource();
                        Feature feature = getARandomFeature();
                        int test = random.nextInt(10);
                        if (test < 3) tile.setFeature(feature);
                        else if (test > 7) tile.setResource(resource);
                        else {
                            tile.setFeature(feature);
                            tile.setResource(resource);
                        }
                    } else if (tile.checkType(TerrainType.Snow) || tile.checkType(TerrainType.Tundra)) {
                        Resource resource = getARandomResource();
                        tile.setResource(resource);
                    }
                }
                tiles.add(tile);
            }
        }
        setRivers(mapX, mapY);
    }

    private void setRivers(int mapX, int mapY){
        for(Tile value : tiles){
            if(value.checkType(TerrainType.Ocean)) setRiversIntoOcean(value, mapX, mapY);
        }
        ArrayList<Tile> doesNotHaveRiver = new ArrayList<Tile>();
        int riverCount = 0;
        for(Tile value : tiles){
            if(value.getRivers().size() == 0) doesNotHaveRiver.add(value);
            else riverCount++;
        }
        int setRiver = (mapX*mapY) / 3 - riverCount;
        while(setRiver > 0){
            int randomTileIndex = (int)(Math.random() * doesNotHaveRiver.size());
            Random random = new Random();
            int setRandom = random.nextInt( 6);
            while(getSurroundings(doesNotHaveRiver.get(randomTileIndex)).get(setRandom) == null)
                setRandom = random.nextInt( 6);
            setRiverTile(doesNotHaveRiver.get(randomTileIndex), getSurroundings(doesNotHaveRiver.get(randomTileIndex)).get(setRandom));
            for(Tile value : doesNotHaveRiver){
                if(Objects.equals(value, getSurroundings(doesNotHaveRiver.get(randomTileIndex)).get(setRandom))){
                    doesNotHaveRiver.remove(getSurroundings(doesNotHaveRiver.get(randomTileIndex)));
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
                if(!getTile(OceanTile.getX() + 1, OceanTile.getY() - 1).checkType(TerrainType.Ocean) && !getTile(OceanTile.getX(), OceanTile.getY() - 1).checkType(TerrainType.Ocean))
                    setRiverTile(getTile(OceanTile.getX() + 1, OceanTile.getY() - 1), getTile(OceanTile.getX(), OceanTile.getY() - 1));
            } else if(OceanTile.getX() - 1 > 0 && OceanTile.getY() - 1 > 0) {
                if(!getTile(OceanTile.getX() - 1, OceanTile.getY()).checkType(TerrainType.Ocean) && !getTile(OceanTile.getX() - 1, OceanTile.getY() - 1).checkType(TerrainType.Ocean))
                    setRiverTile(getTile(OceanTile.getX() - 1, OceanTile.getY()), getTile(OceanTile.getX() - 1, OceanTile.getY() - 1));
            }
        } else {
            if(OceanTile.getX() - 1 > 0 && OceanTile.getY() - 1 > 0){
                if(!getTile(OceanTile.getX() - 1, OceanTile.getY()).checkType(TerrainType.Ocean) && !getTile(OceanTile.getX(), OceanTile.getY() - 1).checkType(TerrainType.Ocean))
                    setRiverTile(getTile(OceanTile.getX() - 1, OceanTile.getY()), getTile(OceanTile.getX(), OceanTile.getY() - 1));
            } else if(OceanTile.getY() + 1 < mapY && OceanTile.getX() - 1 > 0) {
                if(!getTile(OceanTile.getX(), OceanTile.getY() + 1).checkType(TerrainType.Ocean) && !getTile(OceanTile.getX() - 1, OceanTile.getY() + 1).checkType(TerrainType.Ocean))
                    setRiverTile(getTile(OceanTile.getX(), OceanTile.getY() + 1), getTile(OceanTile.getX() - 1, OceanTile.getY() + 1));
            } else if(OceanTile.getX() + 1 < mapX && OceanTile.getY() + 1 < mapY) {
                if(!getTile(OceanTile.getX() + 1, OceanTile.getY()).checkType(TerrainType.Ocean) && !getTile(OceanTile.getX() + 1, OceanTile.getY() + 1).checkType(TerrainType.Ocean))
                    setRiverTile(getTile(OceanTile.getX() + 1, OceanTile.getY()), getTile(OceanTile.getX() + 1, OceanTile.getY() + 1));
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

    private Resource getARandomResource(){
        int pickResource = new Random().nextInt(ResourceType.values().length);
        return new Resource(ResourceType.values()[pickResource]);
    }

    private TerrainType getARandomTerrainType(){
        int pickTerrain = new Random().nextInt(TerrainType.values().length);
        return TerrainType.values()[pickTerrain];
    }

    private Feature getARandomFeature(){
        int pickFeature = new Random().nextInt(FeatureType.values().length);
        return new Feature(FeatureType.values()[pickFeature]);
    }

    public void gameInit(ArrayList<User> players){
        generateMap(MapEnum.MAPWIDTH.amount , MapEnum.MAPHEIGHT.amount);
        int playersCount = players.size();
        ArrayList<Tile> availableMapTiles = new ArrayList<>(tiles);
        initialGraph = Movement.graphInit();
        for (int i = 0; i < playersCount ; i++) {
            Tile settlerDeploy = new Tile();
            Tile warriorDeploy = new Tile();
            Civilization civilization = new Civilization();
            civilization.setUser(players.get(i));
            seenByInit(civilization.getSeenBy());
            this.civilizations.add(civilization);
            outer:
            while (availableMapTiles.size() != 0) {
                Tile center = availableMapTiles.get(random.nextInt(availableMapTiles.size()));
                if(center.getTerrain().equals(TerrainType.Mountain) || center.getTerrain().equals(TerrainType.Ocean))continue;
                ArrayList <Tile> availableSurroundings = getSurroundings(center);
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
                int index = random.nextInt(availableSurroundings.size());
                warriorDeploy = availableSurroundings.get(index);
                settlerDeploy = center;
                break outer;
            }
            makeUnit(UnitType.Settler,civilization,null,settlerDeploy);
            makeUnit(UnitType.Warrior,civilization,null,warriorDeploy);
        }
        playerTurn = civilizations.get(0);
        saveUsersInformation();
    }


    private void makeUnit(UnitType unitType, Civilization civilization , City city, Tile tile){
        Unit unit = new Unit(civilization,city,tile,unitType);
        civilization.addUnit(unit);
        tile.getUnits().add(unit);
        tile.setCivilization(civilization);
        units.add(unit);
        //city.units.add(unit);
        changeVision(tile,civilization.getSeenBy(),1,2);
    }



    public void seenByInit(HashMap<Tile,Integer> seenBy){
        for (Tile key:tiles) {
            seenBy.put(key,-1);
        }
    }

    private void changeVision(Tile tile , HashMap<Tile , Integer> seenBy , int changeAmount , int visionRadius){
        HashMap <Tile,Integer> visibility = new HashMap<>();
        visibility = findVisibles(tile , 0 ,visibility);
        for (Tile key:visibility.keySet()) {
            if(visibility.get(key) <= visionRadius){
                if(seenBy.get(key) == -1 && changeAmount == 1)seenBy.replace(key,0);
                seenBy.replace(key,seenBy.get(key) + changeAmount);
            }
        }
    }

    private HashMap<Tile , Integer> findVisibles(Tile tile , int cycleCount , HashMap<Tile , Integer> visibles){
        if(cycleCount == 0)visibles.put(tile , 0);
        int seePoint = 1;
        if(cycleCount!= 0 && (tile.getTerrain().equals(TerrainType.Hill) || tile.getTerrain().equals(TerrainType.Mountain) /*|| tile.getFeature().equals(FeatureType.Jungle)*/))seePoint = 2;
        ArrayList<Tile> surroundings = getSurroundings(tile);
        for (Tile nextTile: surroundings) {
            if(nextTile == null)continue;
            int nextSeePoint = seePoint + visibles.get(tile);
            if(visibles.get(nextTile) != null ){
                if(visibles.get(nextTile) > nextSeePoint) visibles.replace(nextTile,nextSeePoint);
            }
            else visibles.put(nextTile,nextSeePoint);
        }
        cycleCount++;
        if(cycleCount==2)return visibles;
        for (Tile nextTile:surroundings) {
            if(nextTile == null)continue;
            findVisibles(nextTile , cycleCount , visibles);
        }
        return visibles;
    }

    private TileVisibility getVisibility(Tile tile){
        if(playerTurn.getSeenBy().get(tile) == -1)return TileVisibility.FOGOFWAR;
        if(playerTurn.getSeenBy().get(tile) == 0)return TileVisibility.REVEALED;
        return TileVisibility.VISIBLE;
    }

    public static ArrayList<Tile> getSurroundings(Tile tile){ //Be careful some tiles might be null!
        int first,second;
        if(tile.getX() % 2 == 0){first = -1;second = 0;}else{first = 0;second = 1;}
        ArrayList <Tile> surroundings = new ArrayList<>(){
            {
                add(getTile(tile.getX() , tile.getY() +1)); //index 0 is down
                add(getTile(tile.getX() , tile.getY() -1)); //index 1 is up
                add(getTile(tile.getX()+1 , tile.getY() + first)); //index 2 is up right
                add(getTile(tile.getX()-1 , tile.getY() + first)); //index 3 is up left
                add(getTile(tile.getX()+1 , tile.getY() + second)); //index 4 is down right
                add(getTile(tile.getX()-1 , tile.getY() + second)); //index 5 is down left
            }
        };
        return surroundings;
    }
    private Direction findNeighborDirection(Tile origin,Tile neighbour){
        int first,second;
        if(origin.getX() % 2 == 0){first = -1;second = 0;}else{first = 0;second = 1;}
        if(origin.getX() == neighbour.getX() && origin.getY() + 1 == neighbour.getY())
            return Direction.SOUTH;
        else if(origin.getX() == neighbour.getX() && origin.getY() - 1 == neighbour.getY())
        return Direction.NORTH; 
        else if(origin.getX() + 1 == neighbour.getX() && origin.getY() + first == neighbour.getY())
        return Direction.NORTHEAST;
        else if(origin.getX() - 1 == neighbour.getX() && origin.getY() + first == neighbour.getY())
        return Direction.NORTHWEST;
        else if(origin.getX() + 1 == neighbour.getX() && origin.getY() + second == neighbour.getY())
        return Direction.SOUTHEAST;
        else if(origin.getX() - 1 == neighbour.getX() && origin.getY() + second == neighbour.getY())
        return Direction.SOUTHWEST;
        return null;
    }
    private Tile getDirectionTile(Tile tile,Direction direction){
        return getSurroundings(tile).get(direction.ordinal());
    }
    public static Tile getTile(int x , int y){
        for (Tile key:tiles) {
            if(key.getX() == x && key.getY() == y)return key;
        }
        return null;
    }

    public String nextTurn(){
        int turnIndex = civilizations.indexOf(playerTurn);
        if(turnIndex == civilizations.size() - 1){turnIndex = 0;}
        else turnIndex++;
        playerTurn = civilizations.get(turnIndex);
        restoreUnitMovementLeft();
        return "next player turn!";
    }
    public void restoreUnitMovementLeft(){
        for (Unit unit : playerTurn.getUnits()) {
            if(this.playerTurn == unit.getCivilization()){
                unit.restoreMovementLeft();
                if(this.movingUnits.contains(unit))
                    moveUnit(unit);
            }
        }
    }

    public String initMoveUnit(Matcher matcher){
        if(selectedUnit == null)return "no selected unit";
        int dx = Integer.parseInt(matcher.group("destinationX"));
        int dy = Integer.parseInt(matcher.group("destinationY"));
        if(dx > MapEnum.MAPWIDTH.amount-1 || dy > MapEnum.MAPWIDTH.amount -1)return "invalid coordinates";
        Tile destination = getTile(dx , dy);
        Tile origin = selectedUnit.getTile();
        if(destination.getTerrain().equals(TerrainType.Ocean) ||
           destination.getTerrain().equals(TerrainType.Mountain) ||
                (destination.getFeature() != null && destination.getFeature().getFeatureType().equals(FeatureType.Ice))){
            return "destination is invalid.";   
        }
        Graph graphCopy = new Graph(initialGraph);
        graphCopy = Movement.calculateShortestPathFromSource(graphCopy,graphCopy.getNode(origin));
        selectedUnit.setPath(graphCopy.getNode(destination).getShortestPath());
        selectedUnit.getPath().remove(0);
        selectedUnit.addNodeToPath(graphCopy.getNode(destination));
        moveUnit(selectedUnit);
        selectedUnit = null;
        return "moving...";
    }

    public void moveUnit(Unit unit) {
        while (unit.getMovementsLeft() > 0) {
            changeVision(unit.getTile(), playerTurn.getSeenBy(), -1, 2);
            if (hasRiverBetween(unit.getTile(), unit.getNextMoveNode().getTile()))
                unit.setMovementsLeft(0);
            else {
                
                unit.addMovementsLeft(-(Movement.calculateDistance(unit.getTile(), unit.getNextMoveNode().getTile())));
                if (unit.getMovementsLeft() < 0)
                    unit.setMovementsLeft(0);
            }
            unit.getTile().getUnits().remove(unit);
            unit.setTile(unit.getNextMoveNode().getTile());
            unit.getTile().addUnit(unit);
            unit.getPath().remove(0);
            changeVision(unit.getTile(), playerTurn.getSeenBy(), 1, 2);
            if(unit.getPath().size() == 0)
                break;
        }
        if (unit.getPath().size() > 0){
            if(movingUnits.contains(unit) != true)
                movingUnits.add(unit);
        }
        else
            if(movingUnits.contains(unit))
                movingUnits.remove(unit);
    }

    public String selectUnit(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        boolean isCombatUnit = true;
        if(matcher.group("type").equals("civil"))isCombatUnit = false;
        if(x > MapEnum.MAPWIDTH.amount -1 || y > MapEnum.MAPHEIGHT.amount -1)return "invalid coordinates";
        ArrayList<Unit> tileUnits;
        if((tileUnits = getTile(x , y).getUnits()) == null)return "no units on this tile";
        for (Unit unit:tileUnits) {
            if(unit.getUnitType().mainType == MainType.NONCOMBAT && !isCombatUnit){
                if(unit.getCivilization() != playerTurn)return "selected unit does not belong to your civilization!";
                selectedUnit = unit;
                return "unit selected";
            }
            else if(!(unit.getUnitType().mainType == MainType.NONCOMBAT) && isCombatUnit){
                if(unit.getCivilization() != playerTurn)return "selected unit does not belong to your civilization!";
                selectedUnit = unit;
                return "unit selected";
            }
        }
        return "unit not found";
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

    public void saveUsersInformation(){
        ArrayList<User> users = new ArrayList<User>();
        for(Civilization civilization : this.civilizations){
            users.add(civilization.getUser());
        }
        String fileName = "./src/main/resources/UserDatabase.json";
        Path path = Paths.get(fileName);
        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
