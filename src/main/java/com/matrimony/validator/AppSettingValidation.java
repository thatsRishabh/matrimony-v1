package com.matrimony.validator;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AppSettingValidation {

    @NotBlank(message = "Please enter proper first name")
    @Size(max = 35, message = "first Name should not be greater than 35")
    private String app_name;
    private String logo_path;
    private String logo_thumb_path;
    private String fav_icon;
    private String call_us;
    private String mail_us;
    private String description;
    private String address;
    private String fb_url;
    private String twitter_url;
    private String insta_url;
    private String linkedIn_url;
    private String pinterest_url;
    private String copyright_text;
    private String meta_title;
    private String meta_keywords;
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
}
