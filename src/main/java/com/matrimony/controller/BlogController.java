package com.matrimony.controller;

import com.matrimony.entity.Blog;
import com.matrimony.entity.Testimony;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.BlogService;
import com.matrimony.service.TestimonyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    //add menu
    @PostMapping()
    public ResponseEntity<ApiResponse<Blog>> addBlog(@RequestBody Blog blogRequest){
        return this.blogService.createBlog(blogRequest);
    }

    //get all category
    @PostMapping("/")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Object>> getBlogs(@RequestBody SearchPaginationRequest searchParams){
        return this.blogService.getBlogs(searchParams);
    }

    @PutMapping("/{blogId}")
    public ResponseEntity<ApiResponse<Blog>> updateBlog(@PathVariable("blogId") Long blogId ,@RequestBody Blog blogRequest){
        return this.blogService.updateBlog(blogId, blogRequest);
    }

    // get single category
    @GetMapping("/{blogId}")
    public ResponseEntity<ApiResponse<Blog>>getBlog(@PathVariable("blogId") Long blogId){
        return this.blogService.getBlog(blogId);
    }

    @DeleteMapping("/{blogId}")
    public ResponseEntity<ApiResponse<?>> deleteBlog(@PathVariable("blogId") Long blogId){
        return this.blogService.deleteBlog(blogId);
    }
}
