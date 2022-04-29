package Model.CivlizationRelated;
import java.util.ArrayList;

import Controller.GameController.GameController;
import Controller.GameController.MapControllers.MapFunctions;
import Model.TileRelated.Building.Building;
import Model.TileRelated.Building.BuildingType;
import Model.TileRelated.Improvement.Improvement;
import Model.TileRelated.Terraine.TerrainType;
import Model.TileRelated.Tile.Tile;
import Model.Units.Unit;

public class City {
    private Civilization civilization;
    boolean isCapital = false;
    private int storedFood;
    private int hitPoint = 20;
    private int goldPerTurn;
    private int sciencePerTurn;
    private int foodPerTurn;
    private int productionPerTurn;
    private int population;
    private ArrayList<BuildingType> BuildingTypesCanBeBuilt;
    private ArrayList<Building> buildings;
    private ArrayList<Tile> cityTiles;
    private ArrayList<Unit> garrisonUnits;
    private ArrayList<Unit> units;
    private ArrayList<Citizen> citizens;
    private ArrayList<Improvement> improvements;

    public City() {
        this.civilization = new Civilization();
        this.population = 1;
        this.productionPerTurn ++;
        this.sciencePerTurn ++;
        this.buildings = new ArrayList<Building>();
        this.cityTiles = new ArrayList<Tile>();
        this.garrisonUnits = new ArrayList<Unit>();
        this.units = new ArrayList<Unit>();
        this.citizens = new ArrayList<Citizen>();
        this.improvements = new ArrayList<Improvement>();
        this.BuildingTypesCanBeBuilt = new ArrayList<BuildingType>();
        Citizen citizen = new Citizen(this);
        citizens.add(citizen);
    }

    private void calculateFood(){
        foodPerTurn = 0;
        for (Citizen citizen:citizens) {
            foodPerTurn -= 2;
            if(citizen.getTile() == null)continue;
            foodPerTurn += citizen.getTile().calculateFood();
        }
    }

    public ArrayList<BuildingType> getBuildingTypesCanBeBuilt(){
        return this.BuildingTypesCanBeBuilt;
    }
    public void addCanBeBuiltBuildingType(BuildingType buildingType){
        this.BuildingTypesCanBeBuilt.add(buildingType);
    }

    private void calculateProduction(){
        if(isCapital)productionPerTurn = 3;
        else productionPerTurn = 0;
        for (Citizen citizen:citizens) {
            if(citizen.getTile() == null){
                productionPerTurn ++;
                continue;
            }
            productionPerTurn += citizen.getTile().calculateProduction();
        }
    }

    public void addBuilding(Building building){
        buildings.add(building);
    }

    private void calculateGold(){
        if(isCapital)goldPerTurn = 3;
        else goldPerTurn = 0;
        for (Citizen citizen:citizens) {
            if(citizen.getTile() == null)continue;
            goldPerTurn += citizen.getTile().calculateGold();
        }
        for (Building building:buildings) {
            goldPerTurn -= building.getBuildingType().Maintenance;
        }
    }

    public String assignCitizen(Tile tile){
        if(!cityTiles.contains(tile))return "this tile doesnt belong to you";
        for (Citizen citizen:citizens) {
            if(citizen.getTile() != null){
                citizen.setTile(tile);
                tile.setCitizen(citizen);
                calculateFood();
                calculateProduction();
                calculateGold();
                return "done";
            }
        }
        return "all citizens are on work";
    }

    public String removeCitizenFromWork(Tile tile){
        if(tile.getCitizen() == null)return "no citizen works here";
        tile.getCitizen().setTile(null);
        calculateFood();
        calculateProduction();
        calculateGold();
        tile.setCitizen(null);
        return "done";
    }

    public void populationGrowth(){
        if(storedFood > Math.pow(2,population)){
            population ++;
            Citizen citizen = new Citizen(this);
            citizens.add(citizen);
            storedFood -= Math.pow(2,population);
            productionPerTurn ++;
            sciencePerTurn ++;
            foodPerTurn -= 2;
        }
    }

    public ArrayList<Tile> findCitySurroundings(){
        ArrayList <Tile> surroundings = new ArrayList<>();
        for (Tile cityTile:cityTiles) {
            for (Tile surrounding:MapFunctions.getInstance().getSurroundings(cityTile)) {
                if(surrounding != null && !surrounding.getTerrain().equals(TerrainType.Ocean) &&
                        !surrounding.getTerrain().equals(TerrainType.Mountain) && !surroundings.contains(cityTile)){
                    surroundings.add(cityTile);
                }
            }
        }
        return surroundings;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void setCapital(boolean capital) {
        isCapital = capital;
    }

    public Civilization getCivilization() {
        return civilization;
    }

    public void setCivilization(Civilization civilization) {
        this.civilization = civilization;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public ArrayList<Tile> getCityTiles() {
        return cityTiles;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public int getGoldPerTurn() {
        return goldPerTurn;
    }

    public int getSciencePerTurn() {
        return sciencePerTurn;
    }

    public int getPopulation() {
        return population;
    }

    public ArrayList<Building> getBuilding() {
        return buildings;
    }

    public ArrayList<Unit> getGarrisonUnits() {
        return garrisonUnits;
    }

    public ArrayList<Citizen> getCitizens() {
        return citizens;
    }

    public ArrayList<Improvement> getImprovements() {
        return improvements;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public void setGoldPerTurn(int goldPerTurn) {
        this.goldPerTurn += goldPerTurn;
    }

    public void setSciencePerTurn(int sciencePerTurn) {
        this.sciencePerTurn += sciencePerTurn;
    }

    public int getFoodPerTurn() {
        return foodPerTurn;
    }

    public void setPopulation(int population) {
        this.population += population;
    }

    public void setCityTiles(ArrayList<Tile> cityTiles) {
        this.cityTiles = cityTiles;
    }

    public void setGarrisonUnits(ArrayList<Unit> garrisonUnits) {
        this.garrisonUnits = garrisonUnits;
    }

    public void setCitizens(ArrayList<Citizen> citizens) {
        this.citizens = citizens;
    }

    public void setImprovements(ArrayList<Improvement> improvements) {
        this.improvements = improvements;
    }

    public void addCityTiles(Tile tile) {
        this.cityTiles.add(tile);
    }
}
