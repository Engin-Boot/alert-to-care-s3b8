package com.example.service;

import com.example.entities.Patient;
import com.example.entities.PatientStatus;
import com.example.exceptions.InvalidDateFormatException;
import com.example.exceptions.PatientAlreadyExistsException;
import com.example.exceptions.PatientDoesNotExistException;
import com.example.exceptions.PatientHasAlreadyBeenDischargedException;
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


    public Patient savePatient(Patient patient) throws PatientAlreadyExistsException, InvalidDateFormatException {
        if(Utility.checkIfDateIsValid(patient.getDob())){
            String patient_id = UUID.randomUUID().toString();
            patient.setPatient_id(patient_id);
            if(patientRepository.findById(patient_id).isPresent()) {
                throw new PatientAlreadyExistsException("Patient with id = "+patient_id + " already exists");
            }
            return patientRepository.save(patient);
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

    public Patient dischargePatient(String patient_id) throws PatientDoesNotExistException, PatientHasAlreadyBeenDischargedException {
        Patient patientToDischarge = getPatient(patient_id);
        if(patientToDischarge.getPatientStatus() == PatientStatus.ADMITTED){
            patientToDischarge.setPatientStatus(PatientStatus.DISCHARGED);
            return patientRepository.save(patientToDischarge);
        }
        else{
            throw new PatientHasAlreadyBeenDischargedException("Patient with id = "+ patient_id+ " has already been discahrged");
        }
    }




//    public Client updateClient(Client client, String client_id){
//        client.setClient_id(client_id);
//
//        if(clientRepository.findById(client_id).isPresent()) {
//            client.setNo_of_beds(clientRepository.findById(client_id).get().getNo_of_beds() + client.getNo_of_beds());
//        }
//        return clientRepository.save(client);
//    }


}

