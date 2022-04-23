package Model.TileRelated.Feature;
import java.util.ArrayList;

import Model.TileRelated.Tile.Tile;

public class River {
    private ArrayList<Tile> adjacentTiles;

    public ArrayList<Tile> getAdjacentTiles() {
        return adjacentTiles;
    }
    public River(ArrayList<Tile> adjacentTiles){
        this.adjacentTiles = adjacentTiles;
    }
    public Tile otherTile(Tile tile){
        for (int i = 0; i < adjacentTiles.size(); i++) {
            if(adjacentTiles.get(i).equals(tile) == false)
                return adjacentTiles.get(i);
        }
        return null;
    }
}
