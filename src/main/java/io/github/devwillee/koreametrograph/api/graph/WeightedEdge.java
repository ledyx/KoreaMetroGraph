package io.github.devwillee.koreametrograph.api.graph;

public interface WeightedEdge<V extends Vertex, W> extends Edge<V> {
    W getWeight();
}
