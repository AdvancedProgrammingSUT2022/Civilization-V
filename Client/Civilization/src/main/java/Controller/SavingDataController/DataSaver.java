package Controller.SavingDataController;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import Controller.GameController.MapControllers.TileVisibilityController;
import Controller.NetworkController;
import Controller.PreGameController.LoginAndRegisterController;
import Model.CivlizationRelated.*;
import Model.Enums.AutoSave;
import Model.MapRelated.GameMap;
import Model.NetworkRelated.Request;
import Model.NetworkRelated.RequestType;
import Model.NetworkRelated.Response;
import Model.TileRelated.Tile.Tile;
import Model.Units.Combat.Combat;
import Model.Units.Combat.Ranged;
import Model.Units.Combat.Siege;
import Model.Units.NonCombat.Settler;
import Model.Units.NonCombat.Worker;
import Model.Units.TypeEnums.MainType;
import Model.Units.TypeEnums.UnitType;
import Model.Units.Unit;
import View.GraphicViewController.GameplayGraphicController;
import View.GraphicViewController.LoginPageController;
import View.Images;
import com.google.gson.Gson;
import Model.User.User;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.scene.shape.Polygon;

public class DataSaver {
    private static DataSaver dataSaver;
    private DataSaver(){}
    public static DataSaver getInstance(){
        if(dataSaver == null)
            dataSaver = new DataSaver();
        return dataSaver;
    }

    public AutoSave getAutoSave() {
        return autoSave;
    }

    public void setAutoSave(AutoSave autoSave) {
        this.autoSave = autoSave;
    }

    private AutoSave autoSave;
    public void loadUsers() {
        try {
            String json = new String(Files.readAllBytes(Paths.get("./src/main/resources/UserDatabase.json")));
            setUsersFromJsonString(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setUsersFromJsonString(String json){
        ArrayList<User> createdUsers;
        createdUsers = new Gson().fromJson(json, new TypeToken<List<User>>() {
        }.getType());
        if (createdUsers != null) {
            LoginAndRegisterController.getInstance().setUsers(createdUsers);
        }
    }

    public void updateUsers(){
        ArrayList<String> param = new ArrayList<>();
        Response response = NetworkController.getInstance().send(new Request(RequestType.Users,param));
        DataSaver.getInstance().setUsersFromJsonString(response.getMessage());
        if(LoginAndRegisterController.getInstance().getLoggedInUser() != null) {
            User loggedInUser = LoginAndRegisterController.getInstance().getUser(LoginAndRegisterController.getInstance().getLoggedInUser());
            LoginAndRegisterController.getInstance().setLoggedInUser(loggedInUser);
        }
    }

    public void saveUsers() {
        try {
            FileWriter fileWriter = new FileWriter("./src/main/resources/UserDatabase.json");
            fileWriter.write(new Gson().toJson(LoginAndRegisterController.getInstance().getUsers()));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveGame() throws FileNotFoundException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();
        GameMap.getInstance().saveHashmap();
        String json = gson.toJson(GameMap.getInstance());
        saveToFile(json);
    }


    public GameMap loadGame(String json) throws IOException {
        GameMap gameMap = new Gson().fromJson(json, GameMap.class);
        completeFatherChildFields(gameMap);
        gameMap.loadHashMap();
        return gameMap;
    }

    public String makeJson(GameMap gameMap){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();
        gameMap.saveHashmap();
        return gson.toJson(gameMap);
    }

    private void completeFatherChildFields(GameMap gameMap) {
        for (Civilization civilization:gameMap.getCivilizations()) {
            for(User user: LoginAndRegisterController.getInstance().getUsers()){
                if(user.getNickname().equals(civilization.getUser().getUsername())) {
                    civilization.setUser(user);
                    break;
                }
            }
            for (City city:civilization.getCities()) {
                city.setCivilization(civilization);
                city.getTile().setCivilization(civilization);
                city.getTile().setCity(city);
                ArrayList<Tile> cityTiles = new ArrayList<>(city.getCityTiles());
                for (int i = 0; i < cityTiles.size(); i++) {
                    cityTiles.get(i).setCivilization(civilization);
                    cityTiles.get(i).setCity(city);
                    city.getCityTiles().set(i,equalizeTiles(gameMap,cityTiles.get(i)));
                }
                for (Citizen citizen:city.getCitizens()) {
                    citizen.setCity(city);
                    if(citizen.getTile() != null) {
                        citizen.getTile().setCitizen(citizen);
                        citizen.setTile(equalizeTiles(gameMap, citizen.getTile()));
                    }
                }
                city.setTile(equalizeTiles(gameMap,city.getTile()));
            }
            ArrayList<Unit> copyUnits = new ArrayList<>(civilization.getUnits());
            for (Unit unit:copyUnits) {
                Unit newUnit = makeUnit(unit);
                civilization.getUnits().set(civilization.getUnits().indexOf(unit),newUnit);
                newUnit.setCivilization(civilization);
                newUnit.getTile().getUnits().add(newUnit);
                newUnit.setTile(equalizeTiles(gameMap,newUnit.getTile()));
                gameMap.getUnits().add(newUnit);
            }
            if(gameMap.getPlayerTurn().getUser().getUsername().equals(civilization.getUser().getUsername()))
                gameMap.setPlayerTurn(civilization);
        }
    }


    public Unit makeUnit(Unit original){
        Unit unit;
        UnitType unitType = original.getUnitType();
        if(unitType == UnitType.Settler)
            unit = new Settler();
        else if(unitType == UnitType.Worker)
            unit = new Worker();
        else if(unitType.mainType == MainType.NONCOMBAT)
            unit = new Unit();
        else if(unitType.mainType == MainType.NONRANGED)
            unit = new Combat();
        else if(unitType.mainType == MainType.RANGED)
            unit = new Ranged();
        else
            unit = new Siege();
        unit.setTile(original.getTile());
        unit.setUnitStateType(original.getUnitStateType());
        unit.setUnitType(original.getUnitType());
        return unit;
    }

    public Tile equalizeTiles(GameMap gameMap, Tile originalTile){
        for (int i = 0; i < gameMap.getTiles().size(); i++) {
            if (gameMap.getTiles().get(i).getX() == originalTile.getX() && gameMap.getTiles().get(i).getY() == originalTile.getY()) {
                return equalizeTile(gameMap.getTiles().get(i),originalTile);
            }
        }
        return null;
    }

    public Tile equalizeTile(Tile fakeTile,Tile originalTile){
        if(originalTile != null) {
            fakeTile.setCity(originalTile.getCity());
            fakeTile.setCivilization(originalTile.getCivilization());
            fakeTile.setCitizen(originalTile.getCitizen());
            fakeTile.setUnits(originalTile.getUnits());
            return fakeTile;
        }
        return null;
    }

    private String loadFromFile(String fileName) throws IOException {
            File file = new File("./src/main/resources/GameSaves/" + fileName);
            FileInputStream inputStream = new FileInputStream(file);
            String text = new String(inputStream.readAllBytes());
            inputStream.close();
            return text;
    }
    private void saveToFile(String text) throws FileNotFoundException {
        int m = 1;
        URL resource = DataSaver.class.getResource("/GameSaves");
        File directory = null;
        try {
            directory = Paths.get(resource.toURI()).toFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        for (String string:directory.list()) {
            if (string.contains("SaveNumber"))
                m++;
            }
        File file = new File("./src/main/resources/GameSaves/SaveNumber" + m + ".json");
        PrintWriter printWriter = new PrintWriter(file);
        printWriter.write(text);
        printWriter.close();
    }

}
