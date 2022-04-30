package Model.MapRelated;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

import Controller.GameController.MapControllers.MapPrinter;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.Movement.Graph;
import Model.TileRelated.Building.Building;
import Model.TileRelated.Building.BuildingType;
import Model.TileRelated.Tile.Tile;
import Model.Units.Unit;

public class GameMap {
    private Graph initialGraph;
    private ArrayList<Unit> movingUnits = new ArrayList<>();
    private ArrayList<Unit> units = new ArrayList<Unit>();
    private ArrayList<Tile> tiles = new ArrayList<>();
    private final ArrayList<Civilization> civilizations = new ArrayList<>();
    private ArrayList<Building> builtBuildings = new ArrayList<Building>();
    private HashMap<City, Object[]> buildingsAreBuilding = new HashMap<City, Object[]>();
    private Random random = new Random();
    private static GameMap map;
    private GameMap(){
    }
    public static GameMap getInstance(){
        if(map == null) 
            map = new GameMap();
        return map;
    }
    public HashMap<City, Object[]> getBuildingsAreBuilding() {
        return buildingsAreBuilding;
    }

    public void addBuildingIsBuilding(City city, BuildingType buildingType, int moneyRemaining){
        Object[] BuildingWithLeftRemainingCost = new Object[2];
        BuildingWithLeftRemainingCost[0] = buildingType.name();
        BuildingWithLeftRemainingCost[1] = moneyRemaining;
        buildingsAreBuilding.put(city, BuildingWithLeftRemainingCost);
    }

    public ArrayList<Building> getBuiltBuildings() {
        return builtBuildings;
    }
    public void addBuiltBuilding(Building building){
        this.builtBuildings.add(building);
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
