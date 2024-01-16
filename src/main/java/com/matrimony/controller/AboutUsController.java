package com.matrimony.controller;

import com.matrimony.entity.AboutUs;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.AboutUsService;
import com.matrimony.validator.AboutUsValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/about-us")
public class AboutUsController {

    @Autowired
    private AboutUsService aboutUsService;

    //add menu
    @PostMapping()
    public ResponseEntity<ApiResponse<AboutUs>> addAboutUs(@Valid @RequestBody AboutUsValidation aboutUsRequest){
        return this.aboutUsService.createAboutUs(aboutUsRequest);
    }

    //get all category
    @PostMapping("/")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Object>> getAboutUss(@RequestBody SearchPaginationRequest searchParams){
        return this.aboutUsService.getAboutUss(searchParams);
    }

    @PutMapping("/{aboutUsId}")
    public ResponseEntity<ApiResponse<AboutUs>>  updateAboutUs(@PathVariable("aboutUsId") Long aboutUsId , @Valid @RequestBody AboutUsValidation aboutUsRequest){
        return this.aboutUsService.updateAboutUs(aboutUsId, aboutUsRequest);
    }

    // get single category
    @GetMapping("/{aboutUsId}")
    public ResponseEntity<ApiResponse<AboutUs>>  getAboutUs(@PathVariable("aboutUsId") Long aboutUsId){
        return this.aboutUsService.getAboutUs(aboutUsId);
    }

    @DeleteMapping("/{aboutUsId}")
    public ResponseEntity<ApiResponse<?>> deleteAboutUs(@PathVariable("aboutUsId") Long aboutUsId){
        return this.aboutUsService.deleteAboutUs(aboutUsId);
    }

}
