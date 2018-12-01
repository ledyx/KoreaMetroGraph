package io.github.devwillee.koreametrograph.api.graph;

import java.util.LinkedList;
import java.util.TreeMap;

public interface Graph<V extends Vertex, E extends Edge> {
    TreeMap<V, LinkedList<E>> getGraph();
    void addVertex(V... vertices);
    void removeVertex(V... vertices);
    void removeEdge(V from, V to);
    LinkedList<E> getEdges(V vertex);
    int getVertextCount();
    int getEdgeCount(V vertex);
    int getTotalEdgeCount();
    boolean isExists(V vertex);
    void clear();
    TreeMap<V, LinkedList<E>> getGraphWithSymmetryRemoved();
    int compareVertices(V vertex1, V vertex2);
}
