package com.example.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
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

import com.example.entities.Device;
import com.example.entities.DeviceStatus;
import com.example.exceptions.DeviceDoesNotExistException;
import com.example.exceptions.DeviceNotAssociatedWithBedException;
import com.example.mapper.DeviceMapper;
import com.example.repository.DeviceRepository;

@RunWith(MockitoJUnitRunner.class)
public class DeviceServiceTest {

    @Mock
    DeviceRepository deviceRepository;

    @Mock
    DeviceMapper deviceMapper;

    DeviceService deviceService;

    @Captor
    ArgumentCaptor<Device> deviceArgumentCaptor;

    @Before
    public void setUp() {
        deviceService = new DeviceService(deviceRepository, deviceMapper);
    }

    @Test
    public void given_Existing_DeviceId_When_Get_Device_By_Id_Then_Throw_No_Exception() throws DeviceNotAssociatedWithBedException, DeviceDoesNotExistException {
        String bed_id = UUID.randomUUID().toString();
        String existing_device_id = UUID.randomUUID().toString();
        Device existing_device = new Device( existing_device_id,"type1", DeviceStatus.NOTINUSE.toString(), bed_id);

        when(deviceRepository.findById(any())).thenReturn(Optional.of(existing_device));
        Device device = deviceService.getDeviceHavingAssocationWithBed(existing_device_id);
        Assert.assertNotNull(device.getBedId());
        Assert.assertNotNull(device.getDeviceStatus());
        Assert.assertNotNull(device.getDeviceType());
        Assert.assertNotNull(device.getDeviceId());
    }

    @Test(expected = DeviceDoesNotExistException.class)
    public void given_Non_Existing_DeviceId_When_Get_Device_By_Id_Then_Throw_Exception() throws DeviceNotAssociatedWithBedException, DeviceDoesNotExistException {
        String non_existing_device_id = UUID.randomUUID().toString();

        when(deviceRepository.findById(non_existing_device_id)).thenReturn(Optional.empty());
        deviceService.getDeviceHavingAssocationWithBed(non_existing_device_id);
    }

    @Test(expected = DeviceNotAssociatedWithBedException.class)
    public void given_Existing_DeviceId_When_Get_Device_By_Id_But_Device_Not_Associated_With_Any_Bed_Then_Throw_Exception() throws DeviceNotAssociatedWithBedException, DeviceDoesNotExistException {
        String existing_device_id = UUID.randomUUID().toString();
        Device existing_device = new Device( existing_device_id,"type1", DeviceStatus.NOTINUSE.toString(), null);


        when(deviceRepository.findById(existing_device_id)).thenReturn(Optional.of(existing_device));
        deviceService.getDeviceHavingAssocationWithBed(existing_device_id);
    }

    @Test
    public void given_Devices_When_Created_Then_Throw_No_Exception() {

        int no_of_beds = 3;
        Device device1 = new Device(UUID.randomUUID().toString(),"type1", DeviceStatus.NOTINUSE.toString(), UUID.randomUUID().toString());
        Device device2 = new Device(UUID.randomUUID().toString(),"type2", DeviceStatus.NOTINUSE.toString(), UUID.randomUUID().toString());
        Device device3 = new Device(UUID.randomUUID().toString(),"type3", DeviceStatus.NOTINUSE.toString(), UUID.randomUUID().toString());

        when(deviceMapper.mapDeviceDTOtoDeviceEntity(any()))
                .thenReturn(device1)
                .thenReturn(device2)
                .thenReturn(device3);

        deviceService.createDevices(no_of_beds);

        verify(deviceRepository, times(3)).save(deviceArgumentCaptor.capture());

        for(Device device: deviceArgumentCaptor.getAllValues()) {
            Assert.assertEquals(DeviceStatus.NOTINUSE.toString(), device.getDeviceStatus());
        }
    }

    @Test
    public void given_Device_When_Associating_Device_With_Bed_Then_Throw_No_Exception(){
        String bed_id =  UUID.randomUUID().toString();
        Device device1 = new Device(UUID.randomUUID().toString(),"type1", DeviceStatus.NOTINUSE.toString(), UUID.randomUUID().toString());
        Device device2 = new Device(UUID.randomUUID().toString(),"type2", DeviceStatus.NOTINUSE.toString(), UUID.randomUUID().toString());
        List<Device> deviceList = new ArrayList<>();
        deviceList.add(device1);
        deviceList.add(device2);

        when(deviceRepository.findByDeviceStatus(any())).thenReturn(deviceList);

        deviceService.associateDeviceToBed(bed_id);

        verify(deviceRepository, times(1)).save(deviceArgumentCaptor.capture());

        Assert.assertEquals(bed_id, deviceArgumentCaptor.getValue().getBedId());
        Assert.assertEquals("type1", deviceArgumentCaptor.getValue().getDeviceType());
        Assert.assertEquals(DeviceStatus.INUSE.toString(), deviceArgumentCaptor.getValue().getDeviceStatus());
    }

    @Test
    public void given_Device_When_Updating_Device_After_Patient_Discharge_Then_Throw_No_Exception() throws DeviceDoesNotExistException {
        String valid_bed_id = UUID.randomUUID().toString();
        String device_id = UUID.randomUUID().toString();
        Device existing_device = new Device(device_id,"type1", DeviceStatus.NOTINUSE.toString(), valid_bed_id);

        when(deviceRepository.findByBedId(valid_bed_id)).thenReturn(existing_device);

        deviceService.updateDeviceAfterPatientDischarge(valid_bed_id);

        verify(deviceRepository, times(1)).save(deviceArgumentCaptor.capture());

        Assert.assertNull(deviceArgumentCaptor.getValue().getBedId());
        Assert.assertEquals("type1", deviceArgumentCaptor.getValue().getDeviceType());
        Assert.assertEquals(DeviceStatus.NOTINUSE.toString(), deviceArgumentCaptor.getValue().getDeviceStatus());
    }

    @Test(expected = DeviceDoesNotExistException.class)
    public void given_Device_With_Invalid_Bed_Id_When_Updating_Device_After_Patient_Discharge_Then_Throw_Exception() throws DeviceDoesNotExistException {
        String invalid_bed_id = UUID.randomUUID().toString();
        String device_id = UUID.randomUUID().toString();
        Device existing_device = new Device(device_id,"type1", DeviceStatus.NOTINUSE.toString(), UUID.randomUUID().toString());

        when(deviceRepository.findByBedId(invalid_bed_id)).thenReturn(null);

        deviceService.updateDeviceAfterPatientDischarge(invalid_bed_id);
    }
}

