package com.matrimony.repository;

import com.matrimony.entity.ContactUs;
import com.matrimony.entity.Slider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactUsRepository extends JpaRepository<ContactUs, Long> {
    public Page<ContactUs> findByNameContaining(String name, Pageable pageable);
}
