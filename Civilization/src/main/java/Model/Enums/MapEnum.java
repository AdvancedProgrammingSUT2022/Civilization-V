package Model.Enums;

public enum MapEnum {
    LENGTH(4),
    MAPHEIGHT(5),
    MAPWIDTH(10),
    HEXSIDELONG(9),
    HEXSIDESHORT(7);
    public final int amount;

    private MapEnum(int amount) {
        this.amount = amount;
    }

}
