package com.matrimony.service.serviceImpl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.matrimony.entity.Blog;
import com.matrimony.entity.Profile;
import com.matrimony.entity.User;
import com.matrimony.repository.BlogRepository;
import com.matrimony.repository.SliderRepository;
import com.matrimony.repository.UserRepository;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.response.UserProfileResponse;
import com.matrimony.service.BlogService;
import com.matrimony.service.TestimonyService;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    BlogRepository blogRepository;
    @Autowired
    UserRepository userRepository;

    //   below method will by default create the timestamp
    @PrePersist
    public void prePersist(Blog blog) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        blog.setCreatedAt(now);
        blog.setUpdatedAt(now);
    }

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public ResponseEntity<ApiResponse<Blog>> createBlog(Blog blogRequest) {
        try {
            Blog payload = this.blogRepository.save(blogRequest);
            return ResponseEntity.ok(new ApiResponse<>("success", "Data saved successfully", payload, 200));
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while saving data", e);
            return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }


    @Override
    public ResponseEntity<ApiResponse<Object>> getBlogs(SearchPaginationRequest searchParams) {

        try {
            Long id = searchParams.getId();
            String religion = searchParams.getReligion();
            Integer perPageRecord = searchParams.getPer_page_record();

            // Set the default value of page to 1
            Integer page = (searchParams.getPage() != null) ? searchParams.getPage() : 1;

            Page<Blog> categoryPage;

            if (id != null) {
                Optional<Blog> categoryOptional = blogRepository.findById(id);
                if (categoryOptional.isPresent()) {
                    Blog category = categoryOptional.get();
                    categoryPage = new PageImpl<>(Collections.singletonList(category));
                } else {
                    categoryPage = Page.empty(); // No matching category found
                }
            }
            else if (religion != null) {
                categoryPage = blogRepository.findByTitleContaining(religion, PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else {
                categoryPage = blogRepository.findAll(PageRequest.of(page - 1, perPageRecord,Sort.by(Sort.Order.desc("id"))));
            }
            List<Blog> parents = categoryPage.getContent();

            Map<String, Object> map = Map.of(
                    "data", parents,
//                    "data", responseList,
                    "totalElements", categoryPage.getTotalElements(),
                    "currentPage", page,
                    "perPageRecord", perPageRecord,
                    "totalPages", categoryPage.getTotalPages()
            );
            return ResponseEntity.ok( new ApiResponse<>("success", "Data retrieved successfully", map, 200));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("An error occurred while saving data", e);
//            return e.getMessage();
            return ResponseEntity.internalServerError().body( new ApiResponse<>("error", e.getMessage(), null, 500));
        }

    }

    @Override
    public ResponseEntity<ApiResponse<Blog>> updateBlog(Long blogId, Blog blogRequest) {
        try {
            Optional<Blog> existingBlog = this.blogRepository.findById(blogId);

            if (existingBlog.isPresent()) {
                // Update the existing category with the new data
                Blog updatedBlog = existingBlog.get();
                updatedBlog.setTitle(blogRequest.getTitle());
                updatedBlog.setSlug(blogRequest.getSlug());
                updatedBlog.setVideo_path(blogRequest.getVideo_path());
                updatedBlog.setDateOfPost(blogRequest.getDateOfPost());
                updatedBlog.setContent(blogRequest.getContent());
                updatedBlog.setGroom(blogRequest.getGroom());
                updatedBlog.setBride(blogRequest.getBride());
                updatedBlog.setImage_path(blogRequest.getImage_path());
                updatedBlog.setViews(blogRequest.getViews());
                updatedBlog.setDateOfMarriage(blogRequest.getDateOfMarriage());
                updatedBlog.setOrder_number(blogRequest.getOrder_number());
                updatedBlog.setStatus(blogRequest.getStatus());
                Blog payload = this.blogRepository.save(updatedBlog);
                return ResponseEntity.ok(new ApiResponse<>("success", "Data updated successfully", payload, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "BlogId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while updating data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Blog>>  getBlog(Long blogId) {
        try {
            Optional<Blog> profileEntityOptional = this.blogRepository.findById(blogId);

            if (profileEntityOptional.isPresent()) {
                Blog categoryEntity = profileEntityOptional.get();
                return ResponseEntity.ok(new ApiResponse<>("success", "Data retrieved successfully", categoryEntity, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "BlogId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while retrieving data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> deleteBlog(Long blogId) {
        try {
            Optional<Blog> category = this.blogRepository.findById(blogId);
            if (category.isPresent()) {
                //  CategoryEntity categoryEntity= new CategoryEntity();
                //  categoryEntity.setCid(categoryID);
                //  this.categoryRepository.delete(categoryEntity);

                // below one is easy single line code

                this.blogRepository.deleteById(blogId);
                return ResponseEntity.ok(new ApiResponse<>("success", "Data deleted successfully", null, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "BlogId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while deleting data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

}
