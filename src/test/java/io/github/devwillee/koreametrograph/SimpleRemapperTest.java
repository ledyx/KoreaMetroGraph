package io.github.devwillee.koreametrograph;

import io.github.devwillee.koreametrograph.remapper.SimpleRemapper;

import java.io.IOException;

public class SimpleRemapperTest {
    public static void main(String... args) throws IOException {
        //SimpleRemapper.remapVertices();
        SimpleRemapper.remapEdges();
        //Map<String, List<MetroEdge>> result = new MetroEdgeMapper().parse("src/main/resources/seoul/edges.json");
    }
}
