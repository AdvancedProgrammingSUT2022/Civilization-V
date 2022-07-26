package Controller.GameController;
import Model.Enums.MapEnum;
import Model.MapRelated.GameMap;
import Model.Movement.Graph;
import Model.Movement.Node;
import Model.TileRelated.Feature.FeatureType;
import Model.TileRelated.Road.RoadType;
import Model.TileRelated.Terraine.TerrainType;
import Model.TileRelated.Tile.Tile;
import Model.Units.TypeEnums.UnitType;

import java.util.*;

import Controller.GameController.MapControllers.MapFunctions;

public class Movement {
    private static Movement movement;
    private Movement(){}
    public static Movement getInstance(){
        if(movement == null)
            movement = new Movement();
        return movement;
    }
    public Graph calculateShortestPathFromSource(Graph graph, Node source) {
        source.setDistance((double) 0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry< Node, Double> adjacencyPair:
                    currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Double edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return graph;
    }

    private Node getLowestDistanceNode(Set < Node > unsettledNodes) {
        Node lowestDistanceNode = null;
        double lowestDistance = Double.MAX_VALUE;
        for (Node node: unsettledNodes) {
            double nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private void calculateMinimumDistance(Node evaluationNode,Double edgeWeigh, Node sourceNode) {
        Double sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    public Graph graphInit(GameMap gameMap){
        Graph graph = new Graph();
        for (int j = 1; j < gameMap.getMapHeight() -1 ; j++) {
            for (int i = 1; i < gameMap.getMapWidth() - 1; i++) {
                if(MapFunctions.getInstance().getTile(gameMap , i, j).getTerrain().equals(TerrainType.Mountain) ||
                MapFunctions.getInstance().getTile(gameMap , i, j).getTerrain().equals(TerrainType.Ocean))continue;
                Node node = new Node(MapFunctions.getInstance().getTile(gameMap , i, j));
                graph.addNode(node);
            }
        }
        for (Node thisNode:graph.getNodes()){
            for (Tile surrounding: MapFunctions.getInstance().getSurroundings(gameMap ,thisNode.getTile())) {
                if(surrounding == null || surrounding.getTerrain().equals(TerrainType.Ocean)
                        || surrounding.getTerrain().equals(TerrainType.Mountain) ||
                        (surrounding.getFeature() != null && surrounding.getFeature().getFeatureType().equals(FeatureType.Ice)))continue;
                thisNode.addDestination(graph.getNode(surrounding),calculateDistance(thisNode.getTile(),surrounding,UnitType.AntiTankGun));
            }
        }
        return graph;
    }

    public double calculateDistance(Tile origin, Tile destination,UnitType unitType){
        double mp = 0;
        mp += destination.getTerrain().movementCost;
        if(destination.getFeature() != null)
            mp += destination.getFeature().getFeatureType().movementCost;
        if(origin.getTerrain().equals(TerrainType.Hill) && destination.getTerrain().equals(TerrainType.Hill))mp --;
        if(destination.getRoad() != null && destination.getRoad().getDaysToComplete() ==0 && !destination.getRoad().isRuined()){
            if(destination.getRoad().getRoadType().equals(RoadType.Road))mp -= (RoadType.Road.mpReduction * mp);
            if(destination.getRoad().getRoadType().equals(RoadType.RailWay))mp -= (RoadType.RailWay.mpReduction * mp);
        }
        if(unitType == UnitType.Scout)mp = 1;
        return mp;
    }
}
