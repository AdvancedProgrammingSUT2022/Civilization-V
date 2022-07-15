package Controller.GameController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import Controller.GameController.MapControllers.MapFunctions;
import Controller.GameController.MapControllers.MapPrinter;

import java.util.Random;
import Controller.GameController.MapControllers.TileVisibilityController;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.MapRelated.GameMap;
import Model.Movement.Graph;
import Model.TileRelated.Feature.FeatureType;
import Model.TileRelated.Improvement.ImprovementType;
import Model.TileRelated.Road.RoadType;
import Model.TileRelated.Terraine.TerrainType;
import Model.TileRelated.Tile.Tile;
import Model.Units.NonCombat.NonCombat;
import Model.Units.NonCombat.Settler;
import Model.Units.NonCombat.Worker;
import Model.Units.TypeEnums.UnitType;
import Model.Units.Unit;
import Model.Units.Combat.Combat;
import Model.Units.Combat.Ranged;
import Model.Units.Combat.Siege;
import Model.Units.TypeEnums.CombatType;
import Model.Units.TypeEnums.MainType;
import Model.Units.TypeEnums.UnitStateType;
import View.GraphicViewController.GameplayGraphicController;

public class UnitController {
    private static UnitController unitController;
    private UnitController(){};
    public static UnitController getInstance(){
        if(unitController == null)
            unitController = new UnitController();
        return unitController;
    }


    public boolean selectUnit(Tile tile, boolean isCombatUnit){
        int x = tile.getX();
        int y = tile.getY();
        if(x > GameMap.getInstance().getMapWidth() -1 || y > GameMap.getInstance().getMapHeight() -1)return false;
        ArrayList<Unit> tileUnits;
        if((tileUnits = MapFunctions.getInstance().getTile(x , y).getUnits()) == null)return false;
        for (Unit unit:tileUnits) {
            if((unit.getUnitType().mainType == MainType.NONCOMBAT && !isCombatUnit) || (!(unit.getUnitType().mainType == MainType.NONCOMBAT) && isCombatUnit)){
                if(unit.getCivilization() != GameController.getInstance().getPlayerTurn())return false;
                GameController.getInstance().setSelectedUnit(unit);
                return true;
            }
        }
        return false;
    }

    public void restoreUnitMovementLeft(){
        for (Unit unit : GameController.getInstance().getPlayerTurn().getUnits()) {
            if(GameController.getInstance().getPlayerTurn() == unit.getCivilization()){
                if(GameMap.getInstance().getMovingUnits().contains(unit))
                    unit.moveUnit();
            }
        }
    }

    private void assignPathToUnit(Tile tile){
        int destinationX = tile.getX(),destinationY = tile.getY();
        Tile origin = GameController.getInstance().getSelectedUnit().getTile();
        Tile destination = MapFunctions.getInstance().getTile(destinationX , destinationY);
        Graph graph = new Graph(GameMap.getInstance().getInitialGraph());
        graph = Movement.getInstance().calculateShortestPathFromSource(graph,graph.getNode(origin));
        GameController.getInstance().getSelectedUnit().setPath(graph.getNode(destination).getShortestPath());
        if(GameController.getInstance().getSelectedUnit().getPath().size() > 0)
            GameController.getInstance().getSelectedUnit().getPath().remove(0);
        GameController.getInstance().getSelectedUnit().addNodeToPath(graph.getNode(destination));
    }

    private String checkInitMoveUnitErrors(int destinationX , int destinationY,Tile destination){
        if(GameController.getInstance().getSelectedUnit() == null)
            return "no selected unit";
        else if(GameController.getInstance().getSelectedUnit().getMovementsLeft() == 0)
            return "no movement left";
        else if(destinationX > GameMap.getInstance().getMapWidth() - 1 || destinationY > GameMap.getInstance().getMapHeight() - 1)
            return "invalid coordinates";
        else if(MapFunctions.getInstance().getTile(destinationX, destinationY).getUnits().size() != 0 && MapFunctions.getInstance().getTile(destinationX, destinationY).getUnits().get(0).getCivilization() != GameController.getInstance().getSelectedUnit().getCivilization())
            return "tile contains a unit that isnt from your civilization";
        else if(MapFunctions.getInstance().getTile(destinationX, destinationY).getUnits().size() == 2)
            return "tile is full";
        else if(MapFunctions.getInstance().getTile(destinationX, destinationY).getUnits().size() == 1 
                && ((MapFunctions.getInstance().getTile(destinationX, destinationY).getUnits().get(0).getUnitType().mainType != MainType.NONCOMBAT &&
                GameController.getInstance().getSelectedUnit().getUnitType().mainType != MainType.NONCOMBAT) || 
                (MapFunctions.getInstance().getTile(destinationX, destinationY).getUnits().get(0).getUnitType().mainType == MainType.NONCOMBAT &&
                GameController.getInstance().getSelectedUnit().getUnitType().mainType == MainType.NONCOMBAT)))
            return "connot move to tile because tile contains a unit of same type";
        else if(MapFunctions.getInstance().getTile(destinationX, destinationY).getUnits().size() != 0 && MapFunctions.getInstance().getTile(destinationX, destinationY).getUnits().get(0).getCivilization() != GameController.getInstance().getSelectedUnit().getCivilization())
            return "tile contains a unit that isnt from your civilization";
        else if(destination.getTerrain().equals(TerrainType.Ocean) ||
                destination.getTerrain().equals(TerrainType.Mountain) ||
                (destination.isCapital() && (destination.getCivilization() != GameController.getInstance().getSelectedUnit().getCivilization())) ||
                (destination.getFeature() != null && destination.getFeature().getFeatureType().equals(FeatureType.Ice))){
                    return "destination is invalid.";   
        }
        return null;
    }

