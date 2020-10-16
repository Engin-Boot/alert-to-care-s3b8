package com.example.controller;

import com.example.dto.ClientDTO;
import com.example.entities.Client;
import com.example.exceptions.ClientAlreadyExistsException;
import com.example.service.BedService;
import com.example.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/pms")
public class ConfigurationController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private BedService bedService;

    @PostMapping("/client/config")
    public ResponseEntity<Client> createClient(@Valid @RequestBody ClientDTO clientDTO) throws ClientAlreadyExistsException {
        System.out.println("CCCC");
        Client savedClient = clientService.saveClient(clientDTO);
        System.out.println("saving client");
        bedService.createBeds(savedClient.getNo_of_beds(), savedClient.getClient_id());
        System.out.println("saving beds");
        return new ResponseEntity<Client>(savedClient, HttpStatus.CREATED);
    }

    @PutMapping("/client/{client_id}/config")
    public ResponseEntity<Client> updateClient(@PathVariable(value = "client_id") UUID client_id, @Valid @RequestBody ClientDTO clientDTO) {
        int no_of_beds_to_add = clientDTO.getNo_of_beds();
        Client savedClient = clientService.updateClient(clientDTO, client_id.toString());
        System.out.println("Updating client");
        bedService.createBeds(no_of_beds_to_add, client_id.toString());
        System.out.println("saving beds");
        return new ResponseEntity<Client>(savedClient, HttpStatus.OK);
    }
}
