package com.matrimony.validator;

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
public class OurTeamValidation {

    @NotBlank(message = "Please enter proper first name")
    @Size(max = 35, message = "first Name should not be greater than 35")
    private String name;
    private String designation;
    private String about;
    private Integer order_number;
    private Integer status;
    private String image;
    private String facebookUrl;
    private String linkedinUrl;
    private String whatsappUrl;
}
