package View.GraphicViewController;

import Controller.GameController.GameController;
import Model.CivlizationRelated.Civilization;
import Model.Enums.Menus;
import Model.MapRelated.GameMap;
import Model.TileRelated.Resource.ResourceType;
import View.Menu.Menu;
import View.Pics;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import javax.swing.text.Element;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;

public class TradePanelController implements Initializable {
    public VBox myResources;
    public VBox myOffer;
    public VBox oppOffer;
    public VBox oppResources;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(this::fillFields);
    }

    private void fillFields() {
        fillResources(myResources, myOffer,GameController.getInstance().getPlayerTurn());
        fillResources(oppResources,oppOffer,DiplomacyPanelGraphicPageController.opponent);
    }

    private void fillResources(VBox myResources,VBox myOffer, Civilization civilization) {
        addCoinHbox(civilization,myResources,myOffer);
        for (ResourceType type:civilization.getLuxuryResourceCount().keySet()) {
            if(civilization.getLuxuryResourceCount().get(type) != 0) {
                HBox hBox = new HBox();
                hBox.setPrefWidth(myResources.getPrefWidth());
                hBox.setPrefHeight(30);
                Circle circle = new Circle();
                circle.setRadius(15);
                circle.setFill(new ImagePattern(type.image));
                Label label = new Label(type.name());
                label.setStyle(" -fx-text-fill: #e5d5b2; -fx-font-family: 'Britannic Bold';-fx-font-size: 23;");
                label.setPrefWidth(50);
                TextField textField = new TextField(Integer.toString(civilization.getLuxuryResourceCount().get(type)));
                textField.setStyle("-fx-background-color: rgba(248, 248, 248, 0.35); -fx-background-radius: 10;");
                hBox.getChildren().add(circle);
                hBox.getChildren().add(label);
                hBox.getChildren().add(textField);
                hBox.setSpacing(15);
                myResources.getChildren().add(hBox);
                hBox.setOnMouseClicked(mouseEvent -> {
                    VBox vBox =  ((VBox) hBox.getParent());
                    if(vBox == myOffer)
                        myResources.getChildren().add(hBox);
                    else
                        myOffer.getChildren().add(hBox);
                    vBox.getChildren().remove(hBox);
                });
            }
        }
    }

    public void buttonSizeIncrease(MouseEvent mouseEvent) {
        javafx.scene.control.Button button = (javafx.scene.control.Button) mouseEvent.getSource();
        button.setStyle("-fx-font-size: 21;-fx-background-color: rgba(231,231,121,0.83);");

    }

    public void buttonSizeDecrease(MouseEvent mouseEvent) {
        javafx.scene.control.Button button = (Button) mouseEvent.getSource();
        button.setStyle("-fx-font-size: 18; -fx-background-color: rgba(201, 238, 221, 0.7);");
    }
    public void addCoinHbox(Civilization civilization,VBox myResources,VBox myOffer){
        HBox hBox = new HBox();
        hBox.setPrefWidth(myResources.getPrefWidth());
        hBox.setPrefHeight(30);
        Circle circle = new Circle();
        circle.setRadius(15);
        circle.setFill(new ImagePattern(Pics.coin.image));
        Label label = new Label(Pics.coin.name());
        label.setStyle(" -fx-text-fill: #e5d5b2; -fx-font-family: 'Britannic Bold';-fx-font-size: 23;");
        label.setPrefWidth(50);
        TextField textField = new TextField(Integer.toString(civilization.getGold()));
        textField.setStyle("-fx-background-color: rgba(248, 248, 248, 0.35); -fx-background-radius: 10;");
        hBox.getChildren().add(circle);
        hBox.getChildren().add(label);
        hBox.getChildren().add(textField);
        hBox.setSpacing(15);
        myResources.getChildren().add(hBox);
        hBox.setOnMouseClicked(mouseEvent -> {
            VBox vBox =  ((VBox) hBox.getParent());
            if(vBox == myOffer)
                myResources.getChildren().add(hBox);
            else
                myOffer.getChildren().add(hBox);
            vBox.getChildren().remove(hBox);
        });
    }

    public void back(ActionEvent actionEvent) {
        main.java.Main.changeMenu(Menus.DIPLOMACY_PANEL.value);
    }

    public void trade(ActionEvent actionEvent) {
        doTrade(myOffer,GameController.getInstance().getPlayerTurn(),DiplomacyPanelGraphicPageController.opponent);
        doTrade(oppOffer,DiplomacyPanelGraphicPageController.opponent,GameController.getInstance().getPlayerTurn());
        for (ResourceType type:DiplomacyPanelGraphicPageController.opponent.getLuxuryResourceCount().keySet()) {
            if(DiplomacyPanelGraphicPageController.opponent.getLuxuryResourceCount().get(type) != 0){
                System.out.println(DiplomacyPanelGraphicPageController.opponent.getUser().getUsername() + " " + type.name() + " " +  DiplomacyPanelGraphicPageController.opponent.getLuxuryResourceCount().get(type));
            }
        }
        for (ResourceType type:GameController.getInstance().getPlayerTurn().getLuxuryResourceCount().keySet()) {
            if(GameController.getInstance().getPlayerTurn().getLuxuryResourceCount().get(type) != 0){
                System.out.println(GameController.getInstance().getPlayerTurn().getUser().getUsername() + " " + type.name() + " " +  GameController.getInstance().getPlayerTurn().getLuxuryResourceCount().get(type));
            }
        }
        main.java.Main.changeMenu(Menus.DIPLOMACY_PANEL.value);
    }

    public void doTrade(VBox myOffer,Civilization myCivilization,Civilization otherCiv){
        for (Node node: myOffer.getChildren()) {
            String name = null;
            int amount = 0;
            for (Node hBoxNode: ((HBox)node).getChildren()) {
                if(hBoxNode instanceof Label){
                    name = ((Label)hBoxNode).getText();
                }
                try {
                    if (hBoxNode instanceof TextField) {
                        amount = Integer.parseInt(((TextField) hBoxNode).getText());
                    }
                }catch (NumberFormatException e){
                    System.out.println("wrong text written.");
                    e.printStackTrace();
                }

            }
            if(name != null && name.equals("coin")){
                if (myCivilization.getGold() <= amount){
                    myCivilization.setGold(0);
                }else {
                    myCivilization.setGold(myCivilization.getGold() - amount);
                    otherCiv.setGold(otherCiv.getGold() + amount);
                }
            }
            for (ResourceType type:ResourceType.values()) {
                if(type.name().equals(name)){
                    myCivilization.getLuxuryResourceCount().replace(type,myCivilization.getLuxuryResourceCount().get(type) - amount);
                    otherCiv.getLuxuryResourceCount().replace(type,otherCiv.getLuxuryResourceCount().get(type) + amount);
                }
            }
        }
    }
}

