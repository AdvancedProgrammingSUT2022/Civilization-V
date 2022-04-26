package Model.Units;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.Movement.Node;
import Model.TileRelated.Tile.Tile;
import Model.Units.TypeEnums.UnitStateType;
import Model.Units.TypeEnums.UnitType;

import java.util.LinkedList;
import java.util.List;

import Controller.GameController.GameController;
import Controller.GameController.Movement;
import Controller.GameController.MapControllers.MapPrinter;
import Controller.GameController.MapControllers.TileVisibilityController;

public class Unit {
    protected Civilization civilization;
    protected City city;
    protected Tile tile;
    protected int movementsLeft;
    protected UnitType unitType;
    protected UnitStateType unitStateType;
    protected int maxDamage;
    protected List<Node> path = new LinkedList<>();

    public Unit(Civilization civilization, City city, Tile tile, UnitType unitType) {
        this.civilization = civilization;
        this.civilization.addUnit(this);
        this.city = city;
        this.tile = tile;
        this.setUnitType(unitType);
        this.setUnitStateType(UnitStateType.NORMAL);
        this.setMovementsLeft(unitType.movement);
        movementsLeft = this.unitType.movement;
    }

    public void moveUnit() {
        while (getMovementsLeft() > 0) {
            TileVisibilityController.getInstance().changeVision(getTile(), civilization.getSeenBy(), -1, 2);
            if (MapPrinter.getInstance().hasRiverBetween(getTile(), getNextMoveNode().getTile()))
                setMovementsLeft(0);
            else {
                
                addMovementsLeft(-(Movement.getInstance().calculateDistance(getTile(),getNextMoveNode().getTile())));
                if (getMovementsLeft() < 0)
                    setMovementsLeft(0);
            }
            getTile().getUnits().remove(this);
            setTile(getNextMoveNode().getTile());
            getTile().addUnit(this);
            getPath().remove(0);
            TileVisibilityController.getInstance().changeVision(getTile(), civilization.getSeenBy(), 1, 2);
            if(getPath().size() == 0)
                break;
        }
        if (getPath().size() > 0){
            if(GameController.getInstance().getMap().getMovingUnits().contains(this) != true)
                GameController.getInstance().getMap().getMovingUnits().add(this);
        }
        else
            if(GameController.getInstance().getMap().getMovingUnits().contains(this))
            GameController.getInstance().getMap().getMovingUnits().remove(this);
    }
    public int getMovementsLeft() {
        return movementsLeft;
    }
    public Civilization getCivilization(){
        return civilization;
    }
    
    public void setMovementsLeft(int movementsLeft) {
        this.movementsLeft = movementsLeft;
    }
    public void restoreMovementLeft(){
        this.movementsLeft = this.unitType.movement;
    }
    public void addMovementsLeft(int movementsLeft) {
        this.movementsLeft += movementsLeft;
    }
    public Node getNextMoveNode(){
        return path.get(0);
    }
    public UnitStateType getUnitStateType() {
        return unitStateType;
    }

    public void setUnitStateType(UnitStateType unitStateType) {
        this.unitStateType = unitStateType;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
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

    public void specialAbility(){

    }

    public List<Node> getPath(){
        return path;
    }

    public void setPath(List<Node> path){
        this.path = path; 
    }

    public void addNodeToPath(Node node){
        path.add(node);
    }
}
