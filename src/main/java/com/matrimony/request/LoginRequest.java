package com.matrimony.request;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class LoginRequest {

    @NotBlank(message = "email cannot be empty")
    @Size(max = 60, message = "first email should not be greater than 60")
    private String email;

    @NotBlank(message = "Pass word cannot be empty")
    @Size(max = 60, message = "first password should not be greater than 60")
    private String password;

//    @NotBlank(message = "fcm Token cannot be empty")
    private String fcmToken;

    // Default constructor
    public LoginRequest() {
    }

    // Constructor with email and password
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
