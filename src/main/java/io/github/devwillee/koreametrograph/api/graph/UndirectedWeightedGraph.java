package io.github.devwillee.koreametrograph.api.graph;

public interface UndirectedWeightedGraph<V extends Vertex, WE extends WeightedEdge, W> extends UndirectedGraph<V, WE> {
    void addEdge(V fromVertex, V toVertex, W weight) throws NullPointerException;
}
