package com.matrimony.repository;

import com.matrimony.entity.Blog;
import com.matrimony.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    public Page<Blog> findByTitleContaining(String title, Pageable pageable);
}
