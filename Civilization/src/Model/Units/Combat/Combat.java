package Model.Units.Combat;

import Model.Units.Unit;

public class Combat extends Unit {
    int Xp;
    int DefDmg;
    int MaxDmg;
    boolean isFortified;
    boolean isHealing;

    public void setHealing(boolean healing) {
        isHealing = healing;
    }

    public int getXp() {
        return Xp;
    }

    public void addXp(int xp){
        this.Xp += xp;
    }

    public void attack(){

    }

    public void defend(){

    }

    public void setFortified(boolean fortified) {
        isFortified = fortified;
    }

    public int DefDmg(){
        return 0;
    }

    public int calculateDmg(){
        return 0;
    }
}
