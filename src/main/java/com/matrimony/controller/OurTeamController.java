package com.matrimony.controller;

import com.matrimony.entity.AboutUs;
import com.matrimony.entity.OurTeam;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.AboutUsService;
import com.matrimony.service.OurTeamService;
import com.matrimony.validator.AboutUsValidation;
import com.matrimony.validator.OurTeamValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/our-team")
public class OurTeamController {
    @Autowired
    private OurTeamService ourTeamService;

    //add menu
    @PostMapping()
    public ResponseEntity<ApiResponse<OurTeam>> addOurTeam(@Valid @RequestBody OurTeamValidation ourTeamRequest){
        return this.ourTeamService.createOurTeam(ourTeamRequest);
    }

    //get all category
    @PostMapping("/")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Object>> getOurTeams(@RequestBody SearchPaginationRequest searchParams){
        return this.ourTeamService.getOurTeams(searchParams);
    }

    @PutMapping("/{ourTeamId}")
    public ResponseEntity<ApiResponse<OurTeam>>  updateAboutUs(@PathVariable("ourTeamId") Long ourTeamId , @Valid @RequestBody OurTeamValidation ourTeamRequest){
        return this.ourTeamService.updateOurTeam(ourTeamId, ourTeamRequest);
    }

    // get single category
    @GetMapping("/{ourTeamId}")
    public ResponseEntity<ApiResponse<OurTeam>>  getOurTeam(@PathVariable("ourTeamId") Long ourTeamId){
        return this.ourTeamService.getOurTeam(ourTeamId);
    }

    @DeleteMapping("/{ourTeamId}")
    public ResponseEntity<ApiResponse<?>> deleteOurTeam(@PathVariable("ourTeamId") Long ourTeamId){
        return this.ourTeamService.deleteOurTeam(ourTeamId);
    }
}
