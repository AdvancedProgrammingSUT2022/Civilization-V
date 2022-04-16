package Model;

import java.util.ArrayList;

import Model.Technology.Technology;
import Model.TileAndFeatures.Building.Building;
import Model.TileAndFeatures.Improvement.Improvement;
import Model.TileAndFeatures.Resource.Resource;
import Model.TileAndFeatures.Tile.Tile;
import Model.Units.Unit;

public class Civilization {
    private int population;
    private int gold;
    private User user;
    private ArrayList<City> cities;
    private ArrayList<Tile> tiles;
    private ArrayList<Resource> resources;
    private ArrayList<Technology> technologies;
    private ArrayList<Unit> units;
    private ArrayList<Improvement> improvements;
    private Technology currentResearchProject;
    private ArrayList<DiplomaticTies> diplomaticTies; 
    private Building building;
    public int getPopulation() {
        return population;
    }
    public ArrayList<Improvement> getImprovements() {
        return improvements;
    }
    public void setImprovements(ArrayList<Improvement> improvements) {
        this.improvements = improvements;
    }
    public Building getBuilding() {
        return building;
    }
    public void setBuilding(Building building) {
        this.building = building;
    }
    public ArrayList<DiplomaticTies> getDiplomaticTies() {
        return diplomaticTies;
    }
    public void setDiplomaticTies(ArrayList<DiplomaticTies> diplomaticTies) {
        this.diplomaticTies = diplomaticTies;
    }
    public void setPopulation(int population) {
        this.population = population;
    }
    public int getGold() {
        return gold;
    }
    public void setGold(int gold) {
        this.gold = gold;
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
    public void setUnits(ArrayList<Unit> units) {
        this.units = units;
    }
    public Technology getCurrentResearchProject() {
        return currentResearchProject;
    }
    public void setCurrentResearchProject(Technology currentResearchProject) {
        this.currentResearchProject = currentResearchProject;
    }
}
