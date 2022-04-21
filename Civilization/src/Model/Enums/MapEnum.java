package Model.Enums;

public enum MapEnum {
    LENGTH(4),
    MAPHEIGHT(3),
    MAPWIDTH(5),
    HEXSIDELONG(7),
    HEXSIDESHORT(5);
    public final int amount;
    private MapEnum(int amount) {
        this.amount = amount;
    }

}  
