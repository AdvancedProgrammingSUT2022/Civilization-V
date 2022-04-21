package View;

import Controller.Controller;
import View.PreGameView.Regex;

import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract public class Menu {

    static public Scanner scanner = new Scanner(System.in);
    public abstract HashMap <String, Consumer<Matcher>> createCommandsMap();


    public void run(){
        while (true){
            String input = scanner.nextLine();
            if(input.equals("menu exit")){
                break;
            }
            checkCommand(createCommandsMap(),input);
        }
    }
    public void checkCommand(HashMap <String, Consumer<Matcher>> commands , String input){
        for (String string : commands.keySet()) {
            Matcher matcher = getMatcher(input,string);
            if (matcher.find()) {
                commands.get(string).accept(matcher);
                return;
            }
        }
        System.out.println("Invalid command");
    }

    public Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}
