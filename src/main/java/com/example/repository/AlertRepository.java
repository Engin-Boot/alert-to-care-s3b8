package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.Alert;

@Repository
public interface AlertRepository extends JpaRepository<Alert, String> {

    List<Alert> findByPatientId(String patient_id);

}
