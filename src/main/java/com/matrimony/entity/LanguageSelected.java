package com.matrimony.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.matrimony.service.serviceImpl.LanguageSelectedServiceImpl;
import com.matrimony.service.serviceImpl.ProfileServiceImpl;
import com.matrimony.service.serviceImpl.SliderServiceImpl;
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
@Table(name = "language_selected_entity")
@EntityListeners(LanguageSelectedServiceImpl.class)
//@EntityListeners(ProfileServiceImpl.class)
public class LanguageSelected {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
//
//    @ManyToOne(fetch = FetchType.EAGER)
////    @JsonIgnore
//    private User user;

//
    @ManyToOne(fetch = FetchType.EAGER)
//    @JsonIgnore
    private Profile profile;

//    @ManyToOne(fetch = FetchType.EAGER)
////    @JoinColumn(name = "profile_id")
//    private Profile profile;

    @ManyToOne(fetch = FetchType.EAGER)
//    @JsonIgnore
    private Language language;

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

}
