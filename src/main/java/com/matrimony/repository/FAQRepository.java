package com.matrimony.repository;

import com.matrimony.entity.FAQ;
import com.matrimony.entity.Slider;
import com.matrimony.entity.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FAQRepository extends JpaRepository<FAQ, Long> {

    public Page<FAQ> findByQuestionContaining(String question, Pageable pageable);
}
