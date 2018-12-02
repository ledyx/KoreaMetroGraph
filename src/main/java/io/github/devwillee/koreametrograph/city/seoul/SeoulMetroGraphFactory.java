package io.github.devwillee.koreametrograph.city.seoul;

import io.github.devwillee.koreametrograph.api.*;

import java.util.ArrayList;
import java.util.TreeMap;

public class SeoulMetroGraphFactory extends MetroGraphFactory {

    @Override
    protected String getVerticesJSONPath() {
        return "src/main/resources/seoul/vertices_minimal_simple.json";
    }

    @Override
    protected String getEdgesJSONPath() {
        return "src/main/resources/seoul/edges_simple.json";
    }

    @Override
    public void adjust(MetroGraph metroGraph) {
        /* 1호선 */
        // 구로 & 인천
        metroGraph.setSubLine("구로", "가산디지털단지", "1");

        // 금천구청 & 광명
        metroGraph.setSubLine("금천구청", "광명", "1");

        // 병점
        metroGraph.setSubLine("병점", "서동탄", "1");

        /* 2호선 */
        // 신도림 & 신설동
        metroGraph.setSubLine("신도림", "도림천", "2");

        // 성수
        metroGraph.setSubLine("성수", "용답", "2");

        /* 5호선 */
        // 강동 상일동
        metroGraph.setSubLine("강동", "둔촌동", "5");

        /* 6호선 */
        metroGraph.setSubLine("응암", "구산", "6");

        /* 경의중앙선 */
        metroGraph.setSubLine("가좌", "신촌", "K");
    }
}
