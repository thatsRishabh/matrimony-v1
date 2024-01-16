package com.matrimony.service.serviceImpl;

import com.matrimony.entity.AboutUs;
import com.matrimony.entity.Slider;
import com.matrimony.repository.AboutUsRepository;
import com.matrimony.repository.SliderRepository;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.AboutUsService;
import com.matrimony.validator.AboutUsValidation;
import com.matrimony.validator.SliderValidation;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
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
public class AboutUsServiceImpl implements AboutUsService {

    @Autowired
    AboutUsRepository aboutUsRepository;

    //    below method will by default create the timestamp
    @PrePersist
    public void prePersist(AboutUs  aboutUs) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        aboutUs.setCreatedAt(now);
        aboutUs.setUpdatedAt(now);
    }

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public ResponseEntity<ApiResponse<AboutUs>> createAboutUs(AboutUsValidation aboutUsRequest) {
        try {
            AboutUs aboutUs = new AboutUs();
            aboutUs.setTitle(aboutUsRequest.getTitle());
            aboutUs.setDescription(aboutUsRequest.getDescription());
            aboutUs.setOrder_number(aboutUsRequest.getOrder_number());
            aboutUs.setImage(aboutUsRequest.getImage());
            aboutUs.setStatus(aboutUsRequest.getStatus());
            AboutUs payload = this.aboutUsRepository.save(aboutUs);
            return ResponseEntity.ok(new ApiResponse<>("success", "Data saved successfully", payload, 200));
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while saving data", e);
            return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }
    @Override
    public ResponseEntity<ApiResponse<Object>> getAboutUss(SearchPaginationRequest searchParams) {

        try {
            Long id = searchParams.getId();
            String title = searchParams.getTitle();
            Integer perPageRecord = searchParams.getPer_page_record();

            // Set the default value of page to 1
            Integer page = (searchParams.getPage() != null) ? searchParams.getPage() : 1;

            Page<AboutUs> aboutUsPage;

            if (id != null) {
                Optional<AboutUs> aboutUsOptional = aboutUsRepository.findById(id);
                if (aboutUsOptional.isPresent()) {
                    AboutUs aboutUs = aboutUsOptional.get();
                    aboutUsPage = new PageImpl<>(Collections.singletonList(aboutUs));
                } else {
                    aboutUsPage = Page.empty(); // No matching aboutUs found
                }
            }
            else if (title != null) {
                aboutUsPage = aboutUsRepository.findByTitleContaining(title, PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else {
                aboutUsPage = aboutUsRepository.findAll(PageRequest.of(page - 1, perPageRecord,Sort.by(Sort.Order.desc("id"))));
            }

            List<AboutUs> aboutUsEntities = aboutUsPage.getContent();


            Map<String, Object> map = Map.of(
                    "data", aboutUsEntities,
                    "totalElements", aboutUsPage.getTotalElements(),
                    "currentPage", page,
                    "perPageRecord", perPageRecord,
                    "totalPages", aboutUsPage.getTotalPages()
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
    public ResponseEntity<ApiResponse<AboutUs>> updateAboutUs(Long aboutUsId, AboutUsValidation aboutUsRequest) {
        try {
            Optional<AboutUs> existingAboutUs = this.aboutUsRepository.findById(aboutUsId);

            if (existingAboutUs.isPresent()) {
                // Update the existing aboutUs with the new data
                AboutUs updatedAboutUs = existingAboutUs.get();
                updatedAboutUs.setTitle(aboutUsRequest.getTitle());
                updatedAboutUs.setDescription(aboutUsRequest.getDescription());
                updatedAboutUs.setOrder_number(aboutUsRequest.getOrder_number());
                updatedAboutUs.setImage(aboutUsRequest.getImage());
                updatedAboutUs.setStatus(aboutUsRequest.getStatus());
                AboutUs payload = this.aboutUsRepository.save(updatedAboutUs);

                return ResponseEntity.ok(new ApiResponse<>("success", "Data updated successfully", payload, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "aboutUsId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while updating data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<AboutUs>>  getAboutUs(Long aboutUsId) {
        try {
            Optional<AboutUs> aboutUsEntityOptional = this.aboutUsRepository.findById(aboutUsId);

            if (aboutUsEntityOptional.isPresent()) {
                AboutUs aboutUsEntity = aboutUsEntityOptional.get();
                return ResponseEntity.ok(new ApiResponse<>("success", "Data retrieved successfully", aboutUsEntity, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "aboutUsId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while retrieving data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> deleteAboutUs(Long aboutUsId) {
        try {
            Optional<AboutUs> aboutUs = this.aboutUsRepository.findById(aboutUsId);
            if (aboutUs.isPresent()) {
                //  CategoryEntity categoryEntity= new CategoryEntity();
                //  categoryEntity.setCid(categoryID);
                //  this.categoryRepository.delete(categoryEntity);
                // below one is easy single line code


                this.aboutUsRepository.deleteById(aboutUsId);
                return ResponseEntity.ok(new ApiResponse<>("success", "Data deleted successfully", null, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "aboutUsId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while deleting data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }
}
