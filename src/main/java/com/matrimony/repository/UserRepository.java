package com.matrimony.repository;

import com.matrimony.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAddress(String emailAddress);

    User findByResetOtp(String resetOtp);

    public Page<User> findByFirstNameContaining(String name, Pageable pageable);

    public Page<User> findByGender(int gender, Pageable pageable);

    boolean existsByEmailAddress(String emailAddress);
}
