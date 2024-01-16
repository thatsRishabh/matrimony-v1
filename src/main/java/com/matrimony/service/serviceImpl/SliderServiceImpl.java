package com.matrimony.service.serviceImpl;

import com.matrimony.entity.Slider;
import com.matrimony.entity.User;
import com.matrimony.repository.SliderRepository;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.SliderService;
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
public class SliderServiceImpl implements SliderService {

    @Autowired
    SliderRepository sliderRepository;

    //    below method will by default create the timestamp
    @PrePersist
    public void prePersist(Slider slider) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        slider.setCreatedAt(now);
        slider.setUpdatedAt(now);
    }

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public ResponseEntity<ApiResponse<Slider>> createSlider(SliderValidation sliderRequest) {
        try {
            Slider slider = new Slider();
            slider.setTitle(sliderRequest.getTitle());
            slider.setSub_title(sliderRequest.getSub_title());
            slider.setOrder_number(sliderRequest.getOrder_number());
            slider.setPosition_type(sliderRequest.getPosition_type());
            slider.setStatus(sliderRequest.getStatus());
            slider.setUrl(sliderRequest.getUrl());
            slider.setDescription(sliderRequest.getDescription());
            slider.setImage(sliderRequest.getImage());
            Slider payload = this.sliderRepository.save(slider);
            return ResponseEntity.ok(new ApiResponse<>("success", "Data saved successfully", payload, 200));
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception here and log it
            log.error("An error occurred while saving data", e);
            return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }
    @Override
    public ResponseEntity<ApiResponse<Object>> getSliders(SearchPaginationRequest searchParams) {

        try {
            Long id = searchParams.getId();
            String title = searchParams.getTitle();
            Integer perPageRecord = searchParams.getPer_page_record();

            // Set the default value of page to 1
            Integer page = (searchParams.getPage() != null) ? searchParams.getPage() : 1;

            Page<Slider> sliderPage;

            if (id != null) {
                Optional<Slider> sliderOptional = sliderRepository.findById(id);
                if (sliderOptional.isPresent()) {
                    Slider slider = sliderOptional.get();
                    sliderPage = new PageImpl<>(Collections.singletonList(slider));
                } else {
                    sliderPage = Page.empty(); // No matching slider found
                }
            }
            else if (title != null) {
                sliderPage = sliderRepository.findByTitleContaining(title, PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else {
                sliderPage = sliderRepository.findAll(PageRequest.of(page - 1, perPageRecord,Sort.by(Sort.Order.desc("id"))));
            }

            List<Slider> sliderEntities = sliderPage.getContent();


            Map<String, Object> map = Map.of(
                    "data", sliderEntities,
                    "totalElements", sliderPage.getTotalElements(),
                    "currentPage", page,
                    "perPageRecord", perPageRecord,
                    "totalPages", sliderPage.getTotalPages()
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
    public ResponseEntity<ApiResponse<Slider>> updateSlider(Long sliderId, SliderValidation sliderRequest) {
        try {
            Optional<Slider> existingSlider = this.sliderRepository.findById(sliderId);

            if (existingSlider.isPresent()) {
                // Update the existing slider with the new data
                Slider updatedSlider = existingSlider.get();
                updatedSlider.setTitle(sliderRequest.getTitle());
                updatedSlider.setSub_title(sliderRequest.getSub_title());
                updatedSlider.setOrder_number(sliderRequest.getOrder_number());
                updatedSlider.setPosition_type(sliderRequest.getPosition_type());
                updatedSlider.setStatus(sliderRequest.getStatus());
                updatedSlider.setUrl(sliderRequest.getUrl());
                updatedSlider.setDescription(sliderRequest.getDescription());
                updatedSlider.setImage(sliderRequest.getImage());
                Slider payload = this.sliderRepository.save(updatedSlider);

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
    public ResponseEntity<ApiResponse<Slider>>  getSlider(Long sliderId) {
        try {
            Optional<Slider> sliderEntityOptional = this.sliderRepository.findById(sliderId);

            if (sliderEntityOptional.isPresent()) {
                Slider sliderEntity = sliderEntityOptional.get();
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
    public ResponseEntity<ApiResponse<?>> deleteSlider(Long sliderId) {
        try {
            Optional<Slider> slider = this.sliderRepository.findById(sliderId);
            if (slider.isPresent()) {
                //  CategoryEntity categoryEntity= new CategoryEntity();
                //  categoryEntity.setCid(categoryID);
                //  this.categoryRepository.delete(categoryEntity);
                // below one is easy single line code


                this.sliderRepository.deleteById(sliderId);
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
