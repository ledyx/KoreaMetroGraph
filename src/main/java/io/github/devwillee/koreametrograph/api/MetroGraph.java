package io.github.devwillee.koreametrograph.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.*;

public final class MetroGraph extends AbstractUndirectedWeightedGraph<Station, MetroEdge, MetroWeight> {

    private final ObjectMapper mapper = new ObjectMapper();

    private List<Station> vertices;
    @Getter
    private final Map<String, List<MetroEdge>> edgesByLineNum;

    public MetroGraph(String verticesJsonPath, String edgesJsonPath) throws IOException {
        // Vertices 생성
        JsonNode verticesRoot = mapper.readTree(new File(verticesJsonPath)).get("DATA");
        vertices = mapper.convertValue(verticesRoot, new TypeReference<List<Station>>(){});

        // Edges 생성
        edgesByLineNum = new HashMap<>();
        JsonNode edgesRoot = mapper.readTree(new File(edgesJsonPath));
        edgesRoot.fields().forEachRemaining(edgeNodesByLineNum -> {
            String lineNum = edgeNodesByLineNum.getKey();
            JsonNode edgeNodes = edgeNodesByLineNum.getValue();

            ArrayList<MetroEdge> edges = new ArrayList<>();

            for(JsonNode edge : edgeNodes) {
                Station from = new Station(edge.findValue("from").asText(), lineNum);
                Station to = new Station(edge.findValue("to").asText(), lineNum);
                int time = edge.findValue("time").asInt();
                int distance = edge.findValue("distance").asInt();

                MetroWeight weight = new MetroWeight(time, distance);

                addVertex(from, to);
                addEdge(from, to, weight);

                edges.add(new MetroEdge(from, to, new MetroWeight(time, distance)));
            }

            edgesByLineNum.put(lineNum, edges);
        });
    }

    private final String[] lineNums = {"A", "B", "E", "G", "I", "I2", "K", "KK", "S", "SU", "U", "UI", "M", "W"};

    public void setSubLine(String fromStationName, String toStationName, String lineNum) {
        for(MetroEdge e : getEdges(fromStationName)) {
            if(e.getTo().getStationName().equals(toStationName) && e.getTo().getLineNum().equals(lineNum)) {
                e.getTo().setMainLine(false);
                break;
            }
        }
    }

    // from->to, to->from edge 모두 삭제
    public void removeSymmetryEdges(String fromStationName, String toStationName, String lineNum) {
        removeEdge(fromStationName, toStationName, lineNum);
        removeEdge(toStationName, fromStationName, lineNum);
    }

    /**
     * JSON 파일을 읽어 역정보, 경위도등의 부가정보를 채워 넣는다.
     * @param station
     */
    private Station enrichWithJSON(Station station) {
        return vertices.stream()
                .filter(x -> x.getStationName().equalsIgnoreCase(
                        station.getStationName()) &&
                        x.getLineNum().equalsIgnoreCase(station.getLineNum()))
                .findFirst().orElse(null);
    }

    @Override
    public void addVertex(Station... vertices) {
        for(Station vertex : vertices) {
            //enrichWithJSON(vertex);

            //이미 vertex가 존재하는가?
            if(edgesByVertices.containsKey(vertex))
                continue;

            edgesByVertices.put(enrichWithJSON(vertex), new LinkedList<>());
        }
    }

    public LinkedList<MetroEdge> getEdges(String stationName) {
        return super.getEdges(new Station(stationName, ""));
    }

