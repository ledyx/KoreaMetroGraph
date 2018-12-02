package io.github.devwillee.koreametrograph.api.graph;

public interface WeightedGraph<V extends Vertex, WE extends WeightedEdge, W> extends Graph<V, WE> {
    void addEdge(V from, V to, W weight) throws NullPointerException;
}
