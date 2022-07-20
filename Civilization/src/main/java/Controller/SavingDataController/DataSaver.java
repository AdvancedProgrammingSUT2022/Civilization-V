package Controller.SavingDataController;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import Controller.PreGameController.LoginAndRegisterController;
import Model.CivlizationRelated.*;
import Model.MapRelated.GameMap;
import Model.TileRelated.Tile.Tile;
import Model.Units.Unit;
import com.google.gson.Gson;
import Model.User.User;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class DataSaver {
    private static DataSaver dataSaver;
    private DataSaver(){}
    public static DataSaver getInstance(){
        if(dataSaver == null)
            dataSaver = new DataSaver();
        return dataSaver;
    }

    public void loadUsers() {
        try {
            String json = new String(Files.readAllBytes(Paths.get("./src/main/resources/UserDatabase.json")));
            ArrayList<User> createdUsers;
            createdUsers = new Gson().fromJson(json, new TypeToken<List<User>>() {
            }.getType());
            if (createdUsers != null) {
                LoginAndRegisterController.getInstance().setUsers(createdUsers);
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    public void loadGame() throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();
        String json = loadFromFile();
        GameMap gameMap = gson.fromJson(json, GameMap.class);
        completeFatherChildFields(gameMap);
        gameMap.loadHashMap();
        GameMap.setInstance(gameMap);
        GameMap copy = GameMap.getInstance();
    }

    public void replaceTilesInHashMap(){

    }

    private void completeFatherChildFields(GameMap gameMap) {
        for (Civilization civilization:gameMap.getCivilizations()) {
            for (City city:civilization.getCities()) {
                city.setCivilization(civilization);
                city.getTile().setCivilization(civilization);
                city.getTile().setCity(city);
                for (Citizen citizen:city.getCitizens()) {
                    citizen.setCity(city);
                    if(citizen.getTile() != null) {
                        citizen.getTile().setCitizen(citizen);
                        equalizeTiles(gameMap, citizen.getTile());
                    }
                }
                equalizeTiles(gameMap,city.getTile());
            }
            for (Unit unit:civilization.getUnits()) {
                unit.setCivilization(civilization);
                unit.getTile().getUnits().add(unit);
                gameMap.getUnits().add(unit);
                equalizeTiles(gameMap,unit.getTile());
            }
            if(gameMap.getPlayerTurn().getUser().getUsername().equals(civilization.getUser().getUsername()))
                gameMap.setPlayerTurn(civilization);
        }
    }

    public void equalizeTiles(GameMap gameMap, Tile originalTile){
        for (int i = 0; i < gameMap.getTiles().size(); i++) {
            if (gameMap.getTiles().get(i).getX() == originalTile.getX() && gameMap.getTiles().get(i).getY() == originalTile.getY()) {
                equalizeTile(gameMap.getTiles().get(i),originalTile);
            }
        }
    }

    public void equalizeTile(Tile fakeTile,Tile originalTile){
        if(originalTile != null) {
            fakeTile.setCity(originalTile.getCity());
            fakeTile.setCivilization(originalTile.getCivilization());
            fakeTile.setCitizen(originalTile.getCitizen());
            fakeTile.setUnits(originalTile.getUnits());
            originalTile = fakeTile;
        }
    }

    private static String loadFromFile() throws IOException {
            File file = new File("./src/main/resources/GameDatabaseGameMap.json");
            FileInputStream inputStream = new FileInputStream(file);
            String text = new String(inputStream.readAllBytes());
            inputStream.close();
            return text;
    }
    private static void saveToFile(String text) throws FileNotFoundException {
        File file = new File("./src/main/resources/GameDatabaseGameMap.json");
        PrintWriter printWriter = new PrintWriter(file);
        printWriter.write(text);
        printWriter.close();
    }

}