    // 일반 간선 추가
    @Override
    public void addEdge(Station from, Station to, MetroWeight weight) throws NullPointerException {
        if (!edgesByVertices.containsKey(from))
            throw new NullPointerException("'from' is not exists.\n" + from);

        if (!edgesByVertices.containsKey(to))
            throw new NullPointerException("'to' is not exists.\n" + to);

        // 중복 추가 방지 (정점 하나에 이어진 정점이 2개 이상 포함될 수가 없으므로)
        LinkedList<MetroEdge> edges = edgesByVertices.get(from);
        for (MetroEdge edge : edges) {
            if(edge.equals(from, to)) {
                StringBuilder errorMessageBuilder = new StringBuilder();
                errorMessageBuilder.append("This Edge is already added.");

                errorMessageBuilder.append("\nfrom : ");
                errorMessageBuilder.append("{station_nm : ");
                errorMessageBuilder.append(from.getStationName());
                errorMessageBuilder.append(", line_num : ");
                errorMessageBuilder.append(from.getLineNum());
                errorMessageBuilder.append("}");

                errorMessageBuilder.append("\nto : ");
                errorMessageBuilder.append("{station_nm : ");
                errorMessageBuilder.append(to.getStationName());
                errorMessageBuilder.append(", line_num : ");
                errorMessageBuilder.append(to.getLineNum());
                errorMessageBuilder.append("}");

                throw new IllegalArgumentException(errorMessageBuilder.toString());
            }

        }








        Station enrichedFromVertex = enrichWithJSON(from);
        Station enrichedToVertex = enrichWithJSON(to);

        // fromVertex의 default Identifier는 "Current"이므로 fromVertex의 Identifier 설정하지 않음.
        enrichedToVertex.setIdentifier(Identifier.NEXT);

        //LinkedList<MetroEdge> edges = edgesByVertices.get(enrichedFromVertex);

        MetroEdge newEdge = new MetroEdge(enrichedFromVertex, enrichedToVertex, weight);

        // 중복 추가 방지 (정점 하나에 이어진 정점이 2개 이상 포함될 수가 없으므로)
        /*if(edges.contains(newEdge)) {
            throw new IllegalArgumentException("This Edge is an already added.\n" + newEdge);
        }*/

        edges.add(newEdge);

        addSymmetryEdge(enrichedToVertex, enrichedFromVertex, weight);
    }

    // 무방향 그래프 대칭 처리
    private void addSymmetryEdge(Station toVertex, Station fromVertex, MetroWeight weight) {
        if (!edgesByVertices.containsKey(toVertex))
            edgesByVertices.put(toVertex, new LinkedList<>());

        // 각 vetex의 identifier 재설정을 위한 참조 문제 해결
        Station toVertexCopy = toVertex.clone();
        Station fromVertexCopy = fromVertex.clone();

        toVertexCopy.setIdentifier(Identifier.CURRENT);
        fromVertexCopy.setIdentifier(Identifier.PREVIOUS);

        LinkedList<MetroEdge> edges = edgesByVertices.get(toVertexCopy);
        edges.add(new MetroEdge(toVertexCopy, fromVertexCopy, weight));
    }

    @Override
    public void removeEdge(Station from, Station to) {
        LinkedList<MetroEdge> edges = edgesByVertices.get(from);

        int result = Integer.MIN_VALUE;

        for(int i=0 ; i<edges.size() ; i++) {
            MetroEdge e = edges.get(i);

            if(e.getTo().getStationName().equals(to.getStationName())
                    && e.getTo().getLineNum().equals(to.getLineNum())) {
                result = i;
                break;
            }
        }

        if(result < 0)
            throw new NullPointerException("Not found");

        edges.remove(result);
    }

    public void removeEdge(String fromStationName, String toStationName, String lineNum) {
        removeEdge(new Station(fromStationName, lineNum), new Station(toStationName, lineNum));
    }

