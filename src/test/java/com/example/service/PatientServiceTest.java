//package com.example.service;
//
//import com.example.entities.Client;
//import com.example.entities.Patient;
//import com.example.entities.PatientStatus;
//import com.example.exceptions.*;
//import com.example.repository.ClientRepository;
//import com.example.repository.PatientRepository;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Captor;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class PatientServiceTest {
//
//    @Mock
//    PatientRepository patientRepository;
//
//    PatientService patientService;
//
//    @Captor
//    ArgumentCaptor<Patient> patientArgumentCaptor;
//
//    @Before
//    public void setUp() {
//        patientService = new PatientService(patientRepository);
//    }
//
//    @Test
//    public void given_New_Patient_When_Saved_Then_Throw_No_Exception() throws PatientAlreadyExistsException {
//        String bed_id = UUID.randomUUID().toString();
//        Patient patient = new Patient(UUID.randomUUID().toString(), "name1", "1998-04-01", bed_id, PatientStatus.ADMITTED.toString());
//
//        when(patientRepository.findById(any())).thenReturn(Optional.empty());
//        patientService.savePatient(patient);
//
//        verify(patientRepository).save(patientArgumentCaptor.capture());
//
//        Assert.assertEquals(patient.getPatientStatus(), patientArgumentCaptor.getValue().getPatientStatus());
//        Assert.assertEquals(patient.getDob(), patientArgumentCaptor.getValue().getDob());
//        Assert.assertEquals(patient.getName(), patientArgumentCaptor.getValue().getName());
//        Assert.assertEquals(patient.getBed_id(), patientArgumentCaptor.getValue().getBed_id());
//    }
//
//    @Test(expected = PatientAlreadyExistsException.class)
//    public void given_Existing_Patient_When_Saved_Then_Throw_Exception() throws PatientAlreadyExistsException {
//        String existingPatientId = UUID.randomUUID().toString();
//        String bed_id = UUID.randomUUID().toString();
//        Patient existingPatient = new Patient(existingPatientId,"name1", "1998-04-01", bed_id, PatientStatus.ADMITTED.toString());
//        Patient patientToSave = new Patient(existingPatientId, "name1", "1998-04-01", bed_id, PatientStatus.ADMITTED.toString());
//
//        when(patientRepository.findById(any())).thenReturn(Optional.of(existingPatient));
//
//        patientService.savePatient(patientToSave);
//    }
//
//    @Test(expected = InvalidDateFormatException.class)
//    public void given_Patient_With_Invalid_DOB_When_Validated_Then_Throw_Exception() throws InvalidDateFormatException, PatientCreatedWithIncorrectStatusWhenAdmittedException {
//        Patient patient = new Patient(UUID.randomUUID().toString(),"name1", "20-05-1998", UUID.randomUUID().toString(), PatientStatus.ADMITTED.toString());
//        patientService.validatePatientDetails(patient);
//    }
//
//    @Test(expected = PatientCreatedWithIncorrectStatusWhenAdmittedException.class)
//    public void given_Patient_With_Wrong_AdmissionStatus_When_Validated_Then_Throw_Exception() throws InvalidDateFormatException, PatientCreatedWithIncorrectStatusWhenAdmittedException {
//        Patient patient = new Patient(UUID.randomUUID().toString(),"name1", "1998-05-15", UUID.randomUUID().toString(), PatientStatus.DISCHARGED.toString());
//        patientService.validatePatientDetails(patient);
//    }
//
//    @Test
//    public void given_Valid_Patient_When_Validated_Then_Throw_No_Exception() throws InvalidDateFormatException, PatientCreatedWithIncorrectStatusWhenAdmittedException {
//        Patient patient = new Patient(UUID.randomUUID().toString(),"name1", "1998-05-15", UUID.randomUUID().toString(), PatientStatus.ADMITTED.toString());
//        boolean valid = patientService.validatePatientDetails(patient);
//        Assert.assertTrue(valid);
//    }
//
//    @Test(expected = PatientDoesNotExistException.class)
//    public void given_PatientId_Which_Does_Not_Exist_When_Discharging_Patient_Then_Throw_Exception() throws PatientDoesNotExistException, PatientHasAlreadyBeenDischargedException {
//        String non_existing_patient_id = UUID.randomUUID().toString();
//        when(patientRepository.findById(any())).thenReturn(Optional.empty());
//
//        patientService.dischargePatient(non_existing_patient_id);
//    }
//
//    @Test
//    public void given_Existing_PatientId_When_Discharging_Patient_Then_Throw_No_Exception() throws PatientDoesNotExistException, PatientHasAlreadyBeenDischargedException {
//        String existing_patient_id = UUID.randomUUID().toString();
//        Patient existingPatient = new Patient(existing_patient_id,"name1", "1998-05-15", UUID.randomUUID().toString(), PatientStatus.ADMITTED.toString());
//        when(patientRepository.findById(any())).thenReturn(Optional.of(existingPatient));
//
//        patientService.dischargePatient(existing_patient_id);
//
//        verify(patientRepository).save(patientArgumentCaptor.capture());
//
//        Assert.assertEquals(existingPatient.getPatientStatus(), patientArgumentCaptor.getValue().getPatientStatus());
//        Assert.assertEquals(existingPatient.getDob(), patientArgumentCaptor.getValue().getDob());
//        Assert.assertEquals(existingPatient.getName(), patientArgumentCaptor.getValue().getName());
//        Assert.assertEquals(existingPatient.getBed_id(), patientArgumentCaptor.getValue().getBed_id());
//    }
//
//    @Test(expected = PatientHasAlreadyBeenDischargedException.class)
//    public void given_Patient_Already_Been_Discharged_When_Discharging_That_Patient_Again_Then_Throw_Exception() throws PatientDoesNotExistException, PatientHasAlreadyBeenDischargedException {
//        String discharged_patient_id = UUID.randomUUID().toString();
//        Patient dischargedPatient = new Patient(discharged_patient_id,"name1", "1998-05-15", UUID.randomUUID().toString(), PatientStatus.DISCHARGED.toString());
//        when(patientRepository.findById(any())).thenReturn(Optional.of(dischargedPatient));
//
//        patientService.dischargePatient(discharged_patient_id);
//    }
//}
//
