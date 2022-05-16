package View.PreGameView;

public class Regex {
    public final String register = "user create (?=.*--username (?<username>\\S+))(?=.*--password (?<password>\\S+))(?=.*--nickname (?<nickname>\\S+))";
    public final String login = "user login (?=.*--username (?<username>\\S+))(?=.*--password (?<password>\\S+))";
    public final String logout = "user logout";
    public final String changeNickName = "profile change --nickname (?<nickname>\\S+)";
    public final String showMenu = "menu show-current";
    public final String enterMenu = "menu enter (?<menu>\\S+)";
    public final String changePass = "profile change --password (?=.*--current (?<current>\\S+))(?=.*--new (?<new>\\S+))";
    public final String startGame = "^play game (?<playerData>.+)$";
    //game
    public final String printMap = "print map";
    public final String nextTurn = "next turn";
    public final String getPlayerName = "get player name";
    public final String selectUnit = "select (?<type>(combat)|(civil)) unit (?=.*--y (?<y>\\d+))(?=.*--x (?<x>\\d+))";
    public final String moveUnit = "move unit to (?=.*--y (?<destinationY>\\d+))(?=.*--x (?<destinationX>\\d+))";
    public final String info = "show info (?<type>RESEARCH|UNITS|CITIES|DIPLOMACY|VICTORY|DEMOGRAPHICS|NOTIFICATIONS|MILITARY|ECONOMIC|DIPLOMATIC|DEALS)";
    public final String selectCity = "select city ((--name (?<name>\\S+))|((?=.*--y (?<y>\\d+))(?=.*--x (?<x>\\d+))))";
    public final String showMap = "show map ((?=.*--y (?<y>\\d+))(?=.*--x (?<x>\\d+)))|(--cityName (?<cityName>\\S+))";
    public final String moveMap  = "move map (?=.*--direction (?<direction>RIGHT|LEFT|UP|DOWN))(?=.*--amount (?<amount>\\d+))";
    public final String showValidBuilding = "show valid buildings to build"; // Arash changes 1
    public final String build = "build building (?<buildingType>\\S+)"; // Arash changes 2
    public final String typeOfPayForBuilding = "building type of pay (?<typeOfPay>(now)|(per turns))"; // Arash changes 3
    public final String cancelBuilding = "cancel build building";// Arash changes 4
    public final String showValidUnits = "show valid units"; // Arash changes unit 1
    public final String buildUnit = "build unit (?<unitType>\\S+)"; // Arash changes unit 2
    public final String typeOfPayForUnit = "unit type of pay (?<typeOfPay>(now)|(per turns))"; // Arash changes unit 3
    public final String cancelBuildUnit = "cancel build unit"; // Arash changes unit 4
    public final String chooseTechnologyMenu = "show valid technologies"; // Arash changes technology 1
    public final String studyTechnology = "study (?<technologyType>\\S+)"; // ASRash changes technology 3
    public final String cancelResearchProject = "cancel research project"; // Arash changes technology 2
    public final String changeResearchProject = "change research project to (?<technologyType>\\S+)"; // ArashChanges technology 4
    public final String changeCityConstruction = "change city construction to (building type (?<buildingType>\\S+)|unit type (?<unitType>\\S+))"; // Arash changes
    public final String showInfoPanel = "show info panel";
    public final String cancelTechnology = "cancel technology"; // Arash changes technology 2
    public final String buildImprovement = "build improvement (?<ImprovementType>\\S+)";
    public final String sleep = "unit sleep";
    public final String alert = "unit alert";
    public final String fortify = "unit fortify$";
    public final String fortifyHeal = "unit fortify heal";
    public final String garrison = "unit garrison";
    public final String setup = "unit set up";
    public final String attack = "unit attack (?=.*--y (?<y>\\d+))(?=.*--x (?<x>\\d+))";
    public String cityAttack = "city attack (?=.*--y (?<y>\\d+))(?=.*--x (?<x>\\d+))";
    public final String foundCity = "unit found city (?<cityName>\\S+)";
    public final String cancel = "unit cancel";
    public final String wake = "wake unit";
    public final String delete = "delete unit";
    public final String removeObjects = "unit remove (?<object>jungle|route)";
    public final String repair = "unit repair tile";
    public final String buyTile = "buy tile (?=.*--y (?<y>\\d+))(?=.*--x (?<x>\\d+))";
    public final String assignCitizen = "assign citizen (?=.*--y (?<y>\\d+))(?=.*--x (?<x>\\d+))";
    public final String removeCitizen = "remove citizen from (?=.*--y (?<y>\\d+))(?=.*--x (?<x>\\d+))";
    public final String siegePreAttack = "do siege pre attack";
    public final String civilizationOutPut = "civilization output";
    public final String pillage = "pillage";
    public final String afterCityVictory = "(?<decision>destroy|annex)";
    //cheat code
    public final String cheatGoldIncrease = "cheat increase gold by (?<amount>-?\\d+)";
    public final String cheatHappinessIncrease = "cheat increase happiness by (?<amount>-?\\d+)";
    public final String cheatIncreaseCityHitPoint = "cheat increase hitpoint by (?<amount>-?\\d+)";
    public final String cheatIncreaseCityStrength = "cheat increase strength by (?<amount>-?\\d+)";
    public final String increaseTurns = "cheat increase turns by (?<amount>-?\\d+)";
    public final String increaseStoredFood = "cheat increase stored foods by (?<amount>-?\\d+)";
    public final String unlockFirstHalfOfTechnologies = "unlock first half of technologies";
    public final String unlockSecondHalfOfTechnologies = "unlock second half of technologies";

}
