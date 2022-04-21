package Model.CivlizationRelated;

import java.util.ArrayList;

import Model.Technology.Technology;
import Model.Units.Unit;

public abstract class Info {
    public static String printResearchPanel(Technology technology){
        return null;
    }
    public static String printUnitListPanel(ArrayList<Unit> units){
        return null;
    }
    public static String printCityListPanel(ArrayList<City> cities){
        return null;
    }
    public static String printDiplomacyInfoPanel(ArrayList<Civilization> civilizations,Civilization myCivilization){
        return null;
    }
    public static String printVictoryProgressScreen(ArrayList<Civilization> civilizations,Civilization myCivilization){
        return null;
    }
    public static String printDemographicsInfoPanel(Civilization civilization){
        return null;
    }
    public static String printMilitaryOverviewPanel(Civilization civilization){
        return null;
    }
    public static String printEconomicOverviewPanel(Civilization civilization){
        return null;
    }
    public static String printDiplomaticOverviewPanel(ArrayList<DiplomaticTie> diplomaticTies){
        return null;
    }
    public static String printDealHistoryPanel(ArrayList<Trade> trades){
        return null;
    }
    public static String printNotifHistory(ArrayList<String> notifs){
        return null;
    }
}
