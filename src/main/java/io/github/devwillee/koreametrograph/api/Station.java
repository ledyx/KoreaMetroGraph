package io.github.devwillee.koreametrograph.api;
import lombok.Data;

import java.io.Serializable;

@Data
public class Station implements Comparable<Station>, Serializable {
	private String stationName;
	private String lineNum;
	private Identifier identifier;
	private boolean isMainLine = true;
	
	public Station(String stationName, String lineNum, Identifier identifier) {
		super();
		this.stationName = stationName;
		this.lineNum = lineNum;
		this.identifier = identifier;
	}

	@Override
	public int compareTo(Station o) {
		return this.stationName.compareTo(o.stationName);
	}
}