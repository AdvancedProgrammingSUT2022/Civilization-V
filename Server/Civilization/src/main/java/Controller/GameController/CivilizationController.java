package Controller.GameController;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.MapRelated.GameMap;
import Model.Technology.Technology;
import Model.Technology.TechnologyType;
import Model.Units.TypeEnums.MainType;
import Model.Units.Unit;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;

public class CivilizationController {
    private static CivilizationController CivilizationController;
    private CivilizationController(){};
    public static CivilizationController getInstance(){
        if(CivilizationController == null)
            CivilizationController = new CivilizationController();
        return CivilizationController;
    }
    public String chooseTechnologyMenu(GameMap gameMap){
        System.out.println(showAvailableTechnologies(gameMap));
        System.out.println(showCloseResearch(gameMap));
        System.out.print(showTechnologyTree(gameMap));
        return"";
    }

    private String showTechnologyTree(GameMap gameMap){
        System.out.println("3 : OPEN TECHNOLOGY TREE {");
        StringBuilder output = new StringBuilder();
        int number = 1;
        for(TechnologyType technologyType : showLeadsToTechnologies(gameMap)){
            output.append(number + ": ").append(technologyType.name() + "---> details :");
            output.append("Cost : ").append(technologyType.cost + "   ");
            output.append("Technology Main Type : ").append(technologyType.technologyMainType.name()).append("\n");
            if(technologyType.LeadsToTechs != null){
                output.append("leads to ---> {\n");
                for(int i = 0; i < technologyType.LeadsToTechs.size(); i++){
                    output.append(technologyType.LeadsToTechs.get(i).name() + "\n");
                }
                output.append("}\n");
            }
            number++;
        }
        output.append("}");
        return output.toString();
    }

    private String showCloseResearch(GameMap gameMap){
        System.out.println("2 : CLOSE RESEARCH {");
        boolean text = GameController.getInstance().getPlayerTurn(gameMap).getCurrentResearchProject() == null;
        return text ? "You are not studying any technology \n}" : "your current research project : " + GameController.getInstance().getPlayerTurn(gameMap).getCurrentResearchProject().name() + "\n}";
    }
    private String showAvailableTechnologies(GameMap gameMap){
        System.out.println("1 : your available technologies : {");
        if(GameController.getInstance().getPlayerTurn(gameMap).getTechnologies().size() == 0) return "You do not have any technologies\n}";
        StringBuilder output = new StringBuilder();
        int number = 1;
        for (Technology technology : GameController.getInstance().getPlayerTurn(gameMap).getTechnologies()) {
            output.append(number).append(" : ").append(technology.getTechnologyType().name()).append("\n");
            number++;
        }
        output.append("}");
        return output.toString();
    }

    private ArrayList<TechnologyType> showLeadsToTechnologies(GameMap gameMap){
        ArrayList<TechnologyType> leadsToTechnologies = new ArrayList<TechnologyType>();
        for(TechnologyType technologyType : TechnologyType.values()){
            if(technologyType.PrerequisiteTechs == null){
                leadsToTechnologies.add(technologyType);
            }
        }
        for(Technology technology : GameController.getInstance().getPlayerTurn(gameMap).getTechnologies()){
            if(technology.getTechnologyType().LeadsToTechs != null){
                for(TechnologyType technologyType : technology.getTechnologyType().LeadsToTechs){
                    leadsToTechnologies.add(technologyType);
                }
            }
        }
        for(Technology technology : GameController.getInstance().getPlayerTurn(gameMap).getTechnologies()){
            leadsToTechnologies.removeIf(technologyType -> technology.getTechnologyType().equals(technologyType));
        }
        return leadsToTechnologies;
    }


