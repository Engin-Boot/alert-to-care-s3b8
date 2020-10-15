package com.example.entities;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
public class Bed {

    @Id
    private String bed_id;
    @NotNull(message = "Client_Id should not be null")
    private String clientId;
    @NotNull(message = "BED Status should not be null")
    @Enumerated(EnumType.STRING)
    private BedStatus bedStatus;

    public Bed(){}

    public Bed(String bed_id, String clientId, BedStatus bedStatus) {
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

    public BedStatus getBedStatus() {
        return bedStatus;
    }

    public void setBedStatus(BedStatus bedStatus) {
        this.bedStatus = bedStatus;
    }

    public String getBed_id() {
        return bed_id;
    }
    public void setBed_id(String bed_id) {
        this.bed_id = bed_id;
    }

}