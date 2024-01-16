package com.matrimony.repository;

import com.matrimony.entity.AppSettings;
import com.matrimony.entity.Slider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppSettingRepository extends JpaRepository<AppSettings, Long> {
    Optional<AppSettings> findFirstByOrderByIdAsc();
}
