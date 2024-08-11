package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class JwtAuthData{
	@JsonProperty("username")
	private String username;

	@JsonProperty("password")
	private String password;



	public String getPassword(){
		return password;
	}

	public String getUsername(){
		return username;
	}
}