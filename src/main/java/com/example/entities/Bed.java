package com.example.entities;

import com.example.customannotations.Enum;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;


@Entity
public class Bed {

    @Id
    private String bed_id;
    @NotNull(message = "Client_Id should not be null")
    private String clientId;
    @NotNull(message = "BED Status should not be null")
    @Enum(enumClass = BedStatus.class, ignoreCase = true, message = "Please enter correct value for BedStatus {VACANT, OCCUPIED, MAINTENANCE}")
    private String bedStatus;

    public Bed(){}

    public Bed(String bed_id, @NotNull(message = "Client_Id should not be null") String clientId, @NotNull(message = "BED Status should not be null") String bedStatus) {
        this.bed_id = bed_id;
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

    public String getBed_id() {
        return bed_id;
    }
    public void setBed_id(String bed_id) {
        this.bed_id = bed_id;
    }

}