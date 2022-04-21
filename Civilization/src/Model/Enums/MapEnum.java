package Model.Enums;

public enum MapEnum {
    LENGTH(4),
    MAPHEIGHT(22),
    MAPWIDTH(52),
    HEXSIDELONG(5),
    HEXSIDESHORT(3);
    public final int amount;
    private MapEnum(int amount) {
        this.amount = amount;
    }

}
