package com.example.repository;

import com.example.entities.Alert;
import com.example.entities.Bed;
import com.example.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {

    public List<Device> findByDeviceStatus(String device_status);

    public Device findByBedId(String bed_id);

}
