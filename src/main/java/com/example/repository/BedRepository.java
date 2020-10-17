package com.example.repository;

import com.example.entities.Bed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BedRepository extends JpaRepository<Bed, String> {

    public List<Bed> findByClientId(String client_id);

}
