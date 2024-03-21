package com.lockerz.locker.repository;

import com.lockerz.locker.model.UsersDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UsersDetails, String> {
}
