package com.matrimony.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users_detail")
public class User {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int Id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_type")
    private String userType;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "password")
    private String password;

    @Column(name = "image_Path")
    private String imagePath;

    @Column(name = "phone")
    private String phone;

    @Column(name = "status")
    private int status;

    @Column(name = "address")
    private String address;

    private String resetOtp;

    @Column(name = "gender", columnDefinition = "INT COMMENT '1: male, 2: female'")
    private int gender;

    @Column(name = "subscription_type", columnDefinition = "INT COMMENT '1: free, 2: gold, 3: premium'")
    private int subscriptionType;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_birth")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ", timezone = "UTC")
    private Date dateOfBirth;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String fcmToken;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "role_id")
    private Role role;

    //    below two blocks allow to save parent id but doesn't show json when data is retived from data base,
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Ignore during deserialization (when receiving JSON data)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Column(name = "account_creation_date")
    private LocalDate CreationDate;

    @PrePersist
    protected void onCreate() {
        CreationDate = LocalDate.now();
    }

////    @OneToMany(mappedBy = "quizEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JsonIgnore
//    private Set<Profile> profiles= new HashSet<>();
}
