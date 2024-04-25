package com.matrimony.controller;

import com.matrimony.entity.City;
import com.matrimony.entity.Language;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.CityService;
import com.matrimony.service.LanguageService;
import com.matrimony.validator.CityValidation;
import com.matrimony.validator.LanguageValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/city")
public class CityController {
    @Autowired
    private CityService cityService;

    //add menu
    @PostMapping()
    public ResponseEntity<ApiResponse<City>> addCity(@Valid @RequestBody CityValidation cityRequest){
        return this.cityService.createCity(cityRequest);
    }

    //get all category
    @PostMapping("/")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Object>> getCities(@RequestBody SearchPaginationRequest searchParams){
        return this.cityService.getCities(searchParams);
    }

    @PutMapping("/{cityId}")
    public ResponseEntity<ApiResponse<City>>  updateCity(@PathVariable("cityId") Long cityId , @Valid @RequestBody CityValidation cityRequest){
        return this.cityService.updateCity(cityId, cityRequest);
    }

    // get single category
    @GetMapping("/{cityId}")
    public ResponseEntity<ApiResponse<City>>  getCity(@PathVariable("cityId") Long cityId){
        return this.cityService.getCity(cityId);
    }

    @DeleteMapping("/{cityId}")
    public ResponseEntity<ApiResponse<?>> deleteCity(@PathVariable("cityId") Long cityId){
        return this.cityService.deleteCity(cityId);
    }
}
