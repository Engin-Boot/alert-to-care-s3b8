package com.example.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.ClientDTO;
import com.example.entities.Client;
import com.example.exceptions.ClientAlreadyExistsException;
import com.example.mapper.ClientMapper;
import com.example.repository.ClientRepository;

@Service
public class ClientService {

    ClientRepository clientRepository;

    ClientMapper clientMapper;

    @Autowired
    public ClientService(ClientRepository clientRepository,ClientMapper clientMapper){
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }


    public Client saveClient(ClientDTO clientDTO) throws ClientAlreadyExistsException {
        Client client = clientMapper.mapClientDTOtoClientEntity(clientDTO);
        String client_id = UUID.randomUUID().toString();
        client.setClient_id(client_id);
        if(clientRepository.findById(client_id).isPresent()) {
            throw new ClientAlreadyExistsException("Client with id = "+client_id + " already exists");
        }
        return clientRepository.save(client);
    }

    public Client updateClient(ClientDTO clientDTO, String client_id){
        Client client = clientMapper.mapClientDTOtoClientEntity(clientDTO);
        client.setClient_id(client_id);

        if(checkIfClientExists(client_id)) {
            client.setNo_of_beds(clientRepository.findById(client_id).get().getNo_of_beds() + client.getNo_of_beds());
        }
        return clientRepository.save(client);
    }

    public boolean checkIfClientExists(String client_id){
        return clientRepository.findById(client_id).isPresent();
    }
}
