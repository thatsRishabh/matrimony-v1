package com.matrimony.controller;

import com.matrimony.entity.ContactUs;
import com.matrimony.entity.Slider;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.ContactUsService;
import com.matrimony.service.SliderService;
import com.matrimony.validator.ContactUsValidation;
import com.matrimony.validator.SliderValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/contact-us")
public class ContactUsController {
    @Autowired
    private ContactUsService contactUsService;

    //add menu
    @PostMapping()
    public ResponseEntity<ApiResponse<ContactUs>> addContactUs(@Valid @RequestBody ContactUsValidation contactUsRequest){
        return this.contactUsService.createContactUs(contactUsRequest);
    }

    //get all category
    @PostMapping("/")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Object>> getContactUss(@RequestBody SearchPaginationRequest searchParams){
        return this.contactUsService.getContactUss(searchParams);
    }

    @PutMapping("/{contactUsId}")
    public ResponseEntity<ApiResponse<ContactUs>>  updateContactUs(@PathVariable("contactUsId") Long sliderId , @Valid @RequestBody ContactUsValidation contactUsRequest){
        return this.contactUsService.updateContactUs(sliderId, contactUsRequest);
    }

    // get single category
    @GetMapping("/{contactUsId}")
    public ResponseEntity<ApiResponse<ContactUs>>getContactUs(@PathVariable("contactUsId") Long contactUsId){
        return this.contactUsService.getContactUs(contactUsId);
    }

    @DeleteMapping("/{contactUsId}")
    public ResponseEntity<ApiResponse<?>> deleteContactUs(@PathVariable("contactUsId") Long contactUsId){
        return this.contactUsService.deleteContactUs(contactUsId);
    }

}
