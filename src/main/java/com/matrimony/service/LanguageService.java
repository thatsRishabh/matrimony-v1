package com.matrimony.service;

import com.matrimony.entity.Language;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.validator.LanguageValidation;
import org.springframework.http.ResponseEntity;

public interface LanguageService {

    public ResponseEntity<ApiResponse<Language>> createLanguage(LanguageValidation languageRequest);

    public ResponseEntity<ApiResponse<Object>> getLanguages(SearchPaginationRequest searchParams);

    public ResponseEntity<ApiResponse<Language>> updateLanguage(Long languageId, LanguageValidation languageRequest);

    public ResponseEntity<ApiResponse<Language>>  getLanguage(Long languageId);

    public ResponseEntity<ApiResponse<?>> deleteLanguage(Long languageId);
}
