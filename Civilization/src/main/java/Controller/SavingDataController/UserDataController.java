package Controller.SavingDataController;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import Controller.GameController.GameController;
import Controller.PreGameController.LoginMenuController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Model.CivlizationRelated.Civilization;
import Model.MapRelated.GameMap;
import Model.User.User;
import com.google.gson.reflect.TypeToken;

public class UserDataController {
    private static UserDataController userDataController;
    private UserDataController(){}
    public static UserDataController getInstance(){
        if(userDataController == null)
            userDataController = new UserDataController();
        return userDataController;
    }

    public static void loadUsers() {
        try {
            String json = new String(Files.readAllBytes(Paths.get("./src/main/resources/UserDatabase.json")));
            ArrayList<User> createdUsers;
            createdUsers = new Gson().fromJson(json, new TypeToken<List<User>>() {
            }.getType());
            if (createdUsers != null) {
                LoginMenuController.setUsers(createdUsers);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveUsers() {
        try {
            FileWriter fileWriter = new FileWriter("./src/main/resources/UserDatabase.json");
            fileWriter.write(new Gson().toJson(LoginMenuController.getUsers()));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    public void saveGame(){
//        ArrayList<User> users = new ArrayList<User>();
//        for(Civilization civilization : GameMap.getInstance().getCivilizations()){
//            users.add(civilization.getUser());
//        }
//        String fileName = "./src/main/resources/UserDatabase.json";
//        Path path = Paths.get(fileName);
//        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)){
//            Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            gson.toJson(users, writer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
