package com.example.vitalactions;


import org.json.JSONObject;

public class VitalResolver {
	
	public static String vitalResolver(String measurements){
		String message = "";
		System.out.println(measurements);
		JSONObject obj = new JSONObject(measurements);
		String measurementArray [] = ((String) obj.get("measurements")).split(",");
		for(String vitalString: measurementArray) {
			message += VitalExtracter.vitalExtracter(vitalString);
		}
		return message;
	}
}
