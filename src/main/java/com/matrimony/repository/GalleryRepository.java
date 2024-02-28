package com.matrimony.repository;

import com.matrimony.entity.Gallery;
import com.matrimony.entity.Slider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {
}
