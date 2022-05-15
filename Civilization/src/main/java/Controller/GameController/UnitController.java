package Controller.GameController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import Controller.GameController.MapControllers.MapFunctions;
import java.util.Random;
import Controller.GameController.MapControllers.TileVisibilityController;
import Model.CivlizationRelated.City;
import Model.CivlizationRelated.Civilization;
import Model.Enums.MapEnum;
import Model.MapRelated.GameMap;
import Model.Movement.Graph;
import Model.Technology.Technology;
import Model.TileRelated.Building.Building;
import Model.TileRelated.Building.BuildingType;
import Model.TileRelated.Feature.FeatureType;
import Model.TileRelated.Improvement.Improvement;
import Model.TileRelated.Improvement.ImprovementType;
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

public class UnitController {
    private static UnitController unitController;
    private UnitController(){};
    public static UnitController getInstance(){
        if(unitController == null)
            unitController = new UnitController();
        return unitController;
    }

    public String selectUnit(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        boolean isCombatUnit = true;
        if(matcher.group("type").equals("civil"))isCombatUnit = false;
        if(x > MapEnum.MAPWIDTH.amount -1 || y > MapEnum.MAPHEIGHT.amount -1)return "invalid coordinates";
        ArrayList<Unit> tileUnits;
        if((tileUnits = MapFunctions.getInstance().getTile(x , y).getUnits()) == null)return "no units on this tile";
        for (Unit unit:tileUnits) {
            if((unit.getUnitType().mainType == MainType.NONCOMBAT && !isCombatUnit) || (!(unit.getUnitType().mainType == MainType.NONCOMBAT) && isCombatUnit)){
                if(unit.getCivilization() != GameController.getInstance().getPlayerTurn())return "selected unit does not belong to your civilization!";
                if(unit.getMovementsLeft() == 0)return "no movement left";
                GameController.getInstance().setSelectedUnit(unit);
                return "unit selected";
            }
        }
        return "unit not found";
    }

    public void restoreUnitMovementLeft(){
        for (Unit unit : GameController.getInstance().getPlayerTurn().getUnits()) {
            if(GameController.getInstance().getPlayerTurn() == unit.getCivilization()){
                if(GameMap.getInstance().getMovingUnits().contains(unit))
                    unit.moveUnit();
            }
        }
    }

    private void assignPathToUnit(Matcher matcher){
        int destinationX = Integer.parseInt(matcher.group("destinationX")),destinationY = Integer.parseInt(matcher.group("destinationY"));
        Tile origin = GameController.getInstance().getSelectedUnit().getTile();
        Tile destination = MapFunctions.getInstance().getTile(destinationX , destinationY);
        Graph graph = new Graph(GameMap.getInstance().getInitialGraph());
        graph = Movement.getInstance().calculateShortestPathFromSource(graph,graph.getNode(origin));
        GameController.getInstance().getSelectedUnit().setPath(graph.getNode(destination).getShortestPath());
        GameController.getInstance().getSelectedUnit().getPath().remove(0);
        GameController.getInstance().getSelectedUnit().addNodeToPath(graph.getNode(destination));
    }

    private String checkInitMoveUnitErrors(int destinationX , int destinationY,Tile destination){
        if(GameController.getInstance().getSelectedUnit() == null)return "no selected unit";
        else if(destinationX > MapEnum.MAPWIDTH.amount-1 || destinationY > MapEnum.MAPWIDTH.amount -1)return "invalid coordinates";
        else if(destination.getTerrain().equals(TerrainType.Ocean) ||
        destination.getTerrain().equals(TerrainType.Mountain) ||
             (destination.getFeature() != null && destination.getFeature().getFeatureType().equals(FeatureType.Ice))){
         return "destination is invalid.";   
        }
        return null;
    }

    public String initMoveUnit(Matcher matcher){
        int destinationX = getXFromMatcher(matcher),destinationY = getYFromMatcher(matcher);
        String result;
        if((result = checkInitMoveUnitErrors(destinationX, destinationY, MapFunctions.getInstance().getTile(destinationX , destinationY))) != null)
            return result;
        assignPathToUnit(matcher);
        GameController.getInstance().getSelectedUnit().moveUnit();
        GameController.getInstance().setSelectedUnit(null);
        return "moving...";
    }

