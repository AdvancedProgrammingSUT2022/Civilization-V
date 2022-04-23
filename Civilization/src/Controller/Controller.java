package Controller;

import java.util.regex.Matcher;

abstract public class Controller {
    abstract public String enterMenu(Matcher matcher);

    abstract public String showCurrentMenu();
}
