package Model.Technology;

import com.google.gson.annotations.Expose;

public class Technology {
    @Expose
    TechnologyType technologyType;
    public Technology(TechnologyType technologyType){
        this.technologyType = technologyType;
    }

    public TechnologyType getTechnologyType() {
        return technologyType;
    }
}
