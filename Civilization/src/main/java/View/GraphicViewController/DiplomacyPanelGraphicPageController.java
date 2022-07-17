package View.GraphicViewController;

import Controller.GameController.GameController;
import Controller.GameController.MapControllers.MapFunctions;
import Model.CivlizationRelated.Civilization;
import Model.Enums.Menus;
import Model.MapRelated.GameMap;
import View.GameView.Game;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class DiplomacyPanelGraphicPageController implements Initializable {
    public Pane pane;
    public static Civilization opponent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(this::fillFields);
    }

    private void fillFields() {
        HBox myBox = null;
        for (Node anchorPane:pane.getChildren()) {
            if(anchorPane instanceof  AnchorPane)
                for (Node vbox : ((AnchorPane)anchorPane).getChildren()) {
                    if (vbox instanceof VBox){
                        for (Node hbox : ((VBox) vbox).getChildren()) {
                            if (hbox instanceof HBox && ((VBox) vbox).getChildren().indexOf(hbox) < GameMap.getInstance().getCivilizations().size()) {
                                for (Node nodeHbox : ((HBox) hbox).getChildren()) {
                                    if (nodeHbox instanceof Label label) {
                                        Civilization civ = GameMap.getInstance().getCivilizations().get(((VBox) vbox).getChildren().indexOf(hbox));
                                        label.setText(civ.getUser().getNickname());
                                        if (civ == GameController.getInstance().getPlayerTurn())
                                            myBox = (HBox) hbox;
                                    }
                                }
                            } else {
                                hbox.setVisible(false);
                            }
                        }
                        if(myBox != null)
                            ((VBox) vbox).getChildren().remove(myBox);
                    }
                }
        }
    }

    public void back(ActionEvent actionEvent) {
        main.java.Main.changeMenu(Menus.GAME_MENU.value);
    }

    public void buttonSizeIncrease(MouseEvent mouseEvent) {
        javafx.scene.control.Button button = (javafx.scene.control.Button) mouseEvent.getSource();
        button.setStyle("-fx-font-size: 21;-fx-background-color: rgba(231,231,121,0.83);");

    }

    public void buttonSizeDecrease(MouseEvent mouseEvent) {
        javafx.scene.control.Button button = (Button) mouseEvent.getSource();
        button.setStyle("-fx-font-size: 18; -fx-background-color: rgba(201, 238, 221, 0.7);");
    }

    public void openTradePanel(ActionEvent actionEvent) {
        Node button = (Node) actionEvent.getSource();
        HBox hBox = (HBox) button.getParent();
        for (Node node:hBox.getChildren()) {
            if(node instanceof Label){
                String name = ((Label)node).getText();
                for (Civilization civ: GameMap.getInstance().getCivilizations()) {
                    if(civ.getUser().getUsername().equals(name)){
                        opponent = civ;
                    }
                }
            }
        }
        main.java.Main.changeMenu(Menus.TRADE_PANEL.value);
    }

    public void openChat(ActionEvent actionEvent) {
        main.java.Main.changeMenu(Menus.CHAT_MENU.value);
    }

    public void warOrPeace(ActionEvent actionEvent) {
    }
}

