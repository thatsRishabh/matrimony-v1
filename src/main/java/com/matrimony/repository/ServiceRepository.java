package com.matrimony.repository;

import com.matrimony.entity.ServiceEntity;
import com.matrimony.entity.Slider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    public Page<ServiceEntity> findByTitleContaining(String title, Pageable pageable);
}
