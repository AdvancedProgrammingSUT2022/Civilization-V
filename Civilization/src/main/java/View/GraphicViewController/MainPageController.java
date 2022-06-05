package View.GraphicViewController;

import Controller.PreGameController.MainMenuController;
import Model.Enums.Menus;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {
    @FXML private Label menuName;
    @FXML private Label playerNum;
    @FXML private ChoiceBox<String> autoSave;
    private Pane description;
    @FXML Label playerDes;
    @FXML Label inviteDes;
    @FXML Label MapDes;
    @FXML Label autoSaveDes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuName.setText(MainMenuController.getInstance().showCurrentMenu());
        autoSave.getItems().add("after claiming a city");
        autoSave.getItems().add("every 5 minutes");
        autoSave.getItems().add("after winning a game");
        playerDes.setTooltip(new Tooltip("number of players attending in the game including you"));
        inviteDes.setTooltip(new Tooltip("invite your friends to play with you"));
        MapDes.setTooltip(new Tooltip("customize your own map!"));
        autoSave.setTooltip(new Tooltip("choose on which period you want to save your game"));
    }


    public void buttonSizeIncrease(MouseEvent mouseEvent) {
        Button button =(Button) mouseEvent.getSource();
        button.setStyle("-fx-font-size: 25;-fx-background-color: rgba(231,231,121,0.83);");
    }

    public void buttonSizeDecrease(MouseEvent mouseEvent) {
        Button button =(Button) mouseEvent.getSource();
        button.setStyle("-fx-font-size: 18; -fx-background-color: rgba(201, 238, 221, 0.7);");
    }

    public void logout(MouseEvent mouseEvent) {
        MainMenuController.getInstance().userLogout();
        main.java.Main.changeMenu(Menus.LOGIN_MENU.value);
    }

    public void scoreBoard(MouseEvent mouseEvent) {
        main.java.Main.changeMenu(Menus.SCORE_BOARD.value);
    }

    public void decreasePlayer(MouseEvent mouseEvent) {
        if(Integer.parseInt(playerNum.getText()) > 2)
        playerNum.setText(Integer.toString(Integer.parseInt(playerNum.getText()) - 1));
    }

    public void increasePlayer(MouseEvent mouseEvent) {
        if(Integer.parseInt(playerNum.getText()) < 10)
            playerNum.setText(Integer.toString(Integer.parseInt(playerNum.getText()) + 1));
    }

    public void profile(MouseEvent mouseEvent) {
        main.java.Main.changeMenu(Menus.PROFILE_MENU.value);
    }
}
