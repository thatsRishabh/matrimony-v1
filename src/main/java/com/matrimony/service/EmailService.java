package com.matrimony.service;

import com.matrimony.entity.Slider;
import com.matrimony.response.ApiResponse;
import com.matrimony.validator.EmailValidation;
import com.matrimony.validator.SliderValidation;
import org.springframework.http.ResponseEntity;

public interface EmailService {
    public ResponseEntity<ApiResponse<EmailValidation>> createMail(EmailValidation emailRequest);
}
