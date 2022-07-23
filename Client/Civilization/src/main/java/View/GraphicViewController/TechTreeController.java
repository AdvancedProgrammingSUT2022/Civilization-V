package View.GraphicViewController;

import Controller.PreGameController.LoginAndRegisterController;
import Model.CivlizationRelated.Civilization;
import Model.Enums.Menus;
import Model.MapRelated.GameMap;
import Model.Technology.TechnologyType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class TechTreeController implements Initializable {

    @FXML
    private AnchorPane mainPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Node vbox:mainPane.getChildren()) {
            if(vbox instanceof VBox){
                Label label = (Label) ((VBox) vbox).getChildren().get(0);
                Circle circle = (Circle) ((VBox) vbox).getChildren().get(1);
                for (TechnologyType technologyType:TechnologyType.values()) {
                    if(technologyType.name().equals(label.getText())){
                        circle.setFill(new ImagePattern(technologyType.image));
                        circle.setStrokeWidth(6);
                        label.setWrapText(true);
                        Tooltip tooltip = new Tooltip(technologyType.toolTip);
                        tooltip.setMaxSize(400,400);
                        label.setTooltip(tooltip);

                        for (Civilization civilization: GameMap.getInstance().getCivilizations()) {
                            if(civilization.getUser().equals(LoginAndRegisterController.getInstance().getLoggedInUser())){
                                if(civilization.hasTechnology(technologyType)) circle.setStroke(Color.GOLD);
                                else if(civilization.getCurrentResearchProject() != null && civilization.getCurrentResearchProject().equals(technologyType)) circle.setStroke(Color.BLUE);
                                else if(civilization.searchableTechnologiesTypes().contains(technologyType))circle.setStroke(Color.GREEN);
                                else circle.setStroke(Color.BLACK);
                            }
                        }

                    }
                }
            }
        }
    }

    @FXML
    private void back(ActionEvent actionEvent) {
        main.java.Main.changeMenu(Menus.GAME_MENU.value);
    }
}
