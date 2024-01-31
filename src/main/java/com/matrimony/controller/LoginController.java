package com.matrimony.controller;

import com.matrimony.entity.User;
import com.matrimony.repository.UserRepository;
import com.matrimony.request.LoginRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.response.LoginResponse;
import com.matrimony.service.UserService;
import com.matrimony.service.serviceImpl.UserServiceImpl;
import com.matrimony.validator.EmailValidation;
import com.matrimony.validator.ResetPasswordValidation;
import com.matrimony.validator.UpdatePasswordValidation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Log4j2
@RestController
public class LoginController {


    @Autowired
    private UserService userService;

    @Autowired
    UserServiceImpl userServiceIml;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    UserRepository userRepository;

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String JWT_COOKIE_NAME = "jwtToken";

    @PostMapping(value = "/login/jwt")
    public ResponseEntity<ApiResponse<LoginResponse>> userLogin(@Valid @RequestBody LoginRequest loginRequest,
                                                                HttpServletResponse response) {

        log.info(loginRequest.toString());
        ResponseEntity<ApiResponse<LoginResponse>> userloginResponse = userService.generateToken(loginRequest);

        LoginResponse loginResponse = userloginResponse.getBody().getPayload();
        if (loginResponse != null) {
            Cookie tokenCookie = new Cookie(JWT_COOKIE_NAME, loginResponse.getUserToken());
            tokenCookie.setMaxAge(24 * 60 * 60);
            tokenCookie.setPath("/");
            response.addCookie(tokenCookie);
            System.out.println(loginResponse.getLoginDate());

        }
        log.info("code is :" + userloginResponse.getStatusCode());
        return userloginResponse;

    }

    @PostMapping(value = "/reset-password")
    public ResponseEntity<ApiResponse<?>> passwordReset(@Valid @RequestBody ResetPasswordValidation emailRequest) {
        try {

            User user = userRepository.findByEmailAddress(emailRequest.getEmail());

            if (user == null) {
                return ResponseEntity.internalServerError().body(new ApiResponse<>("error", "User not found", null, 500));
            }

//            generating 4 digit OTP
            String otp = String.valueOf((int) ((Math.random() * 9000) + 1000));

//            saving OTP to database
            user.setResetOtp(otp);
            userRepository.save(user);

//            sending email
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailRequest.getEmail());
            message.setSubject("Password reset Request");
            message.setText("Your OTP is " + otp);
            javaMailSender.send(message);
            return ResponseEntity.ok(new ApiResponse<>("success", "OTP sent successfully", null, 200));
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception here and log it
            log.error("An error occurred while saving data", e);
            return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }


    }

    @PostMapping(value = "/update-password")
    public ResponseEntity<ApiResponse<?>> passwordUpdate(@Valid @RequestBody UpdatePasswordValidation updatePasswordRequest) {
        try {

            User user = userRepository.findByResetOtp(updatePasswordRequest.getResetOtp());


            if (user == null) {
                return ResponseEntity.internalServerError().body(new ApiResponse<>("error", "Invalid or expired token", null, 500));
            }

            if (!updatePasswordRequest.getPassword().equals(updatePasswordRequest.getPasswordConfirm())) {
                return ResponseEntity.internalServerError().body(new ApiResponse<>("error", "Both password do not match", null, 500));
            }

            String password = updatePasswordRequest.getPassword();
            BCryptPasswordEncoder encoder = this.bCryptPasswordEncoder();
            String encodedPassword = encoder.encode(password);
            user.setPassword(encodedPassword);
            user.setResetOtp(null); // Clear the reset token after successful reset
            userRepository.save(user);
            return ResponseEntity.ok(new ApiResponse<>("success", "Password updated successfully", null, 200));
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception here and log it
            log.error("An error occurred while saving data", e);
            return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }


    }

}
