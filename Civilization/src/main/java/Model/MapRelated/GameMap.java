package Model.MapRelated;
import java.util.ArrayList;
import java.util.Random;

import Controller.GameController.MapControllers.MapGenerator;
import Controller.GameController.MapControllers.MapPrinter;
import Model.CivlizationRelated.Civilization;
import Model.Movement.Graph;
import Model.TileRelated.Building.Building;
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
    private ArrayList<Unit> units = new ArrayList<Unit>();
    @Expose
    private ArrayList<Tile> tiles = new ArrayList<>();
    @Expose
    private final ArrayList<Civilization> civilizations = new ArrayList<>();
    @Expose
    private int mapWidth;
    @Expose
    private int mapHeight;

    private static GameMap map;

    private GameMap(){
    }
    public static GameMap getInstance(){
        if(map == null) 
            map = new GameMap();
        return map;
    }
    public int getTurn() {
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
    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
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
    public void setMovingUnits(ArrayList<Unit> movingUnits) {
        this.movingUnits = movingUnits;
    }
    public void setInitialGraph(Graph initialGraph) {
        this.initialGraph = initialGraph;
    }
    public String printMap(){
        return MapPrinter.getInstance().printMap();
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
