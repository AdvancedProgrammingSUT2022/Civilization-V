package View.PreGameView;

public class Regex {
    public final String register = "user create (?=.*--username (?<username>\\S+))(?=.*--password (?<password>\\S+))(?=.*--nickname (?<nickname>\\S+))";
    public final String login = "user login (?=.*--username (?<username>\\S+))(?=.*--password (?<password>\\S+))";
    public final String logout = "user logout";
    public final String changeNickName = "profile change --nickname (?<nickname>\\S+)";
    public final String showMenu = "menu show-current";
    public final String enterMenu = "menu enter (?<menu>\\S+)";
    public final String changePass = "profile change --password (?=.*--current (?<current>\\S+))(?=.*--new (?<new>\\S+))";
}
