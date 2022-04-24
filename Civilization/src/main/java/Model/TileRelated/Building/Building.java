package Model.TileRelated.Building;

public class Building {
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
