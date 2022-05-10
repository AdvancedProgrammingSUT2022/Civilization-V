package Model.TileRelated.Improvement;

public class Improvement {
    private ImprovementType improvementType;
    private int daysToComplete;
    public Improvement(ImprovementType improvementType) {
        this.setImprovementType(improvementType);
    }

    public int getDaysToComplete() {
        return daysToComplete;
    }

    public void setDaysToComplete(int daysToComplete) {
        this.daysToComplete = daysToComplete;
    }

    public ImprovementType getImprovementType() {
        return improvementType;
    }

    public void setImprovementType(ImprovementType improvementType) {
        this.improvementType = improvementType;
    }
}
