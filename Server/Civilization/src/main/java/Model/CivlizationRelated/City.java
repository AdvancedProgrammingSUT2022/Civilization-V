package Model.CivlizationRelated;
import java.util.ArrayList;
import Controller.GameController.MapControllers.MapFunctions;
import Model.MapRelated.GameMap;
import Model.TileRelated.Building.Building;
import Model.TileRelated.Building.BuildingType;
import Model.TileRelated.Feature.FeatureType;
import Model.TileRelated.Improvement.Improvement;
import Model.TileRelated.Terraine.TerrainType;
import Model.TileRelated.Tile.Tile;
import Model.Units.TypeEnums.UnitType;
import Model.Units.Unit;
import Model.Units.Combat.Combat;
import com.google.gson.annotations.Expose;

public class City {
    @Expose
    private String name;
    private Civilization civilization;
    @Expose
    private Tile tile;
    @Expose
    boolean isCapital = false;
    @Expose
    private int storedFood;
    @Expose
    private int goldPerTurn;
    @Expose
    private int sciencePerTurn;
    @Expose
    private int foodPerTurn;
    @Expose
    private int productionPerTurn;
    @Expose
    private double hitPoint = 3;
    @Expose
    private double strength = 15;
    @Expose
    private int population = 1;
    @Expose
    private BuildingType underConstructionBuilding;
    @Expose
    private int BuildingTurn = 0;
    @Expose
    private UnitType underConstructionUnit;
    @Expose
    private int UnitTurn = 0;
    @Expose
    private UnitType unitUnderConstruction;
    @Expose
    private ArrayList<BuildingType> BuildingTypesCanBeBuilt;
    @Expose
    private ArrayList<Building> buildings;
    @Expose
    private ArrayList<Tile> cityTiles;
    @Expose
    private ArrayList<UnitType> unitsCanBeBuilt;
    @Expose
    private Unit garrisonUnit;
    @Expose
    private ArrayList<Unit> units;
    @Expose
    private ArrayList<Citizen> citizens;
    @Expose
    private ArrayList<Improvement> improvements;
    @Expose
    private boolean hasAttackLeft = true;

    public City(Civilization civilization) {
        this.civilization = civilization;
        this.population = 1;
        this.buildings = new ArrayList<Building>();
        this.cityTiles = new ArrayList<Tile>();
        this.units = new ArrayList<Unit>();
        this.unitsCanBeBuilt = new ArrayList<UnitType>();
        this.citizens = new ArrayList<Citizen>();
        this.improvements = new ArrayList<Improvement>();
        this.BuildingTypesCanBeBuilt = new ArrayList<BuildingType>();
        Citizen citizen = new Citizen(this);
        citizens.add(citizen);
        civilization.changeHappiness(-1);
    }

    public boolean isHasAttackLeft() {
        return hasAttackLeft;
    }

