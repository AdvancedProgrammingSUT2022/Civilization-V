package Controller.GameController;

import java.util.ArrayList;
import java.util.regex.Matcher;

import Controller.GameController.MapControllers.MapFunctions;
import Model.Enums.MapEnum;
import Model.MapRelated.GameMap;
import Model.Movement.Graph;
import Model.TileRelated.Feature.FeatureType;
import Model.TileRelated.Terraine.TerrainType;
import Model.TileRelated.Tile.Tile;
import Model.Units.Unit;
import Model.Units.TypeEnums.MainType;

public class UnitController {
    private static UnitController unitController;
    private UnitController(){};
    public static UnitController getInstance(){
        if(unitController == null)
            unitController = new UnitController();
        return unitController;
    }

    public String selectUnit(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        boolean isCombatUnit = true;
        if(matcher.group("type").equals("civil"))isCombatUnit = false;
        if(x > MapEnum.MAPWIDTH.amount -1 || y > MapEnum.MAPHEIGHT.amount -1)return "invalid coordinates";
        ArrayList<Unit> tileUnits;
        if((tileUnits = MapFunctions.getInstance().getTile(x , y).getUnits()) == null)return "no units on this tile";
        for (Unit unit:tileUnits) {
            if((unit.getUnitType().mainType == MainType.NONCOMBAT && !isCombatUnit) || (!(unit.getUnitType().mainType == MainType.NONCOMBAT) && isCombatUnit)){
                if(unit.getCivilization() != GameController.getInstance().getPlayerTurn())return "selected unit does not belong to your civilization!";
                if(unit.getMovementsLeft() == 0)return "no movement left";
                GameController.getInstance().setSelectedUnit(unit);
                return "unit selected";
            }
        }
        return "unit not found";
    }

    public void restoreUnitMovementLeft(){
        for (Unit unit : GameController.getInstance().getPlayerTurn().getUnits()) {
            if(GameController.getInstance().getPlayerTurn() == unit.getCivilization()){
                unit.restoreMovementLeft();
                if(GameMap.getInstance().getMovingUnits().contains(unit))
                    unit.moveUnit();
            }
        }
    }

    private void assignPathToUnit(Matcher matcher){
        int destinationX = Integer.parseInt(matcher.group("destinationX")),destinationY = Integer.parseInt(matcher.group("destinationY"));
        Tile origin = GameController.getInstance().getSelectedUnit().getTile();
        Tile destination = MapFunctions.getInstance().getTile(destinationX , destinationY);
        Graph graph = new Graph(GameMap.getInstance().getInitialGraph());
        graph = Movement.getInstance().calculateShortestPathFromSource(graph,graph.getNode(origin));
        GameController.getInstance().getSelectedUnit().setPath(graph.getNode(destination).getShortestPath());
        GameController.getInstance().getSelectedUnit().getPath().remove(0);
        GameController.getInstance().getSelectedUnit().addNodeToPath(graph.getNode(destination));
    }

    private String checkInitMoveUnitErrors(int destinationX , int destinationY,Tile destination){
        if(GameController.getInstance().getSelectedUnit() == null)return "no selected unit";
        else if(destinationX > MapEnum.MAPWIDTH.amount-1 || destinationY > MapEnum.MAPWIDTH.amount -1)return "invalid coordinates";
        else if(destination.getTerrain().equals(TerrainType.Ocean) ||
        destination.getTerrain().equals(TerrainType.Mountain) ||
             (destination.getFeature() != null && destination.getFeature().getFeatureType().equals(FeatureType.Ice))){
         return "destination is invalid.";   
        }
        return null;
    }

    public String initMoveUnit(Matcher matcher){
        int destinationX = getXFromMatcher(matcher),destinationY = getYFromMatcher(matcher);
        String result;
        if((result = checkInitMoveUnitErrors(destinationX, destinationY, MapFunctions.getInstance().getTile(destinationX , destinationY))) != null)
            return result;
        assignPathToUnit(matcher);
        GameController.getInstance().getSelectedUnit().moveUnit();
        GameController.getInstance().setSelectedUnit(null);
        return "moving...";
    }
    private int getXFromMatcher(Matcher matcher){
        return Integer.parseInt(matcher.group("destinationX"));
    }
    private int getYFromMatcher(Matcher matcher){
        return Integer.parseInt(matcher.group("destinationY"));
    }
}
