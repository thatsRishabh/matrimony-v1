package com.matrimony.service.serviceImpl;

import com.matrimony.entity.Slider;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.EmailService;
import com.matrimony.validator.EmailValidation;
import com.matrimony.validator.SliderValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public ResponseEntity<ApiResponse<EmailValidation>> createMail(EmailValidation emailRequest) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailRequest.getTo());
            message.setSubject(emailRequest.getSubject());
            message.setText(emailRequest.getBody());

            javaMailSender.send(message);
            return ResponseEntity.ok(new ApiResponse<>("success", "Data saved successfully", null, 200));
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception here and log it
            log.error("An error occurred while saving data", e);
            return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }



//    public void sendEmail(String to, String subject, String body) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(body);
//
//        javaMailSender.send(message);
//    }
}
