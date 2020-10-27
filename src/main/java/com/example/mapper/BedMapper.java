package com.example.mapper;

import org.springframework.stereotype.Component;

import com.example.dto.BedDTO;
import com.example.entities.Bed;

@Component
public class BedMapper {

    public Bed mapBedDTOtoBedEntity(BedDTO bedDTO){
        Bed bed = new Bed();
        bed.setBedStatus(bedDTO.getBedStatus());
        bed.setClientId(bedDTO.getClientId());

        return bed;
    }
}
