package View.GraphicViewController;

import Controller.GameController.GameController;
import Controller.GameController.MapControllers.MapFunctions;
import Controller.GameController.MapControllers.MapPrinter;
import Controller.GameController.UnitController;
import Controller.PreGameController.LoginAndRegisterController;
import Model.CivlizationRelated.Civilization;
import Model.Enums.MapEnum;
import Model.MapRelated.GameMap;
import Model.TileRelated.Tile.Tile;
import Model.TileRelated.Tile.TileVisibility;
import Model.User.User;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;
import main.java.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
    private int timesClickedOnTile = 0;
    @FXML
    private Pane pane;
    HashMap<Tile, javafx.scene.shape.Polygon> tileToPoly = new HashMap<>();
    HashMap<javafx.scene.shape.Polygon,Tile> polyToTile = new HashMap<>();
    private Boolean up = false;
    private Boolean down = false;
    private Boolean right = false;
    private Boolean left = false;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private ImageView unitPic;
    @FXML
    private AnchorPane unitBar;


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
        return tileToPoly.get(tile);
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
                if (node instanceof javafx.scene.shape.Polygon polygon ||
                        node instanceof Circle ) {
                    node.setLayoutY(node.getLayoutY() + speed);
                }
            }
        }
        if(down) {
            for (Node node: pane.getChildren()) {
                if (node instanceof javafx.scene.shape.Polygon polygon ||
                        node instanceof Circle ) {
                    node.setLayoutY(node.getLayoutY() - speed);
                }
            }
        }
        if(right) {
            for (Node node : pane.getChildren()) {
                if (node instanceof javafx.scene.shape.Polygon polygon ||
                        node instanceof Circle) {
                    node.setLayoutX(node.getLayoutX() - speed);
                }
            }
        }
        if(left){
            for (Node node: pane.getChildren()) {
                if (node instanceof javafx.scene.shape.Polygon polygon ||
                        node instanceof Circle ) {
                    node.setLayoutX(node.getLayoutX() + speed);
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
    private void assignPicToPoly(Polygon polygon){
        Tile tile = polyToTile.get(polygon);
        Image img = null;
        try {
            if (MapPrinter.getInstance().getVisibility(tile).equals(TileVisibility.FOGOFWAR))
                polygon.setFill(Color.GRAY);
            if (tile.getFeature() == null) {
                img = new Image("/images/Map/newPics/" + tile.getTerrain().name() + ".png");
            } else {
                img = new Image("/images/Map/newPics/" + tile.getFeature().getFeatureType().name() + ".png");
            }
            polygon.setFill(new ImagePattern(img));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void clickSettings(Polygon polygon){
        polygon.setOnMouseClicked(mouseEvent -> {
            if(GameController.getInstance().getSelectedUnit() != null){
                System.out.println(GameController.getInstance().getSelectedUnit().getTile().getX());
                System.out.println(GameController.getInstance().getSelectedUnit().getTile().getY());
                Polygon poly = tileToPoly.get(GameController.getInstance().getSelectedUnit().getTile());
                assignPicToPoly(poly);
            }
            if(polyToTile.get(polygon).getUnits().size() == 0)
                timesClickedOnTile = 0;
            else
                timesClickedOnTile++;
            boolean isCombat = false;
            if(timesClickedOnTile != 0)
            {
                if(polyToTile.get(polygon).getCombatUnitOnTile() != null && timesClickedOnTile % 2 == 0)
                    isCombat = true;
                if(UnitController.getInstance().selectUnit(polyToTile.get(polygon),isCombat))
                    polygon.setFill(Color.RED);
            }
        });
    }
    private void buildPoly(double x,double y,Tile tile,Polygon polygon){
        double shortSide = MapEnum.HEXSIDESHORT.amount;
        double longSide = MapEnum.HEXSIDELONG.amount;
        polygon.getPoints().addAll(x + shortSide               , y,
                x + shortSide + longSide    , y,
                x + 2 * shortSide + longSide, y + shortSide,
                x + shortSide + longSide    , y + shortSide * 2,
                x + shortSide               , y + shortSide * 2,
                x                           , y + shortSide);
        tileToPoly.put(tile,polygon);
        polyToTile.put(polygon,tile);
    }
    private void assignImages(Tile tile,Polygon polygon,double x,double y){
        Image img;
        try {
            if(MapPrinter.getInstance().getVisibility(tile).equals(TileVisibility.FOGOFWAR))
                polygon.setFill(Color.GRAY);
            if(tile.getFeature() == null) {
                img = new Image("/images/Map/newPics/" + tile.getTerrain().name() + ".png");
            } else{
                img = new Image("/images/Map/newPics/" + tile.getFeature().getFeatureType().name() + ".png");
            }
            if(tile.getUnits().size() != 0){
                Circle circle = new Circle();
                circle.setCenterX(x + (double) MapEnum.HEXSIDESHORT.amount * 4 / 3);
                circle.setCenterY(y + (double) MapEnum.HEXSIDELONG.amount* 6/5);
                circle.setRadius(tile.getUnits().get(0).getUnitType().image.getWidth()* 2/ 5);
                circle.setFill(new ImagePattern(tile.getUnits().get(0).getUnitType().image));
                pane.getChildren().add(circle);
            }
            polygon.setFill(new ImagePattern(img));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void createAndAddPolygon(double x, double y, Tile tile) {
        Polygon polygon = new Polygon();
        buildPoly(x,y,tile,polygon);
        clickSettings(polygon);
        assignImages(tile,polygon,x,y);
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

    public void unitBar(Tile tile){
        unitBar.setVisible(true);
        //button1.seto
    }

    public void buttonSizeIncrease(MouseEvent mouseEvent) {
        javafx.scene.control.Button button =(javafx.scene.control.Button) mouseEvent.getSource();
        button.setStyle("-fx-font-size: 21;-fx-background-color: rgba(231,231,121,0.83);");
    }

    public void buttonSizeDecrease(MouseEvent mouseEvent) {
        javafx.scene.control.Button button =(Button) mouseEvent.getSource();
        button.setStyle("-fx-font-size: 18; -fx-background-color: rgba(201, 238, 221, 0.7);");
    }
}
