package com.matrimony.service.serviceImpl;

import com.matrimony.entity.ContactUs;
import com.matrimony.entity.Slider;
import com.matrimony.repository.ContactUsRepository;
import com.matrimony.repository.SliderRepository;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.ContactUsService;
import com.matrimony.validator.ContactUsValidation;
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
public class ContactUsServiceImpl implements ContactUsService {

    @Autowired
    ContactUsRepository contactUsRepository;

    //    below method will by default create the timestamp
    @PrePersist
    public void prePersist(ContactUs contactUs) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        contactUs.setCreatedAt(now);
        contactUs.setUpdatedAt(now);
    }

    private final Logger log = LoggerFactory.getLogger(getClass());

    private String name;
    private String email;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    private Integer mobile_number;

    @Override
    public ResponseEntity<ApiResponse<ContactUs>> createContactUs(ContactUsValidation contactUsRequest) {
        try {
            ContactUs contactUs = new ContactUs();
            contactUs.setName(contactUsRequest.getName());
            contactUs.setEmail(contactUsRequest.getEmail());
            contactUs.setAddress(contactUsRequest.getAddress());
            contactUs.setMobile_number(contactUsRequest.getMobile_number());
            ContactUs payload = this.contactUsRepository.save(contactUs);
            return ResponseEntity.ok(new ApiResponse<>("success", "Data saved successfully", payload, 200));
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while saving data", e);
            return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }
    @Override
    public ResponseEntity<ApiResponse<Object>> getContactUss(SearchPaginationRequest searchParams) {

        try {
            Long id = searchParams.getId();
            String name = searchParams.getName();
            Integer perPageRecord = searchParams.getPer_page_record();

            // Set the default value of page to 1
            Integer page = (searchParams.getPage() != null) ? searchParams.getPage() : 1;

            Page<ContactUs> sliderPage;

            if (id != null) {
                Optional<ContactUs> sliderOptional = contactUsRepository.findById(id);
                if (sliderOptional.isPresent()) {
                    ContactUs slider = sliderOptional.get();
                    sliderPage = new PageImpl<>(Collections.singletonList(slider));
                } else {
                    sliderPage = Page.empty(); // No matching slider found
                }
            }
            else if (name != null) {
                sliderPage = contactUsRepository.findByNameContaining(name, PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else {
                sliderPage = contactUsRepository.findAll(PageRequest.of(page - 1, perPageRecord,Sort.by(Sort.Order.desc("id"))));
            }

            List<ContactUs> sliderEntities = sliderPage.getContent();


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
    public ResponseEntity<ApiResponse<ContactUs>> updateContactUs(Long contactUsId, ContactUsValidation contactUsRequest) {
        try {
            Optional<ContactUs> existingContactUs = this.contactUsRepository.findById(contactUsId);

            if (existingContactUs.isPresent()) {
                // Update the existing slider with the new data
                ContactUs updatedContactUs = existingContactUs.get();
                updatedContactUs.setName(contactUsRequest.getName());
                updatedContactUs.setEmail(contactUsRequest.getEmail());
                updatedContactUs.setAddress(contactUsRequest.getAddress());
                updatedContactUs.setMobile_number(contactUsRequest.getMobile_number());
                ContactUs payload = this.contactUsRepository.save(updatedContactUs);

                return ResponseEntity.ok(new ApiResponse<>("success", "Data updated successfully", payload, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "contactUsId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while updating data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<ContactUs>>  getContactUs(Long contactUsId) {
        try {
            Optional<ContactUs> sliderEntityOptional = this.contactUsRepository.findById(contactUsId);

            if (sliderEntityOptional.isPresent()) {
                ContactUs sliderEntity = sliderEntityOptional.get();
                return ResponseEntity.ok(new ApiResponse<>("success", "Data retrieved successfully", sliderEntity, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "contactUsId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while retrieving data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> deleteContactUs(Long contactUsId) {
        try {
            Optional<ContactUs> slider = this.contactUsRepository.findById(contactUsId);
            if (slider.isPresent()) {
                //  CategoryEntity categoryEntity= new CategoryEntity();
                //  categoryEntity.setCid(categoryID);
                //  this.categoryRepository.delete(categoryEntity);
                // below one is easy single line code


                this.contactUsRepository.deleteById(contactUsId);
                return ResponseEntity.ok(new ApiResponse<>("success", "Data deleted successfully", null, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "contactUsId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while deleting data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }
}
