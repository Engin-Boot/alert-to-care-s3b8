package com.example.controller;

import com.example.entities.Patient;
import com.example.exceptions.*;
import com.example.service.BedService;
import com.example.service.ClientService;
import com.example.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/pms")
public class OccupancyController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private BedService bedService;

    @Autowired
    private ClientService clientService;


    @PostMapping("/client/patient")
    public ResponseEntity<Patient> createPatient(@Valid @RequestBody Patient patient) throws PatientAlreadyExistsException, InvalidDateFormatException, BedDoesNotExistException, BedHasAlreadyBeenOccupiedException, PatientCreatedWithIncorrectStatusWhenAdmittedException {
       //First we are validating patient for DOB and status as ADMITTED. Then we are checking if bed is VACANT. ONLY THEN we are inserting patient
        patientService.validatePatientDetails(patient);
        bedService.updateBedStatusWhenPatientAdmitted(patient.getBed_id());
        System.out.println("Bed Status HAS been updated");
        Patient savedPatient = patientService.savePatient(patient);
        System.out.println("saving patient");
        return new ResponseEntity<Patient>(savedPatient, HttpStatus.CREATED);
    }

    @GetMapping("/client/{client_id}/bed")
    public ResponseEntity<Map<String,String>> getAllBedStatusForSpecifiedClient(@PathVariable(value = "client_id") UUID client_id) throws ClientDoesNotExistException, BedDoesNotExistException {
        //FIRST WE ARE CHECKING IF CLIENT WITH ID=client_id EXISTS.Only then we are getting bed status
        if(clientService.checkIfClientExists(client_id.toString())) {
            Map<String, String> allBedStatus = bedService.getAllBedStatusByClientId(client_id.toString());
            return new ResponseEntity<Map<String, String>>(allBedStatus, HttpStatus.OK);
        }
        else{
            throw new ClientDoesNotExistException("Client with id = "+client_id.toString()+ "does not exist");
        }
    }

    @PutMapping("/client/{client_id}/patient/{patient_id}/status/discharge")
    public ResponseEntity<Patient> dischargePatient(@PathVariable(value = "client_id") UUID client_id, @PathVariable(value = "patient_id") UUID patient_id) throws PatientDoesNotExistException, PatientHasAlreadyBeenDischargedException {

        Patient dischargedPatient = patientService.dischargePatient(patient_id.toString());
        bedService.updateBedStatusWhenPatientDischarged(dischargedPatient.getBed_id());
        return new ResponseEntity<Patient>(dischargedPatient, HttpStatus.OK);
    }




}

