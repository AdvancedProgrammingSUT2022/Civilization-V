package Model.CivlizationRelated;
import java.util.*;

import Model.Technology.TechnologyType;
import Model.TileRelated.Building.Building;
import Model.TileRelated.Feature.Feature;
import Model.TileRelated.Improvement.Improvement;
import Model.Technology.Technology;
import Model.TileRelated.Resource.Resource;
import Model.TileRelated.Resource.ResourceType;
import Model.TileRelated.Road.Road;
import Model.TileRelated.Tile.Tile;
import Model.Units.Unit;
import Model.User.User;
import javafx.scene.paint.Color;

public class Civilization {
    private User user;
    private ArrayList<City> cities = new ArrayList<>();
    private int gold;
    private int goldPerTurn;
    private int sciencePerTurn;
    private double happiness = 10;
    private int totalCoal;
    private int currentCoal;
    private int totalHorses;
    private int currentHorses;
    private int totalIron;
    private int currentIron;
    private int roadMaintenance;
    private Color color;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    private ArrayList<Tile> tiles = new ArrayList<>();
    private ArrayList<Improvement> improvementsUnderConstruction = new ArrayList<>();
    private ArrayList<Road> roadsUnderConstruction = new ArrayList<>();
    private ArrayList<Feature> featuresBeingCleared = new ArrayList<>();
    private HashMap<Tile, Integer> seenBy = new HashMap<>();
    private HashMap<Tile, Feature> revealedFeatures = new HashMap<>();
    private HashMap<Tile, Resource> revealedResources = new HashMap<>();
    private HashMap<Tile, Improvement> revealedImprovements = new HashMap<>();
    private HashMap<Tile, Building> revealedBuildings = new HashMap<>();
    //private ArrayList<Resource> resources;
    private ArrayList<Technology> technologies = new ArrayList<Technology>();
    private ArrayList<Unit> units = new ArrayList<Unit>();
    private LinkedHashMap<TechnologyType, Integer> researchProjects = new LinkedHashMap<TechnologyType, Integer>();
    private TechnologyType currentResearchProject;
    private int researchTurns = 0;
    private ArrayList<DiplomaticTie> diplomaticTies;
    private ArrayList<String> Notification = new ArrayList<>();
    private ArrayList<ResourceType> foundedLuxuryRecourses = new ArrayList<>();
    private HashMap<ResourceType,Integer> luxuryResourceCount = new HashMap<>(){{
        for (ResourceType resourceType:ResourceType.values()) {
            put(resourceType,0);
        }
    }};

    public HashMap<ResourceType, Integer> getLuxuryResourceCount() {
        return luxuryResourceCount;
    }

    public void addLuxuryResourceCount(ResourceType resourceType){
        luxuryResourceCount.put(resourceType,luxuryResourceCount.get(resourceType)+1);
    }

    public ArrayList<ResourceType> getFoundedLuxuryRecourses() {
        return foundedLuxuryRecourses;
    }

    public int getRoadMaintenance() {
        return roadMaintenance;
    }
    public void changeRoadMaintenance(int amount){
        roadMaintenance += amount;
    }

    public void addLuxuryRecourse(ResourceType resourceType){
        foundedLuxuryRecourses.add(resourceType);
    }

    public LinkedHashMap<TechnologyType, Integer> getResearchProjects() {
        return researchProjects;
    }

    public void addResearchProject(TechnologyType technologyType, int turn){
        this.researchProjects.put(technologyType, turn);
    }
    public void setResearchTurns(int researchTurns) {
        this.researchTurns = researchTurns;
    }

    public int getResearchTurns() {
        return researchTurns;
    }

    public ArrayList<Improvement> getImprovementsUnderConstruction(){
        return improvementsUnderConstruction;
    }

    public ArrayList<Road> getRoadsUnderConstruction() {
        return roadsUnderConstruction;
    }

    public void addImprovementUnderConstruction(Improvement improvement){
        improvementsUnderConstruction.add(improvement);
    }

    public void addRoadUnderConstruction(Road road){
        roadsUnderConstruction.add(road);
    }

    public ArrayList<Feature> getFeaturesBeingCleared() {
        return featuresBeingCleared;
    }
    public void addFeaturesBeingCleared(Feature feature){
        featuresBeingCleared.add(feature);
    }
    public void removeFeaturesBeingCleared(Feature feature){
        featuresBeingCleared.remove(feature);
    }

    public void removeFromImprovementsUnderConstruction(Improvement improvement){
        improvementsUnderConstruction.remove(improvement);
    }

    public void removeFromRoadsUnderConstruction(Road road){
        roadsUnderConstruction.remove(road);
    }

    public boolean hasTechnology(TechnologyType givenTechnology){
        for (Technology technology:technologies) {
            if(givenTechnology.equals(technology.getTechnologyType()))return true;
        }
        return false;
    }

    public HashMap<Tile, Building> getRevealedBuildings() {
        return revealedBuildings;
    }

    public void setRevealedBuildings(HashMap<Tile, Building> revealedBuildings) {
        this.revealedBuildings = revealedBuildings;
    }
    public void addRevealBuilding(Tile tile, Building building){
        this.revealedBuildings.put(tile, building);
    }
    public HashMap<Tile, Resource> getRevealedResources() {
        return revealedResources;
    }

