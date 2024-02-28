package com.matrimony.repository;

import com.matrimony.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    public Page<Profile> findByReligionContaining(String religion, Pageable pageable);
    public Page<Profile> findByPlaceOfBirthContaining(String city, Pageable pageable);
    public Page<Profile> findByCasteContaining(String caste, Pageable pageable);
    public Optional<Profile> findByUserId(int id);

//    below is to search inside any object
    public Page<Profile> findByUser_Gender(int gender, Pageable pageable);
}
