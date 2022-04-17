package Model.CivlizationRelated;

import java.util.ArrayList;

public class DiplomaticTie {
    private Civilization civilization;
    private int relationScale;
    private ArrayList<Trade> trades;
    public int getRelationScale() {
        return relationScale;
    }
    public ArrayList<Trade> getTrades() {
        return trades;
    }
    public void setTrades(ArrayList<Trade> trades) {
        this.trades = trades;
    }
    public Civilization getCivilization() {
        return civilization;
    }
    public void setCivilization(Civilization civilization) {
        this.civilization = civilization;
    }
    public void setRelationScale(int relationScale) {
        this.relationScale = relationScale;
    }
    
}
