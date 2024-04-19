package com.matrimony.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.matrimony.service.serviceImpl.FAQServiceImpl;
import com.matrimony.service.serviceImpl.SliderServiceImpl;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "faq_entity")
@EntityListeners(FAQServiceImpl.class)
public class FAQ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String question;
    private String answer;
    private Integer order_number;
    private Integer status;


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
