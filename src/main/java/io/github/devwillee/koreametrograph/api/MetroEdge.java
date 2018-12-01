package io.github.devwillee.koreametrograph.api;

import io.github.devwillee.koreametrograph.api.graph.Edge;
import io.github.devwillee.koreametrograph.api.graph.Vertex;
import io.github.devwillee.koreametrograph.api.graph.WeightedEdge;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MetroEdge implements WeightedEdge<Station, MetroWeight> {
    private Station fromVertex;
    private Station toVertex;
    private MetroWeight weight;

    public MetroEdge(Station fromVertex, Station toVertex, MetroWeight metroWeight) {
        this.fromVertex = fromVertex;
        this.toVertex = toVertex;
        this.weight = metroWeight;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof MetroEdge))
            throw new IllegalArgumentException();

        MetroEdge target = (MetroEdge) obj;

        boolean cond1 = this.fromVertex.equals(target.fromVertex) && this.toVertex.equals(target.toVertex);
        boolean cond2 = this.fromVertex.equals(target.toVertex) && this.toVertex.equals(target.fromVertex);
        return cond1 || cond2;
    }

    @Override
    public boolean contains(Vertex vertex) {
        return fromVertex.equals(vertex) || toVertex.equals(vertex);
    }

    @Override
    public boolean checkSymmetry(Edge edge) {
        return this.fromVertex.equals(edge.getToVertex()) && this.toVertex.equals(edge.getFromVertex());
    }
}
