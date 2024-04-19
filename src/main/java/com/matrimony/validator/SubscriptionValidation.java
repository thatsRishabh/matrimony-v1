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
public class SubscriptionValidation {

    @NotBlank(message = "Please enter proper plan type")
    @Size(max = 35, message = "plan type should not be greater than 35")
    private String plan_type;
    private String feature1;
    private String feature2;
    private String feature3;
    private String feature4;
    private String feature5;

    private Integer duration;

    private Integer cost;

    @NotNull(message = "Please enter a valid status type")
    @Digits(integer = 1, fraction=2, message ="status type should be 1 for active, 2 for inactive")
    private Integer status;
}
