package View.GraphicViewController;

import Controller.SavingDataController.DataSaver;
import Model.Enums.Menus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class LoadMenuController implements Initializable {
    @FXML
    private VBox pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane.setAlignment(Pos.TOP_CENTER);
        URL resource = this.getClass().getResource("/GameSaves");
        try {

            File directory = Paths.get(resource.toURI()).toFile();
            for (String string:directory.list()) {
                if(string.contains("SaveNumber")){
                    Label label = new Label(string.replace(".json",""));
                    label.setOnMouseClicked(mouseEvent -> {
                        try {
                            DataSaver.getInstance().loadGame(string);
                            main.java.Main.changeMenu(Menus.GAME_MENU.value);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    label.setStyle("-fx-font-size: 20");
                    pane.getChildren().add(label);

                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void back(ActionEvent actionEvent) {
        main.java.Main.changeMenu(Menus.MAIN_MENU.value);
    }
}
