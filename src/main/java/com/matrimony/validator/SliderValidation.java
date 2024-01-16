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
public class SliderValidation {

    @NotBlank(message = "Please enter proper first name")
    @Size(max = 35, message = "first Name should not be greater than 35")
    private String title;
    private String sub_title;
    private String url;
    private String description;
    private String image;
    @NotNull(message = "Please enter a valid order number")
    @Digits(integer = 100, fraction=2)
    private Integer order_number;

    @NotNull(message = "Please enter a valid position type")
    @Digits(integer = 1, fraction=2, message ="position type should be 1 for header, 2 for footer")
    private Integer position_type;

    @NotNull(message = "Please enter a valid status type")
    @Digits(integer = 1, fraction=2, message ="status type should be 1 for active, 2 for inactive")
    private Integer status;
}
