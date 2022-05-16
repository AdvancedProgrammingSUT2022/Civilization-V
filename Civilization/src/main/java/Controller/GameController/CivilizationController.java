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
    public String chooseTechnologyMenu(){
        System.out.println(showAvailableTechnologies());
        System.out.println(showCloseResearch());
        System.out.print(showTechnologyTree());
        return"";
    }

    private String showTechnologyTree(){
        System.out.println("3 : OPEN TECHNOLOGY TREE {");
        StringBuilder output = new StringBuilder();
        int number = 1;
        for(TechnologyType technologyType : showLeadsToTechnologies()){
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

    private String showCloseResearch(){
        System.out.println("2 : CLOSE RESEARCH {");
        boolean text = GameController.getInstance().getPlayerTurn().getCurrentResearchProject() == null;
        return text ? "You are not studying any technology \n}" : "your current research project : " + GameController.getInstance().getPlayerTurn().getCurrentResearchProject().name() + "\n}";
    }
    private String showAvailableTechnologies(){
        System.out.println("1 : your available technologies : {");
        if(GameController.getInstance().getPlayerTurn().getTechnologies().size() == 0) return "You do not have any technologies\n}";
        StringBuilder output = new StringBuilder();
        int number = 1;
        for (Technology technology : GameController.getInstance().getPlayerTurn().getTechnologies()) {
            output.append(number).append(" : ").append(technology.getTechnologyType().name()).append("\n");
            number++;
        }
        output.append("}");
        return output.toString();
    }

    private ArrayList<TechnologyType> showLeadsToTechnologies(){
        ArrayList<TechnologyType> leadsToTechnologies = new ArrayList<TechnologyType>();
        for(TechnologyType technologyType : TechnologyType.values()){
            if(technologyType.PrerequisiteTechs == null){
                leadsToTechnologies.add(technologyType);
            }
        }
        for(Technology technology : GameController.getInstance().getPlayerTurn().getTechnologies()){
            if(technology.getTechnologyType().LeadsToTechs != null){
                for(TechnologyType technologyType : technology.getTechnologyType().LeadsToTechs){
                    leadsToTechnologies.add(technologyType);
                }
            }
        }
        for(Technology technology : GameController.getInstance().getPlayerTurn().getTechnologies()){
            leadsToTechnologies.removeIf(technologyType -> technology.getTechnologyType().equals(technologyType));
        }
        return leadsToTechnologies;
    }


    public String createTechnologyForStudy(Matcher matcher){
        GameController.getInstance().getPlayerTurn().addNotification("turn : " + GameController.getInstance().getTurn() + ")" + "create research project:");
        if(GameController.getInstance().getPlayerTurn().getCurrentResearchProject() != null){
            GameController.getInstance().getPlayerTurn().addNotification("you are studying a technology right now");
            return "you are studying a technology right now";
        }
        String chosenTechnologyType = matcher.group("technologyType");
        for(TechnologyType technologyType : showLeadsToTechnologies()){
            if(Objects.equals(chosenTechnologyType, technologyType.name())){
                int turn = calculateTechnologyTurns(technologyType);
                if(turn == -1) {
                    GameController.getInstance().getPlayerTurn().addNotification("you do not have enough production");
                    return "you do not have enough production";
                }
                GameController.getInstance().getPlayerTurn().setCurrentResearchProject(technologyType);
                GameController.getInstance().getPlayerTurn().setResearchTurns(turn);
                if(turn == 0) {
                    Technology technology = new Technology(GameController.getInstance().getPlayerTurn().getCurrentResearchProject());
                    GameController.getInstance().getPlayerTurn().addTechnology(technology);
                    GameController.getInstance().getPlayerTurn().setCurrentResearchProject(null);
                    GameController.getInstance().getPlayerTurn().addNotification("your research project is finished");
                    return "your research project is finished";
                } else {
                    GameController.getInstance().getPlayerTurn().addNotification("your research project is started");
                    return "your research project is started";
                }
            }
        }
        GameController.getInstance().getPlayerTurn().addNotification("not a valid technology type");
        return "not a valid technology type";
    }

    private int calculateTechnologyTurns(TechnologyType technologyType) {
        if(GameController.getInstance().getPlayerTurn().getCities() != null){
            int production = 0;
            for(City city : GameController.getInstance().getPlayerTurn().getCities()){
                production += city.getProductionPerTurn();
            }
            if(production == 0) return -1;
            return technologyType.cost / production;
        } return -1;
    }

    public String changeStudyingTechnology(Matcher matcher){
        for(TechnologyType technologyType : TechnologyType.values()){
            if(technologyType.name().equals(matcher.group("technologyType"))){
                if(GameController.getInstance().getPlayerTurn().getCurrentResearchProject() == null) return "you have no research project to change it";
                if(technologyType.name().equals(GameController.getInstance().getPlayerTurn().getCurrentResearchProject().name())) return "you already are studying this technology";
                if(GameController.getInstance().getPlayerTurn().getTechnologies() != null) {
                    for (Technology technology : GameController.getInstance().getPlayerTurn().getTechnologies()) {
                        if(technology.getTechnologyType().equals(technologyType)) return "you have this technology";
                    }
                }
                LinkedHashMap<TechnologyType, Integer> researchProjects;
                if((researchProjects = GameController.getInstance().getPlayerTurn().getResearchProjects()).size() != 0){
                   if(continueResearch(researchProjects, technologyType) != null) return continueResearch(researchProjects, technologyType);
                }
                GameController.getInstance().getPlayerTurn().addResearchProject(GameController.getInstance().getPlayerTurn().getCurrentResearchProject(), GameController.getInstance().getPlayerTurn().getResearchTurns());
                int turn = calculateTechnologyTurns(technologyType);
                GameController.getInstance().getPlayerTurn().setCurrentResearchProject(technologyType);
                if(turn == -1) return "you do not have enough production";
                GameController.getInstance().getPlayerTurn().setResearchTurns(turn);
                if(turn == 0) {
                    Technology technology = new Technology(GameController.getInstance().getPlayerTurn().getCurrentResearchProject());
                    GameController.getInstance().getPlayerTurn().addTechnology(technology);
                    GameController.getInstance().getPlayerTurn().setCurrentResearchProject(null);
                    return "your research project is finished";
                }
                else return "your research project has started";
            }
        } return "not a valid technology type";
    }

    private String continueResearch(LinkedHashMap<TechnologyType, Integer> researchProjects, TechnologyType technologyType){
        boolean hasThisResearchProject = false;
        for(Map.Entry<TechnologyType, Integer> researchProjectsEntry : researchProjects.entrySet()){
            if(technologyType.name().equals(researchProjectsEntry.getKey().name())){
                keepResearchProjects(researchProjectsEntry.getKey(), researchProjectsEntry.getValue());
                hasThisResearchProject = true;
                break;
            }
        }
        if(hasThisResearchProject) {
            GameController.getInstance().getPlayerTurn().getResearchProjects().remove(technologyType);
            return "your research project is changed";
        } return null;
    }

    private void keepResearchProjects(TechnologyType newResearchProject, int turn){
        TechnologyType previousResearchProject = GameController.getInstance().getPlayerTurn().getCurrentResearchProject();
        int preTurn = GameController.getInstance().getPlayerTurn().getResearchTurns();
        GameController.getInstance().getPlayerTurn().addResearchProject(previousResearchProject, preTurn);
        GameController.getInstance().getPlayerTurn().setResearchTurns(turn);
        GameController.getInstance().getPlayerTurn().setCurrentResearchProject(newResearchProject);
    }

    public String cancelResearchProject(){
        if(GameController.getInstance().getPlayerTurn().getCurrentResearchProject() == null) return "you do not have any research project now";
        int turn = GameController.getInstance().getPlayerTurn().getResearchTurns();
        TechnologyType technologyType = GameController.getInstance().getPlayerTurn().getCurrentResearchProject();
        GameController.getInstance().getPlayerTurn().addResearchProject(technologyType, turn);
        GameController.getInstance().getPlayerTurn().setResearchTurns(0);
        GameController.getInstance().getPlayerTurn().setCurrentResearchProject(null);
        return "your research project has stopped";
    }

    public String InfoPanel(){
        StringBuilder output = new StringBuilder();
        output.append("------------- INFO PANEL -------------\n");
        researchInfo(output);
        unitsPanel(output);
        citiesPanel(output);
        demographicPanel(output);
        militaryOverview(output);
        economicOverview(output);
        notificationHistory(output);
        return output.toString();
    }

    private void researchInfo(StringBuilder stringBuilder){
        stringBuilder.append("---RESEARCH PROJECT---\n");
        TechnologyType technologyType;
        if((technologyType = GameController.getInstance().getPlayerTurn().getCurrentResearchProject())== null) stringBuilder.append("no research project");
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

    private void unitsPanel(StringBuilder stringBuilder){
        stringBuilder.append("---UNITS LIST---\n");
        if(GameController.getInstance().getPlayerTurn().getUnits() != null){
            stringBuilder.append("NAME | STATE\n");
            for(Unit unit : GameController.getInstance().getPlayerTurn().getUnits()){
                stringBuilder.append(unit.getUnitType().name() + " | " + unit.getUnitStateType().name() + "\n");
            }
        } else {
            stringBuilder.append("no units\n");
        }
    }

    private void citiesPanel(StringBuilder stringBuilder){
        stringBuilder.append("---CITIES LIST---\n");
        if(GameController.getInstance().getPlayerTurn().getCities() != null){
            stringBuilder.append("CITIZENS | NAME | STRENGTH\n");
            for(City city : GameController.getInstance().getPlayerTurn().getCities()){
                stringBuilder.append(city.getCitizens().size() + " | " + city.getName() + " | " + city.getStrength() + "\n");
            }
        } else {
            stringBuilder.append("no cities\n");
        }
    }

    private void demographicPanel(StringBuilder stringBuilder){
        DecimalFormat df = new DecimalFormat("0.00");
        stringBuilder.append("---DEMOGRAPHIC---\n");
        double average = rankingAndAverage();
        stringBuilder.append("RANK : USERNAME | SCORE | SCIENCE | POPULATION | LAND | GOLD | CURRENT RESEARCH PROJECT | AVERAGE\n");
        ArrayList<Civilization> civilizations = GameMap.getInstance().getCivilizations();
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

    private double rankingAndAverage() {
        Comparator<Civilization> comparator = Comparator.comparing(Civilization::getUserScore);
        GameMap.getInstance().getCivilizations().sort(comparator);
        int sum = 0;
        for(Civilization civilization : GameMap.getInstance().getCivilizations()){
            sum += civilization.getUserScore();
        }
        return sum / GameMap.getInstance().getCivilizations().size();
    }

    private void notificationHistory(StringBuilder stringBuilder){
        stringBuilder.append("---NOTIFICATION HISTORY---\n");
        ArrayList<String> notifications = GameController.getInstance().getPlayerTurn().getNotification();
        if(notifications.size() == 0) stringBuilder.append("nothing\n");
        else {
            for (int i = 0; i <notifications.size() ; i++) {
                stringBuilder.append(i + 1 + ": " + notifications.get(i) + "\n");
            }
        }
    }

    private void militaryOverview(StringBuilder stringBuilder){
        stringBuilder.append("---MILITARY OVERVIEW---\n");
        if(GameController.getInstance().getPlayerTurn().getUnits() != null){
            stringBuilder.append("NUMBER: UNIT NAME\n");
            ArrayList<Unit> units = GameController.getInstance().getPlayerTurn().getUnits();
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

    private void economicOverview(StringBuilder stringBuilder){
        stringBuilder.append("---ECONOMIC OVERVIEW---\n");
        if(GameController.getInstance().getPlayerTurn().getCities() != null){
            stringBuilder.append("NUMBER : NAME | POPULATION | STRENGTH | FOOD PER TURN | PRODUCTION PER TURN | OBJECT UNDER CONSTRUCTION\n");
            ArrayList<City> cities = GameController.getInstance().getPlayerTurn().getCities();
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
