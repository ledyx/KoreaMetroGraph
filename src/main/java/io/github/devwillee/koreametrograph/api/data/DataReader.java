package io.github.devwillee.koreametrograph.api.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.devwillee.koreametrograph.api.MetroEdge;
import io.github.devwillee.koreametrograph.api.MetroWeight;
import io.github.devwillee.koreametrograph.api.Station;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum DataReader {
    INSTANCE;

    public interface GraphBuilder {
        void build(Station from, Station to, MetroWeight weight);
    }

    private final ObjectMapper mapper = new ObjectMapper();

    public List<Station> parseVertices(String jsonPath) throws IOException {
        JsonNode verticesRoot = mapper.readTree(new File(jsonPath)).get("DATA");
        return mapper.convertValue(verticesRoot, new TypeReference<List<Station>>(){});
    }

    public Map<String, List<MetroEdge>> parseEdgesByLineNum(String jsonPath, GraphBuilder graphBuilder) throws IOException {
        Map<String, List<MetroEdge>> edgesByLineNum = new HashMap<>();

        JsonNode edgesRoot = mapper.readTree(new File(jsonPath));
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

                if (graphBuilder != null)
                    graphBuilder.build(from, to, weight);

                edges.add(new MetroEdge(from, to, weight));
            }

            edgesByLineNum.put(lineNum, edges);
        });

        return edgesByLineNum;
    }
}
