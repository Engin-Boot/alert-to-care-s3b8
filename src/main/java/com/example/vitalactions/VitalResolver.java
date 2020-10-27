package com.example.vitalactions;


import java.util.Iterator;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VitalResolver {
	
	public static String vitalValidatorCaller(Map<String, Integer> measurement){
		Logger logger = LogManager.getLogger(VitalResolver.class);
		String message = "";
		logger.info(measurement);
		Iterator<Map.Entry<String, Integer>> itr = measurement.entrySet().iterator();
		while(itr.hasNext()) {
			Map.Entry<String, Integer> entry = itr.next();
			message += VitalValidator.CheckIfVitalIsOK(entry.getKey(), entry.getValue());
		}
		return message;
	}
}
