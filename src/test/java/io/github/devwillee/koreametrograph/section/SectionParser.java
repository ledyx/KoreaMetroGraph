package io.github.devwillee.koreametrograph.section;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/* 지원하는 호선 */
// 1~9호선
// B(분당) : 75
// A(공항) : 65
// K(경중) : 63
// G(경춘) : 67
// SU(수인) : 71

/* 지원하지 않는 호선 */
// S(신분당)
// U(의정부)
// E(에버라인)
// KK(경강선)
// UI(우이신설)
// 서해
// 자기부상

/**
 * 지정한 역의 주변역 구간 시간과 거리 분석기
 * 이용하려면 Open API key 필요.
 * http://data.seoul.go.kr/dataList/datasetView.do?infId=OA-12763&srvType=A&serviceKind=1
 * 발급받은 Key는 src/test/resources/keys.properties에서 "section=KEY" 형식으로 파일을 생성한다.
 */
public class SectionParser {
    private SectionParser() {
    }

    public static SectionParser newInstance() {
        return new SectionParser();
    }

    private final String resource = "src/test/resources/keys.properties";

    public List<Section> parse(String stationName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        //mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        JsonNode rootNode = mapper.readTree(generateQuery(stationName));
        String code = rootNode.findValue("code").asText();
        if(!code.equalsIgnoreCase("INFO-000"))
            throw new IllegalArgumentException(rootNode.findValue("message").asText());

        List<Section> result = mapper.readValue(rootNode.findValue("stationList").toString(), new TypeReference<List<Section>>(){});
        return result.stream().distinct().filter(x -> x.getTime() < 15).sorted().collect(Collectors.toList());
    }

    private URL generateQuery(String stationName) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("http://swopenAPI.seoul.go.kr/api/subway/");
        sb.append(loadKey());
        sb.append("/json/stationSection/0/10/");
        sb.append(URLEncoder.encode(stationName.trim(), "UTF-8"));
        // sb.append("/");
        return new URL(sb.toString());
    }

    private String loadKey() {
        Properties properties = new Properties();
        try (FileReader f = new FileReader(new File(resource))){
            properties.load(f);
            return properties.getProperty("section");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
