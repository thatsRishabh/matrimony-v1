package com.matrimony.service.serviceImpl;

import com.matrimony.entity.Language;
import com.matrimony.entity.Slider;
import com.matrimony.repository.LanguageRepository;
import com.matrimony.repository.SliderRepository;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.LanguageService;
import com.matrimony.validator.LanguageValidation;
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
public class LanguageServiceImpl implements LanguageService {
    @Autowired
    LanguageRepository languageRepository;

    //    below method will by default create the timestamp
    @PrePersist
    public void prePersist(Language language) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        language.setCreatedAt(now);
        language.setUpdatedAt(now);
    }

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public ResponseEntity<ApiResponse<Language>> createLanguage(LanguageValidation languageRequest) {
        try {
            Language language = new Language();
            language.setName(languageRequest.getName());
            Language payload = this.languageRepository.save(language);
            return ResponseEntity.ok(new ApiResponse<>("success", "Data saved successfully", payload, 200));
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception here and log it
            log.error("An error occurred while saving data", e);
            return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }
    @Override
    public ResponseEntity<ApiResponse<Object>> getLanguages(SearchPaginationRequest searchParams) {

        try {
            Long id = searchParams.getId();
            String name = searchParams.getName();
            Integer perPageRecord = searchParams.getPer_page_record();

            // Set the default value of page to 1
            Integer page = (searchParams.getPage() != null) ? searchParams.getPage() : 1;

            Page<Language> languagePage;

            if (id != null) {
                Optional<Language> sliderOptional = languageRepository.findById(id);
                if (sliderOptional.isPresent()) {
                    Language language = sliderOptional.get();
                    languagePage = new PageImpl<>(Collections.singletonList(language));
                } else {
                    languagePage = Page.empty(); // No matching slider found
                }
            }
            else if (name != null) {
                languagePage = languageRepository.findByNameContaining(name, PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else {
                languagePage = languageRepository.findAll(PageRequest.of(page - 1, perPageRecord,Sort.by(Sort.Order.desc("id"))));
            }

            List<Language> sliderEntities = languagePage.getContent();


            Map<String, Object> map = Map.of(
                    "data", sliderEntities,
                    "totalElements", languagePage.getTotalElements(),
                    "currentPage", page,
                    "perPageRecord", perPageRecord,
                    "totalPages", languagePage.getTotalPages()
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
    public ResponseEntity<ApiResponse<Language>> updateLanguage(Long languageId, LanguageValidation languageRequest) {
        try {
            Optional<Language> existingLanguage = this.languageRepository.findById(languageId);

            if (existingLanguage.isPresent()) {
                // Update the existing slider with the new data
                Language updatedLanguage = existingLanguage.get();
                updatedLanguage.setName(languageRequest.getName());
                Language payload = this.languageRepository.save(updatedLanguage);

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
    public ResponseEntity<ApiResponse<Language>>  getLanguage(Long languageId) {
        try {
            Optional<Language> sliderEntityOptional = this.languageRepository.findById(languageId);

            if (sliderEntityOptional.isPresent()) {
                Language sliderEntity = sliderEntityOptional.get();
                return ResponseEntity.ok(new ApiResponse<>("success", "Data retrieved successfully", sliderEntity, 200));
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
    public ResponseEntity<ApiResponse<?>> deleteLanguage(Long languageId) {
        try {
            Optional<Language>  language = this.languageRepository.findById(languageId);
            if (language.isPresent()) {
                //  CategoryEntity categoryEntity= new CategoryEntity();
                //  categoryEntity.setCid(categoryID);
                //  this.categoryRepository.delete(categoryEntity);
                // below one is easy single line code


                this.languageRepository.deleteById(languageId);
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