    public String checkAndBuildCity(Matcher matcher) {
        Unit selectedUnit = GameController.getInstance().getSelectedUnit();
        if (selectedUnit == null) return "no unit is selected";
        if (selectedUnit.getUnitType() == UnitType.Settler) {
            Tile tile1 = selectedUnit.getTile();
            tile1.getUnits().remove(selectedUnit);
            selectedUnit = new NonCombat(selectedUnit.getCivilization(), selectedUnit.getTile(), selectedUnit.getUnitType());
            selectedUnit = new Settler(selectedUnit.getCivilization(), selectedUnit.getTile());
            if (selectedUnit.getTile().getCivilization() == selectedUnit.getCivilization() ) {
                for (Tile surrounding : MapFunctions.getInstance().getSurroundings(selectedUnit.getTile())) {
                    if (surrounding.getCivilization() != null && surrounding.getCivilization() != selectedUnit.getCivilization()) {
                        return "these tiles belong to another civilization";
                    }
                }
                if(selectedUnit.getCivilization().getCities() != null)
                    for (City city : selectedUnit.getCivilization().getCities()) {
                        for (Tile tile : city.getCityTiles()) {
                            if (tile != null && tile == selectedUnit.getTile()) {
                                return "these Tile belongs to another city";
                            }
                        }
                    }
                String cityName = matcher.group("cityName");
                BuildCity((Settler) selectedUnit, cityName);
                GameController.getInstance().setSelectedUnit(null);
                return "your new city is built";
            } return "this tile belongs to another civilization";
        } return "you can only build new city with settler";
    }

    private void BuildCity(Settler settler, String cityName){
        settler.buildCity(cityName);
        settler.getCivilization().getUnits().remove(settler);
    }

    public void makeUnit(UnitType unitType, Civilization civilization , Tile tile){
        Unit unit = new Unit(civilization,tile,unitType);
        civilization.addUnit(unit);
        tile.getUnits().add(unit);
        tile.setCivilization(civilization);
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
    public String combat(Matcher matcher){
        int y = Integer.parseInt(matcher.group("y"));
        int x = Integer.parseInt(matcher.group("x"));
        String errorMassege;
        if((errorMassege = combatErrors(y,x)) != null)
            return errorMassege;
        Tile tile = MapFunctions.getInstance().getTile(x, y);
        if(GameController.getInstance().getSelectedUnit().getUnitType().canMeleeAttack)
          meleeCombat(tile);
        else if(GameController.getInstance().getSelectedUnit().getUnitType().canMeleeAttack == false && tile.isCapital() == false)
            rangedCombat(tile);
        else if(tile.isCapital() == true && GameController.getInstance().getSelectedUnit().getUnitType().canMeleeAttack == true)
            cityMeleeAttack(tile);
        else if(tile.isCapital() == true && GameController.getInstance().getSelectedUnit().getUnitType().canMeleeAttack == false)
            cityRangedAttack(tile);
        return null;
    }
    private String combatErrors(int y,int x){
        Tile tile = MapFunctions.getInstance().getTile(x, y);
        if(GameController.getInstance().getSelectedUnit() == null)
            return "unit not selected yet";
        if(x > MapEnum.MAPWIDTH.amount -1 || y > MapEnum.MAPHEIGHT.amount -1)
            return "invalid coordinates";
        if(MapFunctions.getInstance().getTile(x, y).getTerrain() == TerrainType.Ocean || MapFunctions.getInstance().getTile(x, y).getTerrain() == TerrainType.Mountain)
            return "this position is either mountain or ocean";
        if(GameController.getInstance().getSelectedUnit().getUnitType().mainType == MainType.NONCOMBAT)
            return "unit cannot perform a attack";
        if( MapFunctions.getInstance().getTile(x, y).getUnits().size() == 0 && tile.isCapital() == false)
            return "selected tile does not contain a unit neither is it a city";
        if( MapFunctions.getInstance().getTile(x, y).getUnits().get(0).getCivilization() ==  GameController.getInstance().getSelectedUnit().getCivilization() ||
            (tile.isCapital() && tile.getCivilization() == GameController.getInstance().getSelectedUnit().getCivilization()))
            return "selected tile contains a unit of same civilization as unit selected or your own city";
        if(((Combat) GameController.getInstance().getSelectedUnit()).isHasAttacked())
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
        Combat attacker = (Combat)GameController.getInstance().getSelectedUnit();
        Combat combatDefender = MapFunctions.getInstance().getTile(tile.getX(),tile.getY()).getCombatUnitOnTile();
        double damageToDefender =  attacker.attackDamage(combatDefender);
        combatDefender.changeHitPoint(-damageToDefender);
        attacker.addXp(15);
        if(combatDefender.getHitPoints() < 0){
            removeUnitFromGame(combatDefender);
            attacker.addXp(15);
        }else
            combatDefender.addXp(15);
        return "ranged attack successful";
    }
    private String rangedCombatErrors(Tile tile) {
        if(TileVisibilityController.getInstance().findVisibles(tile, 0, new HashMap<Tile,Integer>()).containsKey(tile) == false ||
           TileVisibilityController.getInstance().findVisibles(tile, 0, new HashMap<Tile,Integer>()).get(tile) > 2)
            return "tile is out of range";
        return null;
    }
    public String cityMeleeAttack(Tile tile){
        String errorMassege;
        if((errorMassege = rangedCombatErrors(tile)) != null)
            return errorMassege;
        Ranged attacker = (Ranged)GameController.getInstance().getSelectedUnit();
        double damageToCity =  attacker.cityAttackDamage(tile.getCity());
        double damageToUnit = damageToCity * (1 / CalculateStrengthCityRatio(attacker, tile.getCity()));
        tile.getCity().changeHitPoint(-damageToCity);
        attacker.changeHitPoint(-damageToUnit);
        attacker.addXp(15);
        if(attacker.getHitPoints() < 0)
            removeUnitFromGame(attacker);
        if(tile.getCity().getHitPoint() < 0){
            changeCityOwnership(attacker, tile.getCity());
            attacker.addXp(15);
        }
        return "melee attack on city successful";
    }
    public String cityRangedAttack(Tile tile){
        String errorMassege;
        if((errorMassege = rangedCombatErrors(tile)) != null)
            return errorMassege;
        Ranged attacker = (Ranged)GameController.getInstance().getSelectedUnit();
        double damageToCity =  attacker.cityAttackDamage(tile.getCity());
        tile.getCity().changeHitPoint(-damageToCity);
        attacker.addXp(15);
        if(tile.getCity().getHitPoint() < 0){
            tile.getCity().setHitPoint(1);
            attacker.addXp(15);
        }
        return "ranged attack on city successful";
    }
    private void changeCityOwnership(Unit attacker,City city) {
        if(city.getGarrisonUnit() != null)
            removeUnitFromGame(city.getGarrisonUnit());
        city.setCivilization(attacker.getCivilization());
        for (Tile tile : city.getCityTiles()) {
            tile.setCivilization(attacker.getCivilization());
        }
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
            damage = calculateMeleeDamageRatio(ratio) * calculateDamagePerRatio(ratio) * (1 - ((10 - attacker.getHitPoints()) * 5 / 100.0)) * ((90.0 +noiseRandom.nextInt(20)) / 100);
        }
        return  damage;
    }
    public double calculateDamagePerRatio(double ratio){
        return (3 * ratio);
    }

