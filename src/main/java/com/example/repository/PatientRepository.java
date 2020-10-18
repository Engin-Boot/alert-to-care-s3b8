package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {

    public Patient findByBedId(String bed_id);
}
