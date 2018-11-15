package io.github.devwillee.koreametrograph.cities.seoul;

import io.github.devwillee.koreametrograph.api.Identifier;
import io.github.devwillee.koreametrograph.api.MetroGraph;
import io.github.devwillee.koreametrograph.api.MetroGraphFactory;
import io.github.devwillee.koreametrograph.api.Station;

import java.util.ArrayList;
import java.util.TreeMap;

public class SeoulMetroGraphFactory extends MetroGraphFactory {

    // 문자열이 아닌 JSON으로 변경 필요
    @Override
    public MetroGraph create() {
        try {
            MetroGraph metroGraph = new MetroGraph();

            TreeMap<String, ArrayList<String>> raw = Model.build();
            for(String lineNum : raw.keySet()) {

                ArrayList<String> stationNames = raw.get(lineNum);
                for(int i=0 ; i<stationNames.size() - 1 ; i++) {
                    String stationName1 = stationNames.get(i);
                    String stationName2 = stationNames.get(i + 1);

                    // 추가 정보 필요

                    Station vo1 = new Station(stationName1, lineNum, Identifier.CURRENT);
                    metroGraph.addVertex(vo1);

                    Station vo2 = new Station(stationName2, lineNum, Identifier.CURRENT);
                    metroGraph.addVertex(vo2);

                    metroGraph.addEdge(vo1, vo2);
                }
            }

            adjust(metroGraph);

            return metroGraph;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 예외처리. 이전/다음역이 여러개 인 경우 처리와 이어지지 않은 노선에 대한 삭제.
     * @param
     */
    private void adjust(MetroGraph metroGraph) {
        /* 1호선 */
        // 구로 & 인천
        metroGraph.setSubLine("구로", "가산디지털단지", "1");
        metroGraph.removeEdgeSymmetry("구로", "인천", "1");

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
        metroGraph.removeEdgeSymmetry("신도림", "신설동", "2");

        // 성수
        metroGraph.setSubLine("성수", "용답", "2");
        metroGraph.removeEdgeSymmetry("성수", "시청", "2");

        /* 5호선 */
        // 강동 상일동
        metroGraph.setSubLine("강동", "둔촌동", "5");
        metroGraph.removeEdgeSymmetry("강동", "상일동", "5");

        /* 6호선 */
        metroGraph.addEdge(new Station("구산", "6", Identifier.CURRENT), new Station("응암", "6", Identifier.NEXT)); // 환형
        metroGraph.setSubLine("응암", "구산", "6");

        /* 경의중앙선 */
        metroGraph.setSubLine("가좌", "신촌", "K");
        metroGraph.removeEdge("가좌", "지평", "K");
    }
}
