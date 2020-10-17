package com.example.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ClientDTO;
import com.example.entities.Client;
import com.example.exceptions.ClientAlreadyExistsException;
import com.example.service.BedService;
import com.example.service.ClientService;
import com.example.service.DeviceService;

@RestController
@RequestMapping("/pms")
public class ConfigurationController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private BedService bedService;

    @Autowired
    private DeviceService deviceService;

    @PostMapping("/client/config")
    public ResponseEntity<Client> createClient(@Valid @RequestBody ClientDTO clientDTO) throws ClientAlreadyExistsException {
        System.out.println("CCCC");
        Client savedClient = clientService.saveClient(clientDTO);
        System.out.println("saving client");
        bedService.createBeds(savedClient.getNo_of_beds(), savedClient.getClient_id());
        System.out.println("saving beds");
        deviceService.createDevices(savedClient.getNo_of_beds());
        System.out.println("saving devices");
        return new ResponseEntity<Client>(savedClient, HttpStatus.CREATED);
    }

    @PutMapping("/client/{client_id}/config")
    public ResponseEntity<Client> updateClient(@PathVariable(value = "client_id") UUID client_id, @Valid @RequestBody ClientDTO clientDTO) {
        int no_of_beds_to_add = clientDTO.getNo_of_beds();
        Client savedClient = clientService.updateClient(clientDTO, client_id.toString());
        System.out.println("Updating client");
        bedService.createBeds(no_of_beds_to_add, client_id.toString());
        System.out.println("saving beds");
        deviceService.createDevices(no_of_beds_to_add);
        System.out.println("saving devices");
        return new ResponseEntity<Client>(savedClient, HttpStatus.OK);
    }
}
