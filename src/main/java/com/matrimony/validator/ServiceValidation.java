package com.matrimony.validator;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ServiceValidation {

    @NotBlank(message = "Please enter proper title")
    @Size(max = 35, message = "title should not be greater than 35")
    private String title;
    private String sub_title;
    private String description;
    private Integer order_number;
    private Integer status;
    private String image;
}