    public String initMoveUnit(Tile tile){
        int destinationX = tile.getX(),destinationY = tile.getY();
        String result;
        if((result = checkInitMoveUnitErrors(destinationX, destinationY, MapFunctions.getInstance().getTile(destinationX , destinationY))) != null)
            return result;
        assignPathToUnit(tile);
        if(GameController.getInstance().getSelectedUnit() instanceof Worker){
            ((Worker)GameController.getInstance().getSelectedUnit()).stop();
        }
        GameController.getInstance().getSelectedUnit().moveUnit();
        GameController.getInstance().setSelectedUnit(null);
        return "moving...";
    }

    public String checkAndBuildCity(String cityName) {
        Unit selectedUnit = GameController.getInstance().getSelectedUnit();
        if (selectedUnit == null) 
        return "no unit is selected";
        if(selectedUnit.getMovementsLeft() == 0)    
            return "no movements left";
        if (selectedUnit.getUnitType() == UnitType.Settler) {
            selectedUnit = (Settler) selectedUnit;
            if (selectedUnit.getTile().getCivilization() != null && selectedUnit.getTile().getCivilization() != selectedUnit.getCivilization() ) 
                return "this tile belongs to another civilization";
            for (Tile surrounding : MapFunctions.getInstance().getSurroundings(selectedUnit.getTile())) {
                if (surrounding.getCivilization() != null && surrounding.getCivilization() != selectedUnit.getCivilization() ||
                    (surrounding.getUnits().size() > 0 && surrounding.getUnits().get(0).getCivilization() != selectedUnit.getCivilization())) {
                    return "these tiles belong to another civilization or contain a unit of other civilization";
                }
            }
            if(selectedUnit.getCivilization().getCities() != null)
                for (City city : selectedUnit.getCivilization().getCities()) {
                    for (Tile tile : city.getCityTiles()) {
                        if (tile != null && tile == selectedUnit.getTile()) {
                            return "these Tile belongs to another city of yours";
                        }
                    }
                }
            ((Settler) selectedUnit).buildCity(cityName);
            removeUnitFromGame(selectedUnit);
            GameController.getInstance().setSelectedUnit(null);
            return "your new city is built";
        } return "you can only build new city with settler";
    }

//    private void BuildCity(Settler settler, String cityName){
//        settler.buildCity(cityName);
//    }

    public void makeUnit(UnitType unitType, Civilization civilization,Tile tile){
        Unit unit;
        if(unitType == UnitType.Settler)
            unit = new Settler(civilization, tile);
        else if(unitType == UnitType.Worker)
            unit = new Worker(civilization, tile);
        else if(unitType.mainType == MainType.NONCOMBAT)
            unit = new Unit(civilization, tile, unitType);
        else if(unitType.mainType == MainType.NONRANGED) 
            unit = new Combat(civilization,tile,unitType);
        else if(unitType.mainType == MainType.RANGED)
            unit = new Ranged(civilization, tile, unitType);
        else
            unit = new Siege(civilization, tile, unitType);
//        civilization.addUnit(unit);
        tile.getUnits().add(unit);
        unit.setCivilization(civilization);
        GameMap.getInstance().getUnits().add(unit);
        TileVisibilityController.getInstance().changeVision(tile,civilization.getSeenBy(),1,2);
    }


    private int getXFromMatcher(Matcher matcher){
        return Integer.parseInt(matcher.group("destinationX"));
    }
    private int getYFromMatcher(Matcher matcher){
        return Integer.parseInt(matcher.group("destinationY"));
    }

