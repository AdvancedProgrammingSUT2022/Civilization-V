package Controller.GameController.MapControllers;
import java.util.ArrayList;
import java.util.HashMap;
import Model.MapRelated.GameMap;
import Model.TileRelated.Terraine.TerrainType;
import Model.TileRelated.Tile.Tile;

public class TileVisibilityController {
    private static TileVisibilityController tileVisibilityController;
    private TileVisibilityController(){}
    public static TileVisibilityController getInstance(){
        if(tileVisibilityController == null)
            tileVisibilityController = new TileVisibilityController();
        return tileVisibilityController;
    }
    public void changeVision(GameMap gameMap , Tile tile , HashMap<Tile , Integer> seenBy , int changeAmount , int visionRadius){
        HashMap <Tile,Integer> visibility = new HashMap<>();
        visibility = findVisibles(gameMap , tile , 0 ,visibility);
        for (Tile key:visibility.keySet()) {
            if(visibility.get(key) <= visionRadius){
                if(seenBy.get(key) == -1 && changeAmount == 1)seenBy.replace(key,0);
                seenBy.replace(key,seenBy.get(key) + changeAmount);
            }
        }
    }
    public HashMap<Tile , Integer> findVisibles(GameMap gameMap ,Tile tile , int cycleCount , HashMap<Tile , Integer> visibles){
        if(cycleCount == 0)visibles.put(tile , 0);
        int seePoint = 1;
        if(cycleCount!= 0 && (tile.getTerrain().equals(TerrainType.Hill) || tile.getTerrain().equals(TerrainType.Mountain) /*|| tile.getFeature().equals(FeatureType.Jungle)*/))seePoint = 2;
        ArrayList<Tile> surroundings = MapFunctions.getInstance().getSurroundings(gameMap ,tile);
        for (Tile nextTile: surroundings) {
            if(nextTile == null)continue;
            int nextSeePoint = seePoint + visibles.get(tile);
            if(visibles.get(nextTile) != null ){
                if(visibles.get(nextTile) > nextSeePoint) visibles.replace(nextTile,nextSeePoint);
            }
            else visibles.put(nextTile,nextSeePoint);
        }
        cycleCount++;
        if(cycleCount==2)return visibles;
        for (Tile nextTile:surroundings) {
            if(nextTile == null)continue;
            findVisibles(gameMap ,nextTile , cycleCount , visibles);
        }
        return visibles;
    }
    public void seenByInit(GameMap gameMap ,HashMap<Tile,Integer> seenBy){
        for (Tile key: gameMap.getTiles()) {
            seenBy.put(key,-1);
        }
    }
}