    public String createTechnologyForStudy(GameMap gameMap , TechnologyType technologyType){
        GameController.getInstance().getPlayerTurn(gameMap).addNotification("turn : " + GameController.getInstance().getTurn(gameMap) + ")" + "create research project:");
        if(GameController.getInstance().getPlayerTurn(gameMap).getCurrentResearchProject() != null){
            GameController.getInstance().getPlayerTurn(gameMap).addNotification("you are studying a technology right now");
            return "you are studying a technology right now";
        }
                int turn = technologyType.cost;
                if(GameController.getInstance().getPlayerTurn(gameMap).getSciencePerTurn() <= 0) {
                    GameController.getInstance().getPlayerTurn(gameMap).addNotification("your science is 0 or negative");
                    return "your science is 0 or negative";
                }
                GameController.getInstance().getPlayerTurn(gameMap).setCurrentResearchProject(technologyType);
                GameController.getInstance().getPlayerTurn(gameMap).setResearchTurns(turn);
                if(GameController.getInstance().getPlayerTurn(gameMap).getSciencePerTurn() >= turn) {
                    Technology technology = new Technology(GameController.getInstance().getPlayerTurn(gameMap).getCurrentResearchProject());
                    GameController.getInstance().getPlayerTurn(gameMap).addTechnology(technology);
                    GameController.getInstance().getPlayerTurn(gameMap).setCurrentResearchProject(null);
                    GameController.getInstance().getPlayerTurn(gameMap).addNotification("your research project is finished");
                    return "your research project is finished";
                } else {
                    GameController.getInstance().getPlayerTurn(gameMap).addNotification("your research project is started");
                    return "your research project is started";
            }

    }


    public String changeStudyingTechnology(GameMap gameMap ,TechnologyType technologyType){
                if(GameController.getInstance().getPlayerTurn(gameMap).getCurrentResearchProject() == null) return "you have no research project to change it";
                if(technologyType.name().equals(GameController.getInstance().getPlayerTurn(gameMap).getCurrentResearchProject().name())) return "you already are studying this technology";
                if(GameController.getInstance().getPlayerTurn(gameMap).getTechnologies() != null) {
                    for (Technology technology : GameController.getInstance().getPlayerTurn(gameMap).getTechnologies()) {
                        if(technology.getTechnologyType().equals(technologyType)) return "you have this technology";
                    }
                }
                LinkedHashMap<TechnologyType, Integer> researchProjects;
                if((researchProjects = GameController.getInstance().getPlayerTurn(gameMap).getResearchProjects()).size() != 0){
                   if(continueResearch(gameMap , researchProjects, technologyType) != null) return continueResearch(gameMap ,researchProjects, technologyType);
                }
                GameController.getInstance().getPlayerTurn(gameMap).addResearchProject(GameController.getInstance().getPlayerTurn(gameMap).getCurrentResearchProject(), GameController.getInstance().getPlayerTurn(gameMap).getResearchTurns());
                int turn = technologyType.cost;
                GameController.getInstance().getPlayerTurn(gameMap).setCurrentResearchProject(technologyType);
                if(GameController.getInstance().getPlayerTurn(gameMap).getSciencePerTurn() <= 0) return "your science is 0 or negative";
                GameController.getInstance().getPlayerTurn(gameMap).setResearchTurns(turn);
                if(GameController.getInstance().getPlayerTurn(gameMap).getSciencePerTurn() >= turn) {
                    Technology technology = new Technology(GameController.getInstance().getPlayerTurn(gameMap).getCurrentResearchProject());
                    GameController.getInstance().getPlayerTurn(gameMap).addTechnology(technology);
                    GameController.getInstance().getPlayerTurn(gameMap).setCurrentResearchProject(null);
                    return "your research project is finished";
                } else return "your research project has started";
    }

    private String continueResearch(GameMap gameMap , LinkedHashMap<TechnologyType, Integer> researchProjects, TechnologyType technologyType){
        boolean hasThisResearchProject = false;
        for(Map.Entry<TechnologyType, Integer> researchProjectsEntry : researchProjects.entrySet()){
            if(technologyType.name().equals(researchProjectsEntry.getKey().name())){
                keepResearchProjects(gameMap , researchProjectsEntry.getKey(), researchProjectsEntry.getValue());
                hasThisResearchProject = true;
                break;
            }
        }
        if(hasThisResearchProject) {
            GameController.getInstance().getPlayerTurn(gameMap).getResearchProjects().remove(technologyType);
            return "your research project is changed";
        } return null;
    }

