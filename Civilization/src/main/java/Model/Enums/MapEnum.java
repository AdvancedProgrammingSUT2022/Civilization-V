package Model.Enums;

public enum MapEnum {
    LENGTH(4),
    MAPHEIGHT(5),
    MAPWIDTH(6),
    HEXSIDELONG(11),
    HEXSIDESHORT(9);
    public final int amount;

    private MapEnum(int amount) {
        this.amount = amount;
    }

}
