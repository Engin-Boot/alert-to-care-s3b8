package com.example.vitalactions;

import com.example.utility.Utility;

public class VitalValidator {
	
	public static String CheckIfVitalIsOK(String vital, String value) {
		System.out.println(vital.toLowerCase());
		String limits = Utility.getVitalLimit(vital.toLowerCase());
		System.out.println(limits);
		String [] vitallimits = limits.split(",");
		Integer lowerlimit = Integer.parseInt(vitallimits[0].trim());
		System.out.println(lowerlimit);
		Integer upperlimit = Integer.parseInt(vitallimits[1].trim());
		System.out.println(upperlimit);
		
		if(Integer.parseInt(value) < lowerlimit) {
			return vital + " is low ";
		}
		else if(Integer.parseInt(value) > upperlimit){
			return vital + " is high ";
		}
		return "";	
	}

}
