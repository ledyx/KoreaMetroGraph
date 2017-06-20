package io.github.xeyez.stationgraph;
import java.io.Serializable;

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

	
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getLineNum() {
		return lineNum;
	}
	public void setLineNum(String lineNum) {
		this.lineNum = lineNum;
	}

	public Identifier getIdentifier() {
		return identifier;
	}
	public void setIdentifier(Identifier identifier) {
		this.identifier = identifier;
	}

	public boolean isMainLine() {
		return isMainLine;
	}

	public void setMainLine(boolean isMainLine) {
		this.isMainLine = isMainLine;
	}

	
	@Override
	public String toString() {
		return "StationGraphVO [stationName=" + stationName + ", lineNum=" + lineNum + ", identifier=" + identifier
				+ ", isMainLine=" + isMainLine + "]";
	}


	@Override
	public int compareTo(StationGraphVO o) {
		return this.stationName.compareTo(o.stationName);
	}
}