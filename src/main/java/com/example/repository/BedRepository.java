package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.Bed;

@Repository
public interface BedRepository extends JpaRepository<Bed, String> {

    public List<Bed> findByClientId(String client_id);

}
