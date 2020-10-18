package com.example.dto;

import javax.validation.constraints.NotNull;

import com.example.customannotations.Enum;
import com.example.entities.BedStatus;

public class BedDTO {

    @NotNull(message = "Client_Id should not be null")
    private String clientId;
    @NotNull(message = "BED Status should not be null")
    @Enum(enumClass = BedStatus.class, ignoreCase = true, message = "Please enter correct value for BedStatus {VACANT, OCCUPIED, MAINTENANCE}")
    private String bedStatus;

    public BedDTO(){}

    public BedDTO(@NotNull(message = "Client_Id should not be null") String clientId, @NotNull(message = "BED Status should not be null") String bedStatus) {
        this.clientId = clientId;
        this.bedStatus = bedStatus;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getBedStatus() {
        return bedStatus;
    }

    public void setBedStatus(String bedStatus) {
        this.bedStatus = bedStatus.toUpperCase();
    }
}