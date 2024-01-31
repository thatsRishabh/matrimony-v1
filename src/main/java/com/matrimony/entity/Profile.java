package com.matrimony.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.matrimony.service.serviceImpl.MenuServiceImpl;
import com.matrimony.service.serviceImpl.ProfileServiceImpl;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "profile_entity")
@EntityListeners(ProfileServiceImpl.class)
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
//    @Column(columnDefinition = "INT COMMENT '1:self, 2: Parents, 3:Siblings, 4: Agent, 5:Other'")
    private String createdBy;

    private String maritalStatus;

//    @Column(columnDefinition = "INT COMMENT '1:Yes, 2: No'")
    private String haveChildren;
    private String numberOfChildren;
    private String weight;
    private String height;
    private String bodyType;
    private String complexion;
    private String religion;
    private String specialCases;
    private String motherTongue;
    private String caste;
    private String subCaste;
    private String manglik;
    private String familyValues;
    private String nickName;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String education;

    private String profession;
    private String diet;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String aboutYourself;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ", timezone = "UTC")
    private Date dateOfMarriage;

    private String bloodGroup;
    private String annualIncome;
    private String companyName;
    private String numberOfBrother;
    private String numberOfSister;
    private String motherName;
    private String fatherName;
    private String contactPersonName;
    private String contactPersonPhoneNumber;
    private String contactPersonRelationShip;
    private String convenientCallTime;
    private String placeOfBirth;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ", timezone = "UTC")
//    private Timestamp timeOFBirth;
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
    private String photo1;
    private String photo2;
    private String photo3;
    private String facebookUrl;
    private String linkedinUrl;
    private String whatsappUrl;


    //    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ", timezone = "UTC")
    private Timestamp createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ", timezone = "UTC")
    private Timestamp updatedAt;


    @PrePersist
    public void prePersist() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        createdAt = now;
        updatedAt = now;
    }

    @ManyToOne(fetch = FetchType.EAGER)
//    @JsonIgnore
    private User user;

//    @ManyToOne(fetch = FetchType.EAGER)
//    private CategoryEntity categoryEntity;

//    @ManyToOne(fetch = FetchType.EAGER)
//    private CategoryEntity categoryEntity;

    //    below two blocks allow to save parent id but doesn't show json when data is retived from data base,
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Ignore during deserialization (when receiving JSON data)
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

//    public void setParent(Menu parent_id) {
//        this.parent = parent_id;
//    }

}
