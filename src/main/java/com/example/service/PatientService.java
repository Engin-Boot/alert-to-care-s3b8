package com.example.service;

import com.example.entities.Patient;
import com.example.entities.PatientStatus;
import com.example.exceptions.*;
import com.example.repository.PatientRepository;
import com.example.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PatientService {

    PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }


    public Patient savePatient(Patient patient) throws PatientAlreadyExistsException{
        String patient_id = UUID.randomUUID().toString();
        patient.setPatient_id(patient_id);
        if(patientRepository.findById(patient_id).isPresent()) {
            throw new PatientAlreadyExistsException("Patient with id = "+patient_id + " already exists");
        }
        return patientRepository.save(patient);
    }

    public boolean validatePatientDetails(Patient patient) throws InvalidDateFormatException, PatientCreatedWithIncorrectStatusWhenAdmittedException {
        if(Utility.checkIfDateIsValid(patient.getDob())){
            if(patient.getPatientStatus().equalsIgnoreCase("ADMITTED")){
                return true;
            }
            else{
                throw new PatientCreatedWithIncorrectStatusWhenAdmittedException("Patient created with incorrect status. Status should be 'ADMITTED'.");
            }
        }
        else{
            throw new InvalidDateFormatException("DOB is not present in the yyyy-MM-dd format");
        }
    }


    private Patient getPatient(String patient_id) throws PatientDoesNotExistException {
        Optional<Patient> patient = patientRepository.findById(patient_id);
        if(patient.isPresent()){
            return patient.get();
        }
        else{
            throw new PatientDoesNotExistException("Patient does not exist with id = "+patient_id);
        }
    }

    public Patient dischargePatient(String patient_id) throws PatientHasAlreadyBeenDischargedException, PatientDoesNotExistException {
        Patient patientToDischarge = getPatient(patient_id);
        if(patientToDischarge.getPatientStatus().equalsIgnoreCase(PatientStatus.ADMITTED.toString())){
            patientToDischarge.setPatientStatus(PatientStatus.DISCHARGED.toString());
            return patientRepository.save(patientToDischarge);
        }
        else{
            throw new PatientHasAlreadyBeenDischargedException("Patient with id = "+ patient_id+ " has already been discahrged");
        }
    }
}

