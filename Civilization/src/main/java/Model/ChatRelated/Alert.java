package Model.ChatRelated;

import Model.CivlizationRelated.Civilization;
import Model.CivlizationRelated.Trade;

import java.util.ArrayList;

public class Alert {
    private Civilization alertFor;

    private String message;

    public Alert() {

    }

    public Civilization getAlertFor() {
        return alertFor;
    }

    public void setAlertFor(Civilization alertFor) {
        this.alertFor = alertFor;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AlertType getAlertType() {
        return alertType;
    }

    public void setAlertType(AlertType alertType) {
        this.alertType = alertType;
    }

    public Trade getDemand() {
        return demand;
    }

    public void setDemand(Trade demand) {
        this.demand = demand;
    }

    private AlertType alertType;

    private Trade demand;
    private Runnable runnable;

    public Alert(Civilization alertFor, String message) {
        this.alertFor = alertFor;
        this.message = message;
        this.alertType = AlertType.Statement;
        AlertDataBase.getInstance().getAlerts().add(this);
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public Alert(Civilization alertFor, String message, Runnable runnable,AlertType alertType) {
        this.alertFor = alertFor;
        this.message = message;
        this.alertType = AlertType.Statement;
        AlertDataBase.getInstance().getAlerts().add(this);
        this.alertType = alertType;
        this.runnable = runnable;
    }
    public Alert(String message) {
        this.message = message;
        this.alertType = AlertType.Statement;
        AlertDataBase.getInstance().getAlerts().add(this);
    }
    public Alert(Civilization alertFor, String message, Trade demand) {
        this.alertFor = alertFor;
        this.message = message;
        this.alertType = AlertType.Request;
        this.demand = demand;
        runnable = this::acceptDemand;
        AlertDataBase.getInstance().getAlerts().add(this);
    }

    public void acceptDemand(){
        demand.makeTrade();
    }
}
