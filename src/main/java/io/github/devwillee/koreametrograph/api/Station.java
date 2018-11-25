package io.github.devwillee.koreametrograph.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
public class Station implements Comparable<Station>, Serializable {
	@NonNull
	private String stationName;
	@NonNull
	private String lineNum;
	@NonNull
	private Identifier identifier;

	private boolean isMainLine = true;

	private String stationCode;
	private double latitude;
	private double longitude;

	@Override
	public int compareTo(Station o) {
		return this.stationName.compareTo(o.stationName);
	}

	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return super.toString();
	}
}