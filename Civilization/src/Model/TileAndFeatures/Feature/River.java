package Model.TileAndFeatures.Feature;
import java.util.ArrayList;

import Model.TileAndFeatures.Tile.Tile;

public class River {
    private ArrayList<Tile> adjacentTiles;

    public ArrayList<Tile> getAdjacentTiles() {
        return adjacentTiles;
    }
    public River(ArrayList<Tile> adjacentTiles){
        this.adjacentTiles = adjacentTiles;
    }
}
