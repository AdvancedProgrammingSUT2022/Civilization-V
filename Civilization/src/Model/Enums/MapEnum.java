package Model.Enums;

public enum MapEnum {
    LENGTH(4),
    MAPHEIGHT(3),
    MAPWIDTH(5),
    HEXSIDELONG(5),
    HEXSIDESHORT(3);
    public final int amount;
    private MapEnum(int amount) {
        this.amount = amount;
    }

}  
