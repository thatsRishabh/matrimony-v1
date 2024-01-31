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
    private Long quizId;
    private String name;
    private String firstName;
    private String title;
    private String content;
    private String religion;
    private String caste;
    private Boolean active;
    private Integer per_page_record;
    private Integer page;
    private int gender;
}
