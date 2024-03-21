package com.lockerz.locker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LockerOrderRequest implements Serializable {

    private String email;
    private int totalOrder;
    private Double totalDeposit;
    private List<ReqRes> dataOrder;

    public LockerOrderRequest() {
    }



    public LockerOrderRequest(String email, int totalOrder, List<ReqRes> dataOrder) {
        this.email = email;
        this.totalOrder = totalOrder;
        this.dataOrder = dataOrder;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ReqRes> getDataOrder() {
        return dataOrder;
    }

    public void setDataOrder(List<ReqRes> dataOrder) {
        this.dataOrder = dataOrder;
    }

    public int getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(int totalOrder) {
        this.totalOrder = totalOrder;
    }

    public Double getTotalDeposit() {
        return totalDeposit;
    }

    public void setTotalDeposit(Double totalDeposit) {
        this.totalDeposit = totalDeposit;
    }
}
