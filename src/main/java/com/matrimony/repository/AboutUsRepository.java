package com.matrimony.repository;

import com.matrimony.entity.AboutUs;
import com.matrimony.entity.Slider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AboutUsRepository extends JpaRepository<AboutUs, Long> {
    public Page<AboutUs> findByTitleContaining(String title, Pageable pageable);
}
