package com.matrimony.repository;

import com.matrimony.entity.Language;
import com.matrimony.entity.Slider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {

    public Page<Language> findByNameContaining(String name, Pageable pageable);
}
