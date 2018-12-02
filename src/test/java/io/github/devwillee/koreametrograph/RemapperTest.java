package io.github.devwillee.koreametrograph;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.devwillee.koreametrograph.api.*;
import io.github.devwillee.koreametrograph.city.seoul.SeoulMetroGraphFactory;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.TreeMap;

public class RemapperTest {
    public static void main(String... args) throws IOException {
        MetroGraph seoulMetro = MetroGraphFactory.create(SeoulMetroGraphFactory.class);
        TreeMap<Station, LinkedList<MetroEdge>> graph = seoulMetro.getGraph();

        ObjectMapper mapper = new ObjectMapper();
        //mapper.writeValue(new File("src/test/resources/seoul2_min.json"), graph);
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/test/resources/out/seoul3.json"), graph);
    }
}
