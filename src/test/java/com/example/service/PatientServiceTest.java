package com.example.service;

import com.example.dto.PatientDTO;
import com.example.entities.Client;
import com.example.entities.Patient;
import com.example.entities.PatientStatus;
import com.example.exceptions.*;
import com.example.mapper.PatientMapper;
import com.example.repository.ClientRepository;
import com.example.repository.PatientRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PatientServiceTest {

    @Mock
    PatientRepository patientRepository;

    @Mock
    PatientMapper patientMapper;

    PatientService patientService;

    @Captor
    ArgumentCaptor<Patient> patientArgumentCaptor;

    @Before
    public void setUp() {
        patientService = new PatientService(patientRepository, patientMapper);
    }

    @Test
    public void given_New_Patient_When_Saved_Then_Throw_No_Exception() throws PatientAlreadyExistsException {
        String bed_id = UUID.randomUUID().toString();
        String client_id = UUID.randomUUID().toString();
        Patient patient = new Patient(UUID.randomUUID().toString(), "name1", "1998-04-01", bed_id, PatientStatus.ADMITTED.toString(), client_id, true);
        PatientDTO patientDTO = new PatientDTO("name1", "1998-04-01", bed_id, PatientStatus.ADMITTED.toString(),true);

        when(patientMapper.mapPatientDTOtoPatientEntity(patientDTO)).thenReturn(patient);
        when(patientRepository.findById(any())).thenReturn(Optional.empty());
        patientService.savePatient(patientDTO, client_id);

        verify(patientRepository).save(patientArgumentCaptor.capture());

        Assert.assertEquals(patientDTO.getPatientStatus(), patientArgumentCaptor.getValue().getPatientStatus());
        Assert.assertEquals(patientDTO.getDob(), patientArgumentCaptor.getValue().getDob());
        Assert.assertEquals(patientDTO.getName(), patientArgumentCaptor.getValue().getName());
        Assert.assertEquals(patientDTO.getBed_id(), patientArgumentCaptor.getValue().getBedId());
        Assert.assertEquals(client_id, patientArgumentCaptor.getValue().getClientId());
    }

    @Test(expected = PatientAlreadyExistsException.class)
    public void given_Existing_Patient_When_Saved_Then_Throw_Exception() throws PatientAlreadyExistsException {
        String existingPatientId = UUID.randomUUID().toString();
        String client_id = UUID.randomUUID().toString();
        String bed_id = UUID.randomUUID().toString();
        Patient existingPatient = new Patient(existingPatientId,"name1", "1998-04-01", bed_id, PatientStatus.ADMITTED.toString(), client_id, true);
        Patient patientToSave = new Patient(existingPatientId, "name2", "1998-04-01", bed_id, PatientStatus.ADMITTED.toString(), client_id, true);
        PatientDTO patientDTO = new PatientDTO("name1", "1998-04-01", bed_id, PatientStatus.ADMITTED.toString(),true);

        when(patientMapper.mapPatientDTOtoPatientEntity(patientDTO)).thenReturn(patientToSave);
        when(patientRepository.findById(any())).thenReturn(Optional.of(existingPatient));

        patientService.savePatient(patientDTO, client_id);
    }

    @Test(expected = InvalidDateFormatException.class)
    public void given_Patient_With_Invalid_DOB_When_Validated_Then_Throw_Exception() throws InvalidDateFormatException, PatientCreatedWithIncorrectStatusWhenAdmittedException {
        PatientDTO patientDTO = new PatientDTO("name1", "01-04-1997", UUID.randomUUID().toString(), PatientStatus.ADMITTED.toString(),true);
        patientService.validatePatientDetails(patientDTO);
    }

    @Test
    public void given_Valid_Patient_When_Validated_Then_Throw_No_Exception() throws InvalidDateFormatException, PatientCreatedWithIncorrectStatusWhenAdmittedException {
        PatientDTO patientDTO = new PatientDTO("name1", "1998-04-01", UUID.randomUUID().toString(), PatientStatus.ADMITTED.toString(),true);
        boolean checkValid = patientService.validatePatientDetails(patientDTO);
        Assert.assertTrue(checkValid);
    }

    @Test(expected = PatientDoesNotExistException.class)
    public void given_PatientId_Which_Does_Not_Exist_When_Discharging_Patient_Then_Throw_Exception() throws PatientDoesNotExistException, PatientHasAlreadyBeenDischargedException, PatientDoesNotBelongToSpecifiedClientException {
        String non_existing_patient_id = UUID.randomUUID().toString();
        String client_id = UUID.randomUUID().toString();
        when(patientRepository.findById(any())).thenReturn(Optional.empty());

        patientService.dischargePatient(non_existing_patient_id, client_id);
    }

    @Test
    public void given_Existing_PatientId_And_Appropriate_Client_Id_When_Discharging_Patient_Then_Throw_No_Exception() throws PatientDoesNotExistException, PatientHasAlreadyBeenDischargedException, PatientDoesNotBelongToSpecifiedClientException {
        String existing_patient_id = UUID.randomUUID().toString();
        String client_id = UUID.randomUUID().toString();
        Patient existingPatient = new Patient(existing_patient_id,"name1", "1998-05-15", UUID.randomUUID().toString(), PatientStatus.ADMITTED.toString(), client_id, false);
        when(patientRepository.findById(any())).thenReturn(Optional.of(existingPatient));


        patientService.dischargePatient(existing_patient_id, client_id);

        verify(patientRepository).save(patientArgumentCaptor.capture());

        Assert.assertEquals(existing_patient_id, patientArgumentCaptor.getValue().getPatient_id());
        Assert.assertEquals(client_id, patientArgumentCaptor.getValue().getClientId());
        Assert.assertEquals(client_id, patientArgumentCaptor.getValue().getClientId());
        Assert.assertEquals("1998-05-15", patientArgumentCaptor.getValue().getDob());
        Assert.assertEquals(PatientStatus.DISCHARGED.toString(), patientArgumentCaptor.getValue().getPatientStatus());
    }

    @Test(expected = PatientDoesNotBelongToSpecifiedClientException.class)
    public void given_Existing_PatientId_But_Client_Id_Not_Associated_With_Patient_When_Discharging_Patient_Then_Throw_Exception() throws PatientDoesNotExistException, PatientHasAlreadyBeenDischargedException, PatientDoesNotBelongToSpecifiedClientException {
        String existing_patient_id = UUID.randomUUID().toString();
        String not_associated_client_id = UUID.randomUUID().toString();
        Patient existingPatient = new Patient(existing_patient_id,"name1", "1998-05-15", UUID.randomUUID().toString(), PatientStatus.ADMITTED.toString(), UUID.randomUUID().toString(), false);
        when(patientRepository.findById(any())).thenReturn(Optional.of(existingPatient));

        patientService.dischargePatient(existing_patient_id, not_associated_client_id);
    }

    @Test(expected = PatientHasAlreadyBeenDischargedException.class)
    public void given_Patient_Already_Been_Discharged_When_Discharging_That_Patient_Again_Then_Throw_Exception() throws PatientDoesNotExistException, PatientHasAlreadyBeenDischargedException, PatientDoesNotBelongToSpecifiedClientException {
        String discharged_patient_id = UUID.randomUUID().toString();
        String client_id = UUID.randomUUID().toString();
        Patient dischargedPatient = new Patient(discharged_patient_id,"name1", "1998-05-15", UUID.randomUUID().toString(), PatientStatus.DISCHARGED.toString(),client_id, true);
        when(patientRepository.findById(any())).thenReturn(Optional.of(dischargedPatient));

        patientService.dischargePatient(discharged_patient_id, client_id);
    }

    @Test(expected = PatientDoesNotBelongToSpecifiedClientException.class)
    public void given_Existing_PatientId_But_Client_Id_Not_Associated_With_Patient_When_Changing_Patient_Alarm_Status_Then_Throw_Exception() throws PatientDoesNotExistException, PatientHasAlreadyBeenDischargedException, PatientDoesNotBelongToSpecifiedClientException {
        String existing_patient_id = UUID.randomUUID().toString();
        boolean alarmStatus = true;
        String not_associated_client_id = UUID.randomUUID().toString();
        Patient existingPatient = new Patient(existing_patient_id,"name1", "1998-05-15", UUID.randomUUID().toString(), PatientStatus.ADMITTED.toString(), UUID.randomUUID().toString(), false);
        when(patientRepository.findById(any())).thenReturn(Optional.of(existingPatient));

        patientService.changeAlarmStatus(alarmStatus,existing_patient_id, not_associated_client_id);
    }

    @Test(expected = PatientDoesNotExistException.class)
    public void given_PatientId_Which_Does_Not_Exist_When_Changing_Patient_Alarm_Status_Then_Throw_Exception() throws PatientDoesNotExistException, PatientHasAlreadyBeenDischargedException, PatientDoesNotBelongToSpecifiedClientException {
        String non_existing_patient_id = UUID.randomUUID().toString();
        boolean alarmStatus = true;
        String client_id = UUID.randomUUID().toString();
        when(patientRepository.findById(any())).thenReturn(Optional.empty());

        patientService.changeAlarmStatus(alarmStatus, non_existing_patient_id, client_id);
    }

    @Test
    public void given_Existing_PatientId_And_Appropriate_Client_Id_When_Changing_Patient_Alarm_Status_Then_Throw_No_Exception() throws PatientDoesNotExistException, PatientHasAlreadyBeenDischargedException, PatientDoesNotBelongToSpecifiedClientException {
        String existing_patient_id = UUID.randomUUID().toString();
        String client_id = UUID.randomUUID().toString();
        boolean alarmStatusToChangeTo = true;
        Patient existingPatient = new Patient(existing_patient_id,"name1", "1998-05-15", UUID.randomUUID().toString(), PatientStatus.ADMITTED.toString(), client_id, false);
        when(patientRepository.findById(any())).thenReturn(Optional.of(existingPatient));


        patientService.changeAlarmStatus(alarmStatusToChangeTo, existing_patient_id, client_id);

        verify(patientRepository).save(patientArgumentCaptor.capture());

        Assert.assertEquals(existing_patient_id, patientArgumentCaptor.getValue().getPatient_id());
        Assert.assertEquals(client_id, patientArgumentCaptor.getValue().getClientId());
        Assert.assertEquals(client_id, patientArgumentCaptor.getValue().getClientId());
        Assert.assertEquals("1998-05-15", patientArgumentCaptor.getValue().getDob());
        Assert.assertEquals(PatientStatus.ADMITTED.toString(), patientArgumentCaptor.getValue().getPatientStatus());
        Assert.assertEquals(true, patientArgumentCaptor.getValue().getIsAlarmActive());
    }

    @Test
    public void given_Bed_Id_Associated_With_Patient_When_Get_Patient_By_Bed_Id_Then_Throw_No_Exception() throws PatientDoesNotExistException {
        String valid_bed_id = UUID.randomUUID().toString();
        Patient existingPatient = new Patient(UUID.randomUUID().toString(),"name1", "1998-05-15", valid_bed_id, PatientStatus.ADMITTED.toString(), UUID.randomUUID().toString(), false);
        when(patientRepository.findByBedId(valid_bed_id)).thenReturn(existingPatient);

        Patient patient = patientService.getPatientByBedId(valid_bed_id);
        Assert.assertNotNull(patient.getBedId());
        Assert.assertNotNull(patient.getName());
        Assert.assertNotNull(patient.getDob());
        Assert.assertNotNull(patient.getPatientStatus());
        Assert.assertNotNull(patient.getClientId());
        Assert.assertNotNull(patient.getIsAlarmActive());
    }

    @Test(expected = PatientDoesNotExistException.class)
    public void given_Bed_Id_Not_Associated_With_Patient_When_Get_Patient_By_Bed_Id_Then_Throw_Exception() throws PatientDoesNotExistException {
        String invalid_bed_id = UUID.randomUUID().toString();
        Patient existingPatient = new Patient(UUID.randomUUID().toString(),"name1", "1998-05-15", UUID.randomUUID().toString(), PatientStatus.ADMITTED.toString(), UUID.randomUUID().toString(), false);
        when(patientRepository.findByBedId(invalid_bed_id)).thenReturn(null);

        patientService.getPatientByBedId(invalid_bed_id);
    }
}

