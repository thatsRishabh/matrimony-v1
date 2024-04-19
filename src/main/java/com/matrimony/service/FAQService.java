package com.matrimony.service;

import com.matrimony.entity.FAQ;
import com.matrimony.entity.Slider;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.validator.FAQValidation;
import com.matrimony.validator.SliderValidation;
import org.springframework.http.ResponseEntity;

public interface FAQService {
    public ResponseEntity<ApiResponse<FAQ>> createFAQ(FAQValidation faqRequest);

    public ResponseEntity<ApiResponse<Object>> getFAQs(SearchPaginationRequest searchParams);

    public ResponseEntity<ApiResponse<FAQ>> updateFAQ(Long faqId, FAQValidation faqRequest);

    public ResponseEntity<ApiResponse<FAQ>>  getFAQ(Long faqId);

    public ResponseEntity<ApiResponse<?>> deleteFAQ(Long faqId);
}
