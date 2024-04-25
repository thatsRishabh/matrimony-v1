package com.matrimony.validator;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CityValidation {
    @NotBlank(message = "Please enter proper city name")
    @Size(max = 35, message = "city Name should not be greater than 35")
    private String name;
}
