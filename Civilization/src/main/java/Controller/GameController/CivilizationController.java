package Controller.GameController;

import Model.CivlizationRelated.City;
import Model.MapRelated.GameMap;
import Model.Technology.Technology;
import Model.Technology.TechnologyType;

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
            if(technologyType.leadsToTechs != null){
                output.append("leads to ---> {\n");
                for(int i = 0; i < technologyType.leadsToTechs.size(); i++){
                    output.append(technologyType.leadsToTechs.get(i).name() + "\n");
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
        boolean text = GameController.getInstance().getPlayerTurn().getCurrentStudyingTechnology() == null;
        return text ? "You are not studying any technology \n}" : "your current research project : " + GameController.getInstance().getPlayerTurn().getCurrentStudyingTechnology().name() + "\n}";
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
            if(technology.getTechnologyType().leadsToTechs != null){
                for(TechnologyType technologyType : technology.getTechnologyType().leadsToTechs){
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
        if(GameController.getInstance().getPlayerTurn().getCurrentStudyingTechnology() != null) return "you are studying a technology right now";
        String chosenTechnologyType = matcher.group("technologyType");
        for(TechnologyType technologyType : showLeadsToTechnologies()){
            if(Objects.equals(chosenTechnologyType, technologyType.name())){
                GameController.getInstance().getPlayerTurn().setCurrentStudyingTechnology(technologyType);
                int turn = calculateTechnologyTurns(technologyType);
                if(turn == -1) return "you do not have enough production";
                else {
                    GameMap.getInstance().addTechnologyToResearchingTechnologies(GameController.getInstance().getPlayerTurn(), technologyType, turn);
                    return "your research project has started";
                }
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

//    public String changeStudyingTechnology(TechnologyType technologyType){
//
//    }
//
//    private String keepScienceOfTechnology(Technology technology){
//
//    }
}
