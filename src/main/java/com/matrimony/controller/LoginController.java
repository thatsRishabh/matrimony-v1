package com.matrimony.controller;

import com.matrimony.request.LoginRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.response.LoginResponse;
import com.matrimony.service.UserService;
import com.matrimony.service.serviceImpl.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Log4j2
@RestController
public class LoginController {


    @Autowired
    private UserService userService;
    @Autowired
    UserServiceImpl userServiceIml;

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


//    @GetMapping("/access-denied")
//    public ModelAndView accessDeniedPage(ModelAndView modelAndView) {
//        modelAndView.setViewName("/html/Role&Permissions/error-permission");
//        return modelAndView;
//
//    }

}
