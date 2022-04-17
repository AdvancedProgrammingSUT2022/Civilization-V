package Controller.GameController;

import Model.CivlizationRelated.Civilization;
import Model.TileRelated.Tile.Tile;
import Model.Units.Unit;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class GameController {
    private ArrayList<Civilization> civilizations;
    private Civilization playerTurn;
    private ArrayList<Tile> terrains;
    private Unit selectedUnit;

    public Tile getTerrain(int x , int y){
        return null;
    }

    public String changeTurn(){
        return "";
    }

    public String move(Matcher matcher){
        return "";
    }

    public String moveUnit(){
        return "";
    }

    public String attack(Tile terrain){
        return "";
    }

    public String selectUnit(){
        return "";
    }

    public String assignTerrainState(Tile terrain){
        return "";
    }

    public String sleep(){
        return "";
    }
    public void updateData(){

    }

    public void updateCurrentBuildingProject(){

    }

    public void updateCurrentTechnologyProject(){

    }

    public String printResearchPanel(){
        return null;
    }
}
