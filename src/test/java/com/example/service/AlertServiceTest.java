package com.example.service;

import com.example.dto.VitalsDTO;
import com.example.entities.Bed;
import com.example.entities.BedStatus;
import com.example.exceptions.BedDoesNotExistException;
import com.example.exceptions.BedHasAlreadyBeenOccupiedException;
import com.example.mapper.AlertMapper;
import com.example.repository.AlertRepository;
import com.example.repository.BedRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.apache.commons.collections.map.HashedMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AlertServiceTest {

    @Mock
    AlertRepository alertRepository;

    AlertService alertService;

    AlertMapper alertMapper;

    @Captor
    ArgumentCaptor<Bed> bedArgumentCaptor;

    @Before
    public void setUp() {
        alertService = new AlertService(alertRepository, alertMapper);
    }

    @Test
    public void given_Beds_When_Created_Then_Throw_No_Exception() throws JsonProcessingException {
    	Map<String, Integer> map = new HashMap<>();
        System.out.println(alertService.checkVitalsAreOk(map));
    }
}