    public String fortify(){
        Unit unit = GameController.getInstance().getSelectedUnit();
        if(checkFortifyErrors() == false)
            return "you cannot fortify a unit with this combat types.";
        else{
            unit.fortify();
            return "unit fortified!";
        }
    }
    private boolean checkFortifyErrors(){
        Unit unit = GameController.getInstance().getSelectedUnit();
        if(unit.getUnitType().combatType == CombatType.Mounted || 
        unit.getUnitType().combatType == CombatType.Armored ||
        unit.getUnitType().combatType == CombatType.Civilian)
            return false;
            return true;
    }
    public String sleep(){
        GameController.getInstance().getSelectedUnit().sleep();
        return "unit is sleep!";
    }
    public String fortifyUntilHealed(){
        if(checkFortifyErrors() == false)
            return "you cannot fortify a unit with this combat types.";
        GameController.getInstance().getSelectedUnit().fortifyUntilHealed();
        return "unit is sleep!";
    }
    public String alert(){
        GameController.getInstance().getSelectedUnit().alert();
        return "unit is alert!";
    }
    public String combat(Tile tile){
        int y = tile.getY();
        int x = tile.getX();
        String errorMassege;
        if((errorMassege = combatErrors(y,x)) != null)
            return errorMassege;
        if(GameController.getInstance().getSelectedUnit().getUnitType().canMeleeAttack && tile.getCity() == null)
            return meleeCombat(tile);
        else if(!GameController.getInstance().getSelectedUnit().getUnitType().canMeleeAttack && !tile.isCapital())
            return rangedCombat(tile);
        else if(tile.getCity() != null && tile.isCapital() && GameController.getInstance().getSelectedUnit().getUnitType().canMeleeAttack)
            return cityMeleeAttack(tile);
        else if(tile.getCity() != null && tile.isCapital() && !GameController.getInstance().getSelectedUnit().getUnitType().canMeleeAttack)
            return cityRangedAttack(tile);
        return "your selection was invalid";
    }
    private String combatErrors(int y,int x){
        Tile tile = MapFunctions.getInstance().getTile(x, y);
        if(GameController.getInstance().getSelectedUnit() == null)
            return "unit not selected yet";
        if(GameController.getInstance().getSelectedUnit().getMovementsLeft() == 0)
            return "no movement left";
        if(x > GameMap.getInstance().getMapWidth() -1 || y > GameMap.getInstance().getMapHeight() -1)
            return "invalid coordinates";
        if(MapFunctions.getInstance().getTile(x, y).getTerrain() == TerrainType.Ocean || MapFunctions.getInstance().getTile(x, y).getTerrain() == TerrainType.Mountain)
            return "this position is either mountain or ocean";
        if(GameController.getInstance().getSelectedUnit().getUnitType().mainType == MainType.NONCOMBAT)
            return "unit is not of the type that can perform an attack";
        if( MapFunctions.getInstance().getTile(x, y).getUnits().size() == 0 && tile.isCapital() == false)
            return "selected tile does not contain a unit nor is it a city";
        if( MapFunctions.getInstance().getTile(x, y).getUnits().size() > 0  &&
            MapFunctions.getInstance().getTile(x, y).getUnits().get(0).getCivilization() ==  GameController.getInstance().getSelectedUnit().getCivilization() ||
            (tile.isCapital() && tile.getCivilization() == GameController.getInstance().getSelectedUnit().getCivilization()))
            return "selected tile contains a unit of same civilization as unit selected or your own city";
        if((GameController.getInstance().getSelectedUnit() instanceof Combat) && ((Combat) GameController.getInstance().getSelectedUnit()).isHasAttacked())
            return "no attacks left";
        if((GameController.getInstance().getSelectedUnit() instanceof Siege) && ((Siege)GameController.getInstance().getSelectedUnit()).isPreAttackDone() == false)
            return "pre attack not done yet";
        return null;
    }
    //melee attack
    public String meleeCombat(Tile tile){
        String errorMassege;
        if((errorMassege = meleeAttackErrors(tile)) != null)
            return errorMassege;
        else {
            Combat attacker = (Combat)GameController.getInstance().getSelectedUnit();
            Combat combatDefender = MapFunctions.getInstance().getTile(tile.getX(),tile.getY()).getCombatUnitOnTile();
            if(combatDefender == null){
                //target is a non combat -> capture
                Unit nonCombatUnit = MapFunctions.getInstance().getTile(tile.getX(),tile.getY()).getUnits().get(0);
                attacker.captureCivilian(nonCombatUnit);
                attacker.setHasAttacked(true);
                if(attacker.getUnitType().canMoveAfterAttack == false)
                    attacker.setMovementsLeft(0);
                GameController.getInstance().setSelectedUnit(null);
                return "civilian captured";
            }else{
                //target is a combat -> melee attack
                double damageToDefender =  attacker.attackDamage(combatDefender);
                double damageToAttacker = combatDefender.defendDamage(attacker);
                changesAfterMeleeCombat(attacker, combatDefender,damageToAttacker,damageToDefender);
                GameController.getInstance().setSelectedUnit(null);
                return "attack completed";
            }
        }
    }
    private String meleeAttackErrors(Tile tile) {
        if( MapFunctions.getInstance().getSurroundings(GameController.getInstance().getSelectedUnit().getTile()).contains(tile) == false)
            return "this type of unit cannot melee attack";
        return null;
    }
    public String rangedCombat(Tile tile){
        String errorMassege;
        if((errorMassege = rangedCombatErrors(tile)) != null)
            return errorMassege;
        Ranged attacker = (Ranged)GameController.getInstance().getSelectedUnit();
        Combat unitDefender = MapFunctions.getInstance().getTile(tile.getX(),tile.getY()).getCombatUnitOnTile();
        if(unitDefender == null){
            Unit nonCombat = MapFunctions.getInstance().getTile(tile.getX(),tile.getY()).getUnits().get(0);
            removeUnitFromGame(nonCombat);
        }else{
            double damageToDefender = attacker.attackDamage(unitDefender);
            unitDefender.changeHitPoint(-damageToDefender);
            if(unitDefender.getHitPoints() < 0){
                removeUnitFromGame(unitDefender);
                attacker.addXp(15);
            }else
                unitDefender.addXp(15);
            attacker.setHasAttacked(true);
            if(attacker.getUnitType().canMoveAfterAttack == false)
                attacker.setMovementsLeft(0);
        }
        attacker.addXp(15);
        return "ranged attack successful";
    }
    private String rangedCombatErrors(Tile tile) {
        if(!TileVisibilityController.getInstance().findVisibles(tile, 0, new HashMap<>()).containsKey(tile) ||
           TileVisibilityController.getInstance().findVisibles(tile, 0, new HashMap<>()).get(tile) > 2)
            return "tile is out of range";
        return null;
    }
    public String cityMeleeAttack(Tile tile){
        String errorMassege;
        if((errorMassege = rangedCombatErrors(tile)) != null)
            return errorMassege;
        Combat attacker = (Combat)GameController.getInstance().getSelectedUnit();
        double damageToCity = attacker.cityAttackDamage(tile.getCity());
        double damageToUnit = damageToCity * 1.5;
        tile.getCity().changeHitPoint(-damageToCity);
        attacker.changeHitPoint(-damageToUnit);
        attacker.addXp(15);
        if(attacker.getHitPoints() < 0)
            removeUnitFromGame(attacker);
        if(tile.getCity().getHitPoint() < 0){
            GameController.getInstance().setSelectedCityToAttack(tile.getCity());
            attacker.addXp(15);
            return "city was taken choose annex or destroy";
        }
        attacker.setHasAttacked(true);
        if(attacker.getUnitType().canMoveAfterAttack == false)
            attacker.setMovementsLeft(0);
        return "melee attack on city successful";
    }
    public String changesAfterCityVictory(Matcher matcher){
        if(GameController.getInstance().getSelectedCityToAttack() == null)
            return "city not conquerored yet";
        if(matcher.group("decision").equals("annex")){
            annexCity(GameController.getInstance().getSelectedUnit(), GameController.getInstance().getSelectedCityToAttack());
            GameController.getInstance().setSelectedUnit(null);
            return "city annexed successfully!";
        } else if(matcher.group("decision").equals("destroy")){
            destroyCity(GameController.getInstance().getSelectedCityToAttack());
            GameController.getInstance().setSelectedUnit(null);
            return "city destroyed!";
        }
        return "invalid command";
    }
    private void destroyCity(City selectedCityToAttack) {
        for (Tile tile : selectedCityToAttack.getCityTiles()) {
            tile.restoreTile();
        }
        TileVisibilityController.getInstance().changeVision(GameController.getInstance().getSelectedUnit().getTile(), GameController.getInstance().getSelectedUnit().getCivilization().getSeenBy(), -1, 2);
        GameController.getInstance().getSelectedUnit().getTile().getUnits().remove(GameController.getInstance().getSelectedUnit());
        TileVisibilityController.getInstance().changeVision(GameController.getInstance().getSelectedUnit().getTile(), GameController.getInstance().getSelectedUnit().getCivilization().getSeenBy(), 1, 2);
        selectedCityToAttack.getTile().getUnits().add(GameController.getInstance().getSelectedUnit());
        selectedCityToAttack.getCivilization().getCities().remove(selectedCityToAttack);
        selectedCityToAttack = null;
    }
    public String cityRangedAttack(Tile tile){
        String errorMassege;
        if((errorMassege = rangedCombatErrors(tile)) != null)
            return errorMassege;
        Ranged attacker = (Ranged)GameController.getInstance().getSelectedUnit();
        double damageToCity =  attacker.cityAttackDamage(tile.getCity());
        if(attacker.getUnitType().mainType == MainType.SIEGE && ((Siege)attacker).isPreAttackDone() == false)
            return "pre attack isnt done";
        tile.getCity().changeHitPoint(-damageToCity);
        attacker.addXp(15);
        if(tile.getCity().getHitPoint() < 0){
            tile.getCity().setHitPoint(1);
            attacker.addXp(15);
        }
        attacker.setHasAttacked(true);
        if(attacker.getUnitType().canMoveAfterAttack == false)
            attacker.setMovementsLeft(0);
        return "ranged attack on city successful";
    }
    private void annexCity(Unit attacker,City city) {
        city.getCivilization().changeGold(-20);
        if(city.getGarrisonUnit() != null)
            removeUnitFromGame(city.getGarrisonUnit());
        city.setCivilization(attacker.getCivilization());
        for (Tile tile : city.getCityTiles()) {
            tile.setCivilization(attacker.getCivilization());
        }
        attacker.getCivilization().addCity(city);
        TileVisibilityController.getInstance().changeVision(GameController.getInstance().getSelectedUnit().getTile(), GameController.getInstance().getSelectedUnit().getCivilization().getSeenBy(), -1, 2);
        GameController.getInstance().getSelectedUnit().getTile().getUnits().remove(GameController.getInstance().getSelectedUnit());
        TileVisibilityController.getInstance().changeVision(GameController.getInstance().getSelectedUnit().getTile(), GameController.getInstance().getSelectedUnit().getCivilization().getSeenBy(), 1, 2);
        city.getTile().getUnits().add(GameController.getInstance().getSelectedUnit());
        attacker.getCivilization().changeGold(20);
        attacker.getCivilization().changeHappiness(-1);
    }

