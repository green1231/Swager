package models.keys;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;



@ToString
public class NumbersPow{

@Getter
	private final Map<String, Object> filds = new LinkedHashMap<>();

	@JsonAnySetter
	public void put(String key, Object value){
		filds.put(key, value);

	}
}