package com.matrimony.controller;

import com.matrimony.entity.Menu;
import com.matrimony.entity.Slider;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.MenuService;
import com.matrimony.service.SliderService;
import com.matrimony.validator.SliderValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/slider")
public class SliderController {

    @Autowired
    private SliderService sliderService;

    //add menu
    @PostMapping()
    public ResponseEntity<ApiResponse<Slider>> addSlider(@Valid @RequestBody SliderValidation sliderRequest){
        return this.sliderService.createSlider(sliderRequest);
    }

    //get all category
    @PostMapping("/")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Object>> getSliders(@RequestBody SearchPaginationRequest searchParams){
        return this.sliderService.getSliders(searchParams);
    }

    @PutMapping("/{sliderId}")
    public ResponseEntity<ApiResponse<Slider>>  updateSlider(@PathVariable("sliderId") Long sliderId ,@Valid @RequestBody SliderValidation sliderRequest){
        return this.sliderService.updateSlider(sliderId, sliderRequest);
    }

    // get single category
    @GetMapping("/{sliderId}")
    public ResponseEntity<ApiResponse<Slider>>  getSlider(@PathVariable("sliderId") Long sliderId){
        return this.sliderService.getSlider(sliderId);
    }

    @DeleteMapping("/{sliderId}")
    public ResponseEntity<ApiResponse<?>> deleteSlider(@PathVariable("sliderId") Long sliderId){
        return this.sliderService.deleteSlider(sliderId);
    }

}
