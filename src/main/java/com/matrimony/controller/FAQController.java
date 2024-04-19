package com.matrimony.controller;

import com.matrimony.entity.FAQ;
import com.matrimony.entity.Slider;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.FAQService;
import com.matrimony.service.SliderService;
import com.matrimony.validator.FAQValidation;
import com.matrimony.validator.SliderValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/faq")
public class FAQController {
    @Autowired
    private FAQService faqService;

    //add menu
    @PostMapping()
    public ResponseEntity<ApiResponse<FAQ>> addFAQ(@Valid @RequestBody FAQValidation faqRequest){
        return this.faqService.createFAQ(faqRequest);
    }

    //get all category
    @PostMapping("/")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Object>> getFAQs(@RequestBody SearchPaginationRequest searchParams){
        return this.faqService.getFAQs(searchParams);
    }

    @PutMapping("/{faqId}")
    public ResponseEntity<ApiResponse<FAQ>>  updateFAQ(@PathVariable("faqId") Long faqId , @Valid @RequestBody FAQValidation faqRequest){
        return this.faqService.updateFAQ(faqId, faqRequest);
    }

    // get single category
    @GetMapping("/{faqId}")
    public ResponseEntity<ApiResponse<FAQ>>  getFAQ(@PathVariable("faqId") Long faqId){
        return this.faqService.getFAQ(faqId);
    }

    @DeleteMapping("/{faqId}")
    public ResponseEntity<ApiResponse<?>> deleteFAQ(@PathVariable("faqId") Long faqId){
        return this.faqService.deleteFAQ(faqId);
    }
}
