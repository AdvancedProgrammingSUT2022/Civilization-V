package Model.TileRelated.Improvement;

public class Improvement {
    private ImprovementType improvementType;

    public Improvement(ImprovementType improvementType) {
        this.setImprovementType(improvementType);
    }

    public ImprovementType getImprovementType() {
        return improvementType;
    }

    public void setImprovementType(ImprovementType improvementType) {
        this.improvementType = improvementType;
    }
}
