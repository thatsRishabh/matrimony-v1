package com.matrimony.controller;

import com.matrimony.entity.Slider;
import com.matrimony.entity.Testimony;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.SliderService;
import com.matrimony.service.TestimonyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/testimony")
public class TestimonyController {


    @Autowired
    private TestimonyService testimonyService;

    //add menu
    @PostMapping()
    public ResponseEntity<ApiResponse<Testimony>> addTestimony(@RequestBody Testimony testimonyRequest){
        return this.testimonyService.createTestimony(testimonyRequest);
    }

    //get all category
    @PostMapping("/")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Object>> getTestimony(@RequestBody SearchPaginationRequest searchParams){
        return this.testimonyService.getTestimonys(searchParams);
    }

    @PutMapping("/{testimonyId}")
    public ResponseEntity<ApiResponse<Testimony>>  updateTestimony(@PathVariable("testimonyId") Long testimonyId ,@RequestBody Testimony testimonyRequest){
        return this.testimonyService.updateTestimony(testimonyId, testimonyRequest);
    }

    // get single category
    @GetMapping("/{testimonyId}")
    public ResponseEntity<ApiResponse<Testimony>>  getTestimony(@PathVariable("testimonyId") Long testimonyId){
        return this.testimonyService.getTestimony(testimonyId);
    }

    @DeleteMapping("/{testimonyId}")
    public ResponseEntity<ApiResponse<?>> deleteTestimony(@PathVariable("testimonyId") Long testimonyId){
        return this.testimonyService.deleteTestimony(testimonyId);
    }

}
