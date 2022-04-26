package Model.CivlizationRelated;
import java.util.ArrayList;
import java.util.HashMap;
import Model.TileRelated.Building.Building;
import Model.TileRelated.Feature.Feature;
import Model.TileRelated.Improvement.Improvement;
import Model.Technology.Technology;
import Model.TileRelated.Resource.Resource;
import Model.TileRelated.Tile.Tile;
import Model.Units.Unit;
import Model.User.User;

public class Civilization {
    private User user;
    private ArrayList<City> cities;
    private int population;
    private int gold;
    private int goldProductionRatePerRound;
    private int happiness;
    private ArrayList<Tile> tiles;
    private HashMap<Tile, Integer> seenBy = new HashMap<>();
    private HashMap<Tile, Feature> revealedFeatures = new HashMap<>();
    private HashMap<Tile, Resource> revealedResources = new HashMap<>();
    private HashMap<Tile, Improvement> revealedImprovements = new HashMap<>();
    private HashMap<Tile, Building> revealedBuildings = new HashMap<>();
    private ArrayList<Resource> resources;
    private ArrayList<Technology> technologies;
    private ArrayList<Unit> units = new ArrayList<Unit>();
    private Technology currentResearchProject;
    private ArrayList<DiplomaticTie> diplomaticTies; 
    private ArrayList<String> notifHistory;
    public int getPopulation() {
        return population;
    }
    public HashMap<Tile, Building> getRevealedBuildings() {
        return revealedBuildings;
    }
    public void setRevealedBuildings(HashMap<Tile, Building> revealedBuildings) {
        this.revealedBuildings = revealedBuildings;
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
    public void setSeenBy(HashMap<Tile, Integer> seenBy) {
        this.seenBy = seenBy;
    }
    public int getGold() {
        return gold;
    }
    public void setGold(int gold) {
        this.gold = gold;
    }
    public int getHappiness() {
        return happiness;
    }
    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }
    public void addNotif(String notif){
        notifHistory.add(notif);
    }
    public void addDiplomaticTie(DiplomaticTie diplomaticTie){
        diplomaticTies.add(diplomaticTie);
    }
    public void addUnit(Unit unit){
        units.add(unit);
    }
    public void addGole(int gold){
        this.gold += gold;
    }
    public int calculateHappiness(){
        return 0;
    }
    public int calculateGoldIncome(){
        return 0;
    }
    public int getGoldProductionRatePerRound() {
        return goldProductionRatePerRound;
    }
    public void setGoldProductionRatePerRound(int goldProductionRatePerRound) {
        this.goldProductionRatePerRound = goldProductionRatePerRound;
    }
    public ArrayList<DiplomaticTie> getDiplomaticTies() {
        return diplomaticTies;
    }
    public void setDiplomaticTies(ArrayList<DiplomaticTie> diplomaticTies) {
        this.diplomaticTies = diplomaticTies;
    }
    public void setPopulation(int population) {
        this.population = population;
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
    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }
    public ArrayList<Tile> getTiles() {
        return tiles;
    }
    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }
    public ArrayList<Resource> getResources() {
        return resources;
    }
    public void setResources(ArrayList<Resource> resources) {
        this.resources = resources;
    }
    public ArrayList<Technology> getTechnologies() {
        return technologies;
    }
    public void setTechnologies(ArrayList<Technology> technologies) {
        this.technologies = technologies;
    }
    public ArrayList<Unit> getUnits() {
        return units;
    }

    public Technology getCurrentResearchProject() {
        return currentResearchProject;
    }
    public void setCurrentResearchProject(Technology currentResearchProject) {
        this.currentResearchProject = currentResearchProject;
    }
    public Technology getTechnology(){
        return null;
    }
}
