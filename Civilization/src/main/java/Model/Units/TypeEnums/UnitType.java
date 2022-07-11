package Model.Units.TypeEnums;

import Model.Technology.TechnologyType;
import Model.TileRelated.Resource.ResourceType;
import javafx.scene.image.Image;

public enum UnitType {
    Archer(new Image("/images/Units/Archer.png"),70, MainType.RANGED, CombatType.Archery,  4, 6, 2, 2 ,null, TechnologyType.Archery,false,false,true,false),
    ChariotArcher(new Image("/images/Units/ChariotArcher.png"),60, MainType.RANGED, CombatType.Mounted, 3, 6, 2, 4 , ResourceType.Horses, TechnologyType.TheWheel,false,false,false,false),
    Scout(new Image("/images/Units/Scout.png"),25, MainType.NONRANGED, CombatType.Recon, 4 , 0, 0, 2, null, null,true,false,true,false),
    Settler(new Image("/images/Units/Settler.png"),89, MainType.NONCOMBAT, CombatType.Civilian, 0, 0, 0, 2, null, null,true,false,true,false),
    Spearman(new Image("/images/Units/Spearman.png"),50, MainType.NONRANGED, CombatType.Melee, 7, 0, 0, 2, null, TechnologyType.BronzeWorking,true,false,true,false),
    Warrior(new Image("/images/Units/Warrior.png"),40, MainType.NONRANGED, CombatType.Melee, 6, 0 ,0 ,2, null, null,true,false,true,false),
    Worker(new Image("/images/Units/Worker.png"),70, MainType.NONCOMBAT, CombatType.Civilian, 0, 0, 0, 2, null, null,true,false,true,false),
    Catapult(new Image("/images/Units/Catapult.png"),100, MainType.SIEGE, CombatType.Siege, 4, 14, 2, 2, ResourceType.Iron, TechnologyType.Mathematics,false,false,false,true),
    Horseman(new Image("/images/Units/HorseMan.png"),80, MainType.NONRANGED, CombatType.Mounted, 12, 0, 0, 4, ResourceType.Horses, TechnologyType.HorsebackRiding,true,true,false,false),
    Swordsman(new Image("/images/Units/Swordsman.png"),80, MainType.NONRANGED, CombatType.Melee, 11, 0 , 0, 2, ResourceType.Iron, TechnologyType.IronWorking,true,false,true,false),
    Crossbowman(new Image("/images/Units/Crossbowman.png"),120, MainType.RANGED, CombatType.Archery, 6, 12, 2, 2, null, TechnologyType.Machinery,false,false,true,false),
    Knight(new Image("/images/Units/Kinght.png"),150, MainType.NONRANGED, CombatType.Mounted, 18, 0, 0, 3,  ResourceType.Horses, TechnologyType.Chivalry,true,true,false,false),
    longswordsman(new Image("/images/Units/longswordsman.png"),150, MainType.NONRANGED, CombatType.Melee, 18, 0, 0, 3, ResourceType.Iron, TechnologyType.Steel,true,false,true,false),
    Pikeman(new Image("/images/Units/Pikeman.png"),100, MainType.NONRANGED, CombatType.Melee, 10, 0, 0, 2, null, TechnologyType.CivilService,true,false,true,false),
    Trebuchet(new Image("/images/Units/Trebuchet.png"),170, MainType.SIEGE, CombatType.Siege, 6, 20, 2, 2, ResourceType.Iron, TechnologyType.Physics,false,false,false,true),
    Canon(new Image("/images/Units/Canon.png"),250, MainType.SIEGE, CombatType.Siege, 10, 26, 2, 2, null, TechnologyType.Chemistry,false,false,false,false),
    Cavalry(new Image("/images/Units/Cavalry.png"),260, MainType.NONRANGED, CombatType.Mounted, 25, 0, 0, 3, ResourceType.Horses, TechnologyType.MilitaryScience,true,true,false,false),
    Lancer(new Image("/images/Units/Lancer.png"),220, MainType.NONRANGED, CombatType.Mounted, 22, 0, 0, 4,ResourceType.Horses, TechnologyType.Metallurgy,true,true,false,false),
    MusketMan(new Image("/images/Units/MusketMan.png"),120, MainType.RANGED, CombatType.Gunpowder, 16, 0, 0, 2, null, TechnologyType.Gunpowder,true,false,true,false),
    Rifleman(new Image("/images/Units/Rifleman.png"),200, MainType.RANGED, CombatType.Gunpowder, 25, 0, 0, 2, null, TechnologyType.Rifling,true,false,true,false),
    AntiTankGun(new Image("/images/Units/AntiTankGun.png"),300, MainType.RANGED, CombatType.Gunpowder, 32, 0, 0, 2, null, TechnologyType.ReplaceableParts,true,false,true,false),
    Artillery(new Image("/images/Units/Artillery.png"),420, MainType.SIEGE, CombatType.Siege, 16, 32, 3, 2, null, TechnologyType.Dynamite,false,false,false,false),
    Infantry(new Image("/images/Units/Infantry.png"),300, MainType.RANGED, CombatType.Gunpowder, 36, 0, 0, 2, null, TechnologyType.ReplaceableParts,true,false,true,false),
    Panzer(new Image("/images/Units/Panzer.png"),450, MainType.NONRANGED, CombatType.Armored, 60,0, 0, 5,null,TechnologyType.Combustion,true,true,false,true),
    Tank(new Image("/images/Units/Tank.png"),450, MainType.NONRANGED, CombatType.Armored, 50,0, 0, 4, null,TechnologyType.Combustion,true,true,false,false);
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
    public final Image image;


    UnitType(Image image ,int cost, MainType mainType, CombatType combatType, int combatStrength, int rangedCombatStrength, int range, int movement, ResourceType resourcesRequired, TechnologyType technologyRequired,boolean canMeleeAttack,boolean canMoveAfterAttack,boolean hasDefensiveBonuses,boolean hasLimitedVisibility) {
        this.image = image;
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

