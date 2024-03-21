package com.lockerz.locker.repository;

import com.lockerz.locker.model.LockerOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LockerOrdersRepository extends JpaRepository<LockerOrders, String> {
    List<LockerOrders> findByUser_EmailIgnoreCaseAndStatusIgnoreCase(String email, String status);



}
