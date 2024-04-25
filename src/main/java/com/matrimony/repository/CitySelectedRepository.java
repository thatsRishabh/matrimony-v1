package com.matrimony.repository;

import com.matrimony.entity.CitySelected;
import com.matrimony.entity.LanguageSelected;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CitySelectedRepository extends JpaRepository<CitySelected, Long> {
    public List<CitySelected> findByProfileId(long id);
//
//    public List<LanguageSelected> findByProfileIdAndLanguageNameContaining(long id, String languageName);
    public void deleteByProfileId(long id);
}