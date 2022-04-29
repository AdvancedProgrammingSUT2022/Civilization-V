package Model.CivlizationRelated;
import Model.TileRelated.Tile.Tile;

public class Citizen {
    private City city;
    private Tile tile;

    public Citizen(City city){
        this.city = city;
    }
    public City getCity() {
        return city;
    }
    public void setCity(City city) {
        this.city = city;
    }
    public Tile getTile() {
        return tile;
    }
    public void setTile(Tile tile) {
        this.tile = tile;
    }

}
