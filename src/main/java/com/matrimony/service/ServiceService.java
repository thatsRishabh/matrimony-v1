package com.matrimony.service;

import com.matrimony.entity.ServiceEntity;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.validator.ServiceValidation;
import org.springframework.http.ResponseEntity;

public interface ServiceService {
    public ResponseEntity<ApiResponse<ServiceEntity>> createService(ServiceValidation serviceRequest);

    public ResponseEntity<ApiResponse<Object>> getServices(SearchPaginationRequest searchParams);

    public ResponseEntity<ApiResponse<ServiceEntity>> updateService(Long serviceId, ServiceValidation serviceRequest);

    public ResponseEntity<ApiResponse<ServiceEntity>>  getService(Long serviceId);

    public ResponseEntity<ApiResponse<?>> deleteService(Long serviceId);
}
