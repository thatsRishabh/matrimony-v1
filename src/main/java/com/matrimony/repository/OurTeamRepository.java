package com.matrimony.repository;

import com.matrimony.entity.AboutUs;
import com.matrimony.entity.OurTeam;
import com.matrimony.entity.Slider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OurTeamRepository extends JpaRepository<OurTeam, Long> {
    public Page<OurTeam> findByNameContaining(String title, Pageable pageable);
}
