package Model.Units.NonCombat;
import Controller.GameController.CityController;
import Controller.GameController.MapControllers.MapFunctions;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.TileRelated.Tile.Tile;
import Model.Units.TypeEnums.UnitType;

public class Settler extends NonCombat {

    
    public Settler(Civilization civilization, Tile tile) {
        super(civilization, tile,UnitType.Settler);
    }
    public void buildCity(String cityName){
        City city = new City(civilization);
        city.addCityTiles(this.tile);
        this.tile.setCivilization(civilization);
        for (Tile tile : MapFunctions.getInstance().getSurroundings(this.tile)) {
            city.addCityTiles(tile);
            tile.setCivilization(civilization);
        }
        this.civilization.addCity(city);
        if(this.civilization.getCities().size() == 1) { // make palace for first city
            city.setCapital(true);
        }
        civilization.getCities().add(city);
        city.setTile(tile);
        tile.setCity(city);
        this.tile.setCapital(true);
        city.setName(cityName);
        CityController.getInstance().calculateProducts(civilization);

    }
    public int calculateProductionAfterBuildingCity(){
        return 0;
    }
    public int calculateGoldAfterBuildingCity(){
        return 0;
    }
}
