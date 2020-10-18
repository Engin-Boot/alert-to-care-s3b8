package com.example.vitalactions;

import org.junit.Assert;
import org.junit.Test;

public class VitalValidatorTest {
	@Test
	public void given_vital_and_vital_value_when_vitalValidator_is_called_then_return_message() {
		String message = VitalValidator.CheckIfVitalIsOK("spo2", 200);
		Assert.assertEquals(message, "spo2 is high ");
	}
}
