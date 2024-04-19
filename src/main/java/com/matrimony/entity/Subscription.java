package com.matrimony.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.matrimony.service.serviceImpl.SliderServiceImpl;
import com.matrimony.service.serviceImpl.SubscriptionServiceImpl;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "subscription_entity")
@EntityListeners(SubscriptionServiceImpl.class)
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String plan_type;
    private String feature1;
    private String feature2;
    private String feature3;
    private String feature4;
    private String feature5;
    private Integer duration;
    private Integer cost;
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
