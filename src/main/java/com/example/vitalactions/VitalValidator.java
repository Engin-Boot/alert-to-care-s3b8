package com.example.vitalactions;

import com.example.utility.Utility;

public class VitalValidator {
	
	public static String CheckIfVitalIsOK(String vital, Integer value) {
		String limits = Utility.getVitalLimit(vital.toLowerCase());
		String [] vitallimits = limits.split(",");
		Integer lowerlimit = Integer.parseInt(vitallimits[0].trim());
		Integer upperlimit = Integer.parseInt(vitallimits[1].trim());
		if((value) < lowerlimit) {
			return vital + " is low ";
		}
		else if((value) > upperlimit){
			return vital + " is high ";
		}
		return "";	
	}

}
