package Controller.GameController;

import Model.CivlizationRelated.City;
import Model.Technology.Technology;
import Model.Technology.TechnologyType;
import Model.Units.Unit;

import java.util.ArrayList;
import java.util.Objects;
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
        System.out.println(showTechnologyTree());
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
            for(TechnologyType technologyType : leadsToTechnologies){
                if(technology.getTechnologyType().equals(technologyType)){
                    leadsToTechnologies.remove(technologyType);
                }
            }
        }
        return leadsToTechnologies;
    }

    public String createTechnologyForStudy(Matcher matcher){
        if(GameController.getInstance().getPlayerTurn().getCurrentResearchProject() != null) return "you are studying a technology right now";
        String chosenTechnologyType = matcher.group("technologyType");
        for(TechnologyType technologyType : showLeadsToTechnologies()){
            if(Objects.equals(chosenTechnologyType, technologyType.name())){
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
        }
        return "not a valid technology type";
    }

    private int calculateTechnologyTurns(TechnologyType technologyType) {
        if(GameController.getInstance().getPlayerTurn().getCities() != null){
            int production = 0;
            for(City city : GameController.getInstance().getPlayerTurn().getCities()){
                production += city.getProductionPerTurn();
            }
            if(technologyType.cost > production) return -1;
            else return technologyType.cost / production;
        } return -1;
    }

    public String changeStudyingTechnology(Matcher matcher){
        for(TechnologyType technologyType : TechnologyType.values()){
            if(technologyType.name().equals(matcher.group("technologyType"))){
                if(GameController.getInstance().getPlayerTurn().getCurrentResearchProject() == null) return "you have no research project to change it";
                if(technologyType.name().equals(GameController.getInstance().getPlayerTurn().getCurrentResearchProject().name())) return "you already are studying this technology";
                // if have that changing type or not
                // keep scinece and turn of the previous technology
            }
        } return "not a valid technology type";

    }

    private String keepScienceOfTechnology(Technology technology){

    }


    public String Info(){
        StringBuilder output = new StringBuilder();
        output.append("------------- INFO PANEL -------------\n");
        researchInfo(output);
        unitsPanel(output);
        citiesPanel(output);
        // demographic panel
        // notification history
        // military overview
        // economic overview
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
        } stringBuilder.append("no units\n");
    }

    private void citiesPanel(StringBuilder stringBuilder){
        stringBuilder.append("---CITIES LIST---\n");
        if(GameController.getInstance().getPlayerTurn().getCities() != null){
            stringBuilder.append("CITIZENS | NAME | STRENGTH\n");
            for(City city : GameController.getInstance().getPlayerTurn().getCities()){
                stringBuilder.append(city.getCitizens().size() + " | " + city.getName() + " | " + city.getStrength() + "\n");
            }
        } stringBuilder.append("no cities\n");
    }

    private void demographicPanel(StringBuilder stringBuilder){



    }

}
