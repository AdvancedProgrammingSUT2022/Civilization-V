package Model.Technology;

import java.util.ArrayList;

public enum TechnologyType implements Unlocks {
    Combustion(2200, TechnologyMainType.IndustrialEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.ReplaceableParts);
        add(TechnologyType.Railroad);
        add(TechnologyType.Dynamite);
    }}, null) {
        @Override
        public String Unlock() {
            // Tank
            // Panzer
            return "";
        }
    },
    Radio(2200, TechnologyMainType.IndustrialEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Electricity);
    }}, null) {
        @Override
        public String Unlock() {
            // BroadcastTower
            return null;
        }
    },
    Telegraph(2200, TechnologyMainType.IndustrialEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Electricity);
    }}, null) {
        @Override
        public String Unlock() {
            // Military Base
            return "";
        }
    },
    Dynamite(1900, TechnologyMainType.IndustrialEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Fertilizer);
        add(TechnologyType.Rifling);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Combustion);
    }}) {
        @Override
        public String Unlock() {
            // Artillery
            return null;
        }
    },
    Railroad(1900, TechnologyMainType.IndustrialEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.SteamPower);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Combustion);
    }}) {
        @Override
        public String Unlock() {
            // Arsenal , Railroad
            return null;
        }
    },
    ReplaceableParts(1900, TechnologyMainType.IndustrialEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.SteamPower);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Combustion);
    }}) {
        @Override
        public String Unlock() {
            // Anti-Tank, Gun, Infantry
            return null;
        }
    },
    Electricity(1900, TechnologyMainType.IndustrialEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Biology);
        add(TechnologyType.SteamPower);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Telegraph);
        add(TechnologyType.Radio);
    }}) {
        @Override
        public String Unlock() {
            // Stock Exchange
            return null;
        }
    },
    Rifling(1425, TechnologyMainType.RenaissanceEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Metallurgy);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Dynamite);
    }}) {
        @Override
        public String Unlock() {
            return "";
        }
    },
    Fertilizer(1300, TechnologyMainType.RenaissanceEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Chemistry);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Dynamite);
    }}) {
        @Override
        public String Unlock() {
            // Farms without Fresh Water yield increased by 1
            return "";
        }
    },
    Biology(1680, TechnologyMainType.IndustrialEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Archaeology);
        add(TechnologyType.ScientificTheory);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Electricity);
    }}) {
        @Override
        public String Unlock() {
            // nothing
            return "";
        }
    },
    SteamPower(1680, TechnologyMainType.IndustrialEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.ScientificTheory);
        add(MilitaryScience);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Electricity);
        add(TechnologyType.ReplaceableParts);
        add(TechnologyType.Railroad);
    }}) {
        @Override
        public String Unlock() {
            // Factory
            return "";
        }
    },
    Metallurgy(900, TechnologyMainType.RenaissanceEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Gunpowder);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Rifling);
    }}) {
        @Override
        public String Unlock() {
            // Lancer
            return "";
        }
    },
    MilitaryScience(1300, TechnologyMainType.RenaissanceEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Economics);
        add(TechnologyType.Chemistry);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.SteamPower);
    }}) {
        @Override
        public String Unlock() {
            // Cavalry
            // MilitaryAcademy
            return "";
        }
    },
    Economics(900, TechnologyMainType.RenaissanceEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Banking);
        add(TechnologyType.PrintingPress);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.MilitaryScience);
    }}) {
        @Override
        public String Unlock() {
            // Windmill
            return "";
        }
    },
    Chemistry(900, TechnologyMainType.RenaissanceEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Gunpowder);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.MilitaryScience);
        add(TechnologyType.Fertilizer);
    }}) {
        @Override
        public String Unlock() {
            // IronWorking
            return "";
        }
    },
    ScientificTheory(1300, TechnologyMainType.RenaissanceEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Acoustics);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Biology);
        add(TechnologyType.SteamPower);
    }}) {
        @Override
        public String Unlock() {
            // Public School
            // Coal
            return "";
        }
    },
    Gunpowder(680, TechnologyMainType.RenaissanceEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Physics);
        add(TechnologyType.Steel);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Chemistry);
        add(TechnologyType.Metallurgy);
    }}) {
        @Override
        public String Unlock() {
            // Musketman
            return "";
        }
    },
    PrintingPress(650, TechnologyMainType.RenaissanceEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Machinery);
        add(TechnologyType.Physics);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Economics);
    }}) {
        @Override
        public String Unlock() {
            // Theater
            return "";
        }
    },
    Acoustics(650, TechnologyMainType.RenaissanceEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Education);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.ScientificTheory);
    }}) {
        @Override
        public String Unlock() {
            // nothing
            return "";
        }
    },
    Physics(440, TechnologyMainType.MedievalEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Engineering);
        add(TechnologyType.MetalCasting);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.PrintingPress);
        add(TechnologyType.Gunpowder);
    }}) {
        @Override
        public String Unlock() {
            // Trebuchet
            return "";
        }
    },
    Machinery(440, TechnologyMainType.MedievalEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Engineering);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.PrintingPress);
    }}) {
        @Override
        public String Unlock() {
            // Crossbowman,
            // 1.2 faster road movement
            return "";
        }
    },
    Banking(650, TechnologyMainType.RenaissanceEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Education);
        add(TechnologyType.Chivalry);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Economics);
    }}) {
        @Override
        public String Unlock() {
            // Satrap's Court, Bank
            return "";
        }
    },
    Education(440, TechnologyMainType.MedievalEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Theology);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Acoustics);
        add(TechnologyType.Banking);
    }}) {
        @Override
        public String Unlock() {
            // University
            return "";
        }
    },
    Chivalry(440, TechnologyMainType.MedievalEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.CivilService);
        add(TechnologyType.HorsebackRiding);
        add(TechnologyType.Currency);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Banking);
    }}) {
        @Override
        public String Unlock() {
            // knight
            // Camel
            // Archer
            // Castle
            return "";
        }
    },
    Currency(250, TechnologyMainType.MedievalEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Mathematics);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Chivalry);
    }}) {
        @Override
        public String Unlock() {
            // Market
            return "";
        }
    },
    Engineering(250, TechnologyMainType.MedievalEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Mathematics);
        add(TechnologyType.Construction);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Machinery);
        add(TechnologyType.Physics);
    }}) {
        @Override
        public String Unlock() {
            // nothing
            return "";
        }
    },
    Theology(250, TechnologyMainType.MedievalEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Calendar);
        add(TechnologyType.Philosophy);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Education);
    }}) {
        @Override
        public String Unlock() {
            // Monastery Garden
            return "";
        }
    },
    CivilService(400, TechnologyMainType.MedievalEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Philosophy);
        add(TechnologyType.Trapping);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Chivalry);
    }}) {
        @Override
        public String Unlock() {
            // Pikeman
            return "";
        }
    },
    Philosophy(100, TechnologyMainType.ClassicalEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Writing);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Theology);
        add(TechnologyType.CivilService);
    }}) {
        @Override
        public String Unlock() {
            // BurialTomb
            // Temple
            return "";
        }
    },
    Mathematics(100, TechnologyMainType.ClassicalEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.TheWheel);
        add(TechnologyType.Archery);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Currency);
        add(TechnologyType.Engineering);
    }}) {
        @Override
        public String Unlock() {
            // Catapult
            // Courthouse
            return "";
        }
    },
    HorsebackRiding(100, TechnologyMainType.ClassicalEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.TheWheel);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Chivalry);
    }}) {
        @Override
        public String Unlock() {
            // HorseMan
            // Stable
            // Circus
            return "";
        }
    },
    Writing(55, TechnologyMainType.AncientEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Pottery);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Philosophy);
    }}) {
        @Override
        public String Unlock() {
            // Library
            return "";
        }
    },
    Calendar(70, TechnologyMainType.AncientEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Pottery);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Theology);
    }}) {
        @Override
        public String Unlock() {
            // plantation
            return "";
        }
    },
    Trapping(55, TechnologyMainType.AncientEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.AnimalHusbandry);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.CivilService);
    }}) {
        @Override
        public String Unlock() {
            // TradingPost
            // Camp
            return "";
        }
    },
    TheWheel(55, TechnologyMainType.AncientEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.AnimalHusbandry);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.HorsebackRiding);
        add(TechnologyType.Mathematics);
    }}) {
        @Override
        public String Unlock() {
            // chariot
            // Archer
            // Water Mill
            // Build a Road
            return "";
        }
    },
    AnimalHusbandry(35, TechnologyMainType.AncientEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Agriculture);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Trapping);
        add(TechnologyType.TheWheel);
    }}) {
        @Override
        public String Unlock() {
            // horses
            // pasture
            return "";
        }
    },
    Pottery(35, TechnologyMainType.AncientEra, new ArrayList<TechnologyType>() {{
        add(Agriculture);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Calendar);
        add(TechnologyType.Writing);
    }}) {
        @Override
        public String Unlock() {
            // Granary
            return "";
        }
    },
    Agriculture(20, TechnologyMainType.AncientEra, null, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Pottery);
        add(TechnologyType.AnimalHusbandry);
    }}) {
        @Override
        public String Unlock() {
            return "";
            // Farm
        }
    },
    Construction(100, TechnologyMainType.ClassicalEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Masonry);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Engineering);
    }}) {
        @Override
        public String Unlock() {
            // Colosseum
            // Bridges over rivers
            return "";
        }
    },
    Masonry(55, TechnologyMainType.AncientEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Mining);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Construction);
    }}) {
        @Override
        public String Unlock() {
            // Mine
            // delete marsh
            // Walls
            // rock
            return "";
        }

    },
    BronzeWorking(55, TechnologyMainType.AncientEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Mining);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.IronWorking);
    }}) {
        @Override
        public String Unlock() {
            // Spearman
            // Barracks
            // delete Jungle
            return "";
        }
    },
    Mining(35, TechnologyMainType.AncientEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Agriculture);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.BronzeWorking);
    }}) {
        @Override
        public String Unlock() {
            // delete Marsh
            // delete Forest
            return "";
        }
    },
    Archery(35, TechnologyMainType.AncientEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Agriculture);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Mathematics);
    }}) {
        @Override
        public String Unlock() {
            // Archer
            return "";
        }
    },
    Steel(440, TechnologyMainType.MedievalEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.MetalCasting);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Gunpowder);
    }}) {
        @Override
        public String Unlock() {
            // Longswordsman
            return "";
        }
    },
    MetalCasting(240, TechnologyMainType.MedievalEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.IronWorking);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Physics);
        add(TechnologyType.Steel);
    }}) {
        @Override
        public String Unlock() {
            // Forge
            // Workshop
            return "";
        }
    },
    IronWorking(150, TechnologyMainType.ClassicalEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.BronzeWorking);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.MetalCasting);
    }}) {
        @Override
        public String Unlock() {
            // Swordsman
            // Legion
            // Armory
            // Iron
            return "";
        }
    },
    Archaeology(1300, TechnologyMainType.RenaissanceEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Acoustics);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Biology);
    }}) {
        @Override
        public String Unlock() {
            // Museum
            return "";
        }
    };

    public final int cost;
    public final TechnologyMainType technologyMainType;
    public final ArrayList<TechnologyType> PrerequisiteTechs;
    public final ArrayList<TechnologyType> LeadsToTechs;

    TechnologyType(int cost, TechnologyMainType technologyMainType, ArrayList<TechnologyType> prerequisiteTechs, ArrayList<TechnologyType> leadsToTechs) {
        this.cost = cost;
        this.technologyMainType = technologyMainType;
        PrerequisiteTechs = prerequisiteTechs;
        LeadsToTechs = leadsToTechs;
    }
}
