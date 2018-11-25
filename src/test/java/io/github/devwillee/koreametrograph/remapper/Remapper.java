package io.github.devwillee.koreametrograph.remapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Remapper {
    public static void format() throws IOException {
        String[] keys = {"station_cd", "line_num", "station_nm", "station_nm_eng", "station_nm_han", "xpoint_wgs", "ypoint_wgs"};
        // String[] order_lines = {"A", "KK", "SU", "I2", "U", "E", "UI"};

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File("src/test/resources/seoul.json")).get("DATA");

        List<Map<String, String>> temp = mapper.convertValue(root, List.class);


        temp.stream().filter(x -> x.get("station_nm").equalsIgnoreCase("신도림")
                &&
                x.get("line_num").equalsIgnoreCase("1"))
                .collect(Collectors.toList()).get(0);

        /*Map<String, Map<String, Map<String, String>>> result = new LinkedHashMap<>();

        String[] lines = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "E", "G", "I", "I2", "K", "KK", "S", "SU", "U", "UI"};
        for(String line : lines) {

            List<Map<String, String>> manipulatedInfos =
                    Stream.of(temp).flatMap(s -> s.stream())
                            .filter(e -> e.get("line_num").equalsIgnoreCase("1"))
                            .map(e -> {
                                Map<String, String> map = new LinkedHashMap<>();
                                for (String k : keys) {
                                    map.put(k, e.get(k));
                                }

                                return map;
                            }).sorted((o1, o2) -> o2.get("station_cd").compareTo(o1.get("station_cd")))
                            .filter(x -> x.get("xpoint_wgs") != null)
                            .collect(Collectors.toList());

            Map<String, Map<String, String>> infosByStationName = new HashMap<>();
            for (Map<String, String> m : manipulatedInfos) {
                String k = m.get("station_nm");
                infosByStationName.put(k, m);
            }

            result.put(line, infosByStationName);
        }

        // mapper.writeValue(new File("stationinfo_adjusted_min.json"), adjustedInfos);
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/test/resources/seoul2.json"), result);*/
    }
}
