package main.java;

import Controller.SavingDataController.DataSaver;
import Model.Enums.Menus;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class Main extends Application {
    public static Scene scene;
    private static Stage stage;
    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        DataSaver.getInstance().loadUsers();
        Parent root = loadFXML(Menus.Trailer.value);
        assert root != null;
        Scene scene = new Scene(root);
        Main.stage = stage;
        Main.scene = scene;
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void changeMenu(String name){
        Parent root = loadFXML(name);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private static Parent loadFXML(String name){
        try {
            URL address = new URL(Objects.requireNonNull(Main.class.getResource("/fxml/" + name + ".fxml")).toString());
            return FXMLLoader.load(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
