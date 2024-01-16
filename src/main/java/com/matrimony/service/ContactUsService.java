package com.matrimony.service;

import com.matrimony.entity.ContactUs;
import com.matrimony.entity.Slider;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.validator.ContactUsValidation;
import com.matrimony.validator.SliderValidation;
import org.springframework.http.ResponseEntity;

public interface ContactUsService {
    public ResponseEntity<ApiResponse<ContactUs>> createContactUs(ContactUsValidation contactUsRequest);

    public ResponseEntity<ApiResponse<Object>> getContactUss(SearchPaginationRequest searchParams);

    public ResponseEntity<ApiResponse<ContactUs>> updateContactUs(Long contactUsId, ContactUsValidation contactUsRequest);

    public ResponseEntity<ApiResponse<ContactUs>>  getContactUs(Long contactUsId);

    public ResponseEntity<ApiResponse<?>> deleteContactUs(Long contactUsId);
}
