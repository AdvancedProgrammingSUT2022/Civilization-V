package Controller.GameController.MapControllers;
import Model.TileRelated.Tile.Tile;
import java.util.ArrayList;
import Controller.GameController.GameController;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.Enums.Color;
import Model.Enums.Direction;
import Model.Enums.MapEnum;
import Model.MapRelated.GameMap;
import Model.TileRelated.Feature.River;
import Model.TileRelated.Terraine.TerrainType;
import Model.TileRelated.Tile.TileVisibility;
import Model.Units.Combat.Combat;
import Model.Units.TypeEnums.MainType;

public class MapPrinter {
    private static MapPrinter mapPrinter;
    private MapPrinter(){}
    public static MapPrinter getInstance(){
        if(mapPrinter == null)
            mapPrinter = new MapPrinter();
        return mapPrinter;
    }
    public String printMap(){
        String[][] printMap = new String[MapEnum.MAPHEIGHT.amount * MapEnum.HEXSIDESHORT.amount * 2 + MapEnum.HEXSIDESHORT.amount + 1][MapEnum.MAPWIDTH.amount * (MapEnum.HEXSIDELONG.amount + MapEnum.HEXSIDESHORT.amount) + MapEnum.HEXSIDESHORT.amount];
        blankMap(printMap);
        for (int i = 0; i < GameMap.getInstance().getTiles().size(); i++) {
            fillTileInfo(GameMap.getInstance().getTiles().get(i), printMap);
        }
        return printArray(printMap);
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

    private String assignCharToCivilization(Civilization civilization){
        int asciiA = (int)'A';
        char returnChar = (char)(asciiA + GameMap.getInstance().getCivilizations().indexOf(civilization));
        return Character.toString(returnChar);
    }
    private String assignCharToCity(City city){
        int asciiA = (int)'A';
        char returnChar = (char)(asciiA + city.getCivilization().getCities().indexOf(city));
        return Character.toString(returnChar);
    }
    public void nullify(String map[][],int startIndex,int length,int y){
        for (int i = startIndex; i < startIndex + length; i++) {
            map[y][i] = "";
        }
    }

    public void fillTileInfo(Tile tile,String map[][]){
        int x = MapFunctions.getInstance().NonConventionalCoordinatesX(tile);
        int y = MapFunctions.getInstance().NonConventionalCoordinatesY(tile);
        createHex(map, getBackGroundColor(tile), Color.ANSI_RESET, x, y,tile);
        printInfo(map, x, y, tile);
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

    private void printInfo(String map[][],int x,int y,Tile tile){
        if(isFogOfWar(tile) == false){
            ArrayList<String> texts = new ArrayList<>();
            fillTextsForTilePrint(texts, tile);
            int textDistance = MapEnum.HEXSIDESHORT.amount / texts.size(),distance = MapEnum.HEXSIDESHORT.amount * 3 / texts.size();
            if(tile.getCivilization() != null)
                printCivilizationAndCityChar(map, x, y, distance, tile);
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
    private void printCivilizationAndCityChar(String map[][],int x,int y,int textDistance,Tile tile){
        map[y + textDistance][x + (MapEnum.HEXSIDESHORT.amount * 2 + MapEnum.HEXSIDELONG.amount) / 2] = assignCharToCivilization(tile.getCivilization());
        if(tile.getCity() != null){
            nullify(map,1 + x + (MapEnum.HEXSIDESHORT.amount * 2 + MapEnum.HEXSIDELONG.amount) / 2, ("->" + assignCharToCity(tile.getCity())).length(), y + textDistance);
            map[y + textDistance][x + (MapEnum.HEXSIDESHORT.amount * 2 + MapEnum.HEXSIDELONG.amount) / 2] += "->" + assignCharToCity(tile.getCity());
        }
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
        if(tile.getImprovement() != null)texts.add("I:" + tile.getImprovement().getImprovementType().name());else{texts.add(null);}
        texts.add("y:" + tile.getY() + " " + "x:" + tile.getX());
        //if(tile.getBuilding() != null)texts.add("B:" + tile.getBuilding().getBuildingType().name());else{texts.add(null);}
        printUnitInfo(tile, texts, 0);
        printUnitInfo(tile, texts, 1);
    }
    private void printUnitInfo(Tile tile,ArrayList<String> texts,int index){
        if(tile.getUnits().size() == index + 1 && tile.getUnits().get(index).getUnitType().mainType != MainType.NONCOMBAT && tile.getUnits().get(index) instanceof Combat){
            Combat combat = (Combat)tile.getUnits().get(index);
            texts.add("U:" + combat.getUnitType().name() + " " + String.format("%.2f",combat.getHitPoints()) + " " + assignCharToCivilization(combat.getCivilization()));
        }
        else if(tile.getUnits().size() == index + 1){texts.add("U:" + tile.getUnits().get(index).getUnitType().name() + " " + assignCharToCivilization(tile.getUnits().get(index).getCivilization()));}else{texts.add(null);}
    }
    private void addRevealedTypeTexts(Tile tile,ArrayList<String> texts){
        texts.add("REVEALED");
        if(GameController.getInstance().getPlayerTurn().getRevealedFeatures().get(tile) != null)texts.add("F:" + GameController.getInstance().getPlayerTurn().getRevealedFeatures().get(tile).getFeatureType().name());else{texts.add(null);}
        if(GameController.getInstance().getPlayerTurn().getRevealedResources().get(tile) != null)texts.add("R:" + GameController.getInstance().getPlayerTurn().getRevealedResources().get(tile).getResourceType().name());else{texts.add(null);}
        //if(playerTurn.getRevealedImprovements().get(tile) != null)texts.add("I:" + playerTurn.getRevealedImprovements().get(tile).getImprovementType().name());else{texts.add(null);}
        texts.add("y:" + tile.getY() + " " + "x:" + tile.getX());
       // if(playerTurn.getRevealedBuildings().get(tile) != null)texts.add("B:" + playerTurn.getRevealedBuildings().get(tile).getBuildingType().name());else{texts.add(null);}
    }
    private void printInfoTile(String map[][],int textDistance,String infoString,int x,int y){
        nullify(map, x + ((MapEnum.HEXSIDESHORT.amount + MapEnum.HEXSIDESHORT.amount * 2) / 2) -  infoString.length() / 2 + 1,infoString.length(), y + textDistance);
        map[y + textDistance][x + ((MapEnum.HEXSIDESHORT.amount + MapEnum.HEXSIDESHORT.amount * 2) / 2) -  infoString.length() / 2 + 1] = infoString;
    }
    public Boolean hasRiverBetween(Tile one,Tile two){
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
    private TileVisibility getVisibility(Tile tile){
        if(GameController.getInstance().getPlayerTurn().getSeenBy().get(tile) == -1)return TileVisibility.FOGOFWAR;
        if(GameController.getInstance().getPlayerTurn().getSeenBy().get(tile) == 0)return TileVisibility.REVEALED;
        return TileVisibility.VISIBLE;
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
}
