package com.example.controller;

import com.example.entities.Bed;
import com.example.entities.Client;
import com.example.exceptions.ClientAlreadyExistsException;
import com.example.service.BedService;
import com.example.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/pms")
public class ConfigurationController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private BedService bedService;

//    @GetMapping("/patient/{id}")
//    public ResponseEntity<Patient> getPatient(@PathVariable(value = "id") int id) throws PatientNotFoundException {
//        Patient savedPatient = patientService.getPatientById(id);
//        return new ResponseEntity<Patient>(savedPatient, HttpStatus.OK);
//    }


    @PostMapping("/client/config")
    public ResponseEntity<Client> createClient(@RequestBody Client client) throws ClientAlreadyExistsException {
        Client savedClient = clientService.saveClient(client);
        System.out.println("saving client");
        bedService.createBeds(client.getNo_of_beds(), client.getClient_id());
        return new ResponseEntity<Client>(savedClient, HttpStatus.CREATED);
    }

    @PutMapping("/client/{client_id}/config")
    public ResponseEntity<Client> updateClient(@PathVariable(value = "client_id") UUID client_id, @RequestBody Client client) {
        int no_of_beds_to_add = client.getNo_of_beds();
        Client savedClient = clientService.updateClient(client, client_id.toString());
        System.out.println("Updating client");
        bedService.createBeds(no_of_beds_to_add, client_id.toString());
        return new ResponseEntity<Client>(savedClient, HttpStatus.CREATED);
    }

    @PostMapping(path="/saveBed")
    public String addNewPatient(@RequestBody Bed bed){
        bedService.save(bed);
        return "Patient Data Saved";
    }


}
