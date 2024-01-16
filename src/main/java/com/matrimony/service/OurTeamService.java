package com.matrimony.service;

import com.matrimony.entity.AboutUs;
import com.matrimony.entity.OurTeam;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.validator.AboutUsValidation;
import com.matrimony.validator.OurTeamValidation;
import org.springframework.http.ResponseEntity;

public interface OurTeamService {
    public ResponseEntity<ApiResponse<OurTeam>> createOurTeam(OurTeamValidation ourTeamRequest);

    public ResponseEntity<ApiResponse<Object>> getOurTeams(SearchPaginationRequest searchParams);

    public ResponseEntity<ApiResponse<OurTeam>> updateOurTeam(Long ourTeamId, OurTeamValidation ourTeamRequest);

    public ResponseEntity<ApiResponse<OurTeam>>  getOurTeam(Long ourTeamId);

    public ResponseEntity<ApiResponse<?>> deleteOurTeam(Long ourTeamId);
}
