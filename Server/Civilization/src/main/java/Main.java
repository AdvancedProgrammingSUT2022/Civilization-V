package main.java;

import Controller.SavingDataController.DataSaver;
import Model.Enums.Menus;
import Model.NetworkRelated.NetworkController;
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
        NetworkController.getInstance().initializeServer(8000);
        NetworkController.getInstance().listenForClients();
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
