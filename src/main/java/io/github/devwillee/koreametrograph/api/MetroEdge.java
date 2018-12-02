package io.github.devwillee.koreametrograph.api;

import io.github.devwillee.koreametrograph.api.graph.Edge;
import io.github.devwillee.koreametrograph.api.graph.Vertex;
import io.github.devwillee.koreametrograph.api.graph.WeightedEdge;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class MetroEdge implements WeightedEdge<Station, MetroWeight> {
    private Station from;
    private Station to;
    private MetroWeight weight;

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof MetroEdge))
            throw new IllegalArgumentException();

        MetroEdge target = (MetroEdge) obj;
        return equals(target.from, target.to);
    }

    @Override
    public boolean equals(Station from, Station to) {
        boolean cond1 = this.from.equals(from) && this.to.equals(to);
        boolean cond2 = this.from.equals(to) && this.to.equals(from);
        return cond1 || cond2;
    }

    @Override
    public boolean contains(Vertex vertex) {
        return this.from.equals(vertex) || this.to.equals(vertex);
    }

    @Override
    public boolean checkSymmetry(Edge edge) {
        return this.from.equals(edge.getTo()) && this.to.equals(edge.getFrom());
    }
}
