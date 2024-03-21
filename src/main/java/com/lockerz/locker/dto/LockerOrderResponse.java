package com.lockerz.locker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LockerOrderResponse implements Serializable {

    private Double totalBilling;
    private String email;

    private List<LockerDto> lockerList;

    public LockerOrderResponse() {
    }

    public LockerOrderResponse(Double totalBilling, String email) {
        this.totalBilling = totalBilling;
        this.email = email;
    }

    public Double getTotalBilling() {
        return totalBilling;
    }

    public void setTotalBilling(Double totalBilling) {
        this.totalBilling = totalBilling;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<LockerDto> getLockerList() {
        return lockerList;
    }

    public void setLockerList(List<LockerDto> lockerList) {
        this.lockerList = lockerList;
    }
}
