package models.games;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateFieldRequest {

	@JsonProperty("fieldName")
	private String fieldName;

	@JsonProperty("value")
	private Object value;




}