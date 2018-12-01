package io.github.devwillee.koreametrograph.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Station implements Comparable<Station>, Cloneable {
	private String stationName;
	private String lineNum;
	private Identifier identifier = Identifier.CURRENT;

	private boolean isMainLine = true;

	private String stationCode;
	private double latitude;
	private double longitude;

	private String stationName_han;
	private String stationName_eng;

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
		station.setMainLine(this.isMainLine);
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