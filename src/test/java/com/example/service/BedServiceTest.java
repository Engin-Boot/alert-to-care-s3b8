package com.example.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.entities.Bed;
import com.example.entities.BedStatus;
import com.example.exceptions.BedDoesNotBelongToSpecifiedClientException;
import com.example.exceptions.BedDoesNotExistException;
import com.example.exceptions.BedHasAlreadyBeenOccupiedException;
import com.example.mapper.BedMapper;
import com.example.repository.BedRepository;

@RunWith(MockitoJUnitRunner.class)
public class BedServiceTest {

    @Mock
    BedRepository bedRepository;

    @Mock
    BedMapper bedMapper;

    BedService bedService;

    @Captor
    ArgumentCaptor<Bed> bedArgumentCaptor;

    @Before
    public void setUp() {
        bedService = new BedService(bedRepository, bedMapper);
    }

    @Test
    public void given_Beds_When_Created_Then_Throw_No_Exception() {

        int no_of_beds = 3;
        String client_id = UUID.randomUUID().toString();
        Bed bed1 = new Bed(UUID.randomUUID().toString(), client_id, BedStatus.VACANT.toString());
        Bed bed2 = new Bed(UUID.randomUUID().toString(), client_id, BedStatus.VACANT.toString());
        Bed bed3 = new Bed(UUID.randomUUID().toString(), client_id, BedStatus.VACANT.toString());
        when(bedMapper.mapBedDTOtoBedEntity(any()))
                .thenReturn(bed1)
                .thenReturn(bed2)
                .thenReturn(bed3);

        bedService.createBeds(no_of_beds, client_id);

        verify(bedRepository, times(3)).save(bedArgumentCaptor.capture());

        for(Bed bed: bedArgumentCaptor.getAllValues()) {
            Assert.assertEquals(BedStatus.VACANT.toString(), bed.getBedStatus());
        }
    }

    @Test
    public void given_Bed_When_Updating_Status_During_Patient_Admission_Then_Throw_No_Exception() throws BedDoesNotExistException, BedHasAlreadyBeenOccupiedException, BedDoesNotBelongToSpecifiedClientException {
        String bed_id = UUID.randomUUID().toString();
        String client_id = UUID.randomUUID().toString();
        Bed bed = new Bed(bed_id, client_id ,"VACANT");

        when(bedRepository.findById(any())).thenReturn(Optional.of(bed));

        bedService.updateBedStatusWhenPatientAdmitted(bed_id, client_id);

        verify(bedRepository).save(bedArgumentCaptor.capture());

        Assert.assertEquals("OCCUPIED", bedArgumentCaptor.getValue().getBedStatus());
        Assert.assertEquals(bed_id, bedArgumentCaptor.getValue().getBed_id());
        Assert.assertEquals(client_id, bedArgumentCaptor.getValue().getClientId());
    }

    @Test(expected = BedHasAlreadyBeenOccupiedException.class)
    public void given_Bed_When_Updating_Status_During_Patient_Admission_But_Bed_Is_Already_Occupied_Then_Throw_Exception() throws BedDoesNotExistException, BedHasAlreadyBeenOccupiedException, BedDoesNotBelongToSpecifiedClientException {
        String bed_id = UUID.randomUUID().toString();
        String client_id = UUID.randomUUID().toString();
        Bed bed = new Bed(bed_id, client_id ,"OCCUPIED");

        when(bedRepository.findById(any())).thenReturn(Optional.of(bed));

        bedService.updateBedStatusWhenPatientAdmitted(bed_id, client_id);
    }

    @Test(expected = BedDoesNotExistException.class)
    public void given_Bed_When_Updating_Status_During_Patient_Admission_But_Bed_Does_Not_Exist_Then_Throw_Exception() throws BedDoesNotExistException, BedHasAlreadyBeenOccupiedException, BedDoesNotBelongToSpecifiedClientException {
        String bed_id = UUID.randomUUID().toString();
        String client_id = UUID.randomUUID().toString();

        when(bedRepository.findById(any())).thenReturn(Optional.empty());

        bedService.updateBedStatusWhenPatientAdmitted(bed_id, client_id);
    }

