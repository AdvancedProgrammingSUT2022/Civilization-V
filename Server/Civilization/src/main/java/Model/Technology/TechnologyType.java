package Model.Technology;

import Model.TileRelated.Terraine.TerrainType;
import javafx.scene.image.Image;

import java.util.ArrayList;

public enum TechnologyType implements Unlocks {
    Combustion(2200, TechnologyMainType.IndustrialEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.ReplaceableParts);
        add(TechnologyType.Railroad);
        add(TechnologyType.Dynamite);
    }}, null,"The Combustion technology creates the contemporary internal combustion engine, widely used in smaller transportation vehicles.\n" +
            "\n" +
            "Its military application allows the creation of the Destroyer, as well as the first Armored units in the game: either the World War II Tank . This will usher in the dominance of Oil Oil and make the mounted units obsolete.\n" +
            "\n" +
            "Finally, in Brave New World, land trade routes benefit from extended range with this tech .") {
        @Override
        public String Unlock() {
            // Tank
            // Panzer
            return "";
        }
    },
    Radio(2200, TechnologyMainType.IndustrialEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Electricity);
    }}, null , "The Radio is the first modern communication device able to really connect the world together, and thus usher it into the era of modern communications. Its waves can travel vast distances in seconds, and without the need of any physical infrastructure, allowing unparalleled opportunities for exchange of information.\n" +
            "\n" +
            "The technology is best represented by the Broadcast Tower, a building that spreads an empire's culture in the best way possible.") {
        @Override
        public String Unlock() {
            // BroadcastTower
            return null;
        }
    },
    Telegraph(2200, TechnologyMainType.IndustrialEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Electricity);
    }}, null , "The invention of the Telegraph greatly expedites long-distance communication and allows for advancements in military and naval technology. It also enables the construction of the Cristo Redentor wonder.\n" +
            "\n" +
            "In the expansion sets, Telegraph is removed from the tech tree and replaced by Telecommunications, a technology of the Information Era.") {
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
    }} , "The invention of Dynamite literally propels siege weaponry into the modern age. This high-explosive upgrade of gunpowder allows big guns with unprecedented range and power, making possible modern cannons, starting with the Artillery.\n" +
            "\n" +
            "If you have the Great Wall wonder, it will cease to function upon researching Dynamite. This only applies to your own Great Wall when you research it; another player with Dynamite will still be affected by your Great Wall even if they have Dynamite, so long as you don't have it.") {
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
    }},"When the steam engine is first applied to a land transportation machine, the result is a heavy, bulky and unwieldy, but very powerful and incredibly fast. Soon the Railroad concept is developed - a network of iron tracks, onto which sets of steam wagons are capable of moving much faster than carts on roads. The modern transportation network is born.") {
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
    }},"") {
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
    }},"Electricity is another critical technological advancement, without which nothing of modern machines would work. It reveals the importance of another late-game strategical resource, Aluminum, and enables a range of other important buildings, besides leading to many next-generation technologies in the modern age.") {
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
    }},"Rifling allows for the invention of a new loading mechanism for firearms and the development of infantry weapons carried by the Rifleman, the new Industrial Era front-line unit.") {
        @Override
        public String Unlock() {
            return "";
        }
    },
    Fertilizer(1300, TechnologyMainType.RenaissanceEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Chemistry);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Dynamite);
    }},"The development of Fertilizer is a direct result of the earlier developments in Chemistry. Fertilization greatly improves Food production in most tile improvements related to Food.") {
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
    }},"A very important science, Biology deals with living organisms, their characteristics and their use in our society. Thanks to it, the value of Oil is revealed; and great advancements in Medicine become possible. They're represented by the Hospital, a late game food-production building.") {
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
    }},"") {
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
    }},"Metallurgy, the next step in the development of metal working, delivers a new level of sophistication to Renaissance weaponry and prepares its entrance into the Modern age. It also unlocks a variety of new units.\n" +
            "\n" +
            "Lancers are a harder-hitting version of Knights. They have several more points of Combat Strength and receive a bonus against mounted units. However, they have a penalty during defense, making them brittle if they aren't defended.\n" +
            "\n" +
            "Sipahi are unique Ottoman Lancers that are even faster than regular Lancers. They can pillage in enemy territory without breaking stride, making them the single best sabotage unit in the game.\n" +
            "\n" +
            "Hakkapeliitta are unique Swedish Lancers that can interact with Great Generals and have a 15% combat bonus.\n" +
            "\n" +
            "Winged Hussars are unique Polish Lancers that can have extra strength, and two promotions (Formation I and Shock I). It can force defending enemy troops to withdraw.") {
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
    }},"Science finally touches one of the most important fields of human life, combat. Thanks to the newly developed Military Science, a number of advancements become possible which define modern combat strategies. The first of those are the Cavalry, the most powerful mounted unit before tanks.") {
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
    }},"") {
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
    }},"") {
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
    }},"") {
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
    }},"") {
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
    }},"") {
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
    }},"") {
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
    }},"") {
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
    }},"") {
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
    }},"") {
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
    }},"") {
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
    }},"") {
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
    }},"") {
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
    }},"") {
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
    }},"") {
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
    }},"") {
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
    }},"") {
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
    }},"") {
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
    }},"") {
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
    }},"") {
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
    }},"") {
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
    }},"") {
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
    }},"") {
        @Override
        public String Unlock() {
            return "";
        }
    },
    AnimalHusbandry(35, TechnologyMainType.AncientEra, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Agriculture);
    }}, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Trapping);
        add(TechnologyType.TheWheel);
    }},"") {
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
    }},"") {
        @Override
        public String Unlock() {
            // Granary
            return "";
        }
    },
    Agriculture(20, TechnologyMainType.AncientEra, null, new ArrayList<TechnologyType>() {{
        add(TechnologyType.Pottery);
        add(TechnologyType.AnimalHusbandry);
    }},"") {
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
    }},"") {
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
    }},"") {
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
    }},"") {
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
    }},"") {
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
    }},"") {
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
    }},"") {
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
    }},"") {
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
    }},"") {
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
    }},"") {
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
    public Image image;
    public String toolTip;

    static {
        for (
                TechnologyType technologyType: TechnologyType.values()) {
            technologyType.image = new Image("/images/Technologies/" + technologyType.name() + ".png");
        }
    }

    TechnologyType(int cost, TechnologyMainType technologyMainType, ArrayList<TechnologyType> prerequisiteTechs, ArrayList<TechnologyType> leadsToTechs , String toolTip) {
        this.cost = cost;
        this.technologyMainType = technologyMainType;
        PrerequisiteTechs = prerequisiteTechs;
        LeadsToTechs = leadsToTechs;
        this.toolTip = toolTip;
    }
}
