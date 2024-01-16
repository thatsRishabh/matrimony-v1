package com.matrimony.service;

import com.matrimony.entity.Slider;
import com.matrimony.entity.Testimony;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface TestimonyService {
    public ResponseEntity<ApiResponse<Testimony>> createTestimony(Testimony testimonyRequest);

    public ResponseEntity<ApiResponse<Object>> getTestimonys(SearchPaginationRequest searchParams);

    public ResponseEntity<ApiResponse<Testimony>> updateTestimony(Long testimonyId, Testimony testimonyRequest);

    public ResponseEntity<ApiResponse<Testimony>>  getTestimony(Long testimonyId);

    public ResponseEntity<ApiResponse<?>> deleteTestimony(Long testimonyId);
}
