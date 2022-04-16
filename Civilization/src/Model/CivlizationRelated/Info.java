package Model.CivlizationRelated;

import java.util.ArrayList;

import Model.Technology.Technology;
import Model.Units.Unit;

public abstract class Info {
    public String printResearchPanel(Technology technology){
        return null;
    }
    public String printUnitListPanel(ArrayList<Unit> units){
        return null;
    }
    public String printCityListPanel(ArrayList<City> cities){
        return null;
    }
    public String printDiplomacyInfoPanel(ArrayList<Civilization> civilizations,Civilization myCivilization){
        return null;
    }
    public String printVictoryProgressScreen(ArrayList<Civilization> civilizations,Civilization myCivilization){
        return null;
    }
    public String printDemographicsInfoPanel(Civilization civilization){
        return null;
    }
    public String printMillitaryOverviewPanel(Civilization civilization){
        return null;
    }
    public String printEconomicOverviewPanel(Civilization civilization){
        return null;
    }
    public String printDiplomaticOverviewPanel(ArrayList<DiplomaticTie> diplomaticTies){
        return null;
    }
    public String printDealHistoryPanel(ArrayList<Trade> trades){
        return null;
    }
    public String printNotifHistory(ArrayList<String> notifs){
        return null;
    }
}
