package com.matrimony.repository;

import com.matrimony.entity.Slider;
import com.matrimony.entity.Testimony;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestimonyRepository extends JpaRepository<Testimony, Long> {

    public Page<Testimony> findByNameContaining(String title, Pageable pageable);

}
