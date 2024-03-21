package com.lockerz.locker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LockerOrderResponse implements Serializable {

    private Double totalBilling;
    private String email;
    private Double refund;
    private String message;

    private List<ReqRes> lockerList;

    public LockerOrderResponse() {
    }

    public Double getRefund() {
        return refund;
    }

    public void setRefund(Double refund) {
        this.refund = refund;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setLockerList(List<ReqRes> lockerList) {
        this.lockerList = lockerList;
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

    public List<ReqRes> getLockerList() {
        return lockerList;
    }


}