    public double calculateDamageDealtToAttacker(Combat attacker,Combat defender){
        return (calculateDamageDeltToDefendingUnit(attacker, defender) * (1 / CalculateStrengthRatio(attacker,defender)));
 
    }

    private double CalculateStrengthRatio(Combat attacker,Combat defender){
        double finalAttackerDamage = (attacker.getMaxDamage() * (calculateBonusesForAttackingUnit(attacker) + 100)) / 100;
        double finalDefenderDamage = 1,bonus = 0;
        if(defender.getUnitType().hasDefensiveBonuses)
            bonus = calculateBonusesForDefendingUnit(attacker,defender);
        finalDefenderDamage = (defender.getMaxDamage() * (bonus + 100) / 100);
        return  finalAttackerDamage / finalDefenderDamage;
    }

    private double CalculateStrengthCityRatio(Combat attacker,City city){
        double finalAttackerDamage = (attacker.getMaxDamage() * (calculateBonusesForAttackingUnit(attacker) + 100)) / 100;
        return  finalAttackerDamage / city.calculateMaxCityDamage();
    }
    public double calculateDamageDeltToDefendingUnit(Combat attacker,Combat defender){
        double ratio = CalculateStrengthRatio(attacker,defender);
        Random noiseRandom = new Random();
        double damage;
        if(ratio >= 1){
            damage = this.calculateDamagePerRatio(ratio)  * (1 - ((10 - attacker.getHitPoints()) * 5 / 100.0)) * ((90.0 +noiseRandom.nextInt(20)) / 100);
        }else{
            ratio = 1 / ratio;
            damage = calculateMeleeDamageRatio(ratio) * calculateDamagePerRatio(ratio) * (1 - ((10 - attacker.getHitPoints()) * 5 / 100.0)) * ((90.0 +noiseRandom.nextInt(20)) / 100);
        }
        return  damage;
    }
    public double calculateDamageDeltToCity(Combat attacker,City city){
        double ratio = CalculateStrengthCityRatio(attacker,city);
        Random noiseRandom = new Random();
        double damage;
        if(ratio >= 1){
            damage = this.calculateDamagePerRatio(ratio)  * (1 - ((10 - attacker.getHitPoints()) * 5 / 100.0)) * ((90.0 +noiseRandom.nextInt(20)) / 100);
        }else{
            ratio = 1 / ratio;
            damage = (calculateMeleeDamageRatio(ratio) / ((ratio) * 3)) * calculateDamagePerRatio(ratio) * (1 - ((10 - attacker.getHitPoints()) * 5 / 100.0)) * ((90.0 +noiseRandom.nextInt(20)) / 100);
        }
        return  damage;
    }
    public double calculateDamagePerRatio(double ratio){
        return (3 * ratio);
    }

