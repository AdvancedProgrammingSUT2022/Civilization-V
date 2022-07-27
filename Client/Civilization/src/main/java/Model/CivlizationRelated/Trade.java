package Model.CivlizationRelated;

import Model.NetworkRelated.Request;
import Model.TileRelated.Resource.ResourceType;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

public class Trade {
    @Expose
    TradeOffer firstCivilization;
    @Expose
    TradeOffer secondCivilization;

    boolean isDemand;

    public Trade(TradeOffer firstCivilization, TradeOffer secondCivilization,boolean isDemand) {
        this.firstCivilization = firstCivilization;
        this.secondCivilization = secondCivilization;
        this.isDemand = isDemand;
    }

    public void makeTrade(){
        tradeGold();
        tradeResources();
    }

    private void tradeResources() {
        if(!isDemand)
            for(ResourceType resourceType: firstCivilization.getResources().keySet()){
                secondCivilization.getCivilization().getLuxuryResourceCount().replace(resourceType,secondCivilization.getCivilization().getLuxuryResourceCount().get(resourceType) + firstCivilization.getResources().get(resourceType));
                firstCivilization.getCivilization().getLuxuryResourceCount().replace(resourceType,firstCivilization.getCivilization().getLuxuryResourceCount().get(resourceType) - firstCivilization.getResources().get(resourceType));
            }
        for(ResourceType resourceType: secondCivilization.getResources().keySet()){
            firstCivilization.getCivilization().getLuxuryResourceCount().replace(resourceType,firstCivilization.getCivilization().getLuxuryResourceCount().get(resourceType) + secondCivilization.getResources().get(resourceType));
            secondCivilization.getCivilization().getLuxuryResourceCount().replace(resourceType,secondCivilization.getCivilization().getLuxuryResourceCount().get(resourceType) - secondCivilization.getResources().get(resourceType));
        }
    }

    private void tradeGold() {
        firstCivilization.getCivilization().setGold(firstCivilization.getCivilization().getGold() + secondCivilization.getGold());
        secondCivilization.getCivilization().setGold(secondCivilization.getCivilization().getGold() - secondCivilization.getGold());
        if(!isDemand) {
            secondCivilization.getCivilization().setGold(secondCivilization.getCivilization().getGold() + firstCivilization.getGold());
            firstCivilization.getCivilization().setGold(firstCivilization.getCivilization().getGold() - firstCivilization.getGold());
        }
    }

    @Override
    public String toString() {
        String output = "";
        if(isDemand) {
            output += firstCivilization.getCivilization().getUser().getNickname() + " is demanding that you give " + secondCivilization.getGold() + " gold and ";
            for (ResourceType resourcetype: firstCivilization.getResources().keySet()) {
                output +=  resourcetype.name() + " : " +  firstCivilization.getResources().get(resourcetype) + " and ";
            }
        }
        return output;
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Trade fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Trade.class);
    }
}
