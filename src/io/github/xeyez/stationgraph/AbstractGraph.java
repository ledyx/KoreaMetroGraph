package io.github.xeyez.stationgraph;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

public abstract class AbstractGraph<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public class Edge implements Serializable {
		private static final long serialVersionUID = 1L;
		
		protected T fromVertex;
		protected T toVertex;
		
		public Edge(T fromVertex, T toVertex) {
			this.fromVertex = fromVertex;
			this.toVertex = toVertex;
		}
		
		public T getFromVertex() {
			return fromVertex;
		}
		
		public T getToVertex() {
			return toVertex;
		}
		
		
		@Override
		public String toString() {
			return "Edge [fromVertex=" + fromVertex + ", toVertex=" + toVertex + "]";
		}

		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof AbstractGraph.Edge))
				throw new IllegalArgumentException();
			
			Edge target = (Edge) obj;
			
			boolean cond1 = this.fromVertex.equals(target.fromVertex) && this.toVertex.equals(target.toVertex);
			boolean cond2 = this.fromVertex.equals(target.toVertex) && this.toVertex.equals(target.fromVertex);
			return cond1 || cond2;
		}
		
		public boolean contains(T vertex) {
			return fromVertex.equals(vertex) || toVertex.equals(vertex);
		}
		
		public boolean checkSymmetry(Edge target) {
			return this.fromVertex.equals(target.toVertex) && this.toVertex.equals(target.fromVertex);
		}
	}
	
	public enum GraphType {
		UNDIRECTED, DIRECTED
	}
	
	protected GraphType graphType;
	
	/**
	 * 실질적인 Graph 구현체
	 */
	protected TreeMap<T, LinkedList<Edge>> edgesByVertices = new TreeMap<>();
	
	public AbstractGraph() {
		this.graphType = GraphType.UNDIRECTED;
	}
	
	public AbstractGraph(GraphType graphType) {
		this.graphType = graphType;
	}
	
	//정점 추가
	public void addVertex(T... vertice) {
		for(T vertex : vertice) {
			
			//이미 vertex가 존재하는가?
			if(edgesByVertices.containsKey(vertex))
				continue;
				
			edgesByVertices.put(vertex, new LinkedList<>());
		}
	}
	
	protected void sort(LinkedList<Edge> edges, boolean isAscending) {
		Collections.sort(edges, new Comparator<Edge>() {

			@Override
			public int compare(Edge o1, Edge o2) {
				return isAscending? compareVertice(o1.getToVertex(), o2.getToVertex()) : compareVertice(o2.getToVertex(), o1.getToVertex());
			}
		});
	}
	
	public TreeMap<T, LinkedList<Edge>> getEdgesByVertices() {
		return edgesByVertices;
	}
	
	public LinkedList<Edge> getEdges(T vertex) {
		return edgesByVertices.get(vertex);
	}
	
	public T getVertex(T vertex) {
		if(!isExists(vertex))
			throw new NullPointerException();
		
		return getEdgesByVertices().ceilingKey(vertex);
	}
	
	public int getVertextCount() {
		return edgesByVertices.size();
	}

	public int getEdgeCount(T vertex) {
		return edgesByVertices.get(vertex).size();
	}
	
	public int getTotalEdgeCount() {
		Iterator<LinkedList<Edge>> iter = edgesByVertices.values().iterator();
		
		int size = 0;
		while(iter.hasNext()) {
			size += iter.next().size();
		}
		
		return size;
	}
	
	public void removeVertex(T vertex) {
		if(!edgesByVertices.containsKey(vertex))
			throw new NullPointerException();
		
		edgesByVertices.remove(vertex);
	}
	
	public boolean isExists(T vertex) {
		if(graphType == GraphType.UNDIRECTED)
			return edgesByVertices.containsKey(vertex);
		else {
			// 방향 그래프일 경우
			// A-B 관계는 존재하지만, B-A 관계는 없으므로
			// 각 Vertex의 Edge들을 모두 검색해야 함.
			for(LinkedList<Edge> vertices : edgesByVertices.values()) {
				for(Edge e : vertices) {
					if(e.contains(vertex))
						return true;
				}
			}
			
			return false;
		}
	}
	
	public void clear() {
		edgesByVertices.clear();
	}
	
	public TreeMap<T, LinkedList<Edge>> getRemovedSymmetryGraph() {
		if(graphType != GraphType.UNDIRECTED)
			return edgesByVertices;
		
		TreeMap<T, LinkedList<Edge>> edgesByVertexes_copy = new TreeMap<>();
		edgesByVertexes_copy.putAll(edgesByVertices);
		
		// 대칭 그래프 삭제
		for(LinkedList<Edge> edges1 : edgesByVertices.values()) {
			
			for(Edge edge1 : edges1) {
				
				LinkedList<Edge> edges2 = edgesByVertexes_copy.get(edge1.getToVertex());
				for(int i=0 ; i<edges2.size() ; i++) {
					
					Edge edge2 = edges2.get(i);
					if(edge2.getToVertex().equals(edge1.getFromVertex()))
						edges2.remove();
					
				}
			}
		}
		
		return edgesByVertexes_copy;
	}
	
	public LinkedList<Edge> getRemovedSymmetryGraph2() {
		LinkedList<Edge> list = new LinkedList<>();
		for(LinkedList<Edge> edges : edgesByVertices.values()) {
			list.addAll(edges);
		}
		
		if(graphType != GraphType.UNDIRECTED)
			return list;
		
		for(int i=0 ; i<list.size() ; i++) {
			Edge e1 = list.get(i);
			
			for(int j=0 ; j<list.size() ; j++) {
				Edge e2 = list.get(j);
				
				if(e1.checkSymmetry(e2))
					list.remove(j);
			}
		}
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public int compareVertice(T x, T y) {
		if(x == null || y == null)
			throw new IllegalArgumentException("Null is not available.");
		else if(!x.getClass().getTypeName().equals(y.getClass().getTypeName()))
			throw new IllegalArgumentException("Mismatched types.");

		if(x instanceof Comparable)
			return ((Comparable<T>) x).compareTo(y);
		else
			return x.hashCode() < y.hashCode() ? -1 : (x.hashCode() == y.hashCode() ? 0 : 1);
		
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		for(T vertex : edgesByVertices.keySet()) {
			sb.append(vertex);
			sb.append("에 연결된 정점 : ");
			
			LinkedList<Edge> list = edgesByVertices.get(vertex);
			sort(list, true);
			
			for(Edge edge : list) {
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
