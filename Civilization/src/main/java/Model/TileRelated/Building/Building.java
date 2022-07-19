package Model.TileRelated.Building;

import com.google.gson.annotations.Expose;

public class Building {
    @Expose
    private BuildingType buildingType;

    public Building(BuildingType buildingType) {
        this.setBuildingType(buildingType);
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
    }
}
