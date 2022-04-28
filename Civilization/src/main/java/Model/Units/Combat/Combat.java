package Model.Units.Combat;

import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.TileRelated.Tile.Tile;
import Model.Units.Unit;
import Model.Units.TypeEnums.UnitType;

public class Combat extends Unit {

    

    public Combat(Civilization civilization, Tile tile, UnitType unitType) {
        super(civilization, tile, unitType);
        //TODO Auto-generated constructor stub
    }

    protected int Xp;
    protected int hitPoints = 10;
    private int attacksLeft;

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

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
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

    public void calculateFeatureCombatEffect(){

    }
}