    public double calculateMeleeDamageRatio(double ratio){
        return 0.751673 * ratio + 2.50903;
    }

    private double calculateBonusesForAttackingUnit(Combat attacker){
        double bonus = 0;
        bonus += getTerrainAndFeatureBonusAttacker(attacker);
        return bonus;
    }

    private double calculateBonusesForDefendingUnit(Combat attacker,Combat defender){
        double bonus = 0;
        bonus += calculateTerrainAndFeatureBonusDefender(defender);
        bonus += calculateFortifiedBonus(defender);
        if(MapPrinter.getInstance().hasRiverBetween(attacker.getTile(), defender.getTile()) && attacker.getUnitType().mainType == MainType.NONRANGED)
            bonus+= 40;
        if(attacker.getUnitType().combatType == CombatType.Mounted && 
        (defender.getUnitType() == UnitType.Spearman || defender.getUnitType() == UnitType.Pikeman))
            bonus += 100;
        return bonus;
    }

    private double getTerrainAndFeatureBonusAttacker(Combat attacker){
        if(attacker.getTile().getTerrain() == TerrainType.Hill){
            return 40;
        }
        return 0;
    }

    private double calculateTerrainAndFeatureBonusDefender(Combat defender){
        if(defender.getTile().getTerrain() == TerrainType.Hill ||
           (defender.getTile().getFeature() != null && defender.getTile().getFeature().getFeatureType() == FeatureType.Jungle) || 
           (defender.getTile().getFeature() != null && defender.getTile().getFeature().getFeatureType() == FeatureType.Forest)){
            return 40;
        }
        return 0;
    }

