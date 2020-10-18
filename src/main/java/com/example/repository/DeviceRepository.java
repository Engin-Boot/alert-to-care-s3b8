package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {

    public List<Device> findByDeviceStatus(String device_status);

    public Device findByBedId(String bed_id);

}
