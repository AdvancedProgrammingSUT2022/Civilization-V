package Model.Technology;

import java.util.ArrayList;

public enum TechnologyType implements Unlocks{
    Agriculture(20, TechnologyMainType.AncientEra, null, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Pottery);
        add(TechnologyType.AnimalHusbandry);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // Farm
        }
    },
    AnimalHusbandry(35, TechnologyMainType.AncientEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Agriculture);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Trapping);
        add(TechnologyType.TheWheel);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // horses
            // pasture
        }
    },
    Masonry(55, TechnologyMainType.AncientEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Mining);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Construction);
    }}){
        // Mine
        // delete marsh
        // Walls
        // rock
    },
    Mining(35, TechnologyMainType.AncientEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Agriculture);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.BronzeWorking);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // delete Marsh
            // delete Forest
        }
    },
    Pottery(35, TechnologyMainType.AncientEra, new ArrayList<TechnologyType>(){{
        add(Agriculture);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Calendar);
        add(TechnologyType.Writing);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // Granary
        }
    },
    Trapping(55, TechnologyMainType.AncientEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.AnimalHusbandry);
    }}, new ArrayList<TechnologyType>(){{
        add(CivilService);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // TradingPost
            // Camp
        }
    },
    Archery(35, TechnologyMainType.AncientEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Agriculture);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Mathematics);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // Archer
        }
    },
    TheWheel(55, TechnologyMainType.AncientEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.AnimalHusbandry);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.HorsebackRiding);
        add(TechnologyType.Mathematics);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // chariot
            // Archer
            // Water Mill
            // Build a Road
        }
    },
    BronzeWorking(55, TechnologyMainType.AncientEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Mining);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.IronWorking);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // Spearman
            // Barracks
            // delete Jungle
        }
    },
    Writing(55, TechnologyMainType.AncientEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Pottery);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Philosophy);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // Library
        }
    },
    Calendar(70, TechnologyMainType.AncientEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Pottery);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Theology);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // plantation
        }
    },
    Construction(100, TechnologyMainType.ClassicalEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Masonry);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Engineering);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // Colosseum
            // Bridges over rivers
        }
    },
    HorsebackRiding(100, TechnologyMainType.ClassicalEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.TheWheel);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Chivalry);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // HorseMan
            // Stable
            // Circus
        }
    },
    IronWorking(150, TechnologyMainType.ClassicalEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.BronzeWorking);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.MetalCasting);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // Swordsman
            // Legion
            // Armory
            // Iron
        }
    },
    Mathematics(100, TechnologyMainType.ClassicalEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.TheWheel);
        add(TechnologyType.Archery);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Currency);
        add(TechnologyType.Engineering);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // Catapult
            // Courthouse
        }
    },
    Philosophy(100, TechnologyMainType.ClassicalEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Writing);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Theology);
        add(TechnologyType.CivilService);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // BurialTomb
            // Temple
        }
    },
    Chivalry(440, TechnologyMainType.MedievalEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.CivilService);
        add(TechnologyType.HorsebackRiding);
        add(TechnologyType.Currency);
    }}, new ArrayList<TechnologyType>(){{
        add(Banking);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // knight
            // Camel
            // Archer
            // Castle
        }
    },
    CivilService(400, TechnologyMainType.MedievalEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Philosophy);
        add(TechnologyType.Trapping);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Chivalry);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // Pikeman
        }
    },
    Currency(250, TechnologyMainType.MedievalEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Mathematics);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Chivalry);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // Market
        }
    },
    Education(440, TechnologyMainType.MedievalEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Theology);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Acoustics);
        add(TechnologyType.Banking);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // University
        }
    },
    Engineering(250, TechnologyMainType.MedievalEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Mathematics);
        add(TechnologyType.Construction);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Machinery);
        add(TechnologyType.Physics);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // nothing
        }
    },
    Machinery(440, TechnologyMainType.MedievalEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Engineering);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.PrintingPress);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // Crossbowman,
            // 1.2 faster road movement
        }
    },
    MetalCasting(240, TechnologyMainType.MedievalEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.IronWorking);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Physics);
        add(TechnologyType.Steel);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // Forge
            // Workshop
        }
    },
    Physics(440, TechnologyMainType.MedievalEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Engineering);
        add(TechnologyType.MetalCasting);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.PrintingPress);
        add(TechnologyType.Gunpowder);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // Trebuchet
        }
    },
    Steel(440, TechnologyMainType.MedievalEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.MetalCasting);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Gunpowder);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // Longswordsman
        }
    },
    Theology(250, TechnologyMainType.MedievalEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Calendar);
        add(TechnologyType.Philosophy);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Education);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // Monastery Garden
        }
    },

    Acoustics(650, TechnologyMainType.RenaissanceEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Education);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.ScientificTheory);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // nothing
        }
    },
    Archaeology(1300, TechnologyMainType.RenaissanceEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Acoustics);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Biology);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // Museum
        }
    },
    Banking(650, TechnologyMainType.RenaissanceEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Education);
        add(TechnologyType.Chivalry);
    }}, new ArrayList<TechnologyType>(){{
        add(Economics);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // Satrap's Court, Bank
        }
    },
    Chemistry(900, TechnologyMainType.RenaissanceEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Gunpowder);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.MilitaryScience);
        add(TechnologyType.Fertilizer);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // IronWorking
        }
    },
    Economics(900, TechnologyMainType.RenaissanceEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Banking);
        add(TechnologyType.PrintingPress);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.MilitaryScience);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // Windmill
        }
    },
    Fertilizer(1300, TechnologyMainType.RenaissanceEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Chemistry);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Dynamite);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // Farms without Fresh Water yield increased by 1
        }
    },
    Gunpowder(680, TechnologyMainType.RenaissanceEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Physics);
        add(TechnologyType.Steel);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Chemistry);
        add(TechnologyType.Metallurgy);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // Musketman
        }
    },
    Metallurgy(900, TechnologyMainType.RenaissanceEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Gunpowder);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Rifling);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // Lancer
        }
    },
    MilitaryScience(1300, TechnologyMainType.RenaissanceEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Economics);
        add(TechnologyType.Chemistry);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.SteamPower);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // Cavalry
            // MilitaryAcademy
        }
    },
    PrintingPress(650, TechnologyMainType.RenaissanceEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Machinery);
        add(TechnologyType.Physics);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Economics);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // Theater
        }
    },
    Rifling(1425, TechnologyMainType.RenaissanceEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Metallurgy);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Dynamite);
    }}),
    ScientificTheory(1300, TechnologyMainType.RenaissanceEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Acoustics);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Biology);
        add(TechnologyType.SteamPower);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // Public School
            // Coal
        }
    },

    Biology(1680, TechnologyMainType.IndustrialEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Archaeology);
        add(TechnologyType.ScientificTheory);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Electricity);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // nothing
        }
    },
    Combustion(2200, TechnologyMainType.IndustrialEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.ReplaceableParts);
        add(TechnologyType.Railroad);
        add(TechnologyType.Dynamite);
    }}, null){
        @Override
        public void Unlock() {
            super.Unlock();
            // Tank
            // Panzer
        }
    },
    Dynamite(1900, TechnologyMainType.IndustrialEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Fertilizer);
        add(TechnologyType.Rifling);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Combustion);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // Artillery
        }
    },
    Electricity(1900, TechnologyMainType.IndustrialEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Biology);
        add(TechnologyType.SteamPower);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Telegraph);
        add(TechnologyType.Radio);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // Stock Exchange
        }
    },
    Radio(2200, TechnologyMainType.IndustrialEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Electricity);
    }}, null){
        @Override
        public void Unlock() {
            super.Unlock();
            // BroadcastTower
        }
    },
    Railroad(1900, TechnologyMainType.IndustrialEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.SteamPower);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Combustion);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // Arsenal , Railroad
        }
    },
    ReplaceableParts(1900, TechnologyMainType.IndustrialEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.SteamPower);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Combustion);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // Anti-Tank, Gun, Infantry
        }
    },
    SteamPower(1680, TechnologyMainType.IndustrialEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.ScientificTheory);
        add(MilitaryScience);
    }}, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Electricity);
        add(ReplaceableParts);
        add(Railroad);
    }}){
        @Override
        public void Unlock() {
            super.Unlock();
            // Factory
        }
    },
    Telegraph(2200, TechnologyMainType.IndustrialEra, new ArrayList<TechnologyType>(){{
        add(TechnologyType.Electricity);
    }}, null){
        @Override
        public void Unlock() {
            super.Unlock();
            // Military Base
        }
    };

    public final int cost;
    public final TechnologyMainType technologyMainType;
    public final ArrayList<TechnologyType> PrerequisiteTechs;
    public final ArrayList<TechnologyType> leadsToTechs;

    TechnologyType(int cost, TechnologyMainType technologyMainType, ArrayList<TechnologyType> prerequisiteTechs, ArrayList<TechnologyType> leadsToTechs) {
        this.cost = cost;
        this.technologyMainType = technologyMainType;
        PrerequisiteTechs = prerequisiteTechs;
        this.leadsToTechs = leadsToTechs;
    }
}
