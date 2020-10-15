package com.example.service;

import com.example.entities.Client;
import com.example.exceptions.ClientAlreadyExistsException;
import com.example.repository.ClientRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {

    @Mock
    ClientRepository clientRepository;

    ClientService clientService;

    @Captor
    ArgumentCaptor<Client> clientArgumentCaptor;

    @Before
    public void setUp() {
        clientService = new ClientService(clientRepository);
    }

    @Test
    public void given_New_Client_When_Saved_No_Exception() throws ClientAlreadyExistsException {

        Client client = new Client(UUID.randomUUID().toString(), "typ1", "DEFAULT", 3);

        when(clientRepository.findById(any())).thenReturn(Optional.empty());
        clientService.saveClient(client);

        verify(clientRepository).save(clientArgumentCaptor.capture());

        Assert.assertEquals(client.getNo_of_beds(), clientArgumentCaptor.getValue().getNo_of_beds());
        Assert.assertEquals(client.getClient_type(), clientArgumentCaptor.getValue().getClient_type());
        Assert.assertEquals(client.getLayout(), clientArgumentCaptor.getValue().getLayout());
    }

    @Test(expected = ClientAlreadyExistsException.class)
    public void given_Existing_Client_When_Saved_Throw_Exception() throws ClientAlreadyExistsException {
        String existingClientId = UUID.randomUUID().toString();
        Client existingClient = new Client(existingClientId, "typ2", "DEFAULT", 5);
        Client clientToSave = new Client(existingClientId, "typ1", "DEFAULT", 3);

        when(clientRepository.findById(any())).thenReturn(Optional.of(existingClient));

        clientService.saveClient(clientToSave);
    }

    @Test
    public void given_Existing_Client_When_Updated_No_Exception() throws ClientAlreadyExistsException {
        String existingClientId = UUID.randomUUID().toString();
        int no_of_beds_to_add = 3;
        Client existingClient = new Client(existingClientId, "typ2", "DEFAULT", 5);
        Client clientToSave = new Client(existingClientId, "typ1", "DEFAULT", no_of_beds_to_add);

        when(clientRepository.findById(any())).thenReturn(Optional.of(existingClient));
        clientService.updateClient(clientToSave, existingClientId);

        verify(clientRepository).save(clientArgumentCaptor.capture());

        Assert.assertEquals(8, clientArgumentCaptor.getValue().getNo_of_beds());
        Assert.assertEquals("typ1", clientArgumentCaptor.getValue().getClient_type());
        Assert.assertEquals("DEFAULT", clientArgumentCaptor.getValue().getLayout());
    }

    @Test
    public void when_New_Client_Tries_To_Update_Then_Saved_With_No_Exception() throws ClientAlreadyExistsException {
        int no_of_beds_to_add = 3;
        String newClientId = UUID.randomUUID().toString();
        Client clientToSave = new Client(newClientId, "typ1", "DEFAULT", no_of_beds_to_add);

        when(clientRepository.findById(any())).thenReturn(Optional.empty());
        clientService.updateClient(clientToSave, newClientId);

        verify(clientRepository).save(clientArgumentCaptor.capture());

        Assert.assertEquals(3, clientArgumentCaptor.getValue().getNo_of_beds());
        Assert.assertEquals("typ1", clientArgumentCaptor.getValue().getClient_type());
        Assert.assertEquals("DEFAULT", clientArgumentCaptor.getValue().getLayout());
    }

    @Test
    public void given_Existing_Client_When_Checked_If_It_Exists_Then_Throw_No_Exception(){
        String existingClientId = UUID.randomUUID().toString();
        Client existingClient = new Client(existingClientId, "typ1", "DEFAULT", 3);

        when(clientRepository.findById(any())).thenReturn(Optional.of(existingClient));
        boolean checkIfExists = clientService.checkIfClientExists(existingClientId);

        Assert.assertEquals(true, checkIfExists);

    }

    @Test
    public void given_New_Client_When_Checked_If_It_Exists_Then_Throw_No_Exception(){
        String newClientId = UUID.randomUUID().toString();

        when(clientRepository.findById(any())).thenReturn(Optional.empty());
        boolean checkIfExists = clientService.checkIfClientExists(newClientId);

        Assert.assertEquals(false, checkIfExists);
    }

}

