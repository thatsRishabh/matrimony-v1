package com.matrimony.controller;

import com.matrimony.entity.Gallery;
import com.matrimony.entity.Slider;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.GalleryService;
import com.matrimony.service.SliderService;
import com.matrimony.validator.GalleryValidation;
import com.matrimony.validator.SliderValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/gallery")
public class GalleryController {
    @Autowired
    private GalleryService galleryService;

    //add menu
    @PostMapping()
    public ResponseEntity<ApiResponse<Gallery>> addGallery(@Valid @RequestBody GalleryValidation galleryRequest){
        return this.galleryService.createGallery(galleryRequest);
    }

    //get all category
    @PostMapping("/")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Object>> getGalleries(@RequestBody SearchPaginationRequest searchParams){
        return this.galleryService.getGalleries(searchParams);
    }

    @PutMapping("/{galleryId}")
    public ResponseEntity<ApiResponse<Gallery>>  updateGallery(@PathVariable("galleryId") Long galleryId , @Valid @RequestBody GalleryValidation galleryRequest){
        return this.galleryService.updateGallery(galleryId, galleryRequest);
    }

    // get single category
    @GetMapping("/{galleryId}")
    public ResponseEntity<ApiResponse<Gallery>>  getGallery(@PathVariable("galleryId") Long galleryId){
        return this.galleryService.getGallery(galleryId);
    }

    @DeleteMapping("/{galleryId}")
    public ResponseEntity<ApiResponse<?>> deleteGallery(@PathVariable("galleryId") Long galleryId){
        return this.galleryService.deleteGallery(galleryId);
    }
}