    public void setRevealedResources(HashMap<Tile, Resource> revealedResources) {
        this.revealedResources = revealedResources;
    }
    public HashMap<Tile, Improvement> getRevealedImprovements() {
        return revealedImprovements;
    }
    public void setRevealedImprovements(HashMap<Tile, Improvement> revealedImprovements) {
        this.revealedImprovements = revealedImprovements;
    }
    public HashMap<Tile, Feature> getRevealedFeatures() {
        return revealedFeatures;
    }
    public void setRevealedFeatures(HashMap<Tile, Feature> revealedFeatures) {
        this.revealedFeatures = revealedFeatures;
    }
    public HashMap<Tile, Integer> getSeenBy() {
        return seenBy;
    }
    public int getGold() {
        return gold;
    }
    public void setGold(int gold) {
        this.gold = gold;
    }
    public void changeGold(int gold) {
        this.gold += gold;
    }
    public double getHappiness() {
        return happiness;
    }
    public void changeHappiness(double happiness) {
        this.happiness += happiness;
    }
    public void addNotification(String notification){
        Notification.add(notification);
    }
    public ArrayList<String> getNotification() {
        return Notification;
    }
    public void addDiplomaticTie(DiplomaticTie diplomaticTie){
        diplomaticTies.add(diplomaticTie);
    }
    public void addUnit(Unit unit){
        units.add(unit);
    }
    public void addGold(int gold){
        this.gold += gold;
    }

    public ArrayList<DiplomaticTie> getDiplomaticTies() {
        return diplomaticTies;
    }
    public void setDiplomaticTies(ArrayList<DiplomaticTie> diplomaticTies) {
        this.diplomaticTies = diplomaticTies;
    }

    public void checkGoldRunningOut(){
        if(gold <= 0){
            sciencePerTurn -= 2 ;
        }
    }

    public ArrayList<TechnologyType> searchableTechnologiesTypes(){
        Set<TechnologyType> searchableTechnologiesTypes = new HashSet<>();
        for (TechnologyType technology:TechnologyType.values()) {
            if(technology.PrerequisiteTechs == null && !hasTechnology(technology))
                searchableTechnologiesTypes.add(technology);
        }

        for (Technology technology:technologies) {
            for (TechnologyType technologyType:technology.getTechnologyType().LeadsToTechs) {
                if(!hasTechnology(technologyType))
                    searchableTechnologiesTypes.add(technologyType);
            }
        }

        return new ArrayList<>(searchableTechnologiesTypes);
    }

    public int getGoldPerTurn() {
        return goldPerTurn;
    }

    public int getSciencePerTurn() {
        return sciencePerTurn;
    }

    public void setSciencePerTurn(int sciencePerTurn) {
        this.sciencePerTurn = sciencePerTurn;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public ArrayList<City> getCities() {
        return cities;
    }
    public ArrayList<Tile> getTiles() {
        return tiles;
    }
    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }
//    public ArrayList<Resource> getResources() {
//        return resources;
//    }
//    public void setResources(ArrayList<Resource> resources) {
//        this.resources = resources;
//    }
    public ArrayList<Technology> getTechnologies() {
        return technologies;
    }
    public void setTechnologies(ArrayList<Technology> technologies) {
        this.technologies = technologies;
    }
    public ArrayList<Unit> getUnits() {
        return units;
    }
    public void setGoldPerTurn(int goldPerTurn){
        this.goldPerTurn = goldPerTurn;
    }
    public void changeGoldPerTurn(int goldPerTurn){
        this.goldPerTurn += goldPerTurn;
    }
    public void changeSciencePerTurn(int sciencePerTurn){
        this.sciencePerTurn += sciencePerTurn;
    }
    public TechnologyType getCurrentResearchProject() {
        return currentResearchProject;
    }
    public void setCurrentResearchProject(TechnologyType currentResearchProject) {
        this.currentResearchProject = currentResearchProject;
    }
    public void addTechnology(Technology technology){
        this.technologies.add(technology);
    }

    public void addCity(City city) {
        this.cities.add(city);
    }

    public int getUserScore(){
        return user.getScore();
    }

    public int getTotalCoal() {
        return totalCoal;
    }

    public int getCurrentCoal() {
        return currentCoal;
    }

    public int getTotalHorses() {
        return totalHorses;
    }

    public int getCurrentHorses() {
        return currentHorses;
    }

    public int getTotalIron() {
        return totalIron;
    }

    public int getCurrentIron() {
        return currentIron;
    }

    public void changeTotalCoal(int totalCoal) {
        this.totalCoal += totalCoal;
    }

    public void changeCurrentCoal(int currentCoal) {
        this.currentCoal += currentCoal;
    }

    public void changeTotalHorses(int totalHorses) {
        this.totalHorses += totalHorses;
    }

    public void changeCurrentHorses(int currentHorses) {
        this.currentHorses += currentHorses;
    }

    public void changeTotalIron(int totalIron) {
        this.totalIron += totalIron;
    }

    public void changeCurrentIron(int currentIron) {
        this.currentIron += currentIron;
    }
}
