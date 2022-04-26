package Controller.SavingDataController;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Model.CivlizationRelated.Civilization;
import Model.MapRelated.GameMap;
import Model.User.User;

public class UserDataController {
    private static UserDataController userDataController;
    private UserDataController(){}
    public static UserDataController getInstance(){
        if(userDataController == null)
            userDataController = new UserDataController();
        return userDataController;
    }
    public void saveUsersInformation(){
        ArrayList<User> users = new ArrayList<User>();
        for(Civilization civilization : GameMap.getInstance().getCivilizations()){
            users.add(civilization.getUser());
        }
        String fileName = "./src/main/resources/UserDatabase.json";
        Path path = Paths.get(fileName);
        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
