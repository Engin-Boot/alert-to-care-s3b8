package com.example.service;

import com.example.entities.Client;
import com.example.exceptions.ClientAlreadyExistsException;
import com.example.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }



    public Client getClientById(String id){
        if(clientRepository.findById(id).isPresent()) {
            return clientRepository.findById(id).get();
        }
        else{
            return null;
        }
    }

    public Client saveClient(Client client, String client_id) throws ClientAlreadyExistsException {
        client.setClient_id(client_id);
        if(clientRepository.findById(client_id).isPresent()) {
            throw new ClientAlreadyExistsException("Client with id = "+client_id + " already exists");
        }
        return clientRepository.save(client);
    }

    public Client updateClient(Client client, String client_id){
        client.setClient_id(client_id);

        if(clientRepository.findById(client_id).isPresent()) {
            client.setNo_of_beds(clientRepository.findById(client_id).get().getNo_of_beds() + client.getNo_of_beds());
        }
        return clientRepository.save(client);
    }


}
