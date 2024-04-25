package com.matrimony.service;

import com.matrimony.entity.City;
import com.matrimony.entity.Language;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.validator.CityValidation;
import com.matrimony.validator.LanguageValidation;
import org.springframework.http.ResponseEntity;

public interface CityService {
    public ResponseEntity<ApiResponse<City>> createCity(CityValidation cityRequest);

    public ResponseEntity<ApiResponse<Object>> getCities(SearchPaginationRequest searchParams);

    public ResponseEntity<ApiResponse<City>> updateCity(Long cityId, CityValidation cityRequest);

    public ResponseEntity<ApiResponse<City>>  getCity(Long cityId);

    public ResponseEntity<ApiResponse<?>> deleteCity(Long cityId);
}
