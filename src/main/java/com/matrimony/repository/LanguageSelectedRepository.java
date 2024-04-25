package com.matrimony.repository;

import com.matrimony.entity.LanguageSelected;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguageSelectedRepository extends JpaRepository<LanguageSelected, Long> {
    public List<LanguageSelected> findByProfileId(long id);

    public List<LanguageSelected> findByProfileIdAndLanguageNameContaining(long id, String languageName);
    public void deleteByProfileId(long id);
}