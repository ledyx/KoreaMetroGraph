package io.github.devwillee.koreametrograph.remapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.devwillee.koreametrograph.api.Station;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SimpleRemapper {
    public static void remapVertices() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File("src/test/resources/seoul/vertices.json")).get("DATA");

        List<Station> temp = mapper.convertValue(root, new TypeReference<List<Station>>(){});

        Collections.sort(temp);
        Collections.sort(temp, Comparator.comparing(Station::getLineNum));

        mapper.writerWithDefaultPrettyPrinter().withRootName("DATA").writeValue(new File("src/test/resources/seoul/vertices_minimal.json"), temp);
        mapper.writer().withRootName("DATA").writeValue(new File("src/test/resources/seoul/vertices_minimal_simple.json"), temp);
    }

    public static void remapEdges() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File("src/test/resources/seoul/edges.json"));

        mapper.writer().writeValue(new File("src/test/resources/seoul/edges_simple.json"), root);
    }
}
