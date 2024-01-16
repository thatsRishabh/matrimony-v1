package com.matrimony.service;

import com.matrimony.entity.Blog;
import com.matrimony.entity.Slider;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface BlogService {
    public ResponseEntity<ApiResponse<Blog>> createBlog(Blog blogRequest);

    public ResponseEntity<ApiResponse<Object>> getBlogs(SearchPaginationRequest searchParams);

    public ResponseEntity<ApiResponse<Blog>> updateBlog(Long blogId, Blog blogRequest);

    public ResponseEntity<ApiResponse<Blog>>  getBlog(Long blogId);

    public ResponseEntity<ApiResponse<?>> deleteBlog(Long blogId);
}
