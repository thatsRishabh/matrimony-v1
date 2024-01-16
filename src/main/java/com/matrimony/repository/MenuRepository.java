package com.matrimony.repository;

import com.matrimony.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    public Page<Menu> findByNameContaining(String name, Pageable pageable);

    List<Menu> findByParentId(Long parentId);

//    List<Menu> findAllByParentIdIsNull();

    public Page<Menu> findAllByParentIdIsNull(Pageable pageable);


}