    public void setHasAttackLeft(boolean hasAttackLeft) {
        this.hasAttackLeft = hasAttackLeft;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public void setBuildingTypesCanBeBuilt(ArrayList<BuildingType> buildingTypesCanBeBuilt) {
        BuildingTypesCanBeBuilt = buildingTypesCanBeBuilt;
    }

    public void setUnitsCanBeBuilt(ArrayList<UnitType> unitsCanBeBuilt) {
        this.unitsCanBeBuilt = unitsCanBeBuilt;
    }

    public void setUnitTurn(int unitTurn) {
        UnitTurn = unitTurn;
    }

    public int getStoredFood() {
        return storedFood;
    }

    public int getUnitTurn() {
        return UnitTurn;
    }

    public int getBuildingTurn() {
        return BuildingTurn;
    }

    public void setBuildingTurn(int buildingTurn) {
        BuildingTurn = buildingTurn;
    }

    public void setUnderConstructionUnit(UnitType underConstructionUnit) {
        this.underConstructionUnit = underConstructionUnit;
    }

    public UnitType getUnderConstructionUnit() {
        return underConstructionUnit;
    }

    public BuildingType getUnderConstructionBuilding() {
        return underConstructionBuilding;
    }

    public void setUnderConstructionBuilding(BuildingType underConstructionBuilding) {
        this.underConstructionBuilding = underConstructionBuilding;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void calculateFood(){
        foodPerTurn = 0;
        foodPerTurn += cityTiles.get(0).calculateFood();
        for (Citizen citizen:citizens) {
            foodPerTurn -= 2;
            if(citizen.getTile() == null)continue;
            foodPerTurn += citizen.getTile().calculateFood();
        }
        storedFood += foodPerTurn;
        if(unitUnderConstruction != null && unitUnderConstruction.equals(UnitType.Settler))storedFood = 0;
    }
    public Unit getGarrisonUnit() {
        return garrisonUnit;
    }
    public void setGarrisonUnit(Unit garrisonUnit) {
        this.garrisonUnit = garrisonUnit;
    }

    public UnitType getUnitUnderConstruction() {
        return unitUnderConstruction;
    }

    public void setUnitUnderConstruction(UnitType unitUnderConstruction) {
        this.unitUnderConstruction = unitUnderConstruction;
    }

    public ArrayList<BuildingType> getBuildingTypesCanBeBuilt(){
        return this.BuildingTypesCanBeBuilt;
    }
    public void addCanBeBuiltBuildingType(BuildingType buildingType){
        this.BuildingTypesCanBeBuilt.add(buildingType);
    }
    public ArrayList<UnitType> getUnitsCanBeBuilt(){
        return this.unitsCanBeBuilt;
    }
    public void addUnitsToCanBeBuilt(UnitType unitType){
        this.unitsCanBeBuilt.add(unitType);
    }
    public void calculateProduction(){
        if(isCapital)productionPerTurn = 3;
        else productionPerTurn = 0;
        productionPerTurn += cityTiles.get(0).calculateProduction();
        for (Citizen citizen:citizens) {
            if(citizen.getTile() == null){
                productionPerTurn ++;
                continue;
            }
            productionPerTurn += citizen.getTile().calculateProduction();
        }
    }

    public int getProductionPerTurn() {
        return productionPerTurn;
    }

    public void addBuilding(Building building){
        buildings.add(building);
    }

    public void calculateGold(){
        if(isCapital)goldPerTurn = 3;
        else goldPerTurn = 0;
        goldPerTurn += cityTiles.get(0).calculateGold();
        for (Citizen citizen:citizens) {
            if(citizen.getTile() == null)continue;
            goldPerTurn += citizen.getTile().calculateGold();
        }
        for (Building building:buildings) {
            goldPerTurn -= building.getBuildingType().Maintenance;
        }
    }

    public void calculateSciencePerTurn(){
        sciencePerTurn = population;
    }

    public String assignCitizen(Tile tile){
        if(tile.getTerrain().equals(TerrainType.Ocean))return "you cant assign citizens to ocean";
        if(tile.getTerrain().equals(TerrainType.Mountain))return "you cant assign citizens to mountains";
        if(tile.getFeature() != null && tile.getFeature().getFeatureType().equals(FeatureType.Ice))return "you cant assign citizens to ice feature";
        if(!cityTiles.contains(tile)){
            this.civilization.addNotification("this tile doesn't belong to you");
            return "this tile doesn't belong to you";
        }
        if(tile.equals(cityTiles.get(0))) {
            this.civilization.addNotification("cant work on cityCenter");
            return "cant work on cityCenter";
        }
        if(tile.getCitizen() != null) return "tile has citizen";
        for (Citizen citizen:citizens) {
            if(citizen.getTile() == null){
                citizen.setTile(tile);
                tile.setCitizen(citizen);
                calculateFood();
                calculateProduction();
                calculateGold();
                this.civilization.addNotification("done");
                return "citizen assigned successfully";
            }
        }
        this.civilization.addNotification("all citizens are on work");
        return "all citizens are on work";
    }

    public String removeCitizenFromWork(Tile tile){
        if(tile.getCitizen() == null){
            this.civilization.addNotification("no citizen works here");
            return "no citizen works here";
        }
        tile.getCitizen().setTile(null);
        calculateFood();
        calculateProduction();
        calculateGold();
        tile.setCitizen(null);
        this.civilization.addNotification("done!");
        return "citizen removed successfully";
    }

    public void calculateBuildingBonuses(){
        for (Building building:buildings) {
            if(building.getBuildingType().equals(BuildingType.Granary)){
                foodPerTurn += 2;
            }
            else if(building.getBuildingType().equals(BuildingType.Library)){
                sciencePerTurn += population/2 ;
            }
            else if(building.getBuildingType().equals(BuildingType.WaterMill)){
                foodPerTurn += 2;
            }
            else if(building.getBuildingType().equals(BuildingType.Market)){
                goldPerTurn += goldPerTurn/4;
            }
            else if(building.getBuildingType().equals(BuildingType.Mint)){
                for (Tile tile:cityTiles) {
                    if(tile.calculateGold() != 0)goldPerTurn += 3;
                }
            }
            else if(building.getBuildingType().equals(BuildingType.University)){
                for (Tile tile:cityTiles) {
                    if(tile.getFeature() != null && tile.getFeature().getFeatureType().equals(FeatureType.Forest) && tile.getCitizen() != null)sciencePerTurn +=2;
                }
                sciencePerTurn += sciencePerTurn/2;
            }
            else if(building.getBuildingType().equals(BuildingType.Bank)){
                goldPerTurn += goldPerTurn/4;
            }
            else if(building.getBuildingType().equals(BuildingType.PublicSchool)){
                sciencePerTurn += sciencePerTurn/2;
            }
            else if(building.getBuildingType().equals(BuildingType.SatrapsCourt)){
                goldPerTurn += goldPerTurn/4;
            }
            else if(building.getBuildingType().equals(BuildingType.StockExchange)){
                goldPerTurn += goldPerTurn/3;
            }
            else if(building.getBuildingType().equals(BuildingType.Factory)){
                productionPerTurn += productionPerTurn/2 ;
            }
            else if(building.getBuildingType().equals(BuildingType.Windmill)){
                if(!cityTiles.get(0).getTerrain().equals(TerrainType.Hill))productionPerTurn += (15*productionPerTurn)/100 ;
            }
        }
    }

    public void populationGrowthAndHunger(){
        if(civilization.getHappiness()<= 0)return;
        int neededFood = (int)Math.pow(2,population);
        for (Building building:buildings) {
            if(building.getBuildingType().equals(BuildingType.Hospital)){
                neededFood /= 2;
                break;
            }
        }
        if(storedFood > neededFood){
            population ++;
            civilization.changeHappiness(-0.25);
            Citizen citizen = new Citizen(this);
            citizens.add(citizen);
            storedFood -= Math.pow(2,population);
            productionPerTurn ++;
            sciencePerTurn ++;
            foodPerTurn -= 2;
        }
        if(storedFood < -1 * neededFood && citizens.size()!= 0){
            storedFood = 0 ;
            if(citizens.get(0).getTile() != null)citizens.get(0).getTile().setCitizen(null);
            citizens.remove(0);
            population -- ;
        }
    }

    public String buyTile(GameMap gameMap , Tile tile){
        if(!findCitySurroundings(gameMap).contains(tile)){
            this.civilization.addNotification("you can't buy this tile");
            return "you can't buy this tile";
        }
        if(this.civilization.getGold() < 50){
            this.civilization.addNotification("you don't have enough gold");
            return "you don't have enough gold";
        }
        this.civilization.changeGold(-50);
        cityTiles.add(tile);
        this.civilization.getTiles().add(tile);
        tile.setCivilization(this.civilization);
        this.civilization.addNotification("done!");
        return "done!";
    }

    public ArrayList<Tile> findCitySurroundings(GameMap gameMap){
        ArrayList <Tile> surroundings = new ArrayList<>();
        for (Tile cityTile:cityTiles) {
            for (Tile surrounding:MapFunctions.getInstance().getSurroundings(gameMap , cityTile)) {
                if(surrounding != null && !surrounding.getTerrain().equals(TerrainType.Ocean) && !cityTiles.contains(surrounding) &&
                        !surrounding.getTerrain().equals(TerrainType.Mountain) && !surroundings.contains(surrounding)){
                    surroundings.add(surrounding);
                }
            }
        }
        return surroundings;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void addUnit(Unit unit){
        this.units.add(unit);
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
    public double calculateMaxCityDamage(){
        double maxDamage = strength;
        if(garrisonUnit != null)    
            maxDamage *= 120.0/100.0;
        maxDamage += population * 0.5;
        return maxDamage;
    }
    public ArrayList<Unit> getUnits() {
        return units;
    }

    public ArrayList<Tile> getCityTiles() {
        return cityTiles;
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

    public ArrayList<Citizen> getCitizens() {
        return citizens;
    }

    public ArrayList<Improvement> getImprovements() {
        return improvements;
    }
    public double getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(double hitPoint) {
        this.hitPoint = hitPoint;
    }

    public void setGoldPerTurn(int goldPerTurn) {
        this.goldPerTurn += goldPerTurn;
    }

    public void setSciencePerTurn(int sciencePerTurn) {
        this.sciencePerTurn += sciencePerTurn;
    }
    public void changeHitPoint(double hitPoint) {
        this.hitPoint += hitPoint;
    }
    public void changeStrength(double strength){
        this.strength += strength;
    }

    public void changeStoredFood(int amount){
        this.storedFood += amount;
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

    public void setCitizens(ArrayList<Citizen> citizens) {
        this.citizens = citizens;
    }

    public void setImprovements(ArrayList<Improvement> improvements) {
        this.improvements = improvements;
    }

    public void addCityTiles(Tile tile) {
        this.cityTiles.add(tile);
    }

    public double getStrength() {
        return strength;
    }

    public double cityAttackDamage(Combat defender) {
        double multiplyer = 1.5;
        if((defender.getTile().getFeature() != null && defender.getTile().getFeature().getFeatureType() == FeatureType.Jungle )|| defender.getTile().getTerrain() == TerrainType.Hill)
            multiplyer -= 0.4;
        return defender.cityAttackDamage(this) * multiplyer;
    }
}
