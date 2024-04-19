package com.matrimony.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchPaginationRequest {
    private Long id;
    private Long categoryId;
    private Long userId;
    private Long userIdForFriendRequestReceived;
    private Long status;
    private Long quizId;
    private String name;
    private String fullName;
    private String title;
    private String content;
    private String religion;
    private String city;
    private String caste;
    private String Question;
    private Boolean active;
    private Integer per_page_record;
    private Integer page;
    private int gender;
}
