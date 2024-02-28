package com.matrimony.repository;

import com.matrimony.entity.Slider;
import com.matrimony.entity.Timeline;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimelineRepository extends JpaRepository<Timeline, Long> {

    public Page<Timeline> findByTitleContaining(String title, Pageable pageable);
}