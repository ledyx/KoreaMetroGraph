package io.github.devwillee.koreametrograph.api.graph;

import io.github.devwillee.koreametrograph.api.Station;

public interface Edge<V extends Vertex> {
    V getFrom();
    V getTo();
    boolean contains(Vertex vertex);
    boolean checkSymmetry(Edge edge);
    boolean equals(Station from, Station to);
}
