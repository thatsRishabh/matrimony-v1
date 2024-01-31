package com.matrimony.controller;

import com.matrimony.entity.Slider;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.ContactUsService;
import com.matrimony.service.EmailService;
import com.matrimony.validator.EmailValidation;
import com.matrimony.validator.SliderValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    //add menu
    @PostMapping()
    public ResponseEntity<ApiResponse<EmailValidation>> sendMail(@Valid @RequestBody EmailValidation emailRequest){
        return this.emailService.createMail(emailRequest);
    }
}
