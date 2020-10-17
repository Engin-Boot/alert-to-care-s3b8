package com.example.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.PatientDTO;
import com.example.entities.Patient;
import com.example.entities.PatientStatus;
import com.example.exceptions.InvalidDateFormatException;
import com.example.exceptions.PatientAlreadyExistsException;
import com.example.exceptions.PatientCreatedWithIncorrectStatusWhenAdmittedException;
import com.example.exceptions.PatientDoesNotBelongToSpecifiedClientException;
import com.example.exceptions.PatientDoesNotExistException;
import com.example.exceptions.PatientHasAlreadyBeenDischargedException;
import com.example.mapper.PatientMapper;
import com.example.repository.PatientRepository;
import com.example.utility.Utility;

@Service
public class PatientService {

    PatientRepository patientRepository;

    PatientMapper patientMapper;

    @Autowired
    public PatientService(PatientRepository patientRepository, PatientMapper patientMapper){
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }


    public Patient savePatient(PatientDTO patientDTO, String client_id) throws PatientAlreadyExistsException{
        String patient_id = UUID.randomUUID().toString();
        Patient patient = patientMapper.mapPatientDTOtoPatientEntity(patientDTO);
        patient.setPatientStatus(PatientStatus.ADMITTED.toString());
        patient.setPatient_id(patient_id);
        patient.setClientId(client_id);
        if(patientRepository.findById(patient_id).isPresent()) {
            throw new PatientAlreadyExistsException("Patient with id = "+patient_id + " already exists");
        }
        return patientRepository.save(patient);
    }

    public boolean validatePatientDetails(PatientDTO patientDTO) throws InvalidDateFormatException, PatientCreatedWithIncorrectStatusWhenAdmittedException {
        if(Utility.checkIfDateIsValid(patientDTO.getDob())){
            return true;
        }
        else{
            throw new InvalidDateFormatException("DOB is not present in the yyyy-MM-dd format");
        }
    }


    public Patient getPatient(String patient_id) throws PatientDoesNotExistException {
        Optional<Patient> patient = patientRepository.findById(patient_id);
        if(patient.isPresent()){
            return patient.get();
        }
        else{
            throw new PatientDoesNotExistException("Patient does not exist with id = "+patient_id);
        }
    }

    public Patient getPatientByBedId(String bed_id) throws PatientDoesNotExistException {
        Patient patient = patientRepository.findByBedId(bed_id);
        if(patient != null){
            return patient;
        }
        else{
            throw new PatientDoesNotExistException("Patient does not exist with bedId = "+bed_id);
        }
    }

    public Patient dischargePatient(String patient_id, String client_id) throws PatientHasAlreadyBeenDischargedException, PatientDoesNotExistException, PatientDoesNotBelongToSpecifiedClientException {
        Patient patientToDischarge = getPatient(patient_id);
        if(patientToDischarge.getClientId().equals(client_id)) {
            if (patientToDischarge.getPatientStatus().equalsIgnoreCase(PatientStatus.ADMITTED.toString())) {
                patientToDischarge.setPatientStatus(PatientStatus.DISCHARGED.toString());
                return patientRepository.save(patientToDischarge);
            } else {
                throw new PatientHasAlreadyBeenDischargedException("Patient with id = " + patient_id + " has already been discahrged");
            }
        }
        else{
            throw new PatientDoesNotBelongToSpecifiedClientException("Patient with id = "+patient_id + " is not associated with clientId = "+client_id);
        }
    }

    public Patient changeAlarmStatus(boolean isAlarmActive, String patient_id, String client_id) throws PatientDoesNotExistException, PatientDoesNotBelongToSpecifiedClientException {
        Patient patient = getPatient(patient_id);
        if(patient.getClientId().equals(client_id)) {
            patient.setIsAlarmActive(isAlarmActive);
            return patientRepository.save(patient);
        }
        else{
            throw new PatientDoesNotBelongToSpecifiedClientException("Patient with id = "+patient_id + " is not associated with clientId = "+client_id);
        }
    }
}

