package com.example.mapper;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.example.dto.AlertDTO;
import com.example.entities.Alert;

public class AlertMapperTest {
	
	AlertMapper alertmapper;
	
	@Before
    public void setUp() {
		alertmapper = new AlertMapper();
    }
	
	@Test
	public void When_method_mapAlertDTOtoAlertEntityIsCalledThenMapWithNoException() {
		Alert alert = new Alert();
        String bed_id = UUID.randomUUID().toString();
        String client_id = UUID.randomUUID().toString();
        String patient_id = UUID.randomUUID().toString();
        String device_id = UUID.randomUUID().toString();
        String msg = "spo2 is high";
		AlertDTO alertDTO = new AlertDTO(client_id,bed_id,patient_id,device_id, msg);
		alert = alertmapper.mapAlertDTOtoAlertEntity(alertDTO);
		Assert.assertEquals(alert.getAlertMessage(), alertDTO.getAlertMessage());
		Assert.assertEquals(alert.getBedId(), alertDTO.getBedId());
		Assert.assertEquals(alert.getClientId(), alertDTO.getClientId());
		Assert.assertEquals(alert.getPatientId(), alertDTO.getPatientId());
		Assert.assertEquals(alert.getDeviceId(), alertDTO.getDeviceId());
		Assert.assertEquals(alert.getPatientId(), alertDTO.getPatientId());
	}

}
