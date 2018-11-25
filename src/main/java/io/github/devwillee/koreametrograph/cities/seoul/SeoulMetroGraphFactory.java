package io.github.devwillee.koreametrograph.cities.seoul;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.devwillee.koreametrograph.api.Identifier;
import io.github.devwillee.koreametrograph.api.MetroGraph;
import io.github.devwillee.koreametrograph.api.MetroGraphFactory;
import io.github.devwillee.koreametrograph.api.Station;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class SeoulMetroGraphFactory extends MetroGraphFactory {

    @Override
    public void create(MetroGraph metroGraph) throws IOException {
        TreeMap<String, ArrayList<String>> raw = Model.build();
        for(String lineNum : raw.keySet()) {

            ArrayList<String> stationNames = raw.get(lineNum);
            for(int i=0 ; i<stationNames.size() - 1 ; i++) {
                String stationName1 = stationNames.get(i);
                String stationName2 = stationNames.get(i + 1);

                Station vo1 = new Station(stationName1, lineNum, Identifier.CURRENT);
                fill(vo1);
                metroGraph.addVertex(vo1);

                Station vo2 = new Station(stationName2, lineNum, Identifier.CURRENT);
                fill(vo2);
                metroGraph.addVertex(vo2);

                metroGraph.addEdge(vo1, vo2);
            }
        }
    }

    private void fill(Station station) throws IOException {
        if(station.getLineNum().equalsIgnoreCase("J") || station.getLineNum().equalsIgnoreCase("W"))
            return;

        List<Map<String, String>> info_temp;

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File("src/test/resources/seoul.json")).get("DATA");

        List<Map<String, String>> temp = mapper.convertValue(root, List.class);

        info_temp
                = temp.stream()
                .filter(x -> x.get("station_nm").equalsIgnoreCase(station.getStationName()) && x.get("line_num")
                .equalsIgnoreCase(station.getLineNum()))
                .collect(Collectors.toList());

        Map<String, String> info = info_temp.get(0);

        station.setStationCode(info.get("station_cd"));
        station.setLatitude(Double.parseDouble(info.get("xpoint_wgs")));
        station.setLongitude(Double.parseDouble(info.get("ypoint_wgs")));
    }

    @Override
    public void truncate(MetroGraph metroGraph) {
        /* 1호선 */
        // 구로 & 인천
        metroGraph.setSubLine("구로", "가산디지털단지", "1");
        metroGraph.removeSymmetryEdges("구로", "인천", "1");

        // 금천구청 & 광명
        metroGraph.setSubLine("금천구청", "광명", "1");
        metroGraph.removeEdge("금천구청", "신창", "1");
        metroGraph.removeEdge("광명", "병점", "1");

        // 병점
        metroGraph.setSubLine("병점", "서동탄", "1");
        metroGraph.removeEdge("병점", "광명", "1");

        /* 2호선 */
        // 신도림 & 신설동
        metroGraph.setSubLine("신도림", "도림천", "2");
        metroGraph.removeSymmetryEdges("신도림", "신설동", "2");

        // 성수
        metroGraph.setSubLine("성수", "용답", "2");
        metroGraph.removeSymmetryEdges("성수", "시청", "2");

        /* 5호선 */
        // 강동 상일동
        metroGraph.setSubLine("강동", "둔촌동", "5");
        metroGraph.removeSymmetryEdges("강동", "상일동", "5");

        /* 6호선 */
        metroGraph.addEdge(new Station("구산", "6", Identifier.CURRENT), new Station("응암", "6", Identifier.NEXT)); // 환형
        metroGraph.setSubLine("응암", "구산", "6");

        /* 경의중앙선 */
        metroGraph.setSubLine("가좌", "신촌", "K");
        metroGraph.removeEdge("가좌", "지평", "K");
    }
}
