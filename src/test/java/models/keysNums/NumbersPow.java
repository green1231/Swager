package models.keysNums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NumbersPow{

	@JsonProperty("nums")
	private Nums nums;

	public void setNums(Nums nums){
		this.nums = nums;
	}

	public Nums getNums(){
		return nums;
	}

	@Override
 	public String toString(){
		return 
			"NumbersPow{" + 
			"nums = '" + nums + '\'' + 
			"}";
		}
}