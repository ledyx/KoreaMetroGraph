package io.github.devwillee.koreametrograph.api.graph;

public interface Edge<V extends Vertex> {
    V getFromVertex();
    V getToVertex();
    boolean contains(Vertex vertex);
    boolean checkSymmetry(Edge edge);
}
