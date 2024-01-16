package com.matrimony.service.serviceImpl;

import com.matrimony.entity.Slider;
import com.matrimony.entity.Testimony;
import com.matrimony.repository.SliderRepository;
import com.matrimony.repository.TestimonyRepository;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.TestimonyService;
import jakarta.persistence.*;
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
import java.util.*;

@Service
public class TestimonyServiceImpl implements TestimonyService {
    @Autowired
    TestimonyRepository testimonyRepository;


    //   below method will by default create the timestamp
    @PrePersist
    public void prePersist(Testimony testimony) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        testimony.setCreatedAt(now);
        testimony.setUpdatedAt(now);
    }

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public ResponseEntity<ApiResponse<Testimony>> createTestimony(Testimony testimonyRequest) {
        try {
            Testimony payload = this.testimonyRepository.save(testimonyRequest);
            return ResponseEntity.ok(new ApiResponse<>("success", "Data saved successfully", payload, 200));
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while saving data", e);
            return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> getTestimonys(SearchPaginationRequest searchParams) {

        try {
            Long id = searchParams.getId();
            String title = searchParams.getTitle();
            Integer perPageRecord = searchParams.getPer_page_record();

            // Set the default value of page to 1
            Integer page = (searchParams.getPage() != null) ? searchParams.getPage() : 1;

            Page<Testimony> testimonyPage;

            if (id != null) {
                Optional<Testimony> testimonyOptional = testimonyRepository.findById(id);
                if (testimonyOptional.isPresent()) {
                    Testimony testimony = testimonyOptional.get();
                    testimonyPage = new PageImpl<>(Collections.singletonList(testimony));
                } else {
                    testimonyPage = Page.empty(); // No matching testimony found
                }
            }
            else if (title != null) {
                testimonyPage = testimonyRepository.findByNameContaining(title, PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else {
                testimonyPage = testimonyRepository.findAll(PageRequest.of(page - 1, perPageRecord,Sort.by(Sort.Order.desc("id"))));
            }

            List<Testimony> testimonyEntities = testimonyPage.getContent();


            Map<String, Object> map = Map.of(
                    "data", testimonyEntities,
                    "totalElements", testimonyPage.getTotalElements(),
                    "currentPage", page,
                    "perPageRecord", perPageRecord,
                    "totalPages", testimonyPage.getTotalPages()
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
    public ResponseEntity<ApiResponse<Testimony>> updateTestimony(Long testimonyId, Testimony testimonyRequest) {
        try {
            Optional<Testimony> existingTestimony = this.testimonyRepository.findById(testimonyId);

            if (existingTestimony.isPresent()) {
                // Update the existing Testimony with the new data
                Testimony updatedTestimony = existingTestimony.get();
                updatedTestimony.setName(testimonyRequest.getName());
                updatedTestimony.setDescription(testimonyRequest.getDescription());
                updatedTestimony.setOrder_number(testimonyRequest.getOrder_number());
                updatedTestimony.setRating(testimonyRequest.getRating());
                updatedTestimony.setStatus(testimonyRequest.getStatus());
                updatedTestimony.setImage(testimonyRequest.getImage());
                Testimony payload = this.testimonyRepository.save(updatedTestimony);

                return ResponseEntity.ok(new ApiResponse<>("success", "Data updated successfully", payload, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "TestimonyId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while updating data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Testimony>>  getTestimony(Long testimonyId){
        try {
            Optional<Testimony> testimonyEntityOptional = this.testimonyRepository.findById(testimonyId);

            if (testimonyEntityOptional.isPresent()) {
                Testimony testimonyEntity = testimonyEntityOptional.get();
                return ResponseEntity.ok(new ApiResponse<>("success", "Data retrieved successfully", testimonyEntity, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "TestimonyId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while retrieving data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> deleteTestimony(Long testimonyId){
        try {
            Optional<Testimony> testimony = this.testimonyRepository.findById(testimonyId);
            if (testimony.isPresent()) {
                //  CategoryEntity categoryEntity= new CategoryEntity();
                //  categoryEntity.setCid(categoryID);
                //  this.categoryRepository.delete(categoryEntity);

                // below one is easy single line code

                this.testimonyRepository.deleteById(testimonyId);
                return ResponseEntity.ok(new ApiResponse<>("success", "Data deleted successfully", null, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "TestimonyId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while deleting data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }
}
