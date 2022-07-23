package Model.Movement;

import Model.TileRelated.Tile.Tile;
import com.google.gson.annotations.Expose;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Node {

    @Expose
    private Tile tile;
    private List<Node> shortestPath = new LinkedList<>();
    @Expose
    private Double distance = Double.MAX_VALUE;

    Map<Node, Double> adjacentNodes = new HashMap<>();

    public void addDestination(Node destination, double distance) {
        adjacentNodes.put(destination, distance);
    }

    public Node(Tile tile) {
        this.tile = tile;
    }

    public List<Node> getShortestPath() {
        return shortestPath;
    }

    public Double getDistance() {
        return distance;
    }

    public Map<Node, Double> getAdjacentNodes() {
        return adjacentNodes;
    }


    public void setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public void setAdjacentNodes(Map<Node, Double> adjacentNodes) {
        this.adjacentNodes = adjacentNodes;
    }

    public Tile getTile() {
        return tile;
    }
}
