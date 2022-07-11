package View.GraphicViewController;

import Controller.GameController.GameController;
import Controller.GameController.MapControllers.MapFunctions;
import Controller.GameController.MapControllers.MapPrinter;
import Controller.PreGameController.LoginAndRegisterController;
import Model.CivlizationRelated.Civilization;
import Model.Enums.MapEnum;
import Model.MapRelated.GameMap;
import Model.TileRelated.Tile.Tile;
import Model.TileRelated.Tile.TileVisibility;
import Model.Units.TypeEnums.UnitType;
import Model.Units.Unit;
import Model.User.User;
import View.GameView.Game;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.stage.Popup;
import javafx.util.Duration;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;


public class GameplayGraphicController implements Initializable {
    @FXML
    private Label sciencePerTurn;
    @FXML
    private Label currentGold;
    @FXML
    private Label goldPerTurn;
    @FXML
    private Label happiness;
    private Timeline timeline;
    @FXML
    private Pane pane;
    HashMap<Tile, javafx.scene.shape.Polygon> polygons = new HashMap<>();
    private Boolean up = false;
    private Boolean down = false;
    private Boolean right = false;
    private Boolean left = false;
    @FXML
    private Button button1;
    @FXML
    private ImageView unitPic;
    @FXML
    private AnchorPane unitBar;
    @FXML
    private AnchorPane actionPanel;
    @FXML
    private Label unitLabel;
    @FXML
    private Label unitMoves;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<User> users =  new ArrayList<>();
        users.add(new User());
        users.add(new User());
        Platform.runLater(this::printMap);
        initControls();
        getTimeline().play();
    }

    public javafx.scene.shape.Polygon getPolygon(Tile tile){
        return polygons.get(tile);
    }
    public void timeline(){
        move();
        managePanels();
        pane.requestFocus();
    }

    public Timeline getTimeline(){
        DoubleProperty xPosition = new SimpleDoubleProperty(0);
        xPosition.addListener((observable, oldValue, newValue) -> timeline());
        timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(xPosition, 0)),
                new KeyFrame(Duration.seconds(200), new KeyValue(xPosition, -15000)));
        return timeline;
    }

    public void move() {
        double speed = MapEnum.NAVIGATIONSPEED.amount;
        javafx.scene.shape.Polygon firstPoly = getPolygon(MapFunctions.getInstance().getTile(0,0));
        if(up) {
            for (Node node: pane.getChildren()) {
                if(node instanceof javafx.scene.shape.Polygon polygon){
                    polygon.setLayoutY(polygon.getLayoutY() + speed);
                }
            }
        }
        if(down) {
            for (Node node: pane.getChildren()) {
                if(node instanceof javafx.scene.shape.Polygon polygon){
                    polygon.setLayoutY(polygon.getLayoutY() - speed);
                }
            }
        }
        if(right) {
            for (Node node : pane.getChildren()) {
                if (node instanceof javafx.scene.shape.Polygon polygon) {
                    polygon.setLayoutX(polygon.getLayoutX() - speed);
                }
            }
        }
        if(left){
            for (Node node: pane.getChildren()) {
                if(node instanceof javafx.scene.shape.Polygon polygon){
                    polygon.setLayoutX(polygon.getLayoutX() + speed);
                }
            }
        }
    }
    public void initControls(){
        Platform.runLater(() -> pane.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP -> up = true;
                case DOWN -> down = true;
                case RIGHT -> right = true;
                case LEFT -> left = true;
            }
        }));
        Platform.runLater(() -> pane.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case UP -> up = false;
                case DOWN -> down = false;
                case RIGHT -> right = false;
                case LEFT -> left = false;
            }
        }));
    }
    private void printMap(){
        for (int i = 0; i < GameMap.getInstance().getTiles().size(); i++) {
            createAndAddPolygon(MapFunctions.getInstance().NonConventionalCoordinatesX(GameMap.getInstance().getTiles().get(i))
                    ,MapFunctions.getInstance().NonConventionalCoordinatesY(GameMap.getInstance().getTiles().get(i))
                    ,GameMap.getInstance().getTiles().get(i));
        }
    }
    private void createAndAddPolygon(double x, double y, Tile tile) {
        double shortSide = MapEnum.HEXSIDESHORT.amount;
        double longSide = MapEnum.HEXSIDELONG.amount;
        javafx.scene.shape.Polygon polygon = new Polygon();
        polygon.getPoints().addAll(x + shortSide               , y,
                x + shortSide + longSide    , y,
                x + 2 * shortSide + longSide, y + shortSide,
                x + shortSide + longSide    , y + shortSide * 2,
                x + shortSide               , y + shortSide * 2,
                x                           , y + shortSide);
        polygons.put(tile,polygon);
        javafx.scene.image.Image img;
        try {
            if(MapPrinter.getInstance().getVisibility(tile).equals(TileVisibility.FOGOFWAR))
                polygon.setFill(Color.GRAY);
            if(tile.getFeature() == null) {
                img = new javafx.scene.image.Image("/images/Map/newPics/" + tile.getTerrain().name() + ".png");
            } else{
                img = new Image("/images/Map/newPics/" + tile.getFeature().getFeatureType().name() + ".png");
            }
            polygon.setFill(new ImagePattern(img));
        }catch (Exception e){
            e.printStackTrace();
        }
        pane.getChildren().add(0,polygon);
    }

    void managePanels(){
        for (Civilization civ:GameMap.getInstance().getCivilizations()) {
            if(civ.getUser().equals(LoginAndRegisterController.getInstance().getLoggedInUser())){
                sciencePerTurn.setText(Integer.toString(civ.getSciencePerTurn()));
                goldPerTurn.setText("("+Integer.toString(civ.getGoldPerTurn())+")");
                currentGold.setText(Integer.toString(civ.getGold()));
                happiness.setText(Integer.toString(civ.getGold()));
            }
        }
    }

    public void unitBar(){
        unitBar.setVisible(true);
        unitBar.setDisable(false);
        for (UnitType key:UnitType.values()) {
            if(GameController.getInstance().getSelectedUnit().getUnitType().equals(key)){
                unitPic.setImage(key.image);
                unitLabel.setText(key.name());
                unitMoves.setText("Movements: " + GameController.getInstance().getSelectedUnit().getMovementsLeft());
            }
        }
    }

    public void buttonSizeIncrease(MouseEvent mouseEvent) {
        javafx.scene.control.Button button =(javafx.scene.control.Button) mouseEvent.getSource();
        button.setStyle("-fx-font-size: 21;-fx-background-color: rgba(231,231,121,0.83);");
    }

    public void buttonSizeDecrease(MouseEvent mouseEvent) {
        javafx.scene.control.Button button =(Button) mouseEvent.getSource();
        button.setStyle("-fx-font-size: 18; -fx-background-color: rgba(201, 238, 221, 0.7);");
    }

    @FXML
    private void showActions(MouseEvent mouseEvent) {
        if(actionPanel.isDisable()) {
            actionPanel.setDisable(false);
            actionPanel.setVisible(true);
        }
        else {
            actionPanel.setDisable(true);
            actionPanel.setVisible(false);
        }
    }
}
