package Model.Technology;

import Model.TileRelated.Terraine.Terrain;

import java.util.ArrayList;

public enum TechnologyType implements Unlocks{
    Agriculture,//(20, null, new ArrayList<TechnologyType>(){{
//        add(TechnologyType.Pottery);
//        add(TechnologyType.AnimalHusbandry);
//    }}){
//        @Override
//        public void Unlock() {
//            super.Unlock();
//            // Farm
//        }
//    },
    AnimalHusbandry,//(35, new ArrayList<TechnologyType>(){{
//        add(TechnologyType.Agriculture);
//    }}, new ArrayList<TechnologyType>(){{
//        add(TechnologyType.Trapping);
//        add(TechnologyType.TheWheel);
//    }}){
//        @Override
//        public void Unlock() {
//            super.Unlock();
//            // horses
//            // pasture
//        }
//    },
    Masonry,//(55, new ArrayList<TechnologyType>(){{
//        add(TechnologyType.Mining);
//    }}, new ArrayList<TechnologyType>(){{
//        add(TechnologyType.Construction);
//    }}){
//        // Mine
//        // delete marsh
//        // Walls
//        // rock
//    },
    Mining,//(35, new ArrayList<TechnologyType>(){{
//        add(TechnologyType.Agriculture);
//    }}, new ArrayList<TechnologyType>(){{
//        add(TechnologyType.BronzeWorking);
//    }}){
//        @Override
//        public void Unlock() {
//            super.Unlock();
//            // delete Marsh
//            // delete Forest
//        }
//    },
    Pottery,
    Trapping,
    Writing,
    Construction,
    Philosophy,
    Currency,
    Education,
    Engineering,
    MetalCasting,
    Theology,
    Acoustics,
    Archaeology,
    Banking,
    Economics,
    Fertilizer,
    PrintingPress,
    Biology,
    Electricity,
    Radio,
    Railroad,
    Parts,
    SteamPower,
    Telegraph,
    Archery,
    TheWheel,
    BronzeWorking,
    Mathematics,
    HorsebackRiding,
    IronWorking,
    Machinery,
    Chivalry,
    Steel,
    CivilService,
    Physics,
    Chemistry,
    MilitaryScience,
    Metallurgy,
    Gunpowder,
    Rifling,
    ReplaceableParts,
    Dynamite,
    ScientificTheory,
    Calendar,//(60, new ArrayList<TechnologyType>(){{
//        add(TechnologyType.Pottery);
//    }}, new ArrayList<TechnologyType>(){{
//        add(TechnologyType.Theology);
//    }}){
//        @Override
//        public void Unlock() {
//            super.Unlock();
//            // plantation
//        }
//    },
    Combustion;
//    public final int cost;
//    public final ArrayList<TechnologyType> PrerequisiteTechs;
//    public final ArrayList<TechnologyType> leadsToTechs;
//
//    TechnologyType(int cost, ArrayList<TechnologyType> prerequisiteTechs, ArrayList<TechnologyType> leadsToTechs) {
//        this.cost = cost;
//        PrerequisiteTechs = prerequisiteTechs;
//        this.leadsToTechs = leadsToTechs;
//    }
}
