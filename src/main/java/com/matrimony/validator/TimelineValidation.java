package com.matrimony.validator;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Setter
@Getter
@ToString
public class TimelineValidation {

    @NotBlank(message = "Please enter proper title name")
    @Size(max = 35, message = "title Name should not be greater than 35")
    private String title;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "UTC")
    private Timestamp time;

    private String description;
    private String image;
    @NotNull(message = "Please enter a valid order number")
    @Digits(integer = 100, fraction=2)
    private Integer order_number;

}
