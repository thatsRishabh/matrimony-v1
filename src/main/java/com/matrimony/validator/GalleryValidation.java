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
public class GalleryValidation {

    private String image;
    @NotNull(message = "Please enter a valid order number")
    @Digits(integer = 100, fraction=2)
    private Integer order_number;
}
