//package com.example.service;
//
//import com.example.entities.Bed;
//import com.example.entities.BedStatus;
//import com.example.exceptions.BedDoesNotExistException;
//import com.example.exceptions.BedHasAlreadyBeenOccupiedException;
//import com.example.repository.BedRepository;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Captor;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.util.*;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class BedServiceTest {
//
//    @Mock
//    BedRepository bedRepository;
//
//    BedService bedService;
//
//    @Captor
//    ArgumentCaptor<Bed> bedArgumentCaptor;
//
//    @Before
//    public void setUp() {
//        bedService = new BedService(bedRepository);
//    }
//
//    @Test
//    public void given_Beds_When_Created_Then_Throw_No_Exception() {
//
//        int no_of_beds = 3;
//        String client_id = UUID.randomUUID().toString();
//
//        bedService.createBeds(no_of_beds, client_id);
//
//        verify(bedRepository, times(3)).save(bedArgumentCaptor.capture());
//
//        for(Bed bed: bedArgumentCaptor.getAllValues()) {
//            Assert.assertEquals(BedStatus.VACANT.toString(), bed.getBedStatus());
//        }
//    }
//
//    @Test
//    public void given_Patient_When_Admitted_Successfully_Then_Update_Bed_Status() throws BedDoesNotExistException, BedHasAlreadyBeenOccupiedException {
//        String bed_id = UUID.randomUUID().toString();
//        String client_id = UUID.randomUUID().toString();
//        Bed bed = new Bed(bed_id, client_id ,"VACANT");
//
//        when(bedRepository.findById(any())).thenReturn(Optional.of(bed));
//
//        bedService.updateBedStatusWhenPatientAdmitted(bed_id);
//
//        verify(bedRepository).save(bedArgumentCaptor.capture());
//
//        Assert.assertEquals("OCCUPIED", bedArgumentCaptor.getValue().getBedStatus());
//        Assert.assertEquals(bed_id, bedArgumentCaptor.getValue().getBed_id());
//        Assert.assertEquals(client_id, bedArgumentCaptor.getValue().getClientId());
//    }
//
//    @Test(expected = BedHasAlreadyBeenOccupiedException.class)
//    public void given_Patient_When_Admitted_But_Bed_Is_Already_Occupied_Then_Throw_Exception() throws BedDoesNotExistException, BedHasAlreadyBeenOccupiedException {
//        String bed_id = UUID.randomUUID().toString();
//        String client_id = UUID.randomUUID().toString();
//        Bed bed = new Bed(bed_id, client_id ,"OCCUPIED");
//
//        when(bedRepository.findById(any())).thenReturn(Optional.of(bed));
//
//        bedService.updateBedStatusWhenPatientAdmitted(bed_id);
//
//        verify(bedRepository).save(bedArgumentCaptor.capture());
//    }
//
//    @Test(expected = BedDoesNotExistException.class)
//    public void given_Patient_When_Admitted_But_Bed_Does_Not_Exist_Then_Throw_Exception() throws BedDoesNotExistException, BedHasAlreadyBeenOccupiedException {
//        String bed_id = UUID.randomUUID().toString();
//        String client_id = UUID.randomUUID().toString();
//        Bed bed = new Bed(bed_id, client_id ,"OCCUPIED");
//
//        when(bedRepository.findById(any())).thenReturn(Optional.empty());
//
//        bedService.updateBedStatusWhenPatientAdmitted(bed_id);
//    }
//
//    @Test
//    public void given_Patient_When_Discharged_Successfully_Then_Update_Bed_Status(){
//        String bed_id = UUID.randomUUID().toString();
//        String client_id = UUID.randomUUID().toString();
//        Bed bed = new Bed(bed_id, client_id ,"OCCUPIED");
//
//        when(bedRepository.findById(any())).thenReturn(Optional.of(bed));
//
//        bedService.updateBedStatusWhenPatientDischarged(bed_id);
//
//        verify(bedRepository).save(bedArgumentCaptor.capture());
//
//        Assert.assertEquals("VACANT", bedArgumentCaptor.getValue().getBedStatus());
//        Assert.assertEquals(bed_id, bedArgumentCaptor.getValue().getBed_id());
//        Assert.assertEquals(client_id, bedArgumentCaptor.getValue().getClientId());
//    }
//
//    @Test
//    public void given_Beds_When_Got_All_Bed_Status_Successfully_Then_Throw_No_Exception() throws BedDoesNotExistException {
//        String client_id = UUID.randomUUID().toString();
//        String bed_id_1 = UUID.randomUUID().toString();
//        String bed_id_2 = UUID.randomUUID().toString();
//        Bed bed1 = new Bed(bed_id_1, client_id ,"VACANT");
//        Bed bed2 = new Bed(bed_id_2, client_id ,"OCCUPIED");
//        List<Bed> beds = new ArrayList<>();
//        beds.add(bed1);
//        beds.add(bed2);
//
//        when(bedRepository.findByClientId(any())).thenReturn(beds);
//
//        Map<String,String> bedStatus = bedService.getAllBedStatusByClientId(client_id);
//
//        Assert.assertEquals(2, bedStatus.size());
//    }
//
//    @Test(expected = BedDoesNotExistException.class)
//    public void given_No_Beds_Associated_With_Client_id_When_Tried_To_Get_All_Bed_Status_Then_Throw_Exception() throws BedDoesNotExistException {
//        String client_id = UUID.randomUUID().toString();
//        List<Bed> beds = new ArrayList<>();
//
//        when(bedRepository.findByClientId(any())).thenReturn(beds);
//
//        bedService.getAllBedStatusByClientId(client_id);
//    }
//}
//
