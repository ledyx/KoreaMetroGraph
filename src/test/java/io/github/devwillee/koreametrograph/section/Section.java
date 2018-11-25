package io.github.devwillee.koreametrograph.section;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Section implements Comparable<Section> {
    /*@JsonProperty("statnFid")
    private String baseId;*/

    private String baseLineNum;

    @JsonSetter("statnFid")
    private void getBaseLineNum(String statnFid) {
        baseLineNum = extractLineNum(statnFid);
    }

    @JsonProperty("statnFnm")
    private String baseName;

    /*@JsonProperty("statnTid")
    private String endId;*/

    private String endLineNum;

    @JsonSetter("statnTid")
    private void getEndLineNum(String statnTid) {
        endLineNum = extractLineNum(statnTid);
    }

    @JsonProperty("statnTnm")
    private String endName;


    /**
     * Unit : Meter
     */
    @JsonProperty("sctnDstnc")
    private int destination;

    /**
     * Unit : Second
     */
    @JsonProperty("sctnTime")
    private int time;

    private String extractLineNum(String id) {
        int code = Integer.parseInt(id.substring(2, 4));
        String lineNum = null;
        if (code < 10)
            lineNum = String.valueOf(code);
        else {
            switch (code) {
                case 75 :
                    lineNum = "B";
                    break;

                case 65 :
                    lineNum = "A";
                    break;

                case 63 :
                    lineNum = "K";
                    break;

                case 67 :
                    lineNum = "G";
                    break;

                case 71 :
                    lineNum = "SU";
                    break;
            }
        }

        return lineNum;
    }

    @Override
    public int compareTo(Section o) {
        return Integer.compare(this.destination, o.destination);
    }
}