    private boolean checkLineNum(String lineNum) {
        boolean result = false;

        try {
            int lineNum1 = Integer.parseInt(lineNum);
            return 1 <= lineNum1 && lineNum1 <= 9;
        } catch (Exception e) {
            for(String lineNum1 : lineNums) {
                if(lineNum1.equals(lineNum)) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Depth First Search
     */
    public LinkedList<Station> dfs(Station vertex, boolean isAscending) {
        if(!checkLineNum(vertex.getLineNum()))
            throw new IllegalArgumentException("Unavailable lineNum");

        LinkedList<Station> results = new LinkedList<>();

        HashSet<Station> checkVisitSet = new HashSet<>();
        LinkedList<Station> stack = new LinkedList<>();

        //첫 번째 Node 방문
        Station firstVertex = enrichWithJSON(vertex);
        stack.push(firstVertex);
        checkVisitSet.add(firstVertex);

        while(!stack.isEmpty()) {
            Station poppedVertex = stack.pop();
            results.add(poppedVertex);

            sort(edgesByVertices.get(poppedVertex), !isAscending);

            for(MetroEdge edge : edgesByVertices.get(poppedVertex)) {

                Station linkedVertex = edge.getTo();
                linkedVertex = edgesByVertices.ceilingKey(linkedVertex); //추가

                if(!checkVisitSet.contains(linkedVertex)) {
                    checkVisitSet.add(linkedVertex);
                    stack.push(linkedVertex);
                }
            }
        }

        return results;
    }

    /**
     * Depth First Search
     */
    public LinkedList<Station> dfs(boolean isAscending) {
        return dfs(edgesByVertices.firstEntry().getKey(), isAscending);
    }

    /**
     * Beneath First Search
     */
    public LinkedList<Station> bfs(Station vertex, boolean isAscending) {
        if(!checkLineNum(vertex.getLineNum()))
            throw new IllegalArgumentException("Unavailable lineNum");

        LinkedList<Station> results = new LinkedList<>();

        HashSet<Station> checkVisitSet = new HashSet<>();

        Queue<Station> queue = new LinkedList<>();

        //첫 번째 Node 방문
        Station firstVertex = enrichWithJSON(vertex);
        queue.offer(firstVertex);
        checkVisitSet.add(firstVertex);

        while(!queue.isEmpty()) {
            Station dequeuedVertex = queue.poll();
            results.add(dequeuedVertex);

            sort(edgesByVertices.get(dequeuedVertex), isAscending);

            for(MetroEdge edge : edgesByVertices.get(dequeuedVertex)) {
                Station linkedVertex = edge.getTo();
                linkedVertex = edgesByVertices.ceilingKey(linkedVertex); //추가

                if(!checkVisitSet.contains(linkedVertex)) {
                    checkVisitSet.add(linkedVertex);
                    queue.offer(linkedVertex);
                }
            }
        }

        return results;
    }

    /**
     * Beneath First Search
     */
    public LinkedList<Station> bfs(boolean isAscending) {
        return bfs(edgesByVertices.firstEntry().getKey(), isAscending);
    }

    /**
     * Wrapping. 편하게 쓰기 위한 Helper Method.
     * @param stationName
     * @return
     */
    public List<Station> find(String stationName, String lineNum) {
        ArrayList<Station> list = new ArrayList<>();

        boolean isAddedCurrentStation = false;

        for(MetroEdge edge : getEdges(stationName)) {
            Station fromVertex = edge.getFrom();
            if(fromVertex.getLineNum().equals(lineNum) && !isAddedCurrentStation) {
                list.add(fromVertex);
                isAddedCurrentStation = true;
            }

            Station toVertex = edge.getTo();
            if(toVertex.getLineNum().equals(lineNum)) {
                list.add(toVertex);
            }
        }

        return list;
    }

    /**
     * Wrapping. 편하게 쓰기 위한 Helper Method.
     * @param stationName
     * @return
     */
    public List<Station> find(String stationName) {
        List<Station> list = new ArrayList<>();

        String currentLineNum = "";

        for(MetroEdge edge : getEdges(stationName)) {
            Station fromVertex = edge.getFrom();
            if(!fromVertex.getLineNum().equals(currentLineNum)) {
                currentLineNum = fromVertex.getLineNum();
                list.add(fromVertex);
            }

            list.add(edge.getTo());
        }

        return list;
    }
}
