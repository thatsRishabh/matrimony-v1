package com.matrimony.service;

import com.matrimony.entity.AppSettings;
import com.matrimony.entity.Slider;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.validator.AppSettingValidation;
import com.matrimony.validator.SliderValidation;
import org.springframework.http.ResponseEntity;

public interface AppSettingService {
    public ResponseEntity<ApiResponse<AppSettings>> addOrUpdateAppSetting(AppSettingValidation sliderRequest);
    public ResponseEntity<ApiResponse<Object>> getAppSetting(SearchPaginationRequest searchParams);
}
