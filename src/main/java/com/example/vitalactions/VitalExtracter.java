package com.example.vitalactions;


public class VitalExtracter {

	public static String vitalExtracter(String vitalString) {
		String [] splittedVitalString = vitalString.split(":");
		
		if(splittedVitalString.length <= 1) {
			return "Device is malfunctioning";
		}
		String vital = splittedVitalString[0].trim();
		System.out.println(vital);
		String value = splittedVitalString[1].trim();
		System.out.println(value);
		
		return VitalValidator.CheckIfVitalIsOK(vital, value);
	}
}