    private double calculateFortifiedBonus(Combat unit){
        if(unit.getFortifiedTurnCount() > 0)
            return unit.getFortifiedTurnCount() * 25;
        return 0;
    }
    public String removeUnitFromGame(Unit unit){
        if(unit == null)
            return "unit does not exist or unit not selected";
        GameMap.getInstance().getUnits().remove(unit);
        unit.getCivilization().getUnits().remove(unit);
        unit.getTile().getUnits().remove(unit);
        return "unit deleted!";
    }
    public void changesAfterMeleeVictory(Combat victor,Combat loser){
        TileVisibilityController.getInstance().changeVision(victor.getTile(), victor.getCivilization().getSeenBy(), -1, 2);
        victor.getTile().getUnits().remove(victor);
        victor.setTile(loser.getTile());
        loser.getTile().getUnits().add(victor);
        removeUnitFromGame(loser);
        TileVisibilityController.getInstance().changeVision(victor.getTile(), victor.getCivilization().getSeenBy(), 1, 2);
        victor.addXp(30);
        if(victor.getUnitType().canMoveAfterAttack != true)
            victor.setMovementsLeft(0);
    }
    public void changesAfterMeleeCombat(Combat attacker,Combat defender,double damageToAttacker,double damageToDefender){
        attacker.setHasAttacked(true);
        attacker.changeHitPoint(-damageToAttacker);
        defender.changeHitPoint(-damageToDefender);
        if(defender.getHitPoints() <= 0 || attacker.getHitPoints() <= 0){
            if(defender.getHitPoints() <= 0 && attacker.getHitPoints() <= 0){
                removeUnitFromGame(defender);
                removeUnitFromGame(attacker);
            }else{
                Combat victor = new Combat(),loser = new Combat();
                if(defender.getHitPoints() <= 0 ){
                    victor = attacker;loser = defender;
                }
                else if(attacker.getHitPoints() <= 0){
                    victor = defender;loser = attacker;
                }
                changesAfterMeleeVictory(victor, loser);
            }   
        } else{
            if(attacker.getUnitType().canMoveAfterAttack != true)
                attacker.setMovementsLeft(0);
            attacker.addXp(15);defender.addXp(15);
        }
    }
    public String cityUnitAttack(Matcher matcher){
        int y = Integer.parseInt(matcher.group("y"));
        int x = Integer.parseInt(matcher.group("x"));
        String errorMassege;
        Tile tile = MapFunctions.getInstance().getTile(x, y);
        if((errorMassege = cityAttackErrors(tile)) != null)
            return errorMassege;
        City city = GameController.getInstance().getSelectedCity();
        if(tile.getCombatUnitOnTile() != null &&tile.getCombatUnitOnTile().getUnitType().mainType != MainType.NONCOMBAT){
            Combat defender = tile.getCombatUnitOnTile();
            double damageTounit =  city.cityAttackDamage(defender);
            defender.changeHitPoint(-damageTounit);
            defender.addXp(15);
            if(defender.getHitPoints() <= 0){
                removeUnitFromGame(defender);
            }
        }
        else if(tile.getCombatUnitOnTile().getUnitType().mainType == MainType.NONCOMBAT)
            removeUnitFromGame(GameController.getInstance().getSelectedUnit());
        city.setHasAttackLeft(true);
        return "attack was successful.";
    }
    private String cityAttackErrors(Tile tile) {
        if(tile == null)
            return "selected tile isnt valid";
        if(GameController.getInstance().getSelectedCity() == null)
            return "city not selected";
        if(!GameController.getInstance().getSelectedCity().isHasAttackLeft())
            return "already used attack";
        if(tile.getUnits().size() == 0)
            return "tile does not contain a unit";
        if(tile.getUnits().size() > 0 && tile.getUnits().get(0).getCivilization() == GameController.getInstance().getSelectedCity().getCivilization())
            return "unit is of the same civilization";
        if(!TileVisibilityController.getInstance().findVisibles(tile, 0, new HashMap<>()).containsKey(tile) ||
            TileVisibilityController.getInstance().findVisibles(tile, 0, new HashMap<>()).get(tile) > 2)
            return "tile is out of range";

        return null;
    }
    public void updateUnitDataAfterRound(Unit unit){
        if(unit instanceof Combat combatUnit){
            if(unit.getUnitStateType() == UnitStateType.FORTIFYUNTILHEALED){
                if(combatUnit.getHitPoints() < 10)
                    combatUnit.setHitPoints(combatUnit.getHitPoints() + 2);
                if(combatUnit.getHitPoints() > 10)
                    combatUnit.setHitPoints(10);
            }
            combatUnit.setHasAttacked(false);
        }
//        if(GameController.getInstance().getPlayerTurn().getUser().getUsername().equals("nima"))
        System.out.println("1- unit movementsLeft: " + unit.getMovementsLeft() + "movementsLeft: " + unit.getUnitType().movement);
        unit.movementsLeft =  2;
    }
    public void updateAllUnitData(){
        for (Unit unit : GameMap.getInstance().getUnits()) {
            if(unit.getCivilization() == GameController.getInstance().getPlayerTurn())
                UnitController.getInstance().updateUnitDataAfterRound(unit);
        }
    }

