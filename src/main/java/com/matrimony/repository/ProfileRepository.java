package com.matrimony.repository;

import com.matrimony.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    public Page<Profile> findByReligionContaining(String religion, Pageable pageable);
    public Page<Profile> findByCasteContaining(String caste, Pageable pageable);
    public Optional<Profile> findByUserId(int id);
}
