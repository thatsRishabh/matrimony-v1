package com.matrimony.service;

import com.matrimony.entity.Menu;
import com.matrimony.entity.Profile;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.validator.ProfileValidation;
import org.springframework.http.ResponseEntity;

public interface ProfileService {
    public ResponseEntity<ApiResponse<Profile>> createProfile(ProfileValidation profileRequest);

    public ResponseEntity<ApiResponse<Object>> getProfiles(SearchPaginationRequest searchParams);

    public ResponseEntity<ApiResponse<Profile>> updateProfile(Long profileId, ProfileValidation profileRequest);

    public ResponseEntity<ApiResponse<Profile>>  getProfile(Long profileId);

    public ResponseEntity<ApiResponse<?>> deleteProfile(Long profileId);

}