    public String siegePreAttack(){
        String errorMassege;
        if((errorMassege = siegePreAttackErrors()) != null)
            return errorMassege;
        ((Siege)GameController.getInstance().getSelectedUnit()).setPreAttackDone(true);
        ((Siege)GameController.getInstance().getSelectedUnit()).setMovementsLeft(0);
        return "pre attack done";
    }
    private String siegePreAttackErrors() {
        if(GameController.getInstance().getSelectedUnit() == null)
            return "unit not selected yet";
        else if(GameController.getInstance().getSelectedUnit().getMovementsLeft() == 0)
            return "no movement left";
        if(!(GameController.getInstance().getSelectedUnit() instanceof Siege))
            return "selected unit is not a siege unit";
        if(((Siege)GameController.getInstance().getSelectedUnit()).isPreAttackDone())
            return "pre attack already done";
        return null;
    }

    public String buildImprovementMatcher(Matcher matcher){
        Unit selected = GameController.getInstance().getSelectedUnit();
        if(selected == null)return "no unit is selected";
        if(!selected.getUnitType().equals(UnitType.Worker))return "you didn't choose a worker";
        ImprovementType improvementType;
        if(matcher.group("ImprovementType").equals("Camp"))improvementType = ImprovementType.Camp;
        else if(matcher.group("ImprovementType").equals("Farm"))improvementType = ImprovementType.Farm;
        else if(matcher.group("ImprovementType").equals("LumberMill"))improvementType = ImprovementType.LumberMill;
        else if(matcher.group("ImprovementType").equals("Mine"))improvementType = ImprovementType.Mine;
        else if(matcher.group("ImprovementType").equals("Pasture"))improvementType = ImprovementType.Pasture;
        else if(matcher.group("ImprovementType").equals("Plantation"))improvementType = ImprovementType.Plantation;
        else if(matcher.group("ImprovementType").equals("Quarry"))improvementType = ImprovementType.Quarry;
        else if(matcher.group("ImprovementType").equals("TradingPost"))improvementType = ImprovementType.TradingPost;
        else if(matcher.group("ImprovementType").equals("ManuFactory"))improvementType = ImprovementType.ManuFactory;
        else return "not a valid improvement";
        selected = new NonCombat(selected.getCivilization(), selected.getTile(), selected.getUnitType());
        selected = new Worker(selected.getCivilization(), selected.getTile());
        Worker worker = (Worker) selected;
        return worker.buildImprovement(improvementType);
    }

