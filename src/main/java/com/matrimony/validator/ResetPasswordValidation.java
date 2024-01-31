package com.matrimony.validator;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ResetPasswordValidation {
    @NotBlank(message = "Please enter proper first name")
    @Size(max = 60, message = "first Name should not be greater than 35")
    private String email;
}
