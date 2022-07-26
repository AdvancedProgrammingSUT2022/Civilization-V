package Model.Units.TypeEnums;

import Model.Technology.TechnologyType;
import Model.TileRelated.Resource.ResourceType;

public enum UnitType {
    Archer(70, MainType.RANGED, CombatType.Archery,  4, 6, 2, 2 ,null, TechnologyType.Archery,false,false,true,false),
    ChariotArcher(60, MainType.RANGED, CombatType.Mounted, 3, 6, 2, 4 , ResourceType.Horses, TechnologyType.TheWheel,false,false,false,false),
    Scout(25, MainType.NONRANGED, CombatType.Recon, 4 , 0, 0, 2, null, null,true,false,true,false),
    Settler(89, MainType.NONCOMBAT, CombatType.Civilian, 0, 0, 0, 2, null, null,true,false,true,false),
    Spearman(50, MainType.NONRANGED, CombatType.Melee, 7, 0, 0, 2, null, TechnologyType.BronzeWorking,true,false,true,false),
    Warrior(40, MainType.NONRANGED, CombatType.Melee, 6, 0 ,0 ,2, null, null,true,false,true,false),
    Worker(70, MainType.NONCOMBAT, CombatType.Civilian, 0, 0, 0, 2, null, null,true,false,true,false),
    Catapult(100, MainType.SIEGE, CombatType.Siege, 4, 14, 2, 2, ResourceType.Iron, TechnologyType.Mathematics,false,false,false,true),
    Horseman(80, MainType.NONRANGED, CombatType.Mounted, 12, 0, 0, 4, ResourceType.Horses, TechnologyType.HorsebackRiding,true,true,false,false),
    Swordsman(80, MainType.NONRANGED, CombatType.Melee, 11, 0 , 0, 2, ResourceType.Iron, TechnologyType.IronWorking,true,false,true,false),
    Crossbowman(120, MainType.RANGED, CombatType.Archery, 6, 12, 2, 2, null, TechnologyType.Machinery,false,false,true,false),
    Knight(150, MainType.NONRANGED, CombatType.Mounted, 18, 0, 0, 3,  ResourceType.Horses, TechnologyType.Chivalry,true,true,false,false),
    longswordsman(150, MainType.NONRANGED, CombatType.Melee, 18, 0, 0, 3, ResourceType.Iron, TechnologyType.Steel,true,false,true,false),
    Pikeman(100, MainType.NONRANGED, CombatType.Melee, 10, 0, 0, 2, null, TechnologyType.CivilService,true,false,true,false),
    Trebuchet(170, MainType.SIEGE, CombatType.Siege, 6, 20, 2, 2, ResourceType.Iron, TechnologyType.Physics,false,false,false,true),
    Canon(250, MainType.SIEGE, CombatType.Siege, 10, 26, 2, 2, null, TechnologyType.Chemistry,false,false,false,false),
    Cavalry(260, MainType.NONRANGED, CombatType.Mounted, 25, 0, 0, 3, ResourceType.Horses, TechnologyType.MilitaryScience,true,true,false,false),
    Lancer(220, MainType.NONRANGED, CombatType.Mounted, 22, 0, 0, 4,ResourceType.Horses, TechnologyType.Metallurgy,true,true,false,false),
    MusketMan(120, MainType.RANGED, CombatType.Gunpowder, 16, 0, 0, 2, null, TechnologyType.Gunpowder,true,false,true,false),
    Rifleman(200, MainType.RANGED, CombatType.Gunpowder, 25, 0, 0, 2, null, TechnologyType.Rifling,true,false,true,false),
    AntiTankGun(300, MainType.RANGED, CombatType.Gunpowder, 32, 0, 0, 2, null, TechnologyType.ReplaceableParts,true,false,true,false),
    Artillery(420, MainType.SIEGE, CombatType.Siege, 16, 32, 3, 2, null, TechnologyType.Dynamite,false,false,false,false),
    Infantry(300, MainType.RANGED, CombatType.Gunpowder, 36, 0, 0, 2, null, TechnologyType.ReplaceableParts,true,false,true,false),
    Panzer(450, MainType.NONRANGED, CombatType.Armored, 60,0, 0, 5,null,TechnologyType.Combustion,true,true,false,true),
    Tank(450, MainType.NONRANGED, CombatType.Armored, 50,0, 0, 4, null,TechnologyType.Combustion,true,true,false,false);
    public final int cost;
    public final MainType mainType;
    public final CombatType combatType;
    public final int combatStrength;
    public int rangedCombatStrength;
    public final int range;
    public final int movement;
    public ResourceType resourcesRequired;
    public final TechnologyType technologyRequired;
    public final boolean canMeleeAttack;
    public final boolean canMoveAfterAttack;
    public final boolean hasDefensiveBonuses;
    public final boolean hasLimitedVisibility;


    UnitType(int cost, MainType mainType, CombatType combatType, int combatStrength, int rangedCombatStrength, int range, int movement, ResourceType resourcesRequired, TechnologyType technologyRequired,boolean canMeleeAttack,boolean canMoveAfterAttack,boolean hasDefensiveBonuses,boolean hasLimitedVisibility) {
        this.cost = cost;
        this.mainType = mainType;
        this.combatType = combatType;
        this.combatStrength = combatStrength;
        this.rangedCombatStrength = rangedCombatStrength;
        this.range = range;
        this.movement = movement;
        this.resourcesRequired = resourcesRequired;
        this.technologyRequired = technologyRequired;
        this.canMeleeAttack = canMeleeAttack;
        this.hasLimitedVisibility = hasLimitedVisibility;
        this.hasDefensiveBonuses = hasDefensiveBonuses;
        this.canMoveAfterAttack = canMoveAfterAttack;
    }



}

