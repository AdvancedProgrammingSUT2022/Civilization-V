package Model.Enums;

public enum MapEnum {
    LENGTH(4),
//    MAPHEIGHT(8),
//    MAPWIDTH(16),
    HEXSIDELONG(45),
    HEXSIDESHORT(60),
    NAVIGATIONSPEED(5);
    public final int amount;

    private MapEnum(int amount) {
        this.amount = amount;
    }
}
