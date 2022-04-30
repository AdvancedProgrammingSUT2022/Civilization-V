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
    public final String build = "build (?<buildingType>\\S+)"; // Arash changes 2
    public final String nowOrPerTurns = "(?<TypeOfPay>(now)|(per turns))"; // Arash changes 3
    public final String cancelBuilding = "cancel building";// Arash changes 4
    public final String sleep = "unit sleep";
    public final String alert = "unit alert";
    public final String fortify = "unit fortify$";
    public final String fortifyHeal = "unit fortify heal";
    public final String garrison = "unit garrison";
    public final String setup = "unit set up";
    public final String attack = "unit attack (?=.*--y (?<y>\\d+))(?=.*--x (?<x>\\d+))";
    public final String foundCity = "unit found city";
    public final String cancel = "unit cancel";
    public final String wake = "wake unit";
    public final String delete = "delete unit";
    public final String removeObjects = "unit remove (?<object>jungle|route)";
    public final String repair = "unit repair tile";
    public final String buyTile = "buy tile (?=.*--y (?<y>\\d+))(?=.*--x (?<x>\\d+))";
}
