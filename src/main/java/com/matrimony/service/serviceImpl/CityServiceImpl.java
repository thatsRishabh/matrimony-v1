package com.matrimony.service.serviceImpl;

import com.matrimony.entity.City;
import com.matrimony.repository.CityRepository;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.CityService;
import com.matrimony.validator.CityValidation;
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
public class CityServiceImpl implements CityService {
    @Autowired
    CityRepository cityRepository;

    //    below method will by default create the timestamp
    @PrePersist
    public void prePersist(City city) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        city.setCreatedAt(now);
        city.setUpdatedAt(now);
    }

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public ResponseEntity<ApiResponse<City>> createCity(CityValidation languageRequest) {
        try {
            City language = new City();
            language.setName(languageRequest.getName());
            City payload = this.cityRepository.save(language);
            return ResponseEntity.ok(new ApiResponse<>("success", "Data saved successfully", payload, 200));
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception here and log it
            log.error("An error occurred while saving data", e);
            return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }
    @Override
    public ResponseEntity<ApiResponse<Object>> getCities(SearchPaginationRequest searchParams) {

        try {
            Long id = searchParams.getId();
            String name = searchParams.getName();
            Integer perPageRecord = searchParams.getPer_page_record();

            // Set the default value of page to 1
            Integer page = (searchParams.getPage() != null) ? searchParams.getPage() : 1;

            Page<City> languagePage;

            if (id != null) {
                Optional<City> sliderOptional = cityRepository.findById(id);
                if (sliderOptional.isPresent()) {
                    City language = sliderOptional.get();
                    languagePage = new PageImpl<>(Collections.singletonList(language));
                } else {
                    languagePage = Page.empty(); // No matching slider found
                }
            }
            else if (name != null) {
                languagePage = cityRepository.findByNameContaining(name, PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else {
                languagePage = cityRepository.findAll(PageRequest.of(page - 1, perPageRecord,Sort.by(Sort.Order.desc("id"))));
            }

            List<City> sliderEntities = languagePage.getContent();


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
    public ResponseEntity<ApiResponse<City>> updateCity(Long cityId, CityValidation languageRequest) {
        try {
            Optional<City> existingLanguage = this.cityRepository.findById(cityId);

            if (existingLanguage.isPresent()) {
                // Update the existing slider with the new data
                City updatedLanguage = existingLanguage.get();
                updatedLanguage.setName(languageRequest.getName());
                City payload = this.cityRepository.save(updatedLanguage);

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
    public ResponseEntity<ApiResponse<City>>  getCity(Long cityId) {
        try {
            Optional<City> sliderEntityOptional = this.cityRepository.findById(cityId);

            if (sliderEntityOptional.isPresent()) {
                City sliderEntity = sliderEntityOptional.get();
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
    public ResponseEntity<ApiResponse<?>> deleteCity(Long cityId) {
        try {
            Optional<City>  language = this.cityRepository.findById(cityId);
            if (language.isPresent()) {
                //  CategoryEntity categoryEntity= new CategoryEntity();
                //  categoryEntity.setCid(categoryID);
                //  this.categoryRepository.delete(categoryEntity);
                // below one is easy single line code


                this.cityRepository.deleteById(cityId);
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
