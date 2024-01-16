package com.matrimony.response;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Data
public class UserResponse {
    private long userId;

    private String firstName;

    private String lastName;

    private String emailAddress;

    private String userType;

    private LocalDate created_At;

    private String Address;

    private String phone;

    private String status;

    private String role;
}
