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
public class SimilarDlc{

	@JsonProperty("isFree")
	private boolean isFree;

	@JsonProperty("dlcNameFromAnotherGame")
	private String dlcNameFromAnotherGame;
}