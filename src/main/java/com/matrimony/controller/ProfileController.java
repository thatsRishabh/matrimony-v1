package com.matrimony.controller;

import com.matrimony.entity.Menu;
import com.matrimony.entity.Profile;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.MenuService;
import com.matrimony.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    //add menu
    @PostMapping()
    public ResponseEntity<ApiResponse<Profile>> addProfile(@RequestBody Profile profileRequest){
        return this.profileService.createProfile(profileRequest);
    }

    //get all category
    @PostMapping("/")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Object>> getProfile(@RequestBody SearchPaginationRequest searchParams){
        return this.profileService.getProfiles(searchParams);
    }

    @PutMapping("/{profileId}")
    public ResponseEntity<ApiResponse<Profile>> updateProfile(@PathVariable("profileId") Long profileId , @RequestBody Profile profileRequest){
        return this.profileService.updateProfile(profileId, profileRequest);
    }

    // get single category
    @GetMapping("/{profileId}")
    public ResponseEntity<ApiResponse<Profile>>  getProfile(@PathVariable("profileId") Long profileId){
        return this.profileService.getProfile(profileId);
    }

    @DeleteMapping("/{profileId}")
    public ResponseEntity<ApiResponse<?>> deleteMenu(@PathVariable("profileId") Long profileId){
        return this.profileService.deleteProfile(profileId);
    }
}
