package com.example.mapper;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.example.dto.PatientDTO;
import com.example.entities.Patient;

public class PatientMapperTest {
	
	PatientMapper patientmapper;
	
	@Before
    public void setUp() {
		patientmapper = new PatientMapper();
    }
	
	@Test
	public void When_mapPatientDTOtoPatientEntityIsCalledThenMapWithNoException() {
		Patient pateint = new Patient();
		String name = "Test";
        String bed_id = UUID.randomUUID().toString();
        String dob = "2020-03-03";
        String patientStatus = "ADMITTED";
        boolean isAlarmActive = true;
		PatientDTO patientDTO = new PatientDTO(name,dob,bed_id,patientStatus, isAlarmActive);
		pateint = patientmapper.mapPatientDTOtoPatientEntity(patientDTO);
		Assert.assertEquals(pateint.getBedId(), patientDTO.getBed_id());
		Assert.assertEquals(pateint.getName(), patientDTO.getName());
		Assert.assertEquals(pateint.getDob(), patientDTO.getDob());
		Assert.assertEquals(pateint.getPatientStatus(), patientDTO.getPatientStatus());
		Assert.assertEquals(pateint.getIsAlarmActive(), patientDTO.getIsAlarmActive());

	}

}
