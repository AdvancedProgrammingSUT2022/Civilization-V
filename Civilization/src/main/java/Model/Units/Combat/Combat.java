package Model.Units.Combat;
import Controller.GameController.UnitController;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.TileRelated.Tile.Tile;
import Model.Units.Unit;
import Model.Units.TypeEnums.UnitType;

public class Combat extends Unit {

    protected int Xp;
    protected double hitPoints = 10;
    private int fortifiedTurnCount = 0;
    private boolean hasAttacked;

    public Combat(Civilization civilization, Tile tile, UnitType unitType) {
        super(civilization, tile, unitType);
        //TODO Auto-generated constructor stub
    }
    public boolean isHasAttacked() {
        return hasAttacked;
    }
    public void setHasAttacked(boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }
    public Combat(){
        super();
    }

    public int getFortifiedTurnCount() {
        return fortifiedTurnCount;
    }

    public void setFortifiedTurnCount(int fortifiedTurnCount) {
        this.fortifiedTurnCount = fortifiedTurnCount;
    }

    public int getMovementsLeft() {
        return movementsLeft;
    }

    public void setMovementsLeft(int movementsLeft) {
        this.movementsLeft = movementsLeft;
    }

    public double getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(double hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getXp() {
        return Xp;
    }

    public void addXp(int xp){
        this.Xp += xp;
    }
    
    public double attackDamage(Combat enemy){
        return UnitController.getInstance().calculateDamageDeltToDefendingUnit(this,enemy);
    }
    public double defendDamage(Combat enemy){
        return UnitController.getInstance().calculateDamageDealtToAttacker(enemy, this); 
    }
    public void changeHitPoint(double changeAmount){
        this.hitPoints += changeAmount;
    }

    public double getMaxDamage(){
        this.maxDamage = (this.unitType.combatStrength);
        return this.maxDamage;
    }

    public void captureCivilian(Unit nonCombatUnit) {
        this.civilization.getUnits().add(nonCombatUnit);
        nonCombatUnit.setCivilization(this.civilization);
        nonCombatUnit.getCivilization().getUnits().remove(nonCombatUnit);
        this.tile.getUnits().remove(this);
        this.tile = nonCombatUnit.getTile();
        this.tile.getUnits().add(this);
    }
}
