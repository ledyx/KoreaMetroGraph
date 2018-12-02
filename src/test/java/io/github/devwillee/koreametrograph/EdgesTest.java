package io.github.devwillee.koreametrograph;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class EdgesTest {
    public static void main(String... args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File("src/test/resources/seoul/edges.json"));

        Map<String, List<Map<String, String>>> temp = mapper.convertValue(root, Map.class);

        temp.forEach((lineNum, edges) -> {
            System.out.println("lineNum : " + lineNum);
            edges.forEach(edge -> {

            });
        });
    }
}
