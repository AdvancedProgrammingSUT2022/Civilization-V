package Controller.GameController.MapControllers;
import Controller.GameController.GameController;
import Model.CivlizationRelated.Civilization;
import Model.MapRelated.GameMap;
import Model.Technology.Technology;
import Model.Technology.TechnologyType;
import Model.Units.Combat.Combat;
import Model.Units.Unit;
import java.util.regex.Matcher;

public class CheatCode {
    private static CheatCode cheatCode;
    public static CheatCode getInstance(){
        if(cheatCode == null)
            cheatCode = new CheatCode();
        return cheatCode;
    }
    public String goldIncrease (GameMap gameMap , Matcher matcher){
        GameController.getInstance().getPlayerTurn(gameMap).changeGold(Integer.parseInt(matcher.group("amount")));
        return "done";
    }
    public String happinessIncrease (GameMap gameMap , Matcher matcher){
        GameController.getInstance().getPlayerTurn(gameMap).changeHappiness(Integer.parseInt(matcher.group("amount")));
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
    public String increaseTurns (GameMap gameMap , Matcher matcher){
        for (int i = 0; i < gameMap.getCivilizations().size() * Integer.parseInt(matcher.group("amount")); i++)GameController.getInstance().nextTurn(gameMap);
        return "done";
    }
    public String increaseStoredFood (Matcher matcher){
        if(GameController.getInstance().getSelectedCity() == null)return "no city is selected";
        GameController.getInstance().getSelectedCity().changeStoredFood(Integer.parseInt(matcher.group("amount")));
        return "done";
    }

    public String increaseHealthOfUnits(GameMap gameMap ,Matcher matcher){
        int amount = Integer.parseInt(matcher.group("amount"));
        for (Unit unit:GameController.getInstance().getPlayerTurn(gameMap).getUnits()) {
            if(unit instanceof Combat){
                ((Combat)unit).changeHitPoint(amount);
            }
        }
        return "cheating is bad but ok";
    }

    public String increaseXpOfUnits(GameMap gameMap ,Matcher matcher){
        int amount = Integer.parseInt(matcher.group("amount"));
        for (Unit unit:GameController.getInstance().getPlayerTurn(gameMap).getUnits()) {
            if(unit instanceof Combat){
                ((Combat)unit).addXp(amount);
            }
        }
        return "cheating is bad but ok";
    }

    public String increaseIron(GameMap gameMap , Matcher matcher){
        int amount = Integer.parseInt(matcher.group("amount"));
        GameController.getInstance().getPlayerTurn(gameMap).changeTotalIron(amount);
        GameController.getInstance().getPlayerTurn(gameMap).changeCurrentIron(amount);
        return "done!";
    }

    public String unlockFirstHalfTechnologies(GameMap gameMap){
        Civilization player = GameController.getInstance().getPlayerTurn(gameMap);
        player.addTechnology(new Technology(TechnologyType.Agriculture));
        player.addTechnology(new Technology(TechnologyType.AnimalHusbandry));
        player.addTechnology(new Technology(TechnologyType.Masonry));
        player.addTechnology(new Technology(TechnologyType.Mining));
        player.addTechnology(new Technology(TechnologyType.Pottery));
        player.addTechnology(new Technology(TechnologyType.Trapping));
        player.addTechnology(new Technology(TechnologyType.Archery));
        player.addTechnology(new Technology(TechnologyType.TheWheel));
        player.addTechnology(new Technology(TechnologyType.BronzeWorking));
        player.addTechnology(new Technology(TechnologyType.Writing));
        player.addTechnology(new Technology(TechnologyType.Calendar));
        player.addTechnology(new Technology(TechnologyType.Construction));
        player.addTechnology(new Technology(TechnologyType.HorsebackRiding));
        player.addTechnology(new Technology(TechnologyType.IronWorking));
        player.addTechnology(new Technology(TechnologyType.Mathematics));
        player.addTechnology(new Technology(TechnologyType.Philosophy));
        player.addTechnology(new Technology(TechnologyType.Chivalry));
        player.addTechnology(new Technology(TechnologyType.CivilService));
        player.addTechnology(new Technology(TechnologyType.Currency));
        player.addTechnology(new Technology(TechnologyType.Education));
        player.addTechnology(new Technology(TechnologyType.Engineering));
        return "done";
    }

    public String unlockSecondHalfTechnologies(GameMap gameMap){
        Civilization player = GameController.getInstance().getPlayerTurn(gameMap);
        player.addTechnology(new Technology(TechnologyType.Machinery));
        player.addTechnology(new Technology(TechnologyType.MetalCasting));
        player.addTechnology(new Technology(TechnologyType.Physics));
        player.addTechnology(new Technology(TechnologyType.Steel));
        player.addTechnology(new Technology(TechnologyType.Theology));
        player.addTechnology(new Technology(TechnologyType.Acoustics));
        player.addTechnology(new Technology(TechnologyType.Archaeology));
        player.addTechnology(new Technology(TechnologyType.Banking));
        player.addTechnology(new Technology(TechnologyType.Chemistry));
        player.addTechnology(new Technology(TechnologyType.Economics));
        player.addTechnology(new Technology(TechnologyType.Fertilizer));
        player.addTechnology(new Technology(TechnologyType.Gunpowder));
        player.addTechnology(new Technology(TechnologyType.Metallurgy));
        player.addTechnology(new Technology(TechnologyType.IronWorking));
        player.addTechnology(new Technology(TechnologyType.MilitaryScience));
        player.addTechnology(new Technology(TechnologyType.PrintingPress));
        player.addTechnology(new Technology(TechnologyType.Rifling));
        player.addTechnology(new Technology(TechnologyType.ScientificTheory));
        player.addTechnology(new Technology(TechnologyType.Biology));
        player.addTechnology(new Technology(TechnologyType.Combustion));
        player.addTechnology(new Technology(TechnologyType.Dynamite));
        player.addTechnology(new Technology(TechnologyType.Electricity));
        player.addTechnology(new Technology(TechnologyType.Radio));
        player.addTechnology(new Technology(TechnologyType.Railroad));
        player.addTechnology(new Technology(TechnologyType.ReplaceableParts));
        player.addTechnology(new Technology(TechnologyType.SteamPower));
        player.addTechnology(new Technology(TechnologyType.Telegraph));
        return "done";
    }
}
