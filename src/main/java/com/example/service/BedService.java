package com.example.service;

import com.example.entities.Bed;
import com.example.entities.Status;
import com.example.repository.BedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class BedService {
    BedRepository bedRepository;

    @Autowired
    public BedService(BedRepository bedRepository) {
        this.bedRepository = bedRepository;
    }


    public void save(Bed bed) {
        bedRepository.save(bed);
    }

    public void createBeds(int no_of_beds, String client_id){
        for(int i=0;i<no_of_beds;i++){
            Bed bed = new Bed(UUID.randomUUID().toString(), client_id, Status.VACANT );
            bedRepository.save(bed);
        }
    }
}
