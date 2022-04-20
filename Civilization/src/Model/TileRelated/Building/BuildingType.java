package Model.TileRelated.Building;

import Model.Technology.Technology;
import Model.Technology.TechnologyType;

import java.time.chrono.MinguoEra;

public enum BuildingType implements BuildingNotes{

    Barracks(80, 1, new Technology(TechnologyType.BronzeWorking), BuildingMainType.AncientEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    Granary(100, 1, new Technology(TechnologyType.Pottery), BuildingMainType.AncientEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    Library(80, 1, new Technology(TechnologyType.Writing), BuildingMainType.AncientEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    Monument(60, 1, null, BuildingMainType.AncientEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    Walls(100, 1, new Technology(TechnologyType.Masonry), BuildingMainType.AncientEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    WaterMill(120, 2, new Technology(TechnologyType.TheWheel), BuildingMainType.AncientEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    Armory(130, 3, new Technology(TechnologyType.IronWorking), BuildingMainType.ClassicalEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    BurialTomb(120, 0, new Technology(TechnologyType.Philosophy), BuildingMainType.ClassicalEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    Circus(150, 3, new Technology(TechnologyType.HorsebackRiding), BuildingMainType.ClassicalEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    Colosseum(150, 3, new Technology(TechnologyType.Construction), BuildingMainType.ClassicalEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    Courthouse(200, 5, new Technology(TechnologyType.Mathematics), BuildingMainType.ClassicalEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    Stable(100, 1, new Technology(TechnologyType.HorsebackRiding), BuildingMainType.ClassicalEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    Temple(120, 2, new Technology(TechnologyType.Philosophy), BuildingMainType.ClassicalEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    Castle(200, 3, new Technology(TechnologyType.Chivalry), BuildingMainType.MedievalEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    Forge(150, 2, new Technology(TechnologyType.MetalCasting), BuildingMainType.MedievalEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    Garden(120, 2, new Technology(TechnologyType.Theology), BuildingMainType.MedievalEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    Market(120, 0, new Technology(TechnologyType.Currency), BuildingMainType.MedievalEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    Mint(120, 0, new Technology(TechnologyType.Currency), BuildingMainType.MedievalEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    Monastery(120, 2, new Technology(TechnologyType.Theology), BuildingMainType.MedievalEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    University(200, 3, new Technology(TechnologyType.Education), BuildingMainType.MedievalEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    Workshop(100, 2, new Technology(TechnologyType.MetalCasting), BuildingMainType.MedievalEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    Bank(220, 0, new Technology(TechnologyType.Banking), BuildingMainType.RenaissanceEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    MilitaryAcademy(350, 3, new Technology(TechnologyType.MilitaryScience), BuildingMainType.RenaissanceEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    Museum(350, 3, new Technology(TechnologyType.Archaeology), BuildingMainType.RenaissanceEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    OperaHouse(220, 3, new Technology(TechnologyType.Acoustics), BuildingMainType.RenaissanceEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    PublicSchool(350, 3, new Technology(TechnologyType.ScientificTheory), BuildingMainType.RenaissanceEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    SatrapsCourt(220, 0, new Technology(TechnologyType.Banking), BuildingMainType.RenaissanceEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    Theater(300, 5, new Technology(TechnologyType.PrintingPress), BuildingMainType.RenaissanceEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    Windmill(180, 2, new Technology(TechnologyType.Economics), BuildingMainType.RenaissanceEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    Arsenal(350, 3, new Technology(TechnologyType.Radio), BuildingMainType.IndustrialEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    BroadcastTower(600, 3, new Technology(TechnologyType.Radio), BuildingMainType.IndustrialEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    Factory(300, 3, new Technology(TechnologyType.SteamPower), BuildingMainType.IndustrialEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    Hospital(400, 2, new Technology(TechnologyType.Biology), BuildingMainType.IndustrialEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    MilitaryBase(450, 4, new Technology(TechnologyType.Telegraph), BuildingMainType.IndustrialEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    },
    StockExchange(650, 0, new Technology(TechnologyType.Electricity), BuildingMainType.IndustrialEraBuilding){
        @Override
        public void DoNotes() {
            super.DoNotes();
        }
    };
    public final int Cost;
    public final int Maintenance;
    public final Technology technologyRequired;
    public final BuildingMainType buildingMainType;

    BuildingType(int cost, int maintenance, Technology technologyRequired, BuildingMainType buildingMainType) {
        Cost = cost;
        Maintenance = maintenance;
        this.technologyRequired = technologyRequired;
        this.buildingMainType = buildingMainType;
    }

}
