package Model.CivlizationRelated;

import java.util.ArrayList;
import java.util.HashMap;

import Model.TileRelated.Resource.Resource;
import Model.TileRelated.Resource.ResourceType;

public class TradeOffer {
    private Civilization civilization;
    private int gold;

    private HashMap<ResourceType,Integer> resources = new HashMap<>();

    public TradeOffer() {
    }

    public HashMap<ResourceType, Integer> getResources() {
        return resources;
    }

    public void setResources(HashMap<ResourceType, Integer> resources) {
        this.resources = resources;
    }

    public TradeOffer(Civilization civilization, int gold) {
        this.civilization = civilization;
        this.gold = gold;

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
    
}
