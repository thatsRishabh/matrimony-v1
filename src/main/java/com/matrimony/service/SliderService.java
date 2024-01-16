package com.matrimony.service;

import com.matrimony.entity.Menu;
import com.matrimony.entity.Slider;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.validator.SliderValidation;
import org.springframework.http.ResponseEntity;

public interface SliderService {
    public ResponseEntity<ApiResponse<Slider>> createSlider(SliderValidation sliderRequest);

    public ResponseEntity<ApiResponse<Object>> getSliders(SearchPaginationRequest searchParams);

    public ResponseEntity<ApiResponse<Slider>> updateSlider(Long sliderId, SliderValidation sliderRequest);

    public ResponseEntity<ApiResponse<Slider>>  getSlider(Long sliderId);

    public ResponseEntity<ApiResponse<?>> deleteSlider(Long sliderId);

}
