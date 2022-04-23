package Model.Enums;

public enum MapEnum {
    LENGTH(4),
    MAPHEIGHT(14),
    MAPWIDTH(17),
    HEXSIDELONG(7),
    HEXSIDESHORT(7);
    public final int amount;

    private MapEnum(int amount) {
        this.amount = amount;
    }

}