    private void keepResearchProjects(GameMap gameMap ,TechnologyType newResearchProject, int turn){
        TechnologyType previousResearchProject = GameController.getInstance().getPlayerTurn(gameMap).getCurrentResearchProject();
        int preTurn = GameController.getInstance().getPlayerTurn(gameMap).getResearchTurns();
        GameController.getInstance().getPlayerTurn(gameMap).addResearchProject(previousResearchProject, preTurn);
        GameController.getInstance().getPlayerTurn(gameMap).setResearchTurns(turn);
        GameController.getInstance().getPlayerTurn(gameMap).setCurrentResearchProject(newResearchProject);
    }

    public String cancelResearchProject( Civilization civilization){
        if(civilization.getCurrentResearchProject() == null) return "you do not have any research project now";
        int turn = civilization.getResearchTurns();
        TechnologyType technologyType = civilization.getCurrentResearchProject();
        civilization.addResearchProject(technologyType, turn);
        civilization.setResearchTurns(0);
        civilization.setCurrentResearchProject(null);
        return "your research project has stopped";
    }
    // info panel ------------------------------------------------

    private void researchInfo(GameMap gameMap ,StringBuilder stringBuilder){
        stringBuilder.append("---RESEARCH PROJECT---\n");
        TechnologyType technologyType;
        if((technologyType = GameController.getInstance().getPlayerTurn(gameMap).getCurrentResearchProject())== null) stringBuilder.append("no research project");
        else {
            stringBuilder.append("name : " + technologyType.name());
            if(technologyType.LeadsToTechs != null){
                stringBuilder.append(" leads to ---> ");
                for(TechnologyType leadsToTech : technologyType.LeadsToTechs){
                    stringBuilder.append(leadsToTech.name() + " ,");
                }
            }
        }
        stringBuilder.append("\n");
    }

    private void unitsPanel(GameMap gameMap ,StringBuilder stringBuilder){
        stringBuilder.append("---UNITS LIST---\n");
        if(GameController.getInstance().getPlayerTurn(gameMap).getUnits() != null){
            stringBuilder.append("NAME | STATE\n");
            for(Unit unit : GameController.getInstance().getPlayerTurn(gameMap).getUnits()){
                stringBuilder.append(unit.getUnitType().name() + " | " + unit.getUnitStateType().name() + "\n");
            }
        } else {
            stringBuilder.append("no units\n");
        }
    }

    private void citiesPanel(GameMap gameMap ,StringBuilder stringBuilder){
        stringBuilder.append("---CITIES LIST---\n");
        if(GameController.getInstance().getPlayerTurn(gameMap).getCities() != null){
            stringBuilder.append("CITIZENS | NAME | STRENGTH\n");
            for(City city : GameController.getInstance().getPlayerTurn(gameMap).getCities()){
                stringBuilder.append(city.getCitizens().size() + " | " + city.getName() + " | " + city.getStrength() + "\n");
            }
        } else {
            stringBuilder.append("no cities\n");
        }
    }

    private void demographicPanel(GameMap gameMap ,StringBuilder stringBuilder){
        DecimalFormat df = new DecimalFormat("0.00");
        stringBuilder.append("---DEMOGRAPHIC---\n");
        double average = rankingAndAverage(gameMap);
        stringBuilder.append("RANK : USERNAME | SCORE | SCIENCE | POPULATION | LAND | GOLD | CURRENT RESEARCH PROJECT | AVERAGE\n");
        ArrayList<Civilization> civilizations = gameMap.getCivilizations();
        for(int i = 0; i < civilizations.size(); i++){
            stringBuilder.append(" " +i + 1 + " : " + civilizations.get(i).getUser().getUsername() + " | ");
            stringBuilder.append(civilizations.get(i).getUserScore() + " | ");
            stringBuilder.append(civilizations.get(i).getSciencePerTurn() + " | ");
            int population = 0;
            if(civilizations.get(i).getCities() != null){
                for(City city : civilizations.get(i).getCities()){
                    population += city.getPopulation();
                }
            }
            stringBuilder.append(population + " | ");
            stringBuilder.append(civilizations.get(i).getTiles().size() + " Square KM | ");
            stringBuilder.append(civilizations.get(i).getGold() + " | ");
            stringBuilder.append(civilizations.get(i).getCurrentResearchProject() == null ? "nothing" : civilizations.get(i).getCurrentResearchProject().name());
            stringBuilder.append(" | "+ df.format(average) + "\n");
        }
    }

