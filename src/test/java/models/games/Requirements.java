package models.games;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Requirements{

	@JsonProperty("videoCard")
	private String videoCard;

	@JsonProperty("hardDrive")
	private int hardDrive;

	@JsonProperty("osName")
	private String osName;

	@JsonProperty("ramGb")
	private int ramGb;
}