

import Controller.SavingDataController.DataSaver;
import Model.Enums.Menus;
import Model.NetworkRelated.NetworkController;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class Main {
    public static void main(String[] args){
        try {
            DataSaver.getInstance().loadUsersForTheFirstTime();
            NetworkController.getInstance().initializeServer(8000);
            System.out.println("listening on port 8000");
            NetworkController.getInstance().listenForClients();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
