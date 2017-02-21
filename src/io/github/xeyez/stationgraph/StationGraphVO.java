package io.github.xeyez.stationgraph;
import java.io.Serializable;

import lombok.Data;

@Data
public class StationGraphVO implements Comparable<StationGraphVO>, Serializable {
	
	private static final long serialVersionUID = 1L;

	public enum Identifier {
		PREVIOUS, CURRENT, NEXT
	}
	
	private String stationName;
	private String lineNum;
	private Identifier identifier;
	private boolean isMainLine = true;
	
	public StationGraphVO(String stationName, String lineNum, Identifier identifier) {
		super();
		this.stationName = stationName;
		this.lineNum = lineNum;
		this.identifier = identifier;
	}

	@Override
	public int compareTo(StationGraphVO o) {
		return this.stationName.compareTo(o.stationName);
	}
}