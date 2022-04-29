package Model.Units.NonCombat;


import Controller.GameController.MapControllers.MapFunctions;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.MapRelated.GameMap;
import Model.TileRelated.Tile.Tile;
import Model.Units.TypeEnums.UnitType;
import Model.Units.Unit;

public class Settler extends NonCombat {

    
    public Settler(Civilization civilization, Tile tile) {
        super(civilization, tile,UnitType.Settler);
    }
    public City buildCity(){
        City city = new City();
        city.setCivilization(this.civilization);
        city.addCityTiles(this.tile);
        for (Tile tile : MapFunctions.getInstance().getSurroundings(this.tile)) {
            city.addCityTiles(tile);
        }
        this.civilization.addCity(city);
        return city;
    }
    public int calculateProductionAfterBuildingCity(){
        return 0;
    }
    public int calculateGoldAfterBuildingCity(){
        return 0;
    }
}
