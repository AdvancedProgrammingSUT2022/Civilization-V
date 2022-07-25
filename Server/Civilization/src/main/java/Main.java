

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

public class Main {
    public static void main(String[] args){
        try {
            DataSaver.getInstance().loadUsers();
            NetworkController.getInstance().initializeServer(8000);
            System.out.println("listening on port 8000");
            NetworkController.getInstance().listenForClients();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
