package Model.Units;

import Model.Resource.ResourceType;
import Model.Technology.TechnologyType;

public enum UnitType {
    Archer(70, CombatType.Archery,  4, 6, 2, 2 ,null, TechnologyType.Archery),
    ChariotArcher(60, CombatType.Mounted, 3, 6, 2, 4 , ResourceType.Horses, TechnologyType.TheWheel),
    Scout(25, CombatType.Recon, 4 , 0, 0, 2, null, null),
    Settler(89, CombatType.Civilian, 0, 0, 0, 2, null, null),
    Spearman(50, CombatType.Melee, 7, 0, 0, 2, null, TechnologyType.BronzeWorking),
    Warrior(40, CombatType.Melee, 6, 0 ,0 ,2, null, null),
    Worker(70, CombatType.Civilian, 0, 0, 0, 2, null, null),
    Catapult(100, CombatType.Siege, 4, 14, 2, 2, ResourceType.Iron, TechnologyType.Mathematics),
    Horseman(80, CombatType.Mounted, 12, 0, 0, 4, ResourceType.Horses, TechnologyType.HorsebackRiding),
    Swordsman(80, CombatType.Melee, 11, 0 , 0, 2, ResourceType.Iron, TechnologyType.IronWorking),
    Crossbowman(120, CombatType.Archery, 6, 12, 2, 2, null, TechnologyType.Machinery),
    Knight(150, CombatType.Mounted, 18, 0, 0, 3,  ResourceType.Horses, TechnologyType.Chivalry),
    longswordsman(150, CombatType.Melee, 18, 0, 0, 3, ResourceType.Iron, TechnologyType.Steel),
    Pikeman(100, CombatType.Melee, 10, 0, 0, 2, null, TechnologyType.CivilService),
    Trebuchet(170, CombatType.Siege, 6, 20, 2, 2, ResourceType.Iron, TechnologyType.Physics),
    Canon(250, CombatType.Siege, 10, 26, 2, 2, null, TechnologyType.Chemistry),
    Cavalry(260, CombatType.Mounted, 25, 0, 0, 3, ResourceType.Horses, TechnologyType.MilitaryScience),
    Lancer(220, CombatType.Mounted, 22, 0, 0, 4,ResourceType.Horses, TechnologyType.Metallurgy),
    MusketMan(120, CombatType.Gunpowder, 16, 0, 0, 2, null, TechnologyType.Gunpowder),
    Rifleman(200, CombatType.Gunpowder, 25, 0, 0, 2, null, TechnologyType.Rifling),
    AntiTankGun(300, CombatType.Gunpowder, 32, 0, 0, 2, null, TechnologyType.ReplaceableParts),
    Artillery(420, CombatType.Siege, 16, 32, 3, 2, null, TechnologyType.Dynamite),
    Infantry(300, CombatType.Gunpowder, 36, 0, 0, 2, null, TechnologyType.ReplaceableParts),
    Panzer(450, CombatType.Armored, 60, 0, 5, TechnologyType.Combustion),
    Tank(450, CombatType.Armored, 50, 0, 4, TechnologyType.Combustion);

    final int cost;
    final CombatType combatType;
    final int combatStrength;
    private int rangedCombatStrength;
    final int range;
    final int movement;
    private ResourceType resourcesRequired;
    private TechnologyType technologyRequired;


    UnitType(int cost, CombatType combatType, int combatStrength, int rangedCombatStrength, int range, int movement, ResourceType resourcesRequired, TechnologyType technologyRequired) {
        this.cost = cost;
        this.combatType = combatType;
        this.combatStrength = combatStrength;
        this.rangedCombatStrength = rangedCombatStrength;
        this.range = range;
        this.movement = movement;
        this.resourcesRequired = resourcesRequired;
        this.technologyRequired = technologyRequired;
    }

    UnitType(int cost, CombatType combatType, int combatStrength, int range, int movement, TechnologyType technologyRequired) {
        this.cost = cost;
        this.combatType = combatType;
        this.combatStrength = combatStrength;
        this.range = range;
        this.movement = movement;
        this.technologyRequired = technologyRequired;
    }


}
