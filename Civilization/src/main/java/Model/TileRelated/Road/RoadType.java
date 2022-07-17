package Model.TileRelated.Road;
import Model.Technology.TechnologyType;
import javafx.scene.image.Image;

public enum RoadType {
    Road(0.6,TechnologyType.TheWheel, new Image("/images/improvements/Road.png")),
    RailWay(0.8,TechnologyType.Railroad , new Image("/images/improvements/Railway.png"));

    public final double mpReduction;
    public final TechnologyType PrerequisiteTechnology;
    public Image image;

    RoadType(double mpReduction , TechnologyType prerequisiteTechnology , Image image){
        this.mpReduction = mpReduction;
        this.PrerequisiteTechnology = prerequisiteTechnology;
        this.image = image;
    }
}
