package Model.Units;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.MapRelated.GameMap;
import Model.Movement.Node;
import Model.Technology.TechnologyType;
import Model.TileRelated.Tile.Tile;
import Model.Units.TypeEnums.UnitStateType;
import Model.Units.TypeEnums.UnitType;

import java.util.LinkedList;
import java.util.List;

import Controller.GameController.GameController;
import Controller.GameController.Movement;
import Controller.GameController.MapControllers.MapPrinter;
import Controller.GameController.MapControllers.TileVisibilityController;
import com.google.gson.annotations.Expose;
import javafx.scene.control.Label;
import javafx.stage.Popup;

public class Unit {
    protected Civilization civilization;
    @Expose
    protected Tile tile;
    @Expose
    public double movementsLeft;
    @Expose
    protected UnitType unitType;
    @Expose
    protected UnitStateType unitStateType;
    @Expose
    protected double maxDamage;
    protected List<Node> path = new LinkedList<>();

    public Unit(Civilization civilization, Tile tile, UnitType unitType) {
        this.civilization = civilization;
        this.civilization.addUnit(this);
        this.tile = tile;
        this.setUnitType(unitType);
        this.setUnitStateType(UnitStateType.NORMAL);
        this.setMovementsLeft(unitType.movement);
        movementsLeft = this.unitType.movement;
    }

    public Unit(){

    }

    public void moveUnit() {
        System.out.println(movementsLeft);
        while (getMovementsLeft() > 0) {
            TileVisibilityController.getInstance().changeVision(getTile(), civilization.getSeenBy(), -1, 2);
            if (MapPrinter.getInstance().hasRiverBetween(getTile(), getNextMoveNode().getTile()))
                if(getNextMoveNode().getTile().getRoad() != null && !getNextMoveNode().getTile().getRoad().isRuined() && getNextMoveNode().getTile().getRoad().getDaysToComplete() == 0
                        && civilization.hasTechnology(TechnologyType.Construction))addMovementsLeft(-0.5);
                else setMovementsLeft(0);
            else {
                addMovementsLeft(-(Movement.getInstance().calculateDistance(getTile(),getNextMoveNode().getTile(),unitType)));
                if (getMovementsLeft() < 0)
                    setMovementsLeft(0);
            }
            getTile().getUnits().remove(this);
            setTile(getNextMoveNode().getTile());
            getTile().addUnit(this);
            getPath().remove(0);
            TileVisibilityController.getInstance().changeVision(getTile(), civilization.getSeenBy(), 1, 2);
            if(tile.getRuin() != null){
//                Popup popup = new Popup();
//                Label label = new Label(tile.getRuin().getBenefit(civilization));
//                label.setStyle("-fx-font-family: Britannic Bold; -fx-font-size: 25; -fx-text-fill: #9c0dbb");
//                popup.setX(440);
//                popup.setY(90);
//                popup.getContent().add(label);
//                popup.setAutoHide(true);
//                popup.show(Main.scene.getWindow());
//                tile.setRuin(null);
            }
            if(getPath().size() == 0)
                break;
        }
        if (getPath().size() > 0){
            if(!GameController.getInstance().getMap().getMovingUnits().contains(this))
                GameController.getInstance().getMap().getMovingUnits().add(this);
        }
        else
            if(GameController.getInstance().getMap().getMovingUnits().contains(this))
                GameController.getInstance().getMap().getMovingUnits().remove(this);
    }

    public double getMovementsLeft() {
        return movementsLeft;
    }
    public Civilization getCivilization(){
        return civilization;
    }
    public void setCivilization(Civilization civilization){
        this.civilization = civilization;
    }
    public void sleep(){
        unitStateType = UnitStateType.SLEEP;
    }
    public void fortify(){
        unitStateType = UnitStateType.FORTIFIED;
    }
    public void alert(){
        unitStateType = UnitStateType.ALERT;
    }
    public void fortifyUntilHealed(){
        unitStateType = UnitStateType.FORTIFYUNTILHEALED;
    }
    public void setMovementsLeft(int movementsLeft) {
        this.movementsLeft = movementsLeft;
        //System.out.println("2 - this.movementsLeft: " +this.movementsLeft + "movementsLeft:" + movementsLeft);
    }
    public void restoreMovementLeft(){
        this.movementsLeft = this.unitType.movement;
    }
    public void addMovementsLeft(double movementsLeft) {
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
