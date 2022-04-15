package Controller;

import Model.Civilization;
import Model.Terrian.Terrain;
import Model.Units.Unit;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class GameController {
    private ArrayList<Civilization> civilizations;
    private Civilization playerTurn;
    private ArrayList<Terrain> terrains;
    private Unit selectedUnit;

    public Terrain getTerrain(int x , int y){
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

    public String attack(Terrain terrain){
        return "";
    }

    public String selectUnit(){
        return "";
    }

    public String assignTerrainState(Terrain terrain){
        return "";
    }

    public String sleep(){
        return "";
    }
}
