package Model.ChatRelated;

import java.util.ArrayList;

public class AlertDataBase {
    private static AlertDataBase alertDataBase;
    private AlertDataBase(){}
    public static AlertDataBase getInstance(){
        if(alertDataBase == null){
            alertDataBase = new AlertDataBase();
        }
        return alertDataBase;
    }

    public ArrayList<Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(ArrayList<Alert> alerts) {
        this.alerts = alerts;
    }

    private ArrayList<Alert> alerts = new ArrayList<>();
}
