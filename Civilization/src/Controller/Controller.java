package Controller;

import java.util.regex.Matcher;

abstract public class Controller {
    public void exitMenu(){

    }
    public String enterMenu(Matcher matcher){
        return "";
    }

    abstract public String checkEnterMenuErrors(String menu);

    public String showCurrentMenu(){
        return "";
    }
}
