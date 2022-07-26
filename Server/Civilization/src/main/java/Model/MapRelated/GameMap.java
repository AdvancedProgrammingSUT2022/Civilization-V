package Model.MapRelated;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import Controller.GameController.MapControllers.MapFunctions;
import Controller.GameController.MapControllers.MapGenerator;
import Controller.GameController.MapControllers.MapPrinter;
import Model.CivlizationRelated.Civilization;
import Model.Movement.Graph;
import Model.TileRelated.Tile.Tile;
import Model.Units.Unit;
import com.google.gson.annotations.Expose;

public class GameMap {
    @Expose
    private Graph initialGraph;
    @Expose
    private ArrayList<Unit> movingUnits = new ArrayList<>();
    @Expose
    private Civilization playerTurn;
    @Expose
    private int turn = 1;
    @Expose
    private int gameTurn = 0;
    private ArrayList<Unit> units = new ArrayList<>();
    @Expose
    private ArrayList<Tile> tiles = new ArrayList<>();
    @Expose
    private final ArrayList<Civilization> civilizations = new ArrayList<>();
    @Expose
    private int mapWidth;
    @Expose
    private int mapHeight;

    public void loadHashMap(){
        for (Civilization civilization:this.civilizations) {
            HashMap<Tile,Integer> seenBy = new HashMap<>();
            for (int i = 0; i < civilization.getSavingSeenByTile().size(); i++) {
                Tile tile = getTile(civilization.getSavingSeenByTile().get(i).getX(),civilization.getSavingSeenByTile().get(i).getY());
                seenBy.put(tile,civilization.getSavingSeenByInteger().get(i));
            }
            civilization.setSeenBy(seenBy);
        }
    }
    public Tile getTile(int x , int y){

        for (Tile key: this.tiles) {
            if(key.getX() == x && key.getY() == y)
                return key;
        }
        return null;
    }

    public void saveHashmap(){
        for (Civilization civilization:civilizations) {
            civilization.newSavingSeenByTile(civilization.getSeenBy().keySet());
            civilization.setSavingSeenByInteger(new ArrayList<>());
            for (Tile tile:civilization.getSavingSeenByTile()) {
                civilization.getSavingSeenByInteger().add(civilization.getSeenBy().get(tile));
            }
        }
    }
    public int getTurn(GameMap gameMap) {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
    public Civilization getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(Civilization playerTurn) {
        this.playerTurn = playerTurn;
    }
    public Tile getATile(int x, int y){
        for(Tile tile : tiles){
            if(tile.getX() == x && tile.getY() == y){
                return tile;
            }
        }
        return null;
    }


    public int getGameTurn() {
        return gameTurn;
    }

    public void setGameTurn(int gameTurn) {
        this.gameTurn = gameTurn;
    }
    public Graph getInitialGraph() {
        return initialGraph;
    }
    public Random getRandom() {
        return MapGenerator.getInstance().random;
    }
    public ArrayList<Civilization> getCivilizations() {
        return civilizations;
    }
    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }
    public void addUnit(Unit unit){
        this.units.add(unit);
    }
    public ArrayList<Unit> getMovingUnits() {
        return movingUnits;
    }

    public void setInitialGraph(Graph initialGraph) {
        this.initialGraph = initialGraph;
    }
    public String printMap(GameMap gameMap){
        return MapPrinter.getInstance().printMap(gameMap);
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }
}
