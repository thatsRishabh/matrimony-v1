package com.matrimony.controller;

import com.matrimony.entity.AppSettings;
import com.matrimony.entity.Slider;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.AppSettingService;
import com.matrimony.service.SliderService;
import com.matrimony.validator.AppSettingValidation;
import com.matrimony.validator.SliderValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app-setting")
public class AppSettingController {
    @Autowired
    private AppSettingService appSettingService;

    //get all category
    @PostMapping()
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Object>> getAppSetting(@RequestBody SearchPaginationRequest searchParams){
        return this.appSettingService.getAppSetting(searchParams);
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<AppSettings>> addOrUpdateAppSetting(@Valid @RequestBody AppSettingValidation sliderRequest){
        return this.appSettingService.addOrUpdateAppSetting(sliderRequest);
    }

}
