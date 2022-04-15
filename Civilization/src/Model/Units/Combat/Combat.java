package Model.Units.Combat;

import java.util.ArrayList;

import Model.City;
import Model.Civilization;
import Model.Technology.Technology;
import Model.TileAndFeatures.Resource.Resource;
import Model.TileAndFeatures.Tile.Tile;
import Model.Units.Unit;

public class Combat extends Unit {

    public Combat(Civilization civilization, City city, Tile tile, int movement, int cost,
            Technology technologyRequired, ArrayList<Resource> resourcesRequired,int maxCombatStrength) {
        super(civilization, city, tile, movement, cost, technologyRequired, resourcesRequired);
        this.MaxCombatStrength = maxCombatStrength;
        //TODO Auto-generated constructor stub
    }

    protected int Xp;
    protected int hitPoints = 10;
    protected int MaxCombatStrength;
    protected boolean isFortified;
    protected boolean isHealing;
    private int attacksLeft;
    private int movementsLeft;

   

    public int getAttacksLeft() {
        return attacksLeft;
    }

    public void setAttacksLeft(int attacksLeft) {
        this.attacksLeft = attacksLeft;
    }

    public int getMovementsLeft() {
        return movementsLeft;
    }

    public void setMovementsLeft(int movementsLeft) {
        this.movementsLeft = movementsLeft;
    }

    public boolean isFortified() {
        return isFortified;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getMaxCombatStrength() {
        return MaxCombatStrength;
    }

    public void setMaxCombatStrength(int maxCombatStrength) {
        this.MaxCombatStrength = maxCombatStrength;
    }

    public boolean isHealing() {
        return isHealing;
    }

    public void setHealing(boolean isHealing) {
        this.isHealing = isHealing;
    }

    public void setFortified(boolean isFortified) {
        this.isFortified = isFortified;
    }

    public int getXp() {
        return Xp;
    }

    public void addXp(int xp){
        this.Xp += xp;
    }

    public void attack(Unit enemy){

    }

    public void defend(Unit enemy){

    }

    public int calculateDamage(){
        return 0;
    }
}
