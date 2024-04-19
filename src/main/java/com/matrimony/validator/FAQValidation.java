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
public class FAQValidation {


    @NotBlank(message = "Please enter question")
    @Size(max = 80, message = "question should not be greater than 80")
    private String question;
    private String answer;

    @NotNull(message = "Please enter a valid order number")
    @Digits(integer = 100, fraction=2)
    private Integer order_number;


    @NotNull(message = "Please enter a valid status type")
    @Digits(integer = 1, fraction=2, message ="status type should be 1 for active, 2 for inactive")
    private Integer status;
}
