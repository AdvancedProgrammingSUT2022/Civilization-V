package Model.MapRelated;

import java.util.ArrayList;
import java.util.Random;

import Controller.GameController.MapControllers.MapPrinter;
import Model.CivlizationRelated.Civilization;
import Model.Movement.Graph;
import Model.TileRelated.Tile.Tile;
import Model.Units.Unit;

public class GameMap {
    private Graph initialGraph;
    private ArrayList<Unit> movingUnits = new ArrayList<>();
    private ArrayList<Unit> units = new ArrayList<Unit>();
    private ArrayList<Tile> tiles = new ArrayList<>();
    private final ArrayList<Civilization> civilizations = new ArrayList<>();
    private Random random = new Random();
    private static GameMap map;
    private GameMap(){
    }
    public static GameMap getInstance(){
        if(map == null) 
            map = new GameMap();
        return map;
    }
    public Graph getInitialGraph() {
        return initialGraph;
    }
    public Random getRandom() {
        return random;
    }
    public void setRandom(Random random) {
        this.random = random;
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
}
