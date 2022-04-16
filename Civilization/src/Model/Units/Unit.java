package Model.Units;

import java.util.ArrayList;
import Model.City;
import Model.Civilization;
import Model.Technology.Technology;
import Model.TileAndFeatures.Tile.Tile;
import Model.TileAndFeatures.Resource.*;

public class Unit {
    protected Civilization civilization;
    protected City city;
    protected Tile tile;
    protected final int movement;
    protected int leftMovement;
    protected int cost;
    protected Technology technologyRequired;
    protected ArrayList<Resource> resourcesRequired;
    protected boolean isSleep;
    protected boolean isAlerted;
    
    public Unit(Civilization civilization, City city, Tile tile, int movement, int cost, Technology technologyRequired,
            ArrayList<Resource> resourcesRequired) {
        this.civilization = civilization;
        this.city = city;
        this.tile = tile;
        this.movement = movement;
        this.cost = cost;
        this.technologyRequired = technologyRequired;
        this.resourcesRequired = resourcesRequired;
        this.leftMovement = movement;
    }

    public void setAlerted(){

    }

    public void move(Tile dest){

    }

    public void setSleep(){

    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public int getMovement() {
        return this.leftMovement;
    }

    public void setMovement(int leftMovement) {
        this.leftMovement = leftMovement;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Technology getTechnologyRequired() {
        return technologyRequired;
    }

    public void setTechnologyRequired(Technology technologyRequired) {
        this.technologyRequired = technologyRequired;
    }

    public ArrayList<Resource> getResourcesRequired() {
        return resourcesRequired;
    }

    public void setResourcesRequired(ArrayList<Resource> resourcesRequired) {
        this.resourcesRequired = resourcesRequired;
    }

    public boolean isSleep() {
        return isSleep;
    }

    public void setSleep(boolean isSleep) {
        this.isSleep = isSleep;
    }

    public boolean isAlerted() {
        return isAlerted;
    }

    public void setAlerted(boolean isAlerted) {
        this.isAlerted = isAlerted;
    }

    public void specialAbility(){

    }
}
