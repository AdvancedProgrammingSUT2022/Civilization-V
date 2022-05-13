package Controller.GameController.MapControllers;

import Controller.GameController.GameController;
import Model.MapRelated.GameMap;

import java.util.regex.Matcher;

public class CheatCode {
    private static CheatCode cheatCode;
    public static CheatCode getInstance(){
        if(cheatCode == null)
            cheatCode = new CheatCode();
        return cheatCode;
    }
    public String goldIncrease (Matcher matcher){
        GameController.getInstance().getPlayerTurn().changeGold(Integer.parseInt(matcher.group("amount")));
        return "done";
    }
    public String happinessIncrease (Matcher matcher){
        GameController.getInstance().getPlayerTurn().changeHappiness(Integer.parseInt(matcher.group("amount")));
        return "done";
    }
    public String increaseCityHitPoint (Matcher matcher){
        if(GameController.getInstance().getSelectedCity() == null)return "no city is selected";
        GameController.getInstance().getSelectedCity().changeHitPoint(Integer.parseInt(matcher.group("amount")));
        return "done";
    }
    public String increaseCityStrength (Matcher matcher){
        if(GameController.getInstance().getSelectedCity() == null)return "no city is selected";
        GameController.getInstance().getSelectedCity().changeStrength(Integer.parseInt(matcher.group("amount")));
        return "done";
    }
    public String increaseTurns (Matcher matcher){
        for (int i = 0; i < GameMap.getInstance().getCivilizations().size() * Integer.parseInt(matcher.group("amount")); i++)GameController.getInstance().nextTurn();
        return "done";
    }
    public String increaseStoredFood (Matcher matcher){
        if(GameController.getInstance().getSelectedCity() == null)return "no city is selected";
        GameController.getInstance().getSelectedCity().changeStoredFood(Integer.parseInt(matcher.group("amount")));
        return "done";
    }
}
