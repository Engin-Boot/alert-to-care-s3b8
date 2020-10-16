package com.example.mapper;

import com.example.dto.BedDTO;
import com.example.dto.ClientDTO;
import com.example.entities.Bed;
import com.example.entities.Client;
import org.springframework.stereotype.Component;

@Component
public class BedMapper {

    public Bed mapBedDTOtoBedEntity(BedDTO bedDTO){
        Bed bed = new Bed();
        bed.setBedStatus(bedDTO.getBedStatus());
        bed.setClientId(bedDTO.getClientId());

        return bed;
    }
}
