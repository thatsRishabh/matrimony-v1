package com.matrimony.request;

import com.matrimony.entity.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String languageName;
    private String minAnnualIncome;
    private String maxAnnualIncome;
    private int gender;
    private int minAge;
    private int maxAge;
    private String minHeight;
    private String maxHeight;
    private String minWeight;
    private String maxWeight;
    private List<Long> citySelectedList;
    private List<Long> languageSelectedList;
}
