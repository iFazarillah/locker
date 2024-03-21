package com.lockerz.locker.repository;

import com.lockerz.locker.model.LockerOrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LockerOrderDetailsRepository extends JpaRepository<LockerOrderDetails, String> {
}
