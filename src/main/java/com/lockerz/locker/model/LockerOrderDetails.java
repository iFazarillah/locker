package com.lockerz.locker.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
public class LockerOrderDetails extends AbstractDate {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private String id;

    @Column(name = "days_late")
    private int daysLate;

    @Column(name = "total_deposit")
    private Double totalDeposit;

    @Column(name = "total_fine_due")
    private Double totalFineDue;

    public LockerOrderDetails() {
    }

    public LockerOrderDetails(String id, int daysLate, Double totalDeposit, Double totalFineDue) {
        this.id = id;
        this.daysLate = daysLate;
        this.totalDeposit = totalDeposit;
        this.totalFineDue = totalFineDue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDaysLate() {
        return daysLate;
    }

    public void setDaysLate(int daysLate) {
        this.daysLate = daysLate;
    }

    public Double getTotalDeposit() {
        return totalDeposit;
    }

    public void setTotalDeposit(Double totalDeposit) {
        this.totalDeposit = totalDeposit;
    }

    public Double getTotalFineDue() {
        return totalFineDue;
    }

    public void setTotalFineDue(Double totalFineDue) {
        this.totalFineDue = totalFineDue;
    }
}