    public String buildRoadMatcher(Matcher matcher){
        Unit selected = GameController.getInstance().getSelectedUnit();
        if(selected == null)return "no unit is selected";
        if(!selected.getUnitType().equals(UnitType.Worker))return "you didn't choose a worker";
        RoadType roadType;
        if(matcher.group("RoadType").equals("RailWay"))roadType = RoadType.RailWay;
        else if(matcher.group("RoadType").equals("Road"))roadType = RoadType.Road;
        else return "not a valid road type";
        selected = new NonCombat(selected.getCivilization(), selected.getTile(), selected.getUnitType());
        selected = new Worker(selected.getCivilization(), selected.getTile());
        Worker worker = (Worker) selected;
        return worker.buildRoad(roadType);
    }

    public String stopWorker(){
        Unit selected = GameController.getInstance().getSelectedUnit();
        if(selected == null)return "no unit is selected";
        if(!selected.getUnitType().equals(UnitType.Worker))return "you didn't choose a worker";
        selected = new NonCombat(selected.getCivilization(), selected.getTile(), selected.getUnitType());
        selected = new Worker(selected.getCivilization(), selected.getTile());
        Worker worker = (Worker) selected;
        return worker.stop();
    }

    public String RORImatcher(){
        Unit selected = GameController.getInstance().getSelectedUnit();
        if(selected == null)return "no unit is selected";
        if(!selected.getUnitType().equals(UnitType.Worker))return "you didn't choose a worker";
        selected = new NonCombat(selected.getCivilization(), selected.getTile(), selected.getUnitType());
        selected = new Worker(selected.getCivilization(), selected.getTile());
        Worker worker = (Worker) selected;
        return worker.resumeBuildingOrRepairOfImprovements();
    }

    public String RORRmatcher(){
        Unit selected = GameController.getInstance().getSelectedUnit();
        if(selected == null)return "no unit is selected";
        if(!selected.getUnitType().equals(UnitType.Worker))return "you didn't choose a worker";
        return ((Worker)selected).resumeBuildingOrRepairOfRoads();
    }

    public String clearFeature(){
        Unit selected = GameController.getInstance().getSelectedUnit();
        if(selected == null)return "no unit is selected";
        if(!selected.getUnitType().equals(UnitType.Worker))return "you didn't choose a worker";
//        selected = new NonCombat(selected.getCivilization(), selected.getTile(), selected.getUnitType());
//        selected = new Worker(selected.getCivilization(), selected.getTile());
//        Worker worker = (Worker) selected;
        return ((Worker)selected).clearFeature();
    }

    public String destroyRoad(){
        Unit selected = GameController.getInstance().getSelectedUnit();
        if(selected == null)return "no unit is selected";
        if(!selected.getUnitType().equals(UnitType.Worker))return "you didn't choose a worker";
        return ((Worker)selected).destroyRoad();
    }

    public String pillage() {
        String errorMassege;
        if((errorMassege = checkPillageErrors()) != null)
            return errorMassege;
        GameController.getInstance().getSelectedUnit().getTile().getImprovement().setRuined(true);
        if(GameController.getInstance().getSelectedUnit().getTile().getResource() != null)GameController.getInstance().getSelectedUnit().getTile().getResource().setAvailable(false);
        return null;
    }
    private String checkPillageErrors() {
        if(GameController.getInstance().getSelectedUnit() == null)
            return "unit not selected yet";
        if(GameController.getInstance().getSelectedUnit().getTile().getCity() == null)
            return "unit is not in any particular city";
        if(GameController.getInstance().getSelectedUnit().getTile().getCity().getCivilization() == GameController.getInstance().getSelectedUnit().getCivilization())
            return "unit is in its own civilization";    
        if(GameController.getInstance().getSelectedUnit().getTile().getImprovement() == null)
            return "tile does not contain an improvement"; 
        return null;
    }
    public String garrisonUnit() {
        String errorMassege;
        if((errorMassege = garrisonUnitErrors()) != null)
            return errorMassege;
        City garrisonCity = new City(GameController.getInstance().getSelectedUnit().getCivilization());
        for (City city : GameController.getInstance().getPlayerTurn().getCities()) {
            if(city.getTile() == ((Combat)GameController.getInstance().getSelectedUnit()).getTile())
                garrisonCity = city; 
        }
        garrisonCity.setGarrisonUnit(GameController.getInstance().getSelectedUnit());
        return "unit garrissoned successfully";
    }
    private String garrisonUnitErrors() {
        boolean isOnACity = false;
        if(GameController.getInstance().getSelectedUnit() == null)
            return "unit is not selected";
        if((GameController.getInstance().getSelectedUnit() instanceof Combat) == false )
            return "unit cannot be garrisonned";
        for (City city : GameController.getInstance().getPlayerTurn().getCities()) {
            if(city.getTile() == ((Combat)GameController.getInstance().getSelectedUnit()).getTile())
                isOnACity = true;
        }
        if(isOnACity == false)
            return "unit is not on a city tile";
        return null;
    }
}
