package View.GraphicViewController;
import Controller.GameController.CityController;
import Controller.GameController.GameController;
import Controller.GameController.MapControllers.MapFunctions;
import Controller.GameController.MapControllers.MapPrinter;
import Controller.GameController.UnitController;
import Controller.PreGameController.LoginAndRegisterController;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.Enums.MapEnum;
import Model.MapRelated.GameMap;
import Model.TileRelated.Tile.Tile;
import Model.TileRelated.Tile.TileVisibility;
import Model.Units.TypeEnums.UnitType;
import View.GameView.Game;
import View.Images;
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
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;


public class GameplayGraphicController implements Initializable {
    Image image = new Image("/images/Map/cloud.png");
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
    @FXML
    private Button sleep;
    @FXML
    private Button alert;
    @FXML
    private Button fortify;
    @FXML
    private Button heal;
    @FXML
    private Button setup;
    @FXML
    private Button wake;
    @FXML
    private Button delete;
    @FXML
    private Button upgrade;
    @FXML
    private Button buildImprovement;
    @FXML
    private Button rangedAttack;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(this::printMap);
        initControls();
        getTimeline().play();
        profilePic.setImage(Images.profilePics.pics.get(LoginAndRegisterController.getInstance().getLoggedInUser().getProfPicIndex()));
        buildCity.setOnMouseClicked(mouseEvent -> {
            Tile tile = GameController.getInstance().getSelectedUnit().getTile();
            notification.setText(UnitController.getInstance().checkAndBuildCity(GameController.getInstance().getSelectedUnit().getCivilization().getUser().getNickname() +" "+ (GameController.getInstance().getSelectedUnit().getCivilization().getCities().size()+1)));
            assignRectangleToCities(tile,MapFunctions.getInstance().NonConventionalCoordinatesX(tile)
                    , MapFunctions.getInstance().NonConventionalCoordinatesY(tile));
            updateMap();
        });
    }

    public javafx.scene.shape.Polygon getPolygon(Tile tile) {
        return tileToPoly.get(tile);
    }

    public void timeline() {
        move();
        managePanels();
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

    public void move() {
        double speed = MapEnum.NAVIGATIONSPEED.amount;
        javafx.scene.shape.Polygon firstPoly = getPolygon(MapFunctions.getInstance().getTile(0, 0));
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
            if (MapPrinter.getInstance().getVisibility(tile).equals(TileVisibility.FOGOFWAR))
                polygon.setFill(new ImagePattern(image));
            else {
                if (tile.getFeature() == null) {
                    img = new Image("/images/Map/newPics/" + tile.getTerrain().name() + ".png");
                } else {
                    img = new Image("/images/Map/newPics/" + tile.getFeature().getFeatureType().name() + ".png");
                }
                polygon.setFill(new ImagePattern(img));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clickSettings(Polygon polygon) {
        polygon.setOnMouseClicked(mouseEvent -> {
            if(selectedPoly != null){
                if(polyToTile.get(selectedPoly).getUnits().size() != 0) {
                    selectedPoly.setEffect(null);
                    selectedPoly.setStrokeWidth(selectedPoly.getStrokeWidth() / 10);
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
                    polygon.setEffect(new BoxBlur());
                    polygon.setStrokeWidth(polygon.getStrokeWidth() * 10);
                    unitBar();
                }
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
                pane.getChildren().add(circle);
                unitImages.add(circle);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void assignImages(Tile tile,Polygon polygon,double x,double y){
        resetPoly(polygon);
        assignPicToPoly(polygon);
        assignPicToUnits(tile,x,y);
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
                updateMap();
                CityController.getInstance().selectCity(((Text) stack.getChildren().get(1)).getText());
                manageCityPanel();
                for (Tile key:GameController.getInstance().getSelectedCity().getCityTiles()) {
                    Polygon pol = tileToPoly.get(key);
                    pol.setStroke(Paint.valueOf("Green"));
                    pol.setStrokeWidth(pol.getStrokeWidth()*10);
                }
            });
            cityBanners.add(stack);
            pane.getChildren().add(stack);
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
        assignImages(tile, polygon, x, y);
        pane.getChildren().add(0, polygon);
    }

    void managePanels() {
        for (Civilization civ : GameMap.getInstance().getCivilizations()) {
            if (civ.getUser().equals(LoginAndRegisterController.getInstance().getLoggedInUser())) {
                sciencePerTurn.setText(Integer.toString(civ.getSciencePerTurn()));
                goldPerTurn.setText("(" + Integer.toString(civ.getGoldPerTurn()) + ")");
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
            unitLabel.setText(GameController.getInstance().getSelectedUnit().getUnitType().name());
            unitMoves.setText("Movements: " + GameController.getInstance().getSelectedUnit().getMovementsLeft());
        }
        if(GameController.getInstance().getSelectedUnit().getUnitType().equals(UnitType.Settler))buildCity.setDisable(false);
        else buildCity.setDisable(true);
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
        updateMap();
    }
}
