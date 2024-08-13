package models.carsBrands;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ResponseItem{

	@JsonProperty("models")
	private List<String> models;

	@JsonProperty("brand")
	private String brand;

	public List<String> getModels(){
		return models;
	}

	public String getBrand(){
		return brand;
	}
}