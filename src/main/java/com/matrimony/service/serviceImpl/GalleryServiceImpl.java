package com.matrimony.service.serviceImpl;

import com.matrimony.entity.Gallery;
import com.matrimony.entity.Slider;
import com.matrimony.repository.GalleryRepository;
import com.matrimony.repository.SliderRepository;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.GalleryService;
import com.matrimony.validator.GalleryValidation;
import com.matrimony.validator.SliderValidation;
import jakarta.persistence.PrePersist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GalleryServiceImpl implements GalleryService {
    @Autowired
    GalleryRepository galleryRepository;

    //    below method will by default create the timestamp
    @PrePersist
    public void prePersist(Gallery gallery) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        gallery.setCreatedAt(now);
        gallery.setUpdatedAt(now);
    }

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public ResponseEntity<ApiResponse<Gallery>> createGallery(GalleryValidation galleryRequest) {
        try {
            Gallery  gallery = new Gallery();
            gallery.setOrder_number(galleryRequest.getOrder_number());
            gallery.setImage(galleryRequest.getImage());
            Gallery payload = this.galleryRepository.save(gallery);
            return ResponseEntity.ok(new ApiResponse<>("success", "Data saved successfully", payload, 200));
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception here and log it
            log.error("An error occurred while saving data", e);
            return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }
    @Override
    public ResponseEntity<ApiResponse<Object>> getGalleries(SearchPaginationRequest searchParams) {

        try {
            Long id = searchParams.getId();
            Integer perPageRecord = searchParams.getPer_page_record();

            // Set the default value of page to 1
            Integer page = (searchParams.getPage() != null) ? searchParams.getPage() : 1;

            Page<Gallery> galleryPage;

            if (id != null) {
                Optional<Gallery> galleryOptional = galleryRepository.findById(id);
                if (galleryOptional.isPresent()) {
                    Gallery gallery = galleryOptional.get();
                    galleryPage = new PageImpl<>(Collections.singletonList(gallery));
                } else {
                    galleryPage = Page.empty(); // No matching slider found
                }
            }
            else {
                galleryPage = galleryRepository.findAll(PageRequest.of(page - 1, perPageRecord,Sort.by(Sort.Order.desc("id"))));
            }

            List<Gallery> galleryEntities = galleryPage.getContent();


            Map<String, Object> map = Map.of(
                    "data", galleryEntities,
                    "totalElements", galleryPage.getTotalElements(),
                    "currentPage", page,
                    "perPageRecord", perPageRecord,
                    "totalPages", galleryPage.getTotalPages()
            );
            return ResponseEntity.ok( new ApiResponse<>("success", "Data retrieved successfully", map, 200));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("An error occurred while saving data", e);
//            return e.getMessage();
            return ResponseEntity.internalServerError().body( new ApiResponse<>("error", e.getMessage(), null, 500));
        }

    }

    @Override
    public ResponseEntity<ApiResponse<Gallery>> updateGallery(Long sliderId, GalleryValidation galleryRequest) {
        try {
            Optional<Gallery> existingGallery = this.galleryRepository.findById(sliderId);

            if (existingGallery.isPresent()) {
                // Update the existing slider with the new data
                Gallery updatedGallery = existingGallery.get();
                updatedGallery.setOrder_number(galleryRequest.getOrder_number());
                updatedGallery.setImage(galleryRequest.getImage());
                Gallery payload = this.galleryRepository.save(updatedGallery);

                return ResponseEntity.ok(new ApiResponse<>("success", "Data updated successfully", payload, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "sliderId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while updating data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Gallery>>  getGallery(Long sliderId) {
        try {
            Optional<Gallery> galleryEntityOptional = this.galleryRepository.findById(sliderId);

            if (galleryEntityOptional.isPresent()) {
                Gallery galleryEntity = galleryEntityOptional.get();
                return ResponseEntity.ok(new ApiResponse<>("success", "Data retrieved successfully", galleryEntity, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "sliderId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while retrieving data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> deleteGallery(Long galleryId) {
        try {
            Optional<Gallery> gallery = this.galleryRepository.findById(galleryId);
            if (gallery.isPresent()) {

                this.galleryRepository.deleteById(galleryId);
                return ResponseEntity.ok(new ApiResponse<>("success", "Data deleted successfully", null, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "sliderId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while deleting data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }
}
