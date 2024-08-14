package models.keysNums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class KeyNumsRoot{

	@JsonProperty("1")
	private String jsonMember1;

	@JsonProperty("_2")
	private String jsonMember2;

	@JsonProperty("numbersPow")
	private NumbersPow numbersPow;

	@JsonProperty("true")
	private boolean trues;

	@JsonProperty("'single_quotes'")
	private String singleQuotes;

	@JsonProperty("bmw:users")
	private String bmwUsers;

	@JsonProperty("что то на русском")
	private String rus;


}