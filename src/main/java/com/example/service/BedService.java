package com.example.service;

import com.example.dto.BedDTO;
import com.example.entities.Bed;
import com.example.entities.BedStatus;
import com.example.exceptions.BedDoesNotExistException;
import com.example.exceptions.BedHasAlreadyBeenOccupiedException;
import com.example.mapper.BedMapper;
import com.example.repository.BedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class BedService {
    BedRepository bedRepository;

    BedMapper bedMapper;

    @Autowired
    public BedService(BedRepository bedRepository,BedMapper bedMapper) {
        this.bedRepository = bedRepository;
        this.bedMapper = bedMapper;
    }


    public void createBeds(int no_of_beds, String client_id){
        for(int i=0;i<no_of_beds;i++){
            String bed_id = UUID.randomUUID().toString();
            BedDTO bedDTO = new BedDTO(client_id, BedStatus.VACANT.toString());
            Bed bed = bedMapper.mapBedDTOtoBedEntity(bedDTO);
            bed.setBed_id(bed_id);
            bedRepository.save(bed);
        }
    }

    private boolean checkIfBedVacant(Optional<Bed> bed){
        if(bed.get().getBedStatus().equalsIgnoreCase(BedStatus.VACANT.toString())){
            return true;
        }
        return false;
    }

    public Bed updateBedStatusWhenPatientAdmitted(String bed_id) throws BedHasAlreadyBeenOccupiedException, BedDoesNotExistException {
        Optional<Bed> bed = bedRepository.findById(bed_id);
        if(bed.isPresent()){
            if(checkIfBedVacant(bed)){
                bed.get().setBedStatus(BedStatus.OCCUPIED.toString());
                return bedRepository.save(bed.get());
            }
            else{
                throw new BedHasAlreadyBeenOccupiedException("Bed with id = " + bed_id + " has already been occupied");
            }
        }
        else{
            throw new BedDoesNotExistException("Bed with id = " + bed_id + " does not exist");
        }
    }

    public Map<String,String> getAllBedStatusByClientId(String client_id) throws BedDoesNotExistException {
        Map<String, String> allBedStatus = new HashMap<>();
        List<Bed> beds = bedRepository.findByClientId(client_id);
        if(beds.isEmpty()){
            throw new BedDoesNotExistException("Beds does not exist for clientId= "+client_id);
        }
        for(Bed bed: beds){
            allBedStatus.put(bed.getBed_id(), bed.getBedStatus().toString());
        }
        return allBedStatus;
    }

    public void updateBedStatusWhenPatientDischarged(String bed_id){
        Bed bed = bedRepository.findById(bed_id).get();
        bed.setBedStatus(BedStatus.VACANT.toString());
        bedRepository.save(bed);
    }


}
