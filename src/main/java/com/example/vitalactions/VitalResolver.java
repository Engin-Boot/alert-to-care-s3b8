package com.example.vitalactions;


import java.util.Iterator;
import java.util.Map;

public class VitalResolver {
	
	public static String vitalValidatorCaller(Map<String, Integer> measurement){
		String message = "";
		System.out.println(measurement);
		Iterator<Map.Entry<String, Integer>> itr = measurement.entrySet().iterator();
		while(itr.hasNext()) {
			Map.Entry<String, Integer> entry = itr.next();
			message += VitalValidator.CheckIfVitalIsOK(entry.getKey(), entry.getValue());
		}
		return message;
	}
}
