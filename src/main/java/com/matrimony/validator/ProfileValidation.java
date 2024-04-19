package com.matrimony.validator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.matrimony.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.Date;

@Setter
@Getter
@ToString
public class ProfileValidation {

//    @Column(columnDefinition = "INT COMMENT '1:self, 2: Parents, 3:Siblings, 4: Agent, 5:Other'")

//    @NotBlank(message = "Please enter a valid created By Status")
    @Digits(integer =2, fraction = 0, message = "'1:self, 2: Parents, 3:Siblings, 4: Agent, 5:Other'")
    private String createdBy;
//
    @NotBlank(message = "Please enter a valid marital Status")
    @Digits(integer = 2, fraction = 0, message = "'1:Married, 2: Unmarried'")
    private String maritalStatus;

//    @NotBlank(message = "Please enter a valid Children Status")
    @Digits(integer = 2, fraction = 0, message = "'1:Yes, 2: No'")
    private String haveChildren;

//    @NotBlank(message = "Please enter a valid Children Status")
    @Digits(integer = 2, fraction = 0, message = "Please enter a valid Children Status")
    private String numberOfChildren;

    @NotBlank(message = "Please enter a valid weight Status")
//    @Pattern(regexp = "\\d{2,5}", message = "weight number should be between 2-3 digits")
    @Digits(integer =3, fraction = 2, message = "Please enter the weight in Kg and Gram format")
    private String weight;

    @NotBlank(message = "Please enter a valid height Status")
//    @Pattern(regexp = "\\d{2,3}", message = "height number should be between 2-3 digits")
    @Digits(integer =2, fraction = 2, message = "Please enter the height in feet and inch format")
    private String height;
    private String bodyType;
    private String complexion;
    private String religion;
    private String specialCases;
    private String motherTongue;
    private String caste;
    private String nickName;
    private String subCaste;
    private String manglik;
    private String familyValues;
    private String education;

    private String profession;
    private String diet;
    private String aboutYourself;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ", timezone = "UTC")
    private Date dateOfMarriage;

    private String bloodGroup;

    @NotBlank(message = "Please enter a valid annual Income Status")
    @Pattern(regexp = "\\d{6,10}", message = "Annual income number should be between 6 to 10 digits")
    private String annualIncome;
    private String companyName;

//    @NotBlank(message = "Please enter a valid number Of Brother ")
    @Digits(integer = 2, fraction = 0, message = "Please enter a valid number Of Brother")
    private String numberOfBrother;

//    @NotBlank(message = "Please enter a valid number Of sister")
    @Digits(integer = 2, fraction = 0)
    private String numberOfSister;
    private String motherName;
    private String fatherName;
    private String contactPersonName;
    private String contactPersonRelationShip;

    @Digits(integer =10, fraction = 0, message = "'Please enter 10 digit mobile number'")
    private String contactPersonPhoneNumber;
    private String convenientCallTime;
    private String placeOfBirth;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "UTC")
    private Timestamp timeOFBirth;
    private String hobbies;
    private String interests;
    private String favoriteReads;
    private String preferredMovies;
    private String sports;
    private String favoriteCuisine;
    private String spokenLanguages;
    private String rasi;
    private String nakshatra;
    private String astroprofile;

    @NotBlank(message = "Please enter a image")
    private String photo1;

    @NotBlank(message = "Please enter a image")
    private String photo2;

    @NotBlank(message = "Please upload a image")
    private String photo3;
    private String facebookUrl;
    private String linkedinUrl;

//    @NotBlank(message = "Please enter a valid number Of whatsApp")
    @Digits(integer = 10, fraction = 0 , message = "Please enter a valid number Of whatsApp")
    private String whatsappUrl;
    private User user;
}
