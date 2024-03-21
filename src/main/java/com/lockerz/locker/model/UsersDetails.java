package com.lockerz.locker.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "user_details")
public class UsersDetails {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private String id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "id_card_number")
    private String idCardNumber;

    public UsersDetails() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }
}
