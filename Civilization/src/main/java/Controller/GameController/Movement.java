package Controller.GameController;

import Model.Enums.MapEnum;
import Model.Movement.Graph;
import Model.Movement.Node;
import Model.TileRelated.Feature.FeatureType;
import Model.TileRelated.Terraine.TerrainType;
import Model.TileRelated.Tile.Tile;

import java.util.*;

public class Movement {
    public static Graph calculateShortestPathFromSource(Graph graph, Node source) {
        source.setDistance(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry< Node, Integer> adjacencyPair:
                    currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return graph;
    }

    private static Node getLowestDistanceNode(Set < Node > unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node: unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private static void calculateMinimumDistance(Node evaluationNode,
                                                 Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    public static Graph graphInit(){
        Graph graph = new Graph();
        for (int j = 1; j < MapEnum.MAPHEIGHT.amount -1 ; j++) {
            for (int i = 1; i < MapEnum.MAPWIDTH.amount - 1; i++) {
                Node node = new Node(GameController.getTile(i, j));
                graph.addNode(node);
            }
        }
        for (Node thisNode:graph.getNodes()){
            for (Tile surrounding: GameController.getSurroundings(thisNode.getTile())) {
                if(surrounding == null || surrounding.getTerrain().equals(TerrainType.Ocean)
                        || surrounding.getTerrain().equals(TerrainType.Mountain) ||
                        (surrounding.getFeature() != null && surrounding.getFeature().equals(FeatureType.Ice)))continue;
                thisNode.addDestination(graph.getNode(surrounding),calculateDistance(thisNode.getTile(),surrounding));
            }
        }
        return graph;
    }

    private static int calculateDistance(Tile origin, Tile destination){
        int mp = destination.getTerrain().movementCost;
        if(destination.getFeature() != null)
            mp += destination.getFeature().mpCost;
        if(origin.getTerrain().equals(TerrainType.Hill) && destination.getTerrain().equals(TerrainType.Hill))mp --;
        return mp;
    }
}
