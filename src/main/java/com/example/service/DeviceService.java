package com.example.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.DeviceDTO;
import com.example.entities.Device;
import com.example.entities.DeviceStatus;
import com.example.exceptions.DeviceDoesNotExistException;
import com.example.exceptions.DeviceNotAssociatedWithBedException;
import com.example.mapper.DeviceMapper;
import com.example.repository.DeviceRepository;


@Service
public class DeviceService {
    DeviceRepository deviceRepository;

    DeviceMapper deviceMapper;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository, DeviceMapper deviceMapper) {
        this.deviceRepository = deviceRepository;
        this.deviceMapper = deviceMapper;
    }

    public Device getDeviceHavingAssocationWithBed(String device_id) throws DeviceDoesNotExistException, DeviceNotAssociatedWithBedException {
        Optional<Device> device = deviceRepository.findById(device_id);
        if(device.isPresent()){
            if(device.get().getBedId() != null){
                return device.get();
            }
            else{
                throw new DeviceNotAssociatedWithBedException("Device with id = "+device_id+ " not associated with any bed");
            }
        }
        else{
            throw new DeviceDoesNotExistException("Device does not exist with id = "+device_id);
        }
    }

    public void createDevices(int no_of_beds){
        for(int i=0;i<no_of_beds;i++){
            String device_id = UUID.randomUUID().toString();
            DeviceDTO deviceDTO = new DeviceDTO(device_id, "TYPE 1", DeviceStatus.NOTINUSE.toString(), null);
            Device device = deviceMapper.mapDeviceDTOtoDeviceEntity(deviceDTO);
            device.setDeviceId(device_id);
            deviceRepository.save(device);
        }
    }

    public Device associateDeviceToBed(String bed_id){
        Device deviceNotInUse = getDeviceWhichIsNotInUse();
        deviceNotInUse.setBedId(bed_id);
        deviceNotInUse.setDeviceStatus(DeviceStatus.INUSE.toString());
        return deviceRepository.save(deviceNotInUse);
    }

    private Device getDeviceWhichIsNotInUse(){
        return deviceRepository.findByDeviceStatus(DeviceStatus.NOTINUSE.toString()).get(0);
    }

    private Device getDeviceByBedId(String bed_id) throws DeviceDoesNotExistException {
        Device device = deviceRepository.findByBedId(bed_id);
        if(device != null){
            return device;
        }
        else{
            throw new DeviceDoesNotExistException("Device does not exist with bedId = "+bed_id);
        }
    }

    public Device updateDeviceAfterPatientDischarge(String bed_id) throws DeviceDoesNotExistException {
        Device deviceToUpdate = getDeviceByBedId(bed_id);
        deviceToUpdate.setBedId(null);
        deviceToUpdate.setDeviceStatus(DeviceStatus.NOTINUSE.toString());
        return deviceRepository.save(deviceToUpdate);
    }


    


}
