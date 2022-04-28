package Model.CivlizationRelated;
import Model.TileRelated.Tile.Tile;

public class Citizen {
    private City city;
    private Tile tile;
    private boolean hasWork;
    public Citizen(City city) {
        this.city = city;
        this.tile = new Tile();
        this.hasWork = false;
    }

    public boolean isHasWork() {
        return hasWork;
    }

    public void setHasWork(boolean hasWork) {
        this.hasWork = hasWork;
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
