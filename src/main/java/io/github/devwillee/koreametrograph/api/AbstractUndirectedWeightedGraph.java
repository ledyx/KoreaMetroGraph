package io.github.devwillee.koreametrograph.api;

import io.github.devwillee.koreametrograph.api.graph.UndirectedWeightedGraph;
import io.github.devwillee.koreametrograph.api.graph.Vertex;
import io.github.devwillee.koreametrograph.api.graph.WeightedEdge;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

public abstract class AbstractUndirectedWeightedGraph<V extends Vertex, WE extends WeightedEdge, W> implements UndirectedWeightedGraph<V, WE, W> {

	/**
	 * 실질적인 Graph 구현체
	 */
	protected TreeMap<V, LinkedList<WE>> edgesByVertices = new TreeMap<>();

	@Override
	public void removeVertex(V... vertices) {
		for(V vertex : vertices) {
			if(!edgesByVertices.containsKey(vertex))
				throw new NullPointerException();

			edgesByVertices.remove(vertex);
		}
	}

	@Override
	public TreeMap<V, LinkedList<WE>> getGraph() {
		return edgesByVertices;
	}

	@Override
	public LinkedList<WE> getEdges(V vertex) {
		return edgesByVertices.get(vertex);
	}

	@Override
	public int getVertextCount() {
		return edgesByVertices.size();
	}

	@Override
	public int getEdgeCount(V vertex) {
		return edgesByVertices.get(vertex).size();
	}

	@Override
	public int getTotalEdgeCount() {
		Iterator<LinkedList<WE>> iter = edgesByVertices.values().iterator();
		
		int size = 0;
		while(iter.hasNext()) {
			size += iter.next().size();
		}

		return size;
	}

	@Override
	public boolean isExists(V vertex) {
		// 방향 그래프일 경우
		// A-B 관계는 존재하지만, B-A 관계는 없으므로
		// 각 Vertex의 Edge들을 모두 검색해야 함.
		for(LinkedList<WE> vertices : edgesByVertices.values()) {
			for(WE WE : vertices) {
				if(WE.contains(vertex))
					return true;
			}
		}

		return false;
	}

	@Override
	public void clear() {
		edgesByVertices.clear();
	}

	@Override
	public TreeMap<V, LinkedList<WE>> getGraphWithSymmetryRemoved() {
		TreeMap<V, LinkedList<WE>> graphClone = new TreeMap<>();
		graphClone.putAll(edgesByVertices);

		// 대칭 그래프 삭제
		for(LinkedList<WE> edges1 : edgesByVertices.values()) {
			for(WE edge1 : edges1) {
				LinkedList<WE> edges2 = graphClone.get(edge1.getToVertex());
				for(int i=0 ; i<edges2.size() ; i++) {

					WE edge2 = edges2.get(i);
					if(edge2.getToVertex().equals(edge1.getFromVertex()))
						edges2.remove();

				}
			}
		}

		return graphClone;
	}

	@Override
	public int compareVertices(V vertex1, V vertex2) {
		if(vertex1 == null || vertex2 == null)
			throw new IllegalArgumentException("Null is not available.");
		else if(!vertex1.getClass().getTypeName().equals(vertex2.getClass().getTypeName()))
			throw new IllegalArgumentException("Mismatched types.");

		if(vertex1 instanceof Comparable)
			return ((Comparable<V>) vertex1).compareTo(vertex2);
		else
			return vertex1.hashCode() < vertex2.hashCode() ? -1 : (vertex1.hashCode() == vertex2.hashCode() ? 0 : 1);
		
	}

	protected void sort(LinkedList<WE> edges, boolean isAscending) {
		Collections.sort(edges, (o1, o2) -> isAscending?
				compareVertices((V) o1.getToVertex(), (V) o2.getToVertex())
				: compareVertices((V) o2.getToVertex(), (V) o1.getToVertex()));
	}

	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		for(V vertex : edgesByVertices.keySet()) {
			sb.append(vertex);
			sb.append("에 연결된 정점 : ");
			
			LinkedList<WE> list = edgesByVertices.get(vertex);
			sort(list, true);
			
			for(WE edge : list) {
				sb.append(edge.getFromVertex());
				sb.append("-");
				sb.append(edge.getToVertex());
				
				sb.append(" ");
			}
			
			sb.append("\n");
		}
		
		return sb.toString();
	}
}
