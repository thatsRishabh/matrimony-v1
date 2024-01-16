package com.matrimony.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.matrimony.service.AppSettingService;
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
@Table(name = "app_setting_entity")
@EntityListeners(AppSettingService.class)
public class AppSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String app_name;
    private String logo_path;
    private String logo_thumb_path;
    private String fav_icon;
    private String call_us;
    private String mail_us;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String address;
    private String fb_url;
    private String twitter_url;
    private String insta_url;
    private String linkedIn_url;
    private String pinterest_url;
    private String copyright_text;
    private String meta_title;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String meta_keywords;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String meta_description;
    private String org_number;
    private String customer_care_number;
    private String allowed_app_version;
    private String invite_url;
    private String play_store_url;
    private String app_store_url;
    private String support_email;
    private String support_contact_number;
    private String certificate_image;

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
