package io.github.devwillee.koreametrograph.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.devwillee.koreametrograph.api.graph.Vertex;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Station implements Vertex<Station> {
	@JsonProperty("station_nm")
	private String stationName;
	@JsonProperty("line_num")
	private String lineNum;

	private Identifier identifier = Identifier.CURRENT;
	private boolean mainLine = true;

	@JsonProperty("fr_code")
	private String foreignCode;

	@JsonProperty("station_cd")
	private String stationCode;
	@JsonProperty("xpoint_wgs")
	private double latitude;
	@JsonProperty("ypoint_wgs")
	private double longitude;

	@JsonProperty("station_nm_han")
	private String stationName_han;
	@JsonProperty("station_nm_eng")
	private String stationName_eng;

	private Station() {}

	public Station(String stationName, String lineNum) {
		this.stationName = stationName;
		this.lineNum = lineNum;
	}

	public Station(String stationName, String lineNum, Identifier identifier) {
		this.stationName = stationName;
		this.lineNum = lineNum;
		this.identifier = identifier;
	}

	@Override
	public int compareTo(Station o) {
		return this.stationName.compareTo(o.stationName);
	}

	@Override
	protected Station clone() {
		Station station = new Station(this.stationName, this.lineNum);
		station.setIdentifier(this.identifier);
		station.setMainLine(this.mainLine);
		station.setForeignCode(this.foreignCode);
		station.setStationCode(this.stationCode);
		station.setLatitude(this.latitude);
		station.setLongitude(this.longitude);
		station.setStationName_han(this.stationName_han);
		station.setStationName_eng(this.stationName_eng);
		return station;
	}

	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return null;
	}
}