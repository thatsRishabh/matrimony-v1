package com.matrimony.service;

import com.matrimony.entity.AboutUs;
import com.matrimony.entity.Slider;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.validator.AboutUsValidation;
import com.matrimony.validator.SliderValidation;
import org.springframework.http.ResponseEntity;

public interface AboutUsService {
    public ResponseEntity<ApiResponse<AboutUs>> createAboutUs(AboutUsValidation aboutUsRequest);

    public ResponseEntity<ApiResponse<Object>> getAboutUss(SearchPaginationRequest searchParams);

    public ResponseEntity<ApiResponse<AboutUs>> updateAboutUs(Long aboutUsId, AboutUsValidation aboutUsRequest);

    public ResponseEntity<ApiResponse<AboutUs>>  getAboutUs(Long aboutUsId);

    public ResponseEntity<ApiResponse<?>> deleteAboutUs(Long aboutUsId);
}
