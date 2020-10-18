package com.example.vitalactions;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class VitalResolverTest {
	@Test
	public void given_vital_and_vital_value_when_vitalResolver_is_called_then_return_message() {
		Map<String,Integer> measurement = new HashMap<String,Integer>();
		measurement.put("spo2",20);
		String message = VitalResolver.vitalValidatorCaller(measurement);
		Assert.assertEquals(message, "spo2 is low ");
	}
}
