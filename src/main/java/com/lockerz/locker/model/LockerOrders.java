package com.lockerz.locker.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "locker_orders")
public class LockerOrders extends AbstractDate {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private String id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "locker_code")
    private Lockers locker;

    @Column(name = "password")
    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    @Column(name = "rental_start_time", columnDefinition = "timestamp(6)")
    private LocalDateTime rentalStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    @Column(name = "rental_finish_time", columnDefinition = "timestamp(6)")
    private LocalDateTime rentalFinishTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    @Column(name = "rental_completed_time", columnDefinition = "timestamp(6)")
    private LocalDateTime rentalCompletedTime;

    @Column(name = "status")
    private String status;

    @Column(name = "locked")
    private boolean isLocked;

    @Column(name = "eligible_to_unlock ")
    private boolean isEligibleToUnlock;

    @OneToOne
    @JoinColumn(name = "locker_order_detail_id")
    private LockerOrderDetails lockerOrderDetail;

    @Column(name = "wrong_password_counter")
    private int wrongPasswordCounter;

    public LockerOrders() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Lockers getLocker() {
        return locker;
    }

    public void setLocker(Lockers locker) {
        this.locker = locker;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getRentalStartTime() {
        return rentalStartTime;
    }

    public void setRentalStartTime(LocalDateTime rentalStartTime) {
        this.rentalStartTime = rentalStartTime;
    }

    public LocalDateTime getRentalFinishTime() {
        return rentalFinishTime;
    }

    public void setRentalFinishTime(LocalDateTime rentalFinishTime) {
        this.rentalFinishTime = rentalFinishTime;
    }

    public LocalDateTime getRentalCompletedTime() {
        return rentalCompletedTime;
    }

    public void setRentalCompletedTime(LocalDateTime rentalCompletedTime) {
        this.rentalCompletedTime = rentalCompletedTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public boolean isEligibleToUnlock() {
        return isEligibleToUnlock;
    }

    public void setEligibleToUnlock(boolean eligibleToUnlock) {
        isEligibleToUnlock = eligibleToUnlock;
    }

    public LockerOrderDetails getLockerOrderDetail() {
        return lockerOrderDetail;
    }

    public void setLockerOrderDetail(LockerOrderDetails lockerOrderDetail) {
        this.lockerOrderDetail = lockerOrderDetail;
    }

    public int getWrongPasswordCounter() {
        return wrongPasswordCounter;
    }

    public void setWrongPasswordCounter(int wrongPasswordCounter) {
        this.wrongPasswordCounter = wrongPasswordCounter;
    }
}
