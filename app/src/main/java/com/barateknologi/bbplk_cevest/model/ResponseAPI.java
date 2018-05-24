package com.barateknologi.bbplk_cevest.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseAPI{

	@SerializedName("jsoncaridata")
	private List<JsoncaridataItem> jsoncaridata;

	public void setJsoncaridata(List<JsoncaridataItem> jsoncaridata){
		this.jsoncaridata = jsoncaridata;
	}

	public List<JsoncaridataItem> getJsoncaridata(){
		return jsoncaridata;
	}
}