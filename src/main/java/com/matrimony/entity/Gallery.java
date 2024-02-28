package com.matrimony.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.matrimony.service.serviceImpl.GalleryServiceImpl;
import com.matrimony.service.serviceImpl.NotificationServiceImpl;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "gallery_entity")
@EntityListeners(GalleryServiceImpl.class)
public class Gallery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Integer order_number;
    private String image;

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
