package com.matrimony.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.matrimony.service.serviceImpl.ContactUsServiceImpl;
import com.matrimony.service.serviceImpl.OurTeamServiceImpl;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "our_team_entity")
@EntityListeners(OurTeamServiceImpl.class)
public class OurTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String designation;
    private String about;
    private Integer order_number;
    private Integer status;
    private String image;
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
}
