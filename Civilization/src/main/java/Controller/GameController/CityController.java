package Controller.GameController;

import Controller.GameController.MapControllers.MapFunctions;
import Controller.GameController.MapControllers.MapGenerator;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.Enums.MapEnum;
import Model.MapRelated.GameMap;
import Model.TileRelated.Tile.Tile;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;

public class CityController {
    private static CityController cityController;
    private CityController(){};

    public static CityController getInstance() {
        if (cityController == null)
            cityController = new CityController();
        return cityController;
    }

    public String selectCity(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if(x > MapEnum.MAPWIDTH.amount -1 || y > MapEnum.MAPHEIGHT.amount -1) return "invalid coordinates";
        Civilization civilization;
        if(( civilization = MapFunctions.getInstance().getTile(x, y).getCivilization()) == null) return "this tile does not belong to anyone";
        if(civilization != GameController.getInstance().getPlayerTurn()) return "this tile does not belong to your civilization";
        ArrayList<City> playerCities = GameController.getInstance().getPlayerTurn().getCities();
        if(playerCities == null) return "no cities on your civilization";
        for(City city : playerCities){
            if(Objects.equals(city.getCityTiles().get(0), MapFunctions.getInstance().getTile(x, y))){
                GameController.getInstance().setSelectedCity(city);
                return "city selected";
            }
        }
        return "city not found";
    }

    public String buyTile(Matcher matcher){
        if(GameController.getInstance().getSelectedCity() == null)return "no city is selected";
        Tile tile = MapFunctions.getInstance().getTile(Integer.parseInt(matcher.group("x")),Integer.parseInt(matcher.group("y")));
        if(tile == null)return "no such tile exists";
        return GameController.getInstance().getSelectedCity().buyTile(tile);
    }

    public void calculateProducts(){
        for (Civilization civilization: GameMap.getInstance().getCivilizations()) {
            for (City city:civilization.getCities()) {
                city.calculateProduction();
                city.calculateGold();
                city.calculateFood();
                civilization.changeGold(city.getGoldPerTurn());
                city.populationGrowth();
            }
        }
    }
}
