package Controller.GameController;

import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.Enums.MapEnum;
import Model.MapRelated.GameMap;
import Model.Movement.Graph;
import Model.TileRelated.Feature.Feature;
import Model.TileRelated.Feature.FeatureType;
import Model.TileRelated.Feature.River;
import Model.TileRelated.Resource.Resource;
import Model.TileRelated.Terraine.TerrainType;
import Model.TileRelated.Tile.Tile;
import Model.Units.TypeEnums.MainType;
import Model.Units.TypeEnums.UnitType;
import Model.User.User;
import Model.Units.Unit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Controller.GameController.MapControllers.MapGenerator;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;

public class GameController{
    private Civilization playerTurn;
    private Unit selectedUnit;
    private static GameController gameController; 
    private GameMap map = GameMap.getInstance();
    private GameController(){}
    public static GameController getInstance(){
        if(gameController == null)
            gameController = new GameController();
        return gameController;
    }
    public Civilization getPlayerTurn() {
        return playerTurn;
    }
    public void setPlayerTurn(Civilization playerTurn) {
        this.playerTurn = playerTurn;
    }
    public GameMap getMap(){
        return this.map;
    }
    public Unit getSelectedUnit(){
        return selectedUnit;
    }
    public void setSelectedUnit(Unit selectedUnit){
        this.selectedUnit = selectedUnit;
    }
    public String nextTurn(){
        changePlayer();
        restoreMovementLefts();
        return "next player turn!";
    }
    private void changePlayer(){
        int turnIndex = this.map.getCivilizations().indexOf(playerTurn);
        if(turnIndex == this.map.getCivilizations().size() - 1){turnIndex = 0;}
        else turnIndex++;
        playerTurn = this.map.getCivilizations().get(turnIndex);
    }
    private void restoreMovementLefts(){
        UnitController.getInstance().restoreUnitMovementLeft();
    }
    public String printMap(){
        return this.map.printMap();
    }
    public void gameInit(ArrayList<User> players) {
        MapGenerator.getInstance().gameInit(players);
    }
    public String selectUnit(Matcher matcher) {
        return UnitController.getInstance().selectUnit(matcher);
    }
    public String initMoveUnit(Matcher matcher) {
        return UnitController.getInstance().initMoveUnit(matcher); 
    }

}
