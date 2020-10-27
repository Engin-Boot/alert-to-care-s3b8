package com.example.controller;

import com.example.dto.PatientDTO;
import com.example.entities.Patient;
import com.example.exceptions.*;
import com.example.service.BedService;
import com.example.service.ClientService;
import com.example.service.DeviceService;
import com.example.service.PatientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/pms")
public class OccupancyController {
	
	final Logger logger = LogManager.getLogger(OccupancyController.class);

    @Autowired
    private PatientService patientService;

    @Autowired
    private BedService bedService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private DeviceService deviceService;


    @PostMapping("/client/{client_id}/patient")
    public ResponseEntity<Patient> createPatient(@PathVariable(value = "client_id") UUID client_id, @Valid @RequestBody PatientDTO patientDTO) throws PatientAlreadyExistsException, InvalidDateFormatException, BedDoesNotExistException, BedHasAlreadyBeenOccupiedException, PatientCreatedWithIncorrectStatusWhenAdmittedException, BedDoesNotBelongToSpecifiedClientException {
       //First we are validating patient for DOB and status as ADMITTED. Then we are checking if bed is VACANT. ONLY THEN we are inserting patient. Once inserting patient we are associating random NOTINUSE Device With bed that patient is associated with

        patientService.validatePatientDetails(patientDTO);
        bedService.updateBedStatusWhenPatientAdmitted(patientDTO.getBed_id(), client_id.toString());
        logger.info("Bed Status has been updated");
        Patient savedPatient = patientService.savePatient(patientDTO, client_id.toString());
        logger.info("saving patient");
        deviceService.associateDeviceToBed(patientDTO.getBed_id());
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

    @PutMapping("/client/{client_id}/patient/{patient_id}/discharge")
    public ResponseEntity<Patient> dischargePatient(@PathVariable(value = "client_id") UUID client_id, @PathVariable(value = "patient_id") UUID patient_id) throws PatientDoesNotExistException, PatientHasAlreadyBeenDischargedException, PatientDoesNotBelongToSpecifiedClientException, DeviceDoesNotExistException, BedDoesNotExistException {
        //NO REQUEST BODY PASSED HERE.
        Patient dischargedPatient = patientService.dischargePatient(patient_id.toString(), client_id.toString());
        bedService.updateBedStatusAfterPatientDischarged(dischargedPatient.getBedId());
        deviceService.updateDeviceAfterPatientDischarge(dischargedPatient.getBedId());
        return new ResponseEntity<Patient>(dischargedPatient, HttpStatus.OK);
    }

    //controller for getting all the empty bed of particular client
    @GetMapping("/client/{client_id}/emptybeds")
    public ResponseEntity<List<String>> getEmptyBedsForSpecifiedClient(@PathVariable(value = "client_id") UUID client_id) throws ClientDoesNotExistException, BedDoesNotExistException {
        //FIRST WE ARE CHECKING IF CLIENT WITH ID=client_id EXISTS.Only then we are getting bed status
        if(clientService.checkIfClientExists(client_id.toString())) {
            List<String> emptyBeds = bedService.getEmptyBedsByClientId(client_id.toString());
            return new ResponseEntity<List<String>>(emptyBeds, HttpStatus.OK);
        }
        else{
            throw new ClientDoesNotExistException("Client with id = "+client_id.toString()+ "does not exist");
        }
    }
    //controller for getting all the patients admitted
    @GetMapping("/client/{client_id}/admittedpatients")
    public ResponseEntity<List<Patient>> getPatientsAdmittedToSpecifiedClient(@PathVariable(value = "client_id") UUID client_id) throws ClientDoesNotExistException, BedDoesNotExistException {
        //FIRST WE ARE CHECKING IF CLIENT WITH ID=client_id EXISTS.Only then we are getting bed status
        if(clientService.checkIfClientExists(client_id.toString())) {
            List<Patient> admittedPatients = patientService.getAdmittedPatients(client_id.toString());
            return new ResponseEntity<List<Patient>>(admittedPatients, HttpStatus.OK);
        }
        else{
            throw new ClientDoesNotExistException("Client with id = "+client_id.toString()+ "does not exist");
        }
    }
}

