package View.GraphicViewController;
import Controller.GameController.CityController;
import Controller.GameController.GameController;
import Controller.GameController.MapControllers.MapFunctions;
import Controller.GameController.MapControllers.MapPrinter;
import Controller.GameController.MapControllers.TileVisibilityController;
import Controller.GameController.UnitController;
import Controller.PreGameController.LoginAndRegisterController;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.Enums.MapEnum;
import Model.MapRelated.GameMap;
import Model.TileRelated.Tile.Tile;
import Model.TileRelated.Tile.TileVisibility;
import Model.Units.Combat.Combat;
import Model.Units.Combat.Ranged;
import Model.Units.TypeEnums.UnitType;
import View.Images;
import View.Pics;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import main.java.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.*;


public class GameplayGraphicController implements Initializable {
    public Label tileInfo;
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
    HashMap<javafx.scene.shape.Polygon, Tile> polyToTile = new HashMap<>();
    private Boolean up = false;
    private Polygon selectedPoly;
    private Boolean down = false;
    private Boolean right = false;
    private Boolean left = false;
    ArrayList<StackPane> cityBanners = new ArrayList<>();
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
    @FXML
    private ImageView profilePic;
    @FXML
    private Button buildCity;

    private ArrayList<Circle> unitImages = new ArrayList<>();
    private ArrayList<ImageView> citizenImages = new ArrayList<>();
    private ArrayList<ImageView> cityPics = new ArrayList<>();
//    @FXML
//    private Button sleep;
//    @FXML
//    private Button alert;
//    @FXML
//    private Button fortify;
//    @FXML
//    private Button heal;
//    @FXML
//    private Button setup;
//    @FXML
//    private Button wake;
//    @FXML
//    private Button delete;
//    @FXML
//    private Button upgrade;
//    @FXML
//    private Button buildImprovement;
//    @FXML
//    private Button rangedAttack;
    private Rectangle rectangle;
    @FXML
    private AnchorPane cityPanel;
    @FXML
    private Label populationCityBar;
    @FXML
    private Label growthCityBar;
    @FXML
    private Label foodCityBar;
    @FXML
    private Label productionCityBar;
    @FXML
    private Label goldCityBar;
    @FXML
    private Label scienceCityBar;
    @FXML
    private Label notification;
    @FXML
    private HBox cityButtons;
    @FXML
    private AnchorPane researchBar;
    @FXML
    private Label researchNotif;
    @FXML
    private Button research;
    @FXML
    private ImageView researchPic;

    private boolean moveMode = false;

    private boolean attackMode = false;

