package Model.CivlizationRelated;

import java.util.ArrayList;

import Model.TileAndFeatures.Resource.Resource;

public class TradeOffer {
    private Civilization civilization;
    private int gold;
    private ArrayList<Resource> resources;
    public TradeOffer(Civilization civilization, int gold, ArrayList<Resource> resources) {
        this.civilization = civilization;
        this.gold = gold;
        this.resources = resources;
    }
    public Civilization getCivilization() {
        return civilization;
    }
    public void setCivilization(Civilization civilization) {
        this.civilization = civilization;
    }
    public int getGold() {
        return gold;
    }
    public void setGold(int gold) {
        this.gold = gold;
    }
    public ArrayList<Resource> getResources() {
        return resources;
    }
    public void setResources(ArrayList<Resource> resources) {
        this.resources = resources;
    }
    
}
