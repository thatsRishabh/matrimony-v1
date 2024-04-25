package com.matrimony.controller;

import com.matrimony.entity.Language;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.LanguageService;
import com.matrimony.validator.LanguageValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/language")
public class LanguageController {
    @Autowired
    private LanguageService languageService;

    //add menu
    @PostMapping()
    public ResponseEntity<ApiResponse<Language>> addLanguage(@Valid @RequestBody LanguageValidation languageRequest){
        return this.languageService.createLanguage(languageRequest);
    }

    //get all category
    @PostMapping("/")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Object>> getLanguages(@RequestBody SearchPaginationRequest searchParams){
        return this.languageService.getLanguages(searchParams);
    }

    @PutMapping("/{languageId}")
    public ResponseEntity<ApiResponse<Language>>  updateLanguage(@PathVariable("languageId") Long languageId , @Valid @RequestBody LanguageValidation languageRequest){
        return this.languageService.updateLanguage(languageId, languageRequest);
    }

    // get single category
    @GetMapping("/{languageId}")
    public ResponseEntity<ApiResponse<Language>>  getLanguage(@PathVariable("languageId") Long languageId){
        return this.languageService.getLanguage(languageId);
    }

    @DeleteMapping("/{languageId}")
    public ResponseEntity<ApiResponse<?>> deleteLanguage(@PathVariable("languageId") Long languageId){
        return this.languageService.deleteLanguage(languageId);
    }
}
