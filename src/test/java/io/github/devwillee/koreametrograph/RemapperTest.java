package io.github.devwillee.koreametrograph;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.devwillee.koreametrograph.api.AbstractGraph;
import io.github.devwillee.koreametrograph.api.MetroGraph;
import io.github.devwillee.koreametrograph.api.MetroGraphFactory;
import io.github.devwillee.koreametrograph.api.Station;
import io.github.devwillee.koreametrograph.cities.seoul.SeoulMetroGraphFactory;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.TreeMap;

public class RemapperTest {
    public static void main(String... args) throws IOException {
        MetroGraph seoulMetro = MetroGraphFactory.create(SeoulMetroGraphFactory.class);
        TreeMap<Station, LinkedList<AbstractGraph<Station>.Edge>> graph = seoulMetro.getEdgesByVertices();

        ObjectMapper mapper = new ObjectMapper();
        //mapper.writeValue(new File("src/test/resources/seoul2_min.json"), graph);
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/test/resources/out/seoul3.json"), graph);
    }
}
