package com.lockerz.locker.repository;

import com.lockerz.locker.model.Lockers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LockersRepository extends JpaRepository<Lockers, String> {
}
