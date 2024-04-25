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
public class LanguageValidation {
    @NotBlank(message = "Please enter proper language name")
    @Size(max = 35, message = "language Name should not be greater than 35")
    private String name;
}
