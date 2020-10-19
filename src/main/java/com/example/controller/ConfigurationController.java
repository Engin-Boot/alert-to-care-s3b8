package com.example.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	
	final Logger logger = LogManager.getLogger(ConfigurationController.class);
    @Autowired
    private ClientService clientService;

    @Autowired
    private BedService bedService;

    @Autowired
    private DeviceService deviceService;

    @PostMapping("/client/config")
    public ResponseEntity<Client> createClient(@Valid @RequestBody ClientDTO clientDTO) throws ClientAlreadyExistsException {
        Client savedClient = clientService.saveClient(clientDTO);
        logger.info("Saving client");
        bedService.createBeds(savedClient.getNo_of_beds(), savedClient.getClient_id());
        logger.info("Saving Beds");
        deviceService.createDevices(savedClient.getNo_of_beds());
        logger.info("Saving Devices");
        return new ResponseEntity<Client>(savedClient, HttpStatus.CREATED);
    }

    @PutMapping("/client/{client_id}/config")
    public ResponseEntity<Client> updateClient(@PathVariable(value = "client_id") UUID client_id, @Valid @RequestBody ClientDTO clientDTO) {
        int no_of_beds_to_add = clientDTO.getNo_of_beds();
        Client savedClient = clientService.updateClient(clientDTO, client_id.toString());
        logger.info("Updating client");
        bedService.createBeds(no_of_beds_to_add, client_id.toString());
        logger.info("Saving Beds");
        deviceService.createDevices(no_of_beds_to_add);
        logger.info("Saving devices");
        return new ResponseEntity<Client>(savedClient, HttpStatus.OK);
    }
}