    private HashMap<Tile,Integer> availablePolys = new HashMap<>();
    @FXML
    private Label terrainType;
    @FXML
    private Label featureType;
    @FXML
    private Label recourseType;
    @FXML
    private Label tileUnit;
    @FXML
    private Polygon tilePolyInfo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(this::printMap);
        initControls();
        getTimeline().play();
        profilePic.setImage(Images.profilePics.pics.get(LoginAndRegisterController.getInstance().getLoggedInUser().getProfPicIndex()));
        researchPic.setImage(Pics.questionMark.image);
    }

    public javafx.scene.shape.Polygon getPolygon(Tile tile) {
        return tileToPoly.get(tile);
    }

    public void timeline() {
        move();
        manageMainPanel();
        pane.requestFocus();
    }

    public Timeline getTimeline() {
        DoubleProperty xPosition = new SimpleDoubleProperty(0);
        xPosition.addListener((observable, oldValue, newValue) -> timeline());
        timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(xPosition, 0)),
                new KeyFrame(Duration.seconds(200), new KeyValue(xPosition, -15000)));
        return timeline;
    }

    public void buildCity(){
        Tile tile = GameController.getInstance().getSelectedUnit().getTile();
        notification.setText(UnitController.getInstance().checkAndBuildCity(GameController.getInstance().getSelectedUnit().getCivilization().getUser().getNickname() +" "+ (GameController.getInstance().getSelectedUnit().getCivilization().getCities().size()+1)));
        assignRectangleToCities(tile,MapFunctions.getInstance().NonConventionalCoordinatesX(tile)
                , MapFunctions.getInstance().NonConventionalCoordinatesY(tile));
        updateMap();
    }

    public void move() {
        double speed = MapEnum.NAVIGATIONSPEED.amount;
        if(up)
            pane.setLayoutY(pane.getLayoutY() + speed);
        if (down)
            pane.setLayoutY(pane.getLayoutY() - speed);
        if (right)
            pane.setLayoutX(pane.getLayoutX() - speed);
        if(left)
            pane.setLayoutX(pane.getLayoutX() + speed);
    }

    public void initControls() {
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
        Platform.runLater(() -> Main.scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.M && GameController.getInstance().getSelectedUnit() != null) {
                Polygon polygon = tileToPoly.get(GameController.getInstance().getSelectedUnit().getTile());
                availablePolys =  TileVisibilityController.getInstance().findVisibles(polyToTile.get(polygon), 0, new HashMap<>());
                for (Tile tile :availablePolys.keySet()) {
                    if(availablePolys.get(tile) <= GameController.getInstance().getSelectedUnit().getMovementsLeft()) {
                        tileToPoly.get(tile).setStroke(Paint.valueOf("Gray"));
                        tileToPoly.get(tile).setEffect(new InnerShadow(75, 1, 1, GameController.getInstance().getSelectedUnit().getCivilization().getColor()));
                        moveMode = true;
                    }
                }
            }
            if (keyEvent.getCode() == KeyCode.A && GameController.getInstance().getSelectedUnit() != null) {
                Polygon polygon = tileToPoly.get(GameController.getInstance().getSelectedUnit().getTile());
                availablePolys =  TileVisibilityController.getInstance().findVisibles(polyToTile.get(polygon), 0, new HashMap<>());
                for (Tile tile :availablePolys.keySet()) {
                    if(GameController.getInstance().getSelectedUnit() instanceof Ranged){
                        if (availablePolys.get(tile) <= GameController.getInstance().getSelectedUnit().getUnitType().range) {
                            tileToPoly.get(tile).setStroke(Paint.valueOf("Gray"));
                            tileToPoly.get(tile).setEffect(new InnerShadow(75, 1, 1, RED));
                            attackMode = true;
                        }
                    }else if (GameController.getInstance().getSelectedUnit() instanceof Combat) {
                        if (availablePolys.get(tile) <= 1) {
                            tileToPoly.get(tile).setStroke(Paint.valueOf("Gray"));
                            tileToPoly.get(tile).setEffect(new InnerShadow(75, 1, 1, RED));
                            attackMode = true;
                        }
                    }
                }
            }
        }));

    }

    public void moveButton(){
        Polygon polygon = tileToPoly.get(GameController.getInstance().getSelectedUnit().getTile());
        availablePolys =  TileVisibilityController.getInstance().findVisibles(polyToTile.get(polygon), 0, new HashMap<>());
        for (Tile tile :availablePolys.keySet()) {
            tileToPoly.get(tile).setStroke(Paint.valueOf("Gray"));
            tileToPoly.get(tile).setEffect(new InnerShadow(75, 1, 1, RED));
            moveMode = true;
        }
    }

    private void printMap() {
        for (int i = 0; i < GameMap.getInstance().getTiles().size(); i++) {
            createAndAddPolygon(MapFunctions.getInstance().NonConventionalCoordinatesX(GameMap.getInstance().getTiles().get(i))
                    , MapFunctions.getInstance().NonConventionalCoordinatesY(GameMap.getInstance().getTiles().get(i))
                    , GameMap.getInstance().getTiles().get(i));
        }
    }

    private void updateMap(){
        for (StackPane stack:cityBanners) {
            pane.getChildren().remove(stack);
        }
        for (ImageView imageView:citizenImages) {
            pane.getChildren().remove(imageView);
        }
        for (ImageView imageView:cityPics) {
            pane.getChildren().remove(imageView);
        }
        researchBar.setVisible(true);
        researchBar.setDisable(false);
        manageResearchBar();
        cityButtons.setVisible(false);
        cityButtons.setDisable(true);
        cityBanners = new ArrayList<>();
        cityPanel.setDisable(true);
        cityPanel.setVisible(false);
        unitBar.setDisable(true);
        unitBar.setVisible(false);
        actionPanel.setVisible(false);
        actionPanel.setDisable(true);
        for (Circle circle: unitImages) {
            pane.getChildren().remove(circle);
        }
        for (int i = 0; i < GameMap.getInstance().getTiles().size(); i++) {
            assignPicToCities(GameMap.getInstance().getTiles().get(i), MapFunctions.getInstance().NonConventionalCoordinatesX(GameMap.getInstance().getTiles().get(i))
                    , MapFunctions.getInstance().NonConventionalCoordinatesY(GameMap.getInstance().getTiles().get(i)));
            assignImages(GameMap.getInstance().getTiles().get(i), tileToPoly.get(GameMap.getInstance().getTiles().get(i)), MapFunctions.getInstance().NonConventionalCoordinatesX(GameMap.getInstance().getTiles().get(i))
                    , MapFunctions.getInstance().NonConventionalCoordinatesY(GameMap.getInstance().getTiles().get(i)));
            assignRectangleToCities(GameMap.getInstance().getTiles().get(i), MapFunctions.getInstance().NonConventionalCoordinatesX(GameMap.getInstance().getTiles().get(i))
                    , MapFunctions.getInstance().NonConventionalCoordinatesY(GameMap.getInstance().getTiles().get(i)));
        }
    }

    private void assignPicToPoly(Polygon polygon) {
        Tile tile = polyToTile.get(polygon);
        Image img;
        try {
            if(MapPrinter.getInstance().getVisibility(tile).equals(TileVisibility.REVEALED)){
                polygon.setEffect(new InnerShadow(170, 1, 1, BLACK));
            }
            if (MapPrinter.getInstance().getVisibility(tile).equals(TileVisibility.FOGOFWAR)) {
                polygon.setFill(new ImagePattern(Pics.cloud.image));
            }
            else {
                if (tile.getFeature() == null) {
                    System.out.println("no terrain" + tile.getTerrain());
                    img = tile.getTerrain().image;
                } else {
                    System.out.println("yes terrain" + tile.getTerrain() + "feature " +tile.getFeature());
                    img = tile.getFeature().getFeatureType().image;
                }
                polygon.setFill(new ImagePattern(img));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printDetails(){
        System.out.println("-----------------------------");
        System.out.println("selected unit info: ");
        System.out.println("unit type : " + GameController.getInstance().getSelectedUnit().getUnitType());
        System.out.println(GameController.getInstance().getSelectedUnit().getTile().getX());
        System.out.println(GameController.getInstance().getSelectedUnit().getTile().getY());
        System.out.println("movements left :" + GameController.getInstance().getSelectedUnit().getMovementsLeft());
        System.out.println("-----------------------------");
    }


    private void clickSettings(Polygon polygon) {
        polygon.setOnMouseClicked(mouseEvent -> {
            if (moveMode) {
                System.out.println("im in move init");
                GameController.getInstance().initMoveUnit(polyToTile.get(polygon));
                updateMap();
                moveMode = false;
            }
            if (attackMode) {
                GameController.getInstance().attack(polyToTile.get(polygon));
                updateMap();
                moveMode = false;
            }
            if (selectedPoly != null) {
                if (polyToTile.get(selectedPoly).getUnits().size() != 0) {
                    selectedPoly.setEffect(null);
                    selectedPoly.setStrokeWidth(selectedPoly.getStrokeWidth() / 4);
                }
                selectedPoly.setStroke(null);
                assignPicToPoly(selectedPoly);
            }
            if (polyToTile.get(polygon).getUnits().size() == 0)
                timesClickedOnTile = 0;
            else
                timesClickedOnTile++;
            boolean isCombat = polyToTile.get(polygon).getUnits().size() == 1 && polyToTile.get(polygon).getCombatUnitOnTile() != null;
            if (timesClickedOnTile != 0) {
                if (polyToTile.get(polygon).getCombatUnitOnTile() != null && timesClickedOnTile % 2 == 0)
                    isCombat = true;
                if (UnitController.getInstance().selectUnit(polyToTile.get(polygon), isCombat)) {
                    updateMap();
                    selectedPoly = polygon;
                    polygon.setStroke(Paint.valueOf("Cyan"));
                    polygon.setEffect(new InnerShadow(75, 1, 1, CYAN));
                    polygon.setStrokeWidth(polygon.getStrokeWidth() * 4);
                    unitBar();
                    printDetails();
                }
            }
            polygon.requestFocus();
        });
        polygon.setOnMouseEntered(mouseEvent -> {
           tileInfo.setText("Terrain: " + polyToTile.get(polygon).getTerrain().name());
           if(polyToTile.get(polygon).getFeature() != null)
               tileInfo.setText(tileInfo.getText() + " Feature: " + polyToTile.get(polygon).getFeature().getFeatureType().name());
           if(polyToTile.get(polygon).getCombatUnitOnTile() != null){
               tileInfo.setText(tileInfo.getText() + " Hp : " +polyToTile.get(polygon).getCombatUnitOnTile().getHitPoints());
           }
        });
    }
    private void resetPoly(Polygon polygon){
        polygon.setStrokeWidth(1);
        polygon.setStroke(null);
        polygon.setEffect(null);
    }
    private void buildPoly(double x, double y, Tile tile, Polygon polygon) {
        double shortSide = MapEnum.HEXSIDESHORT.amount;
        double longSide = MapEnum.HEXSIDELONG.amount;
        polygon.getPoints().addAll(x + shortSide, y,
                x + shortSide + longSide, y,
                x + 2 * shortSide + longSide, y + shortSide,
                x + shortSide + longSide, y + shortSide * 2,
                x + shortSide, y + shortSide * 2,
                x, y + shortSide);
        tileToPoly.put(tile, polygon);
        polyToTile.put(polygon, tile);
    }

    private void assignPicToUnits(Tile tile,double x,double y){
        try {
            if(tile.getUnits().size() != 0 && !MapPrinter.getInstance().getVisibility(tile).equals(TileVisibility.FOGOFWAR)){
                Circle circle = new Circle();
                circle.setCenterX(x + (double) MapEnum.HEXSIDESHORT.amount * 4 / 3);
                circle.setCenterY(y + (double) MapEnum.HEXSIDELONG.amount * 6 / 5);
                circle.setRadius(tile.getUnits().get(0).getUnitType().image.getWidth() * 1 / 5);
                circle.setFill(new ImagePattern(tile.getUnits().get(0).getUnitType().image));
                circle.setStrokeWidth(6);
                circle.setStroke(tile.getUnits().get(0).getCivilization().getColor());
                pane.getChildren().add(circle);
                unitImages.add(circle);
                if(tile.getUnits().size() == 2){
                    Circle circleSecond = new Circle();
                    circleSecond.setCenterX(x + (double) MapEnum.HEXSIDESHORT.amount * 4 / 3 + circle.getRadius() / 4);
                    circle.setCenterX(circle.getCenterX() - circle.getRadius() / 4);
                    circleSecond.setCenterY(y + (double) MapEnum.HEXSIDELONG.amount * 6 / 5);
                    circleSecond.setRadius(tile.getUnits().get(0).getUnitType().image.getWidth() * 1 / 5);
                    circleSecond.setFill(new ImagePattern(tile.getUnits().get(0).getUnitType().image));
                    circleSecond.setStrokeWidth(6);
                    circleSecond.setStroke(tile.getUnits().get(0).getCivilization().getColor());
                    pane.getChildren().add(circleSecond);
                    unitImages.add(circleSecond);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void assignPicToCitizen(Tile tile,double x,double y){
        try{
            ImageView imageView = new ImageView();
            imageView.setFitHeight(1280/18);
            imageView.setFitWidth(1600/18);
            imageView.setLayoutX(x + (double) MapEnum.HEXSIDESHORT.amount * 3 / 5);
            imageView.setLayoutY(y - (double) MapEnum.HEXSIDELONG.amount);
            imageView.setImage(Pics.citizen.image);
            imageView.setOnMouseClicked(mouseEvent -> {
                if(tile.getCitizen() == null) {
                    notification.setText(GameController.getInstance().getSelectedCity().assignCitizen(tile));
                    if(notification.getText().equals("citizen assigned successfully"))imageView.setOpacity(1);
                }
                else {
                    notification.setText(GameController.getInstance().getSelectedCity().removeCitizenFromWork(tile));
                    if(notification.getText().equals("citizen removed successfully"))imageView.setOpacity(0.5);
                }
                manageMainPanel();
                manageCityPanel();
            });
            pane.getChildren().add(imageView);
            citizenImages.add(imageView);
            if(tile.getCitizen() == null)imageView.setOpacity(0.5);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void assignCoinToTile(Tile tile,double x,double y){
        try{
            ImageView imageView = new ImageView();
            imageView.setFitHeight(128/2);
            imageView.setFitWidth(128/2);
            tileToPoly.get(tile).setEffect(new InnerShadow(75, 1, 1, YELLOW));
            imageView.setLayoutX(x + (double) MapEnum.HEXSIDESHORT.amount * 4 / 5);
            imageView.setLayoutY(y - (double) MapEnum.HEXSIDELONG.amount);
            imageView.setImage(Pics.coin.image);
            imageView.setOnMouseClicked(mouseEvent -> {
                notification.setText(GameController.getInstance().getSelectedCity().buyTile(tile));
                cityShow();
            });
            pane.getChildren().add(imageView);
            citizenImages.add(imageView);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void assignImages(Tile tile,Polygon polygon,double x,double y){
        resetPoly(polygon);
        assignPicToPoly(polygon);
        assignPicToUnits(tile,x,y);
        assignPicForResources(tile,x,y);
    }

    private void assignPicForResources(Tile tile, double x, double y) {
        if(tile.getResource() != null && !MapPrinter.getInstance().getVisibility(tile).equals(TileVisibility.FOGOFWAR)) {
            Circle circle = new Circle();
            circle.setCenterX(x + (double) MapEnum.HEXSIDESHORT.amount * 4 / 3);
            circle.setCenterY(y + (double) MapEnum.HEXSIDELONG.amount * 6 / 5);
            circle.setRadius(tile.getResource().getResourceType().image.getWidth() * 1 / 5);
            circle.setFill(new ImagePattern(tile.getResource().getResourceType().image));
            pane.getChildren().add(circle);
        }
    }

    private void assignRectangleToCities(Tile tile, double x, double y) {
        if(tile.isCapital() && !MapPrinter.getInstance().getVisibility(tile).equals(TileVisibility.FOGOFWAR)) {
            Rectangle rectangle = new Rectangle(x + (double) MapEnum.HEXSIDESHORT.amount * 1 / 2, y - (double) MapEnum.HEXSIDELONG.amount * 1 / 5, 100, 30);
            Text text = new Text(tile.getCity().getName());
            text.setStyle("-fx-font-family: 'Britannic Bold';-fx-text-fill: rgba(12,7,7,0.68);-fx-font-size: 18;");
            rectangle.setArcHeight(35);
            rectangle.setArcWidth(35);
            rectangle.setFill(Paint.valueOf("#9bd8c9b3"));
            StackPane stack = new StackPane();
            stack.getChildren().addAll(rectangle, text);
            stack.setLayoutX(x + (double) MapEnum.HEXSIDESHORT.amount * 1 / 2);
            stack.setLayoutY(y - (double) MapEnum.HEXSIDELONG.amount * 1 / 5);
            stack.setOnMouseClicked(mouseEvent -> {
                CityController.getInstance().selectCity(((Text) stack.getChildren().get(1)).getText());
                if(tile.getCivilization().getUser().equals(LoginAndRegisterController.getInstance().getLoggedInUser())) {
                    cityShow();
                }
                else {
                    enemyCityShow();
                }
            });
            cityBanners.add(stack);
            pane.getChildren().add(stack);
        }
    }

    private void enemyCityShow() {
        updateMap();
        researchBar.setVisible(false);
        researchBar.setDisable(true);
        for (Tile key:GameController.getInstance().getSelectedCity().getCityTiles()) {
            Polygon pol = tileToPoly.get(key);
            pol.setStroke(key.getCivilization().getColor());
            pol.setEffect(new InnerShadow(75, 1, 1, key.getCivilization().getColor()));
            pol.setStrokeWidth(pol.getStrokeWidth()*10);
        }
    }

    private void cityShow() {
        updateMap();
        researchBar.setVisible(false);
        researchBar.setDisable(true);
        cityButtons.setVisible(true);
        cityButtons.setDisable(false);
        manageCityPanel();
        for (Tile key:GameController.getInstance().getSelectedCity().getCityTiles()) {
            Polygon pol = tileToPoly.get(key);
            pol.setStroke(key.getCivilization().getColor());
            pol.setEffect(new InnerShadow(75, 1, 1, key.getCivilization().getColor()));
            pol.setStrokeWidth(pol.getStrokeWidth()*10);
        }
    }

    private void assignPicToCities(Tile tile, double x, double y) {
        if(tile.isCapital() && !MapPrinter.getInstance().getVisibility(tile).equals(TileVisibility.FOGOFWAR)) {
            ImageView imageView = new ImageView();
            imageView.setImage(Pics.city1.image);
            imageView.setLayoutX(x + (double) MapEnum.HEXSIDESHORT.amount * 2 / 5);
            imageView.setLayoutY(y - (double) MapEnum.HEXSIDELONG.amount * 1 / 5);
            imageView.setFitWidth(595/5);
            imageView.setFitHeight(476/5);
            cityPics.add(imageView);
            pane.getChildren().add(imageView);
        }
    }

    private void manageCityPanel() {
        City city = GameController.getInstance().getSelectedCity();
        cityPanel.setVisible(true);
        cityPanel.setDisable(false);
        populationCityBar.setText("Population:     " + city.getPopulation());
        if(city.getFoodPerTurn() > 0)growthCityBar.setText("growth in " + ((int)((Math.pow(2,city.getPopulation())) - city.getStoredFood())/(city.getFoodPerTurn())+1) + " turns");
        else growthCityBar.setText("City is not growing!");
        foodCityBar.setText("Food per Turn: " + city.getFoodPerTurn());
        productionCityBar.setText("Production per Turn: " + city.getProductionPerTurn());
        goldCityBar.setText("Gold per Turn: "+ city.getGoldPerTurn());
        scienceCityBar.setText("Science Per Turn: " + city.getSciencePerTurn());
    }

    private void createAndAddPolygon(double x, double y, Tile tile) {
        Polygon polygon = new Polygon();
        buildPoly(x, y, tile, polygon);
        clickSettings(polygon);
        hoverSettings(polygon);
        assignImages(tile, polygon, x, y);
        pane.getChildren().add(0, polygon);
    }

    private void hoverSettings(Polygon polygon) {
        Tile tile = polyToTile.get(polygon);
        polygon.setOnMouseEntered(mouseEvent -> {
            if(MapPrinter.getInstance().getVisibility(tile).equals(TileVisibility.FOGOFWAR)){
                tilePolyInfo.setFill(new ImagePattern(Pics.cloud.image));
                recourseType.setText("Recourse: Fogged");
                terrainType.setText("Terrain: Fogged");
                featureType.setText("Feature: Fogged");
                unitLabel.setText("CombatUnit: Fogged");
            }
            else {
                tilePolyInfo.setFill(new ImagePattern(tile.getTerrain().image));
                if(tile.getResource() != null)recourseType.setText("Recourse: " + tile.getResource().getResourceType().name());
                else recourseType.setText("Recourse: No Recourse");
                terrainType.setText("Terrain: " + tile.getTerrain().name());
                if(tile.getFeature() != null)featureType.setText("Feature: " + tile.getFeature().getFeatureType().name());
                else featureType.setText("Feature: No Feature");
                if(MapPrinter.getInstance().getVisibility(tile).equals(TileVisibility.VISIBLE)){
                    if(tile.getCombatUnitOnTile() == null)tileUnit.setText("CombatUnit: No Unit");
                    else tileUnit.setText("Combat: HP:" + tile.getCombatUnitOnTile().getHitPoints() + " _ Strength: " + tile.getCombatUnitOnTile().getUnitType().combatStrength);
                }
                else {
                    tileUnit.setText("CombatUnit: Revealed Tile");
                }
            }
        });
    }

    void manageMainPanel() {
        for (Civilization civ : GameMap.getInstance().getCivilizations()) {
            if (civ.getUser().equals(LoginAndRegisterController.getInstance().getLoggedInUser())) {
                sciencePerTurn.setText(Integer.toString(civ.getSciencePerTurn()));
                goldPerTurn.setText("(" + (civ.getGoldPerTurn()) + ")");
                currentGold.setText(Integer.toString(civ.getGold()));
                happiness.setText(Integer.toString(civ.getGold()));
            }
        }
    }

    public void unitBar() {
        if (GameController.getInstance().getSelectedUnit() != null) {
            unitBar.setVisible(true);
            unitBar.setDisable(false);
            unitPic.setImage(GameController.getInstance().getSelectedUnit().getUnitType().image);
            if(GameController.getInstance().getSelectedUnit() instanceof Combat)
                unitLabel.setText(GameController.getInstance().getSelectedUnit().getUnitType().name() + " HP : " + ((Combat)GameController.getInstance().getSelectedUnit()).getHitPoints());
            else
                unitLabel.setText(GameController.getInstance().getSelectedUnit().getUnitType().name());
            unitMoves.setText("Movements: " + GameController.getInstance().getSelectedUnit().getMovementsLeft());
        }
        if(GameController.getInstance().getSelectedUnit().getUnitType().equals(UnitType.Settler))buildCity.setDisable(false);
        else buildCity.setDisable(true);
    }

    public void manageResearchBar(){
        for (Civilization civ : GameMap.getInstance().getCivilizations()) {
            if (civ.getUser().equals(LoginAndRegisterController.getInstance().getLoggedInUser())) {
                if(civ.getCurrentResearchProject() == null){
                    researchNotif.setText("no Research!");
                    research.setDisable(false);
                    researchPic.setImage(Pics.questionMark.image);
                }
                else {
                    research.setDisable(true);
                   // researchPic.setImage();
                }
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

    @FXML
    private void showActions(MouseEvent mouseEvent) {
        if (actionPanel.isDisable()) {
            actionPanel.setDisable(false);
            actionPanel.setVisible(true);
        } else {
            actionPanel.setDisable(true);
            actionPanel.setVisible(false);
        }
    }

    public void nextTurn(ActionEvent actionEvent) {
        GameController.getInstance().nextTurn();
        System.out.println(GameController.getInstance().getPlayerTurn().getUser().getUsername());
        updateMap();
    }

    @FXML
    private void citizenManagement(MouseEvent mouseEvent) {
        updateMap();
        cityShow();
        for (Tile key:GameController.getInstance().getSelectedCity().getCityTiles()) {
            if(key.isCapital())continue;
            assignPicToCitizen(key,MapFunctions.getInstance().NonConventionalCoordinatesX(key)
                    , MapFunctions.getInstance().NonConventionalCoordinatesY(key));
        }
    }

    @FXML
    private void buyTile(MouseEvent mouseEvent) {
        updateMap();
        cityShow();
        for (Tile key:GameController.getInstance().getSelectedCity().findCitySurroundings()) {
            assignCoinToTile(key,MapFunctions.getInstance().NonConventionalCoordinatesX(key)
                    , MapFunctions.getInstance().NonConventionalCoordinatesY(key));
        }
    }
}
