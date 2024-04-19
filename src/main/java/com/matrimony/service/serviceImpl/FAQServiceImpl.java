package com.matrimony.service.serviceImpl;

import com.matrimony.entity.FAQ;
import com.matrimony.entity.Slider;
import com.matrimony.repository.FAQRepository;
import com.matrimony.repository.SliderRepository;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.FAQService;
import com.matrimony.validator.FAQValidation;
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
public class FAQServiceImpl implements FAQService {
    @Autowired
    FAQRepository faqRepository;

    //    below method will by default create the timestamp
    @PrePersist
    public void prePersist(FAQ faq) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        faq.setCreatedAt(now);
        faq.setUpdatedAt(now);
    }

    private final Logger log = LoggerFactory.getLogger(getClass());

    private String question;
    private String answer;
    private Integer order_number;
    private Integer status;

    @Override
    public ResponseEntity<ApiResponse<FAQ>> createFAQ(FAQValidation faqRequest) {
        try {
            FAQ faq = new FAQ();
            faq.setQuestion(faqRequest.getQuestion());
            faq.setAnswer(faqRequest.getAnswer());
            faq.setOrder_number(faqRequest.getOrder_number());
            faq.setStatus(faqRequest.getStatus());
            FAQ payload = this.faqRepository.save(faq);
            return ResponseEntity.ok(new ApiResponse<>("success", "Data saved successfully", payload, 200));
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception here and log it
            log.error("An error occurred while saving data", e);
            return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }
    @Override
    public ResponseEntity<ApiResponse<Object>> getFAQs(SearchPaginationRequest searchParams) {

        try {
            Long id = searchParams.getId();
            String question = searchParams.getQuestion();
            Integer perPageRecord = searchParams.getPer_page_record();

            // Set the default value of page to 1
            Integer page = (searchParams.getPage() != null) ? searchParams.getPage() : 1;

            Page<FAQ> faqPage;

            if (id != null) {
                Optional<FAQ> faqOptional = faqRepository.findById(id);
                if (faqOptional.isPresent()) {
                    FAQ faq = faqOptional.get();
                    faqPage = new PageImpl<>(Collections.singletonList(faq));
                } else {
                    faqPage = Page.empty(); // No matching slider found
                }
            }
            else if (question != null) {
                faqPage = faqRepository.findByQuestionContaining(question, PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else {
                faqPage = faqRepository.findAll(PageRequest.of(page - 1, perPageRecord,Sort.by(Sort.Order.desc("id"))));
            }

            List<FAQ> faqEntities = faqPage.getContent();


            Map<String, Object> map = Map.of(
                    "data", faqEntities,
                    "totalElements", faqPage.getTotalElements(),
                    "currentPage", page,
                    "perPageRecord", perPageRecord,
                    "totalPages", faqPage.getTotalPages()
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
    public ResponseEntity<ApiResponse<FAQ>> updateFAQ(Long faqId, FAQValidation faqRequest) {
        try {
            Optional<FAQ> existingFAQ = this.faqRepository.findById(faqId);

            if (existingFAQ.isPresent()) {
                // Update the existing slider with the new data
                FAQ updatedSlider = existingFAQ.get();
                updatedSlider.setQuestion(faqRequest.getQuestion());
                updatedSlider.setAnswer(faqRequest.getAnswer());
                updatedSlider.setOrder_number(faqRequest.getOrder_number());
                updatedSlider.setStatus(faqRequest.getStatus());
                FAQ payload = this.faqRepository.save(updatedSlider);

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
    public ResponseEntity<ApiResponse<FAQ>>  getFAQ(Long faqId) {
        try {
            Optional<FAQ> faqEntityOptional = this.faqRepository.findById(faqId);

            if (faqEntityOptional.isPresent()) {
                FAQ sliderEntity = faqEntityOptional.get();
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
    public ResponseEntity<ApiResponse<?>> deleteFAQ(Long faqId) {
        try {
            Optional<FAQ> faq = this.faqRepository.findById(faqId);
            if (faq.isPresent()) {
                //  CategoryEntity categoryEntity= new CategoryEntity();
                //  categoryEntity.setCid(categoryID);
                //  this.categoryRepository.delete(categoryEntity);
                // below one is easy single line code


                this.faqRepository.deleteById(faqId);
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
