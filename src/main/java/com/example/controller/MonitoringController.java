package com.example.controller;

import com.example.dto.ClientDTO;
import com.example.dto.VitalsDTO;
import com.example.entities.*;
import com.example.exceptions.*;
import com.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/pms")
public class MonitoringController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private BedService bedService;

    @Autowired
    private AlertService alertService;

    @PostMapping("/device/{device_id}")
    public ResponseEntity<Alert> createAlert(@PathVariable(value = "device_id") UUID device_id, @Valid @RequestBody VitalsDTO vitalsDTO) throws DeviceDoesNotExistException, BedDoesNotExistException, PatientDoesNotExistException, DeviceNotAssociatedWithBedException {
        //GET DEVICE FROM ID FIRST. FROM DEVICE EXTRACT BED_ID. THEN GET BED FROM THE BED-ID.GET CLIENT_ID FROM THE BED. THEN GET PATIENT USING BED_ID
        Device device = deviceService.getDevice(device_id.toString());
        String bed_id = device.getBedId();

        Bed bed = bedService.getBed(bed_id);
        String client_id = bed.getBed_id();

        Patient patient = patientService.getPatientByBedId(bed_id);
        String patient_id = patient.getPatient_id();

        Alert savedAlert = alertService.saveAlert(bed_id,client_id,patient_id,device_id.toString(),vitalsDTO);
        if(savedAlert == null){
            return new ResponseEntity<Alert>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<Alert>(savedAlert, HttpStatus.CREATED);
    }

//    @PutMapping("/client/{client_id}/config")
//    public ResponseEntity<Client> updateClient(@PathVariable(value = "client_id") UUID client_id, @Valid @RequestBody ClientDTO clientDTO) {
//        int no_of_beds_to_add = clientDTO.getNo_of_beds();
//        Client savedClient = clientService.updateClient(clientDTO, client_id.toString());
//        System.out.println("Updating client");
//        bedService.createBeds(no_of_beds_to_add, client_id.toString());
//        System.out.println("saving beds");
//        return new ResponseEntity<Client>(savedClient, HttpStatus.OK);
//    }
}
