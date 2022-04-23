package Model.Units.TypeEnums;

import Model.Technology.TechnologyType;
import Model.TileRelated.Resource.ResourceType;

public enum UnitType {
    Archer(70, MainType.RANGED, CombatType.Archery,  4, 6, 2, 2 ,null, TechnologyType.Archery),
    ChariotArcher(60, MainType.RANGED, CombatType.Mounted, 3, 6, 2, 4 , ResourceType.Horses, TechnologyType.TheWheel),
    Scout(25, MainType.NONRANGED, CombatType.Recon, 4 , 0, 0, 2, null, null),
    Settler(89, MainType.NONCOMBAT, CombatType.Civilian, 0, 0, 0, 2, null, null),
    Spearman(50, MainType.NONRANGED, CombatType.Melee, 7, 0, 0, 2, null, TechnologyType.BronzeWorking),
    Warrior(40, MainType.NONRANGED, CombatType.Melee, 6, 0 ,0 ,2, null, null),
    Worker(70, MainType.NONCOMBAT, CombatType.Civilian, 0, 0, 0, 2, null, null),
    Catapult(100, MainType.SIEGE, CombatType.Siege, 4, 14, 2, 2, ResourceType.Iron, TechnologyType.Mathematics),
    Horseman(80, MainType.NONRANGED, CombatType.Mounted, 12, 0, 0, 4, ResourceType.Horses, TechnologyType.HorsebackRiding),
    Swordsman(80, MainType.NONRANGED, CombatType.Melee, 11, 0 , 0, 2, ResourceType.Iron, TechnologyType.IronWorking),
    Crossbowman(120, MainType.RANGED, CombatType.Archery, 6, 12, 2, 2, null, TechnologyType.Machinery),
    Knight(150, MainType.NONRANGED, CombatType.Mounted, 18, 0, 0, 3,  ResourceType.Horses, TechnologyType.Chivalry),
    longswordsman(150, MainType.NONRANGED, CombatType.Melee, 18, 0, 0, 3, ResourceType.Iron, TechnologyType.Steel),
    Pikeman(100, MainType.NONRANGED, CombatType.Melee, 10, 0, 0, 2, null, TechnologyType.CivilService),
    Trebuchet(170, MainType.SIEGE, CombatType.Siege, 6, 20, 2, 2, ResourceType.Iron, TechnologyType.Physics),
    Canon(250, MainType.SIEGE, CombatType.Siege, 10, 26, 2, 2, null, TechnologyType.Chemistry),
    Cavalry(260, MainType.NONRANGED, CombatType.Mounted, 25, 0, 0, 3, ResourceType.Horses, TechnologyType.MilitaryScience),
    Lancer(220, MainType.NONRANGED, CombatType.Mounted, 22, 0, 0, 4,ResourceType.Horses, TechnologyType.Metallurgy),
    MusketMan(120, MainType.RANGED, CombatType.Gunpowder, 16, 0, 0, 2, null, TechnologyType.Gunpowder),
    Rifleman(200, MainType.RANGED, CombatType.Gunpowder, 25, 0, 0, 2, null, TechnologyType.Rifling),
    AntiTankGun(300, MainType.RANGED, CombatType.Gunpowder, 32, 0, 0, 2, null, TechnologyType.ReplaceableParts),
    Artillery(420, MainType.SIEGE, CombatType.Siege, 16, 32, 3, 2, null, TechnologyType.Dynamite),
    Infantry(300, MainType.RANGED, CombatType.Gunpowder, 36, 0, 0, 2, null, TechnologyType.ReplaceableParts),
    Panzer(450, MainType.NONRANGED, CombatType.Armored, 60, 0, 5, TechnologyType.Combustion),
    Tank(450, MainType.NONRANGED, CombatType.Armored, 50, 0, 4, TechnologyType.Combustion);
    public final int cost;
    public final MainType mainType;
    public final CombatType combatType;
    public final int combatStrength;
    public int rangedCombatStrength;
    public final int range;
    public final int movement;
    public ResourceType resourcesRequired;
    public final TechnologyType technologyRequired;


    UnitType(int cost, MainType mainType, CombatType combatType, int combatStrength, int rangedCombatStrength, int range, int movement, ResourceType resourcesRequired, TechnologyType technologyRequired) {
        this.cost = cost;
        this.mainType = mainType;
        this.combatType = combatType;
        this.combatStrength = combatStrength;
        this.rangedCombatStrength = rangedCombatStrength;
        this.range = range;
        this.movement = movement;
        this.resourcesRequired = resourcesRequired;
        this.technologyRequired = technologyRequired;
    }

    UnitType(int cost, MainType mainType, CombatType combatType, int combatStrength, int range, int movement, TechnologyType technologyRequired) {
        this.cost = cost;
        this.mainType = mainType;
        this.combatType = combatType;
        this.combatStrength = combatStrength;
        this.range = range;
        this.movement = movement;
        this.technologyRequired = technologyRequired;
    }


}

