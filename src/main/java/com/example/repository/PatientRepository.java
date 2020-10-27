<<<<<<< HEAD
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
=======
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
>>>>>>> c7f7f1d525f38a0be4d1be9bb7a35d89c079543a
