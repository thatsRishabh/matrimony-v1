package com.matrimony.validator;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ContactUsValidation {

    @NotBlank(message = "Please enter proper first name")
    @Size(max = 35, message = "first Name should not be greater than 35")
    private String name;

    private String email;

    private String address;

    @NotNull(message = "Please enter a valid phone number")
    @Digits(integer = 10, fraction = 0, message = "Phone number should be exactly 10 digits")
    private Integer mobile_number;

}
