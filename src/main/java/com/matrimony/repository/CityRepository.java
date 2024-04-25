package com.matrimony.repository;

import com.matrimony.entity.City;
import com.matrimony.entity.Language;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {

    public Page<City> findByNameContaining(String name, Pageable pageable);
}
