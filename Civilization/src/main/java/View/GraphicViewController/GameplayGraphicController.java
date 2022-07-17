package View.GraphicViewController;
import Controller.GameController.CityController;
import Controller.GameController.CivilizationController;
import Controller.GameController.GameController;
import Controller.GameController.MapControllers.CheatCode;
import Controller.GameController.MapControllers.MapFunctions;
import Controller.GameController.MapControllers.MapPrinter;
import Controller.GameController.MapControllers.TileVisibilityController;
import Controller.GameController.UnitController;
import Controller.PreGameController.LoginAndRegisterController;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.Enums.MapEnum;
import Model.Enums.Menus;
import Model.MapRelated.GameMap;
import Model.Technology.TechnologyType;
import Model.TileRelated.Building.BuildingType;
import Model.TileRelated.Improvement.ImprovementType;
import Model.TileRelated.Tile.Tile;
import Model.TileRelated.Tile.TileVisibility;
import Model.Units.Combat.Combat;
import Model.Units.Combat.Ranged;
import Model.Units.TypeEnums.MainType;
import Model.Units.TypeEnums.UnitType;
import Model.Units.Unit;
import View.Images;
import View.Pics;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.css.Style;
import javafx.css.StyleClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Window;
import javafx.util.Duration;
import main.java.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



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
    private boolean cheating = false;

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
    @FXML
    private ScrollPane technologies;
    @FXML
    private ScrollPane buildUnitsBar;
    @FXML
    private Button productionBuild;
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
    private Button buildImprovement;
    @FXML
    private Button rangedAttack;
    @FXML
    private Button upgrade;
    @FXML
    private AnchorPane improvementPanel;
    @FXML
    private HBox cheatBar;
    @FXML
    private TextField cheatTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(this::printMap);
        initControls();
        getTimeline().play();
        profilePic.setImage(Images.profilePics.pics.get(LoginAndRegisterController.getInstance().getLoggedInUser().getProfPicIndex()));
        researchPic.setImage(Pics.questionMark.image);
        CheatCode.getInstance().unlockFirstHalfTechnologies();
        GameController.getInstance().getPlayerTurn().setGold(20000);
    }

    public javafx.scene.shape.Polygon getPolygon(Tile tile) {
        return tileToPoly.get(tile);
    }

    public void timeline() {
        move();
        manageMainPanel();
        if(!cheating)pane.requestFocus();
        else cheatTextField.requestFocus();
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
        GameController.getInstance().getPlayerTurn().addNotification(notification.getText());
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
                            tileToPoly.get(tile).setEffect(new InnerShadow(75, 1, 1, Color.RED));
                            attackMode = true;
                        }
                    }else if (GameController.getInstance().getSelectedUnit() instanceof Combat) {
                        if (availablePolys.get(tile) <= 1) {
                            tileToPoly.get(tile).setStroke(Paint.valueOf("Gray"));
                            tileToPoly.get(tile).setEffect(new InnerShadow(75, 1, 1, Color.RED));
                            attackMode = true;
                        }
                    }
                }
            }
        }));

    }

    public void manageBuildUnitBar(){
        buildUnitsBar.setDisable(false);
        buildUnitsBar.setVisible(true);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        Label label1 = new Label("Choose Production:");
        vBox.getChildren().add(label1);
        label1.setAlignment(Pos.CENTER);
        label1.setStyle("-fx-font-size: 22; -fx-text-fill: #000000; -fx-font-family: 'Britannic Bold'");
                for (UnitType unit: CityController.getInstance().validUnits()) {
                    HBox hBox = new HBox();
                    hBox.setSpacing(7);
                    hBox.setAlignment(Pos.CENTER);
                    ImageView imageView = new ImageView(unit.image);
                    imageView.setFitWidth(60);
                    imageView.setFitHeight(60);
                    Button production = new Button("Production");
                    production.setOnMouseClicked(mouseEvent -> {
                        if(GameController.getInstance().getSelectedCity().getUnderConstructionUnit() != null)
                            notification.setText("you are training a " + GameController.getInstance().getSelectedCity().getUnderConstructionUnit().name() + " right now");
                        else {
                            CityController.getInstance().setSelectedUnitType(unit);
                            notification.setText(CityController.getInstance().buildNowOrPerTurnsForUnit("per turns"));
                            updateMap();
                        }
                    });
                    Button gold = new Button("Gold");
                    gold.setOnMouseClicked(mouseEvent -> {
                        if(GameController.getInstance().getSelectedCity().getUnderConstructionUnit() != null)
                            notification.setText("you are training a " + GameController.getInstance().getSelectedCity().getUnderConstructionUnit().name() + " right now");
                        else {
                            CityController.getInstance().setSelectedUnitType(unit);
                            notification.setText(CityController.getInstance().buildNowOrPerTurnsForUnit("build now"));
                            updateMap();
                        }
                    });
                    Label label = new Label(unit.name());
                    label.setMaxWidth(50);
                    hBox.getChildren().add(imageView);
                    hBox.getChildren().add(label);
                    hBox.getChildren().add(production);
                    hBox.getChildren().add(gold);
                    vBox.getChildren().add(hBox);
                }
        for (BuildingType buildingType: CityController.getInstance().showValidBuildings()) {
            HBox hBox = new HBox();
            hBox.setSpacing(7);
            hBox.setAlignment(Pos.CENTER);
            ImageView imageView = new ImageView();
            imageView.setImage(buildingType.image);
            imageView.setFitWidth(60);
            imageView.setFitHeight(60);
            Button production = new Button("Production");
            production.setOnMouseClicked(mouseEvent -> {
                if(GameController.getInstance().getSelectedCity().getUnderConstructionBuilding() != null)
                    notification.setText("you are training a " + GameController.getInstance().getSelectedCity().getUnderConstructionBuilding().name() + " right now");
                else {
                    CityController.getInstance().setSelectedBuildingType(buildingType);
                    notification.setText(CityController.getInstance().buildNowOrPerTurns("per turns"));
                    updateMap();
                }
            });
            Button gold = new Button("Gold");
            gold.setOnMouseClicked(mouseEvent -> {
                if(GameController.getInstance().getSelectedCity().getUnderConstructionBuilding() != null)
                    notification.setText("you are training a " + GameController.getInstance().getSelectedCity().getUnderConstructionBuilding().name() + " right now");
                else {
                    CityController.getInstance().setSelectedBuildingType(buildingType);
                    notification.setText(CityController.getInstance().buildNowOrPerTurns("build now"));
                    updateMap();
                }
            });
            Label label = new Label(buildingType.name());
            hBox.getChildren().add(imageView);
            hBox.getChildren().add(label);
            hBox.getChildren().add(production);
            hBox.getChildren().add(gold);
            vBox.getChildren().add(hBox);
        }
        buildUnitsBar.setContent(vBox);
    }


    public void manageTechnologies(){
        technologies.setVisible(true);
        technologies.setDisable(false);
        for (Civilization civilization: GameMap.getInstance().getCivilizations()) {
            if(civilization.getUser().equals(LoginAndRegisterController.getInstance().getLoggedInUser())){
                VBox vBox = new VBox();
                vBox.setAlignment(Pos.CENTER);
                Label label1 = new Label("Choose your Research:");
                label1.setAlignment(Pos.CENTER);
                label1.setStyle("-fx-font-size: 22; -fx-text-fill: #000000; -fx-font-family: 'Britannic Bold'");
                vBox.getChildren().add(label1);
                for (TechnologyType technologyType: civilization.searchableTechnologiesTypes()) {
                        HBox hBox = new HBox();
                        hBox.setSpacing(7);
                        hBox.setAlignment(Pos.CENTER);
                        ImageView imageView = new ImageView(technologyType.image);
                        imageView.setFitWidth(60);
                        imageView.setFitHeight(60);
                        Button research = new Button("Research");
                        research.setOnMouseClicked(mouseEvent -> {
                            notification.setText(CivilizationController.getInstance().createTechnologyForStudy(technologyType));
                            GameController.getInstance().getPlayerTurn().addNotification(notification.getText());
                            updateMap();
                        });
                        Label label = new Label(technologyType.name());
                        hBox.getChildren().add(imageView);
                        hBox.getChildren().add(label);
                        hBox.getChildren().add(research);
                        vBox.getChildren().add(hBox);
                }
                technologies.setContent(vBox);
            }
        }
    }

    public void moveButton(){
        Polygon polygon = tileToPoly.get(GameController.getInstance().getSelectedUnit().getTile());
        availablePolys =  TileVisibilityController.getInstance().findVisibles(polyToTile.get(polygon), 0, new HashMap<>());
        for (Tile tile :availablePolys.keySet()) {
            tileToPoly.get(tile).setStroke(Paint.valueOf("Gray"));
            tileToPoly.get(tile).setEffect(new InnerShadow(75, 1, 1, Color.RED));
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
        ArrayList<Circle> circles = new ArrayList<>();
        for (Node node:pane.getChildren()) {
            if(node instanceof Circle) {
                circles.add((Circle) node);
            }
        }
        for (Circle circle:circles) {
            pane.getChildren().remove(circle);
        }
        technologies.setDisable(true);
        technologies.setVisible(false);
        buildUnitsBar.setVisible(false);
        buildUnitsBar.setDisable(true);
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
        improvementPanel.setVisible(false);
        improvementPanel.setDisable(true);
        for (Circle circle: unitImages) {
            pane.getChildren().remove(circle);
        }
        for (int i = 0; i < GameMap.getInstance().getTiles().size(); i++) {
            assignPicToRoads(GameMap.getInstance().getTiles().get(i), MapFunctions.getInstance().NonConventionalCoordinatesX(GameMap.getInstance().getTiles().get(i))
                    , MapFunctions.getInstance().NonConventionalCoordinatesY(GameMap.getInstance().getTiles().get(i)));
            assignPicToCities(GameMap.getInstance().getTiles().get(i), MapFunctions.getInstance().NonConventionalCoordinatesX(GameMap.getInstance().getTiles().get(i))
                    , MapFunctions.getInstance().NonConventionalCoordinatesY(GameMap.getInstance().getTiles().get(i)));
            assignPicToImprovements(GameMap.getInstance().getTiles().get(i), MapFunctions.getInstance().NonConventionalCoordinatesX(GameMap.getInstance().getTiles().get(i))
                    , MapFunctions.getInstance().NonConventionalCoordinatesY(GameMap.getInstance().getTiles().get(i)));
            assignPicForResources(GameMap.getInstance().getTiles().get(i), MapFunctions.getInstance().NonConventionalCoordinatesX(GameMap.getInstance().getTiles().get(i))
                    , MapFunctions.getInstance().NonConventionalCoordinatesY(GameMap.getInstance().getTiles().get(i)));
            assignImages(GameMap.getInstance().getTiles().get(i), tileToPoly.get(GameMap.getInstance().getTiles().get(i)), MapFunctions.getInstance().NonConventionalCoordinatesX(GameMap.getInstance().getTiles().get(i))
                    , MapFunctions.getInstance().NonConventionalCoordinatesY(GameMap.getInstance().getTiles().get(i)));
            assignRectangleToCities(GameMap.getInstance().getTiles().get(i), MapFunctions.getInstance().NonConventionalCoordinatesX(GameMap.getInstance().getTiles().get(i))
                    , MapFunctions.getInstance().NonConventionalCoordinatesY(GameMap.getInstance().getTiles().get(i)));
        }
    }

    private void assignPicToImprovements(Tile tile, int x, int y) {
        if(tile.getImprovement() != null && tile.getImprovement().getDaysToComplete() <=0 && !tile.getImprovement().isRuined() && !MapPrinter.getInstance().getVisibility(tile).equals(TileVisibility.FOGOFWAR)) {
            ImageView imageView = new ImageView();
            imageView.setImage(tile.getImprovement().getImprovementType().image);
            imageView.setLayoutX(x + (double) MapEnum.HEXSIDESHORT.amount * 2 / 5);
            imageView.setLayoutY(y - (double) MapEnum.HEXSIDELONG.amount * 1 / 5);
            if(tile.getImprovement().getImprovementType().equals(ImprovementType.Pasture)){
                imageView.setFitWidth(300/5);
                imageView.setFitHeight(300/5);
                imageView.setLayoutX(x + (double) MapEnum.HEXSIDESHORT.amount * 4 / 5);
                imageView.setLayoutY(y );
            }
            else if (tile.getImprovement().getImprovementType().equals(ImprovementType.Camp)){
                imageView.setFitWidth(200/5);
                imageView.setFitHeight(200/5);
                imageView.setLayoutX(x + (double) MapEnum.HEXSIDESHORT.amount * 6 / 5);
                imageView.setLayoutY(y );
            }
            else if(tile.getImprovement().getImprovementType().equals(ImprovementType.Plantation)){
                imageView.setFitWidth(500/5);
                imageView.setFitHeight(500/5);
            }
            else {
                imageView.setFitWidth(595/5);
                imageView.setFitHeight(476/5);
            }
            cityPics.add(imageView);
            pane.getChildren().add(imageView);
        }
    }

    private void assignPicToRoads(Tile tile, int x, int y) {
        if(tile.getRoad() != null && !MapPrinter.getInstance().getVisibility(tile).equals(TileVisibility.FOGOFWAR)) {
            ImageView imageView = new ImageView();
            imageView.setImage(tile.getRoad().getRoadType().image);
            imageView.setLayoutX(x + (double) MapEnum.HEXSIDESHORT.amount * 2 / 5);
            imageView.setLayoutY(y - (double) MapEnum.HEXSIDELONG.amount * 1 / 5);
            imageView.setFitWidth(595/5);
            imageView.setFitHeight(476/5);
            cityPics.add(imageView);
            pane.getChildren().add(imageView);
        }
    }

    private void assignPicToPoly(Polygon polygon) {
        Tile tile = polyToTile.get(polygon);
        Image img;
        try {
            if(MapPrinter.getInstance().getVisibility(tile).equals(TileVisibility.REVEALED)){
                polygon.setEffect(new InnerShadow(170, 1, 1, Color.BLACK));
            }
            if (MapPrinter.getInstance().getVisibility(tile).equals(TileVisibility.FOGOFWAR)) {
                polygon.setFill(new ImagePattern(Pics.cloud.image));
            }
            else {
                if (tile.getFeature() == null) {
                    img = tile.getTerrain().image;
                } else {
                    img = tile.getFeature().getFeatureType().image;
                }
                polygon.setFill(new ImagePattern(img));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void printDetails(){
//        System.out.println("-----------------------------");
//        System.out.println("selected unit info: ");
//        System.out.println("unit type : " + GameController.getInstance().getSelectedUnit().getUnitType());
//        System.out.println(GameController.getInstance().getSelectedUnit().getTile().getX());
//        System.out.println(GameController.getInstance().getSelectedUnit().getTile().getY());
//        System.out.println("movements left :" + GameController.getInstance().getSelectedUnit().getMovementsLeft());
//        System.out.println("-----------------------------");
//    }


    private void clickSettings(Polygon polygon) {
        polygon.setOnMouseClicked(mouseEvent -> {
            if (moveMode) {
//                System.out.println("im in move init");
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
                    polygon.setEffect(new InnerShadow(75, 1, 1, Color.CYAN));
                    polygon.setStrokeWidth(polygon.getStrokeWidth() * 4);
                    unitBar();
                }
            }
            polygon.requestFocus();
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
                    GameController.getInstance().getPlayerTurn().addNotification(notification.getText());
                    if(notification.getText().equals("citizen assigned successfully"))imageView.setOpacity(1);
                }
                else {
                    notification.setText(GameController.getInstance().getSelectedCity().removeCitizenFromWork(tile));
                    GameController.getInstance().getPlayerTurn().addNotification(notification.getText());
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
            tileToPoly.get(tile).setEffect(new InnerShadow(75, 1, 1, Color.YELLOW));
            imageView.setLayoutX(x + (double) MapEnum.HEXSIDESHORT.amount * 4 / 5);
            imageView.setLayoutY(y - (double) MapEnum.HEXSIDELONG.amount);
            imageView.setImage(Pics.coin.image);
            imageView.setOnMouseClicked(mouseEvent -> {
                notification.setText(GameController.getInstance().getSelectedCity().buyTile(tile));
                GameController.getInstance().getPlayerTurn().addNotification(notification.getText());
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
        if(tile.getResource() != null && !MapPrinter.getInstance().getVisibility(tile).equals(TileVisibility.FOGOFWAR) &&
                (tile.getResource().getResourceType().requiredTechnology == null || GameController.getInstance().getPlayerTurn().hasTechnology(tile.getResource().getResourceType().requiredTechnology))) {
            Circle circle = new Circle();
            circle.setCenterX(x + (double) MapEnum.HEXSIDESHORT.amount * 4 / 3);
            circle.setCenterY(y + (double) MapEnum.HEXSIDELONG.amount * 11 / 5);
            circle.setRadius(tile.getResource().getResourceType().image.getWidth() * 2 / 16);
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
                cityShow();
            });
            cityBanners.add(stack);
            pane.getChildren().add(stack);
        }
    }


    private void cityShow() {
        updateMap();
        researchBar.setVisible(false);
        researchBar.setDisable(true);
        cityButtons.setVisible(true);
        cityButtons.setDisable(false);
        if(GameController.getInstance().getSelectedCity().getCivilization().getUser().equals(LoginAndRegisterController.getInstance().getLoggedInUser()))manageCityPanel();
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
                recourseType.setText("Resource: Fogged");
                terrainType.setText("Terrain: Fogged");
                featureType.setText("Feature: Fogged");
                tileUnit.setText("CombatUnit: Fogged");
            }
            else {
                tilePolyInfo.setFill(new ImagePattern(tile.getTerrain().image));
                if(tile.getResource() != null && (tile.getResource().getResourceType().requiredTechnology == null || GameController.getInstance().getPlayerTurn().hasTechnology(tile.getResource().getResourceType().requiredTechnology)))
                    recourseType.setText("Resource: " + tile.getResource().getResourceType().name());
                else recourseType.setText("No Resource");
                terrainType.setText("Terrain: " + tile.getTerrain().name());
                if(tile.getFeature() != null)featureType.setText("Feature: " + tile.getFeature().getFeatureType().name());
                else featureType.setText("No Feature");
                if(MapPrinter.getInstance().getVisibility(tile).equals(TileVisibility.VISIBLE)){
                    if(tile.getCombatUnitOnTile() == null)tileUnit.setText("No Unit");
                    else tileUnit.setText("Combat: HP:" + tile.getCombatUnitOnTile().getHitPoints() + " _ Strength: " + tile.getCombatUnitOnTile().getUnitType().combatStrength);
                }
                else {
                    tileUnit.setText("Revealed Tile");
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
                happiness.setText(Integer.toString((int)civ.getHappiness()));
            }
        }
    }

    public void unitBar() {
        Unit selected = GameController.getInstance().getSelectedUnit();
        if (selected != null) {
            unitBar.setVisible(true);
            unitBar.setDisable(false);
            unitPic.setImage(GameController.getInstance().getSelectedUnit().getUnitType().image);
            unitLabel.setText(GameController.getInstance().getSelectedUnit().getUnitType().name());
            unitMoves.setText("Movements: " + GameController.getInstance().getSelectedUnit().getMovementsLeft());
        }
        if(selected.getUnitType().equals(UnitType.Settler))buildCity.setDisable(false);
        else buildCity.setDisable(true);
        if(selected.getUnitType().mainType.equals(MainType.NONCOMBAT))sleep.setDisable(false);
        else sleep.setDisable(true);
        if(!selected.getUnitType().mainType.equals(MainType.NONCOMBAT))alert.setDisable(false);
        else alert.setDisable(true);
        if(!selected.getUnitType().mainType.equals(MainType.NONCOMBAT))fortify.setDisable(false);
        else fortify.setDisable(true);
        if(!selected.getUnitType().mainType.equals(MainType.NONCOMBAT))heal.setDisable(false);
        else heal.setDisable(true);
        if(selected.getUnitType().mainType.equals(MainType.SIEGE))setup.setDisable(false);
        else setup.setDisable(true);
        if(selected.getUnitType().mainType.equals(MainType.NONCOMBAT))wake.setDisable(false);
        else wake.setDisable(true);
        if(selected.getUnitType().equals(UnitType.Worker))buildImprovement.setDisable(false);
        else buildImprovement.setDisable(true);
        if(selected.getUnitType().mainType.equals(MainType.RANGED) || selected.getUnitType().mainType.equals(MainType.SIEGE))rangedAttack.setDisable(false);
        else rangedAttack.setDisable(true);
        if(selected.getUnitType().mainType.equals(MainType.NONCOMBAT))upgrade.setDisable(false);
        else upgrade.setDisable(true);
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
                    researchNotif.setText(civ.getCurrentResearchProject().name());
                    researchPic.setImage(civ.getCurrentResearchProject().image);
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
        improvementPanel.setDisable(true);
        improvementPanel.setVisible(false);
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
//        System.out.println(GameController.getInstance().getPlayerTurn().getUser().getUsername());
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
    @FXML
    private void openMilitaryOverview(MouseEvent mouseEvent){
        Pane pane = new Pane();
        javafx.stage.Popup militaryOverview = new javafx.stage.Popup();
        Window window = Main.scene.getWindow();
        Label name = new Label("MILITARY OVERVIEW");
        VBox unit_supply = UnitSupply();
        VBox vBox = new VBox();
        vBox.setMinHeight(0);
        Button button = new Button("close");
        if(GameController.getInstance().getPlayerTurn().getUnits().size() != 0){
            vBox.getChildren().add(new Label("UNIT DETAILS"));
            vBox.getChildren().add(new Separator());
            for(Unit unit : GameController.getInstance().getPlayerTurn().getUnits()){
                ImageView imageView = new ImageView(unit.getUnitType().image);
                imageView.setFitHeight(60);
                imageView.setFitWidth(60);
                Label u_name = new Label("unit name : "+unit.getUnitType().name());
                Label u_status = new Label("unit status : "+unit.getUnitStateType().name());
                String u_s = "combat strength : " + unit.getUnitType().combatStrength;
                Label u_combatStrength = new Label(u_s);
                VBox vBox_details;
                if(unit instanceof Combat) {
                    Label u_hitPoint = new Label("hit point : " + ((Combat) unit).getHitPoints());
                    vBox_details = new VBox(u_name, u_combatStrength, u_status, u_hitPoint);
                } else {
                    vBox_details = new VBox(u_name, u_combatStrength, u_status);
                }
                HBox hBox_u = new HBox(imageView, vBox_details);
                vBox.getChildren().add(hBox_u);
                vBox.getChildren().add(new Separator());
            }
        }
        button.setLayoutY(vBox.getPrefHeight());
        unit_supply.getChildren().add(button);
        HBox allDetails = new HBox(unit_supply, new Separator(), vBox);
        VBox all = new VBox(name, allDetails);
        pane.getChildren().add(all);
        militaryOverview.getContent().add(pane);
        militaryOverview.show(window);
        button.setStyle(" -fx-font-family: 'Britannic Bold'; -fx-background-radius: 10;-fx-background-color: rgba(201, 238, 221, 0.7); -fx-font-size: 18 ;-fx-text-fill: #4f4e4e;");
        button.setOnMouseClicked(mouseEvent1 -> {
            militaryOverview.hide();
        });
    }

    private VBox UnitSupply(){
        Label name = new Label("UNIT SUPPLY");
        Label cities = new Label("cities \t\t\t" + GameController.getInstance().getPlayerTurn().getCities().size());
        Label civPopulation = new Label("population \t\t" + getPopulation());
        return new VBox(name, cities, civPopulation);
    }
    private int getPopulation(){
        int population = 0;
        for(City city : GameController.getInstance().getPlayerTurn().getCities())
            population += city.getPopulation();
        return population;
    }
    
    @FXML
    private void openNotificationHistory(MouseEvent mouseEvent){
        Popup popup = new Popup();
        popup.centerOnScreen();
        Window window = Main.scene.getWindow();
        Label name = new Label("NOTIFICATION HISTORY");
        name.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20; -fx-text-fill: #47a8d3");
        VBox notificationHistory = new VBox(name, new Separator());
        ArrayList<String> notifications = GameController.getInstance().getPlayerTurn().getNotification();
        for(String notification : notifications){
            Label label = new Label(notification);
            label.setStyle("-fx-font-family: Britannic Bold; -fx-font-size: 15");
            label.setFont(new Font(25));
            notificationHistory.getChildren().add(label);
            notificationHistory.getChildren().add(new Separator());
        }
        ScrollPane scrollPane = new ScrollPane(notificationHistory);
        scrollPane.setPrefHeight(200);
        scrollPane.setPrefWidth(300);
        scrollPane.setStyle("-fx-background: rgba(0,0,0,0.16); -fx-background-color: transparent ; -fx-arc-height: 35 ; -fx-arc-width: 35");
        Button button = new Button("close");
        button.setStyle("-fx-font-family: Britannic Bold;" + " -fx-background-radius: 20;" +
                " -fx-background-color: rgba(201, 238, 221, 0.7);" + " -fx-font-size: 18; "
                + "-fx-text-fill: #4f4e4e;");
        notificationHistory.getChildren().add(button);
        button.setAlignment(Pos.BOTTOM_LEFT);
        pane.setEffect(new Lighting());
        popup.getContent().add(scrollPane);
        popup.show(window);

        button.setOnMouseClicked(mouseEvent1 -> {
            pane.setEffect(null);
            popup.hide();
        });
    }

    @FXML
    private void openChatRoom(MouseEvent mouseEvent) {
        main.java.Main.changeMenu("ChatPage");
    }

    public void sleepUnit(ActionEvent actionEvent) {
        notification.setText(UnitController.getInstance().sleep());
    }

    public void alertUnit(ActionEvent actionEvent) {
        notification.setText(UnitController.getInstance().alert());
    }

    public void fortifyUnit(ActionEvent actionEvent) {
        notification.setText(UnitController.getInstance().fortify());
    }

    public void healUnit(ActionEvent actionEvent) {
        notification.setText(UnitController.getInstance().fortifyUntilHealed());
    }

    public void wakeUnit(ActionEvent actionEvent) {
        notification.setText(GameController.getInstance().wake());
    }

    public void deleteUnit(ActionEvent actionEvent) {
        notification.setText(GameController.getInstance().deleteUnit());
    }

    public void upgradeUnit(ActionEvent actionEvent) {
        
    }

    public void rangeAttackUnit(ActionEvent actionEvent) {
    }

    public void preAttack(ActionEvent actionEvent) {
    }

    public void openDiplomacyPanel(MouseEvent actionEvent) {
        Main.changeMenu(Menus.DIPLOMACY_PANEL.value);
    }

    @FXML
    private void buildRoad(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        notification.setText(UnitController.getInstance().buildRoadMatcher(button.getText()));
        updateMap();
    }

    @FXML
    private void openImprovementPanel(MouseEvent mouseEvent) {
        actionPanel.setDisable(true);
        actionPanel.setVisible(false);
        improvementPanel.setVisible(true);
        improvementPanel.setDisable(false);
    }

    @FXML
    private void buildImprovement(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        notification.setText(UnitController.getInstance().buildImprovementMatcher(button.getText()));
        updateMap();
    }

    @FXML
    private void showCheatField(ActionEvent actionEvent) {
        cheatBar.setVisible(true);
        cheatBar.setDisable(false);
        cheating = true;

    }

    @FXML
    private void cheat(ActionEvent actionEvent) {
        String input = cheatTextField.getText();
        String regex;
        Pattern pattern;
        Matcher matcher;
        if(input.matches(regex = "increase gold by (?<amount>-?\\d+)")){
            matcher = (Pattern.compile(regex)).matcher(input);
            matcher.find();
            notification.setText(CheatCode.getInstance().goldIncrease(matcher));
        }
        else if(input.matches(regex = "increase happiness by (?<amount>-?\\d+)")){
            matcher = (Pattern.compile(regex)).matcher(input);
            matcher.find();
            notification.setText(CheatCode.getInstance().happinessIncrease(matcher));
        }
        else if(input.matches(regex = "increase hitpoint by (?<amount>-?\\d+)")){
            matcher = (Pattern.compile(regex)).matcher(input);
            matcher.find();
            notification.setText(CheatCode.getInstance().increaseCityHitPoint(matcher));
        }
        else if(input.matches(regex = "increase strength by (?<amount>-?\\d+)")){
            matcher = (Pattern.compile(regex)).matcher(input);
            matcher.find();
            notification.setText(CheatCode.getInstance().increaseCityStrength(matcher));
        }
        else if(input.matches(regex = "increase turns by (?<amount>-?\\d+)")){
            matcher = (Pattern.compile(regex)).matcher(input);
            matcher.find();
            notification.setText(CheatCode.getInstance().increaseTurns(matcher));        }
        else if(input.matches(regex = "increase stored foods by (?<amount>-?\\d+)")){
            matcher = (Pattern.compile(regex)).matcher(input);
            matcher.find();
            notification.setText(CheatCode.getInstance().increaseStoredFood(matcher));        }
        else if(input.matches(regex = "unlock first half of technologies")){
            matcher = (Pattern.compile(regex)).matcher(input);
            matcher.find();
            notification.setText(CheatCode.getInstance().unlockFirstHalfTechnologies());        }
        else if(input.matches(regex = "unlock second half of technologies")){
            matcher = (Pattern.compile(regex)).matcher(input);
            matcher.find();
            notification.setText(CheatCode.getInstance().unlockSecondHalfTechnologies());
        }
        else if(input.matches(regex = "increase health of all units by (?<amount>\\d+)")){
            matcher = (Pattern.compile(regex)).matcher(input);
            matcher.find();
            notification.setText(CheatCode.getInstance().increaseHealthOfUnits(matcher));
        }
        else if(input.matches(regex ="increase xp of all units by (?<amount>\\d+)")){
            matcher = (Pattern.compile(regex)).matcher(input);
            matcher.find();
            notification.setText(CheatCode.getInstance().increaseXpOfUnits(matcher));
        }
        else
            notification.setText("wrong cheat!");
        cheating = false;
        cheatBar.setDisable(true);
        cheatBar.setVisible(false);
        updateMap();
    }
}
