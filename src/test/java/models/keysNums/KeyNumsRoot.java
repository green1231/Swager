package models.keysNums;

import com.fasterxml.jackson.annotation.JsonProperty;

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

	public void setJsonMember1(String jsonMember1){
		this.jsonMember1 = jsonMember1;
	}

	public String getJsonMember1(){
		return jsonMember1;
	}

	public void setJsonMember2(String jsonMember2){
		this.jsonMember2 = jsonMember2;
	}

	public String getJsonMember2(){
		return jsonMember2;
	}

	public void setNumbersPow(NumbersPow numbersPow){
		this.numbersPow = numbersPow;
	}

	public NumbersPow getNumbersPow(){
		return numbersPow;
	}

	public void setTrue(boolean trues){
		this.trues = true;
	}

	public boolean isTrue(){
		return true;
	}

	public void setSingleQuotes(String singleQuotes){
		this.singleQuotes = singleQuotes;
	}

	public String getSingleQuotes(){
		return singleQuotes;
	}

	public void setBmwUsers(String bmwUsers){
		this.bmwUsers = bmwUsers;
	}

	public String getBmwUsers(){
		return bmwUsers;
	}

	public void set(String rus){
		this. rus = rus;
	}

	public String get(){
		return rus;
	}

	@Override
 	public String toString(){
		return 
			"KeyNumsRoot{" + 
			"1 = '" + jsonMember1 + '\'' + 
			",_2 = '" + jsonMember2 + '\'' + 
			",numbersPow = '" + numbersPow + '\'' + 
			",true = '" + true + '\'' + 
			",'single_quotes' = '" + singleQuotes + '\'' + 
			",bmw:users = '" + bmwUsers + '\'' + 
			",что то на русском = '" +  + '\'' + 
			"}";
		}
}