package com.lockerz.locker.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lockers")
public class Lockers extends AbstractDate {


    @Id
    @Column(name = "locker_code")
    private String lockerCode;

    @Column(name = "type")
    private String type;

    @Column(name = "deposit")
    private Double deposit;

    @Column(name = "daily_late_fee")
    private Double dailyLateFee;

    @Column(name = "unlock_fee")
    private Double unlockFee;

    public String getLockerCode() {
        return lockerCode;
    }

    public void setLockerCode(String lockerCode) {
        this.lockerCode = lockerCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Double getDailyLateFee() {
        return dailyLateFee;
    }

    public void setDailyLateFee(Double dailyLateFee) {
        this.dailyLateFee = dailyLateFee;
    }

    public Double getUnlockFee() {
        return unlockFee;
    }

    public void setUnlockFee(Double unlockFee) {
        this.unlockFee = unlockFee;
    }
}