    public double calculateMeleeDamageRatio(double ratio){
        return (1.78379 * Math.pow(ratio,2) - 3.2216 * ratio + 2.52562);
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
    public void removeUnitFromGame(Unit unit){
        GameMap.getInstance().getUnits().remove(unit);
        unit.getCivilization().getUnits().remove(unit);
        unit.getTile().getUnits().remove(unit);
    }
    public void changesAfterMeleeVictory(Combat victor,Combat loser){
        victor.getTile().getUnits().remove(victor);
        victor.setTile(loser.getTile());
        loser.getTile().getUnits().add(victor);
        removeUnitFromGame(loser);
        victor.addXp(30);
        if(victor.getUnitType().canMoveAfterAttack != true)
            victor.setMovementsLeft(0);
    }
    public void changesAfterMeleeCombat(Combat attacker,Combat defender,double damageToAttacker,double damageToDefender){
        attacker.setHasAttacked(true);
        attacker.changeHitPoint(-damageToAttacker);
        defender.changeHitPoint(-damageToDefender);
        if(defender.getHitPoints() < 0 || attacker.getHitPoints() < 0){
            if(defender.getHitPoints() < 0 && attacker.getHitPoints() < 0){
                removeUnitFromGame(defender);
                removeUnitFromGame(attacker);
            }else{
                Combat victor = new Combat(),loser = new Combat();
                if(defender.getHitPoints() < 0 ){
                    victor = attacker;loser = defender;
                }
                else if(attacker.getHitPoints() < 0){
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
    public void updateUnitDataAfterRound(Unit unit){
        if(unit instanceof Combat){
            Combat combatUnit = ((Combat) unit);
            if(unit.getUnitStateType() == UnitStateType.FORTIFYUNTILHEALED){
                if(combatUnit.getHitPoints() < 10)
                    combatUnit.setHitPoints(combatUnit.getHitPoints() + 2);
                if(combatUnit.getHitPoints() > 10)
                    combatUnit.setHitPoints(10);
            }
            combatUnit.setHasAttacked(false);
        }
        unit.setMovementsLeft(unit.getUnitType().movement);
    }
    public void updateAllUnitData(){
        for (Unit unit : GameMap.getInstance().getUnits()) {
            UnitController.getInstance().updateUnitDataAfterRound(unit);   
        }
    }

    public String siegePreAttack(){
        String errorMassege;
        if((errorMassege = siegePreAttackErrors()) != null)
            return errorMassege;
        ((Siege)GameController.getInstance().getSelectedUnit()).setPreAttackDone(true);
        return null;
    }
    private String siegePreAttackErrors() {
        if((GameController.getInstance().getSelectedUnit() instanceof Siege) == false)
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
}
