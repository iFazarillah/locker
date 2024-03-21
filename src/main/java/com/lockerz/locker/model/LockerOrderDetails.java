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


}
