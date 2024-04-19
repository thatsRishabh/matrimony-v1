package com.matrimony.validator;

import com.matrimony.entity.Role;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class UserValidation {


    @NotBlank(message = "Please enter proper first name")
    @Size(max = 35, message = "first Name should not be greater than 35")
    private String fullName;

    @NotBlank(message = "Please enter proper email address")
    @Email(message = "Please enter a valid email Id", regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-zA-Z.]{2,5}")
    private String emailAddress;

    @NotBlank(message = "Please enter a valid password")
    @Size(min = 5, message = "Password should be at least 5 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).*$",
            message = "Password should have at least one number, one letter, and one special character")
    private String password;

    @NotBlank(message = "Please enter a valid password")
    @Size(min = 5, message = "Password should be at least 5 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).*$",
            message = "Password should have at least one number, one letter, and one special character")
    private String confirmPassword;

    @NotBlank(message = "Please enter a user Type")
    @Size(min = 5, message = "User Type should be at least 5 characters")
    private String userType;

    private String address;

    @NotBlank(message = "Please enter a valid phone number")
    @Size(min = 10, max = 10, message = "Phone number should be exactly 10 characters")
    private String phone;

    @NotNull(message = "Please enter a valid gender")
    @Digits(integer = 1, fraction=2, message ="Gender should be 1 for male, 2 for female")
    private int gender;

    @NotNull(message = "Please enter a valid gender Intrest")
    @Digits(integer = 1, fraction=2, message ="Gender should be 1 for male, 2 for female, 3 for both")
    private Integer genderIntrest;

    @NotNull(message = "Please enter a valid subscription type")
    @Digits(integer = 1, fraction=2, message ="Subscription type should be 1 for free, 2 for premium")
    private int subscriptionType;

    @NotNull(message = "Please enter a valid date of birth")
    private Date dateOfBirth;

    @NotBlank(message = "Please upload a image")
    private String imagePath;

    @NotNull(message = "Please enter a valid Role")
//    @Digits(integer = 1, fraction=2)
    private Role role;
//    @NotBlank(message = "Please enter proper employee name")
//    private int role;
}
