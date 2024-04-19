package com.matrimony.service.serviceImpl;

import com.matrimony.entity.Slider;
import com.matrimony.entity.Subscription;
import com.matrimony.repository.SliderRepository;
import com.matrimony.repository.SubscriptionRepository;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.SubscriptionService;
import com.matrimony.validator.SliderValidation;
import com.matrimony.validator.SubscriptionValidation;
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
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    SubscriptionRepository subscriptionRepository;

    //    below method will by default create the timestamp
    @PrePersist
    public void prePersist(Subscription subscription) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        subscription.setCreatedAt(now);
        subscription.setUpdatedAt(now);
    }

    private final Logger log = LoggerFactory.getLogger(getClass());


    @Override
    public ResponseEntity<ApiResponse<Subscription>> createSubscription(SubscriptionValidation subscriptionRequest) {
        try {
            Subscription subscription = new Subscription();
            subscription.setPlan_type(subscriptionRequest.getPlan_type());
            subscription.setFeature1(subscriptionRequest.getFeature1());
            subscription.setFeature2(subscriptionRequest.getFeature2());
            subscription.setFeature3(subscriptionRequest.getFeature3());
            subscription.setFeature4(subscriptionRequest.getFeature4());
            subscription.setFeature5(subscriptionRequest.getFeature5());
            subscription.setDuration(subscriptionRequest.getDuration());
            subscription.setCost(subscriptionRequest.getCost());
            subscription.setStatus(subscriptionRequest.getStatus());
            Subscription payload = this.subscriptionRepository.save(subscription);
            return ResponseEntity.ok(new ApiResponse<>("success", "Data saved successfully", payload, 200));
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception here and log it
            log.error("An error occurred while saving data", e);
            return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }
    @Override
    public ResponseEntity<ApiResponse<Object>> getSubscriptions(SearchPaginationRequest searchParams) {

        try {
            Long id = searchParams.getId();
            Integer perPageRecord = searchParams.getPer_page_record();

            // Set the default value of page to 1
            Integer page = (searchParams.getPage() != null) ? searchParams.getPage() : 1;

            Page<Subscription> subscriptionPage;

            if (id != null) {
                Optional<Subscription> subscriptionOptional = subscriptionRepository.findById(id);
                if (subscriptionOptional.isPresent()) {
                    Subscription slider = subscriptionOptional.get();
                    subscriptionPage = new PageImpl<>(Collections.singletonList(slider));
                } else {
                    subscriptionPage = Page.empty(); // No matching slider found
                }
            }
            else {
                subscriptionPage = subscriptionRepository.findAll(PageRequest.of(page - 1, perPageRecord,Sort.by(Sort.Order.desc("id"))));
            }

            List<Subscription> subscriptionEntities = subscriptionPage.getContent();


            Map<String, Object> map = Map.of(
                    "data", subscriptionEntities,
                    "totalElements", subscriptionPage.getTotalElements(),
                    "currentPage", page,
                    "perPageRecord", perPageRecord,
                    "totalPages", subscriptionPage.getTotalPages()
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
    public ResponseEntity<ApiResponse<Subscription>> updateSubscription(Long subscriptionId, SubscriptionValidation subscriptionRequest) {
        try {
            Optional<Subscription> existingSubscription = this.subscriptionRepository.findById(subscriptionId);

            if (existingSubscription.isPresent()) {
                // Update the existing slider with the new data
                Subscription updatedSlider = existingSubscription.get();
                updatedSlider.setPlan_type(subscriptionRequest.getPlan_type());
                updatedSlider.setFeature1(subscriptionRequest.getFeature1());
                updatedSlider.setFeature2(subscriptionRequest.getFeature2());
                updatedSlider.setFeature3(subscriptionRequest.getFeature3());
                updatedSlider.setFeature4(subscriptionRequest.getFeature4());
                updatedSlider.setFeature5(subscriptionRequest.getFeature5());
                updatedSlider.setDuration(subscriptionRequest.getDuration());
                updatedSlider.setCost(subscriptionRequest.getCost());
                updatedSlider.setStatus(subscriptionRequest.getStatus());
                Subscription payload = this.subscriptionRepository.save(updatedSlider);

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
    public ResponseEntity<ApiResponse<Subscription>>  getSubscription(Long subscriptionId) {
        try {
            Optional<Subscription> subscriptionEntityOptional = this.subscriptionRepository.findById(subscriptionId);

            if (subscriptionEntityOptional.isPresent()) {
                Subscription subscriptionEntity = subscriptionEntityOptional.get();
                return ResponseEntity.ok(new ApiResponse<>("success", "Data retrieved successfully", subscriptionEntity, 200));
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
    public ResponseEntity<ApiResponse<?>> deleteSubscription(Long subscriptionId) {
        try {
            Optional<Subscription> subscription = this.subscriptionRepository.findById(subscriptionId);
            if (subscription.isPresent()) {
                //  CategoryEntity categoryEntity= new CategoryEntity();
                //  categoryEntity.setCid(categoryID);
                //  this.categoryRepository.delete(categoryEntity);
                // below one is easy single line code


                this.subscriptionRepository.deleteById(subscriptionId);
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