    private double rankingAndAverage(GameMap gameMap) {
        Comparator<Civilization> comparator = Comparator.comparing(Civilization::getUserScore);
        gameMap.getCivilizations().sort(comparator);
        int sum = 0;
        for(Civilization civilization : gameMap.getCivilizations()){
            sum += civilization.getUserScore();
        }
        return sum / gameMap.getCivilizations().size();
    }

    private void notificationHistory(GameMap gameMap ,StringBuilder stringBuilder){
        stringBuilder.append("---NOTIFICATION HISTORY---\n");
        ArrayList<String> notifications = GameController.getInstance().getPlayerTurn(gameMap).getNotification();
        if(notifications.size() == 0) stringBuilder.append("nothing\n");
        else {
            for (int i = 0; i <notifications.size() ; i++) {
                stringBuilder.append(i + 1 + ": " + notifications.get(i) + "\n");
            }
        }
    }

    private void militaryOverview(GameMap gameMap ,StringBuilder stringBuilder){
        stringBuilder.append("---MILITARY OVERVIEW---\n");
        if(GameController.getInstance().getPlayerTurn(gameMap).getUnits() != null){
            stringBuilder.append("NUMBER: UNIT NAME\n");
            ArrayList<Unit> units = GameController.getInstance().getPlayerTurn(gameMap).getUnits();
            int count = 0;
            for (Unit unit : units) {
                if(unit.getUnitType().mainType.equals(MainType.RANGED)){
                    stringBuilder.append(" " + count + 1 + " : " + unit.getUnitType().name() +"\n");
                    count++;
                }
            }
        } else {
            stringBuilder.append("no military units\n");
        }
    }

    private void economicOverview(GameMap gameMap , StringBuilder stringBuilder){
        stringBuilder.append("---ECONOMIC OVERVIEW---\n");
        if(GameController.getInstance().getPlayerTurn(gameMap).getCities() != null){
            stringBuilder.append("NUMBER : NAME | POPULATION | STRENGTH | FOOD PER TURN | PRODUCTION PER TURN | OBJECT UNDER CONSTRUCTION\n");
            ArrayList<City> cities = GameController.getInstance().getPlayerTurn(gameMap).getCities();
            for (int i = 0; i < cities.size(); i++) {
                stringBuilder.append(i + 1 + " : " + cities.get(i).getName() + " | " + cities.get(i).getPopulation() + " | ");
                stringBuilder.append(cities.get(i).getStrength() + " | " + cities.get(i).getFoodPerTurn() + " | ");
                stringBuilder.append(cities.get(i).getProductionPerTurn() + " | ");
                if(cities.get(i).getUnderConstructionBuilding() != null){
                    stringBuilder.append(cities.get(i).getUnderConstructionBuilding().name() + "\n");
                } else if(cities.get(i).getUnderConstructionUnit() != null){
                    stringBuilder.append(cities.get(i).getUnderConstructionUnit().name() + "\n");
                } else stringBuilder.append("nothing\n");
            }
        } else {
            stringBuilder.append("no cities\n");
        }
    }
    public void calculateProducts(Civilization civilization){
        civilization.setGoldPerTurn(0);
        civilization.setSciencePerTurn(0);
        for (City city:civilization.getCities()) {
            city.calculateProduction();
            city.calculateSciencePerTurn();
            city.calculateGold();
            city.calculateFood();
            city.populationGrowthAndHunger();
            city.calculateBuildingBonuses();
            civilization.changeSciencePerTurn(city.getSciencePerTurn());
            civilization.changeGoldPerTurn(city.getGoldPerTurn());
        }
        civilization.changeGoldPerTurn(-1 * civilization.getRoadMaintenance());
        civilization.changeGold(civilization.getGoldPerTurn());
        civilization.checkGoldRunningOut();
    }
}
