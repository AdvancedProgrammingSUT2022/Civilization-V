package Model.TileRelated.Terraine;

public class Terrain {
    private TerrainType terrainType;
    public Terrain(TerrainType terrainType){
        this.setTerrainType(terrainType);
    }
    public TerrainType getTerrainType() {
        return terrainType;
    }
    public void setTerrainType(TerrainType terrainType) {
        this.terrainType = terrainType;
    }

}
