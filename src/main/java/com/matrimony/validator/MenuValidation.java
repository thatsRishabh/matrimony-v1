package com.matrimony.validator;

import com.matrimony.entity.Role;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.Date;

@Setter
@Getter
@ToString
public class MenuValidation {


    @NotBlank(message = "Please enter proper first name")
    @Size(max = 35, message = "first Name should not be greater than 35")
    private String name;

    private String slug;

    private String icon_path;

    @NotNull(message = "Please enter a valid order number")
    @Digits(integer = 100, fraction=2)
    private int order_number;

    @NotNull(message = "Please enter a valid position type")
    @Digits(integer = 1, fraction=2, message ="position type should be 1 for header, 2 for footer")
    private int position_type;

    @NotNull(message = "Please enter a valid status type")
    @Digits(integer = 1, fraction=2, message ="status type should be 1 for active, 2 for inactive")
    private int status;

}
