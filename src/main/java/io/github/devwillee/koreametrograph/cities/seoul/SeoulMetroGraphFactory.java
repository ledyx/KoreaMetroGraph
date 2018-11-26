package io.github.devwillee.koreametrograph.cities.seoul;

import io.github.devwillee.koreametrograph.api.Identifier;
import io.github.devwillee.koreametrograph.api.MetroGraph;
import io.github.devwillee.koreametrograph.api.MetroGraphFactory;
import io.github.devwillee.koreametrograph.api.Station;

import java.util.ArrayList;
import java.util.TreeMap;

public class SeoulMetroGraphFactory extends MetroGraphFactory {

    @Override
    protected String getJSONPath() {
        return "src/test/resources/seoul.json";
    }

    @Override
    public void create(MetroGraph metroGraph) {
        TreeMap<String, ArrayList<String>> raw = Model.build();
        for(String lineNum : raw.keySet()) {

            ArrayList<String> stationNames = raw.get(lineNum);
            for(int i=0 ; i<stationNames.size() - 1 ; i++) {
                String stationName1 = stationNames.get(i);
                String stationName2 = stationNames.get(i + 1);

                Station vo1 = new Station(stationName1, lineNum, Identifier.CURRENT);
                metroGraph.addVertex(vo1);

                Station vo2 = new Station(stationName2, lineNum, Identifier.CURRENT);
                metroGraph.addVertex(vo2);

                metroGraph.addEdge(vo1, vo2);
            }
        }
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
        metroGraph.addEdge(new Station("구산", "6", Identifier.CURRENT),
                           new Station("응암", "6", Identifier.NEXT)); // 환형
        metroGraph.setSubLine("응암", "구산", "6");

        /* 경의중앙선 */
        metroGraph.setSubLine("가좌", "신촌", "K");
        metroGraph.removeEdge("가좌", "지평", "K");
    }
}
