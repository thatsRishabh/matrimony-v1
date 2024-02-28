package com.matrimony.service;

import com.matrimony.entity.Gallery;
import com.matrimony.entity.Slider;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.validator.GalleryValidation;
import com.matrimony.validator.SliderValidation;
import org.springframework.http.ResponseEntity;

public interface GalleryService {
    public ResponseEntity<ApiResponse<Gallery>> createGallery(GalleryValidation galleryRequest);

    public ResponseEntity<ApiResponse<Object>> getGalleries(SearchPaginationRequest searchParams);

    public ResponseEntity<ApiResponse<Gallery>> updateGallery(Long galleryId, GalleryValidation galleryRequest);

    public ResponseEntity<ApiResponse<Gallery>>  getGallery(Long galleryId);

    public ResponseEntity<ApiResponse<?>> deleteGallery(Long galleryId);
}
