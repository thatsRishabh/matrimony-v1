package com.matrimony.service.serviceImpl;

import com.matrimony.entity.ServiceEntity;
import com.matrimony.entity.Slider;
import com.matrimony.repository.ServiceRepository;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.ServiceService;
import com.matrimony.validator.ServiceValidation;
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
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    ServiceRepository serviceRepository;

    //    below method will by default create the timestamp
    @PrePersist
    public void prePersist(ServiceEntity serviceEntity) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        serviceEntity.setCreatedAt(now);
        serviceEntity.setUpdatedAt(now);
    }

    private final Logger log = LoggerFactory.getLogger(getClass());


    private String description;
    private Integer status;
    private String image;

    @Override
    public ResponseEntity<ApiResponse<ServiceEntity>> createService(ServiceValidation serviceRequest) {
        try {
            ServiceEntity service = new ServiceEntity();
            service.setTitle(serviceRequest.getTitle());
            service.setSub_title(serviceRequest.getSub_title());
            service.setOrder_number(serviceRequest.getOrder_number());
            service.setStatus(serviceRequest.getStatus());
            service.setDescription(serviceRequest.getDescription());
            service.setImage(serviceRequest.getImage());
            ServiceEntity payload = this.serviceRepository.save(service);
            return ResponseEntity.ok(new ApiResponse<>("success", "Data saved successfully", payload, 200));
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while saving data", e);
            return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }
    @Override
    public ResponseEntity<ApiResponse<Object>> getServices(SearchPaginationRequest searchParams) {

        try {
            Long id = searchParams.getId();
            String title = searchParams.getTitle();
            Integer perPageRecord = searchParams.getPer_page_record();

            // Set the default value of page to 1
            Integer page = (searchParams.getPage() != null) ? searchParams.getPage() : 1;

            Page<ServiceEntity> servicePage;

            if (id != null) {
                Optional<ServiceEntity> serviceOptional = serviceRepository.findById(id);
                if (serviceOptional.isPresent()) {
                    ServiceEntity service = serviceOptional.get();
                    servicePage = new PageImpl<>(Collections.singletonList(service));
                } else {
                    servicePage = Page.empty(); // No matching service found
                }
            }
            else if (title != null) {
                servicePage = serviceRepository.findByTitleContaining(title, PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else {
                servicePage = serviceRepository.findAll(PageRequest.of(page - 1, perPageRecord,Sort.by(Sort.Order.desc("id"))));
            }

            List<ServiceEntity> serviceEntities = servicePage.getContent();


            Map<String, Object> map = Map.of(
                    "data", serviceEntities,
                    "totalElements", servicePage.getTotalElements(),
                    "currentPage", page,
                    "perPageRecord", perPageRecord,
                    "totalPages", servicePage.getTotalPages()
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
    public ResponseEntity<ApiResponse<ServiceEntity>> updateService(Long serviceId, ServiceValidation serviceRequest) {
        try {
            Optional<ServiceEntity> existingSlider = this.serviceRepository.findById(serviceId);

            if (existingSlider.isPresent()) {
                // Update the existing slider with the new data
                ServiceEntity updatedSlider = existingSlider.get();
                updatedSlider.setTitle(serviceRequest.getTitle());
                updatedSlider.setSub_title(serviceRequest.getSub_title());
                updatedSlider.setOrder_number(serviceRequest.getOrder_number());
                updatedSlider.setStatus(serviceRequest.getStatus());
                updatedSlider.setDescription(serviceRequest.getDescription());
                updatedSlider.setImage(serviceRequest.getImage());
                ServiceEntity payload = this.serviceRepository.save(updatedSlider);

                return ResponseEntity.ok(new ApiResponse<>("success", "Data updated successfully", payload, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "serviceId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while updating data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<ServiceEntity>>  getService(Long serviceId) {
        try {
            Optional<ServiceEntity> serviceEntityOptional = this.serviceRepository.findById(serviceId);

            if (serviceEntityOptional.isPresent()) {
                ServiceEntity serviceEntity = serviceEntityOptional.get();
                return ResponseEntity.ok(new ApiResponse<>("success", "Data retrieved successfully", serviceEntity, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "serviceId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while retrieving data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> deleteService(Long serviceId) {
        try {
            Optional<ServiceEntity> service = this.serviceRepository.findById(serviceId);
            if (service.isPresent()) {
                this.serviceRepository.deleteById(serviceId);
                return ResponseEntity.ok(new ApiResponse<>("success", "Data deleted successfully", null, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "serviceId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while deleting data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }
}
