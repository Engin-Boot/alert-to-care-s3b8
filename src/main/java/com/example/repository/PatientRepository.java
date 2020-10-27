package com.example.repository;

import com.example.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {

    public Patient findByBedId(String bed_id);
    public List<Patient> findByClientId(String client_id);
}
