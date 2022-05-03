package Model.Technology;

public class Technology {
    TechnologyType technologyType;
    public Technology(TechnologyType technologyType){
        this.technologyType = technologyType;
    }

    public TechnologyType getTechnologyType() {
        return technologyType;
    }
}
