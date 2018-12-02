package io.github.devwillee.koreametrograph.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetroEdgeMapper {
    public Map<String, List<MetroEdge>> parse(String jsonPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode edgesRoot = mapper.readTree(new File(jsonPath));

        Map<String, List<MetroEdge>> edgesByLineNum = new HashMap<>();

        edgesRoot.fields().forEachRemaining(edgeNodesByLineNum -> {
            String lineNum = edgeNodesByLineNum.getKey();
            JsonNode edgeNodes = edgeNodesByLineNum.getValue();

            ArrayList<MetroEdge> edges = new ArrayList<>();

            for(JsonNode edge : edgeNodes) {
                Station from = new Station(edge.findValue("from").asText(), lineNum);
                Station to = new Station(edge.findValue("to").asText(), lineNum);
                int time = edge.findValue("time").asInt();
                int distance = edge.findValue("distance").asInt();

                edges.add(new MetroEdge(from, to, new MetroWeight(time, distance)));
            }

            edgesByLineNum.put(lineNum, edges);
        });

        return edgesByLineNum;
    }
}