    @Test(expected = BedDoesNotBelongToSpecifiedClientException.class)
    public void given_Bed_When_Updating_Status_During_Patient_Admission_But_Bed_Not_Associated_With_Client_Then_Throw_Exception() throws BedDoesNotExistException, BedHasAlreadyBeenOccupiedException, BedDoesNotBelongToSpecifiedClientException {
        String bed_id = UUID.randomUUID().toString();
        String client_id = UUID.randomUUID().toString();
        Bed bed = new Bed(bed_id, UUID.randomUUID().toString() ,"OCCUPIED");

        when(bedRepository.findById(any())).thenReturn(Optional.of(bed));

        bedService.updateBedStatusWhenPatientAdmitted(bed_id, client_id);
    }

    @Test
    public void given_Bed_When_Updating_Status_After_Patient_Discharge_Then_Throw_No_Exception() throws BedDoesNotExistException {
        String bed_id = UUID.randomUUID().toString();
        String client_id = UUID.randomUUID().toString();
        Bed bed = new Bed(bed_id, client_id ,"OCCUPIED");

        when(bedRepository.findById(any())).thenReturn(Optional.of(bed));

        bedService.updateBedStatusAfterPatientDischarged(bed_id);

        verify(bedRepository).save(bedArgumentCaptor.capture());

        Assert.assertEquals("VACANT", bedArgumentCaptor.getValue().getBedStatus());
        Assert.assertEquals(bed_id, bedArgumentCaptor.getValue().getBed_id());
        Assert.assertEquals(client_id, bedArgumentCaptor.getValue().getClientId());
    }

    @Test
    public void given_Beds_When_Got_All_Bed_Status_Successfully_Then_Throw_No_Exception() throws BedDoesNotExistException {
        String client_id = UUID.randomUUID().toString();
        String bed_id_1 = UUID.randomUUID().toString();
        String bed_id_2 = UUID.randomUUID().toString();
        Bed bed1 = new Bed(bed_id_1, client_id ,"VACANT");
        Bed bed2 = new Bed(bed_id_2, client_id ,"OCCUPIED");
        List<Bed> beds = new ArrayList<>();
        beds.add(bed1);
        beds.add(bed2);

        when(bedRepository.findByClientId(any())).thenReturn(beds);

        Map<String,String> bedStatus = bedService.getAllBedStatusByClientId(client_id);

        Assert.assertEquals(2, bedStatus.size());
    }

    @Test(expected = BedDoesNotExistException.class)
    public void given_No_Beds_Associated_With_Client_id_When_Tried_To_Get_All_Bed_Status_Then_Throw_Exception() throws BedDoesNotExistException {
        String client_id = UUID.randomUUID().toString();
        List<Bed> beds = new ArrayList<>();

        when(bedRepository.findByClientId(any())).thenReturn(beds);

        bedService.getAllBedStatusByClientId(client_id);
    }

    @Test
    public void given_Existing_Bed_id_When_Tried_To_Get_Bed_Then_Throw_No_Exception() throws BedDoesNotExistException {
        String existing_bed_id = UUID.randomUUID().toString();
        Bed bed = new Bed(existing_bed_id, UUID.randomUUID().toString() ,"OCCUPIED");
        when(bedRepository.findById(existing_bed_id)).thenReturn(Optional.of(bed));

        Bed savedBed = bedService.getBed(existing_bed_id);
        Assert.assertNotNull(savedBed.getBed_id());
        Assert.assertNotNull(savedBed.getBedStatus());
        Assert.assertNotNull(savedBed.getClientId());
    }

    @Test(expected = BedDoesNotExistException.class)
    public void given_Non_Existing_Bed_id_When_Tried_To_Get_Bed_Then_Throw_Exception() throws BedDoesNotExistException {
        String non_existing_bed_id = UUID.randomUUID().toString();
        when(bedRepository.findById(any())).thenReturn(Optional.empty());

        bedService.getBed(non_existing_bed_id);
    }
}

