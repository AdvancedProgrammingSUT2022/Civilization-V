package View.GraphicViewController;

import java.util.ArrayList;

public class CalledMethods {
    private ArrayList<String> methodsName = new ArrayList<>();
    public CalledMethods(){

    }

    private static CalledMethods calledMethods;
    public static CalledMethods getInstance(){
        if(calledMethods == null)
            calledMethods = new CalledMethods();
        return calledMethods;
    }

    public ArrayList<String> getMethodsName() {
        return methodsName;
    }
}