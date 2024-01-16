package com.matrimony.repository;

import com.matrimony.entity.Menu;
import com.matrimony.entity.Slider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SliderRepository extends JpaRepository<Slider, Long> {

    public Page<Slider> findByTitleContaining(String title, Pageable pageable);
}
