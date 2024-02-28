package com.matrimony.controller;

import com.matrimony.entity.Timeline;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.TimelineService;
import com.matrimony.validator.TimelineValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/timeline")
public class TimelineController {
    @Autowired
    private TimelineService timelineService;

    //add menu
    @PostMapping()
    public ResponseEntity<ApiResponse<Timeline>> addTimeline(@Valid @RequestBody TimelineValidation timelineRequest){
        return this.timelineService.createTimeline(timelineRequest);
    }

    //get all category
    @PostMapping("/")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Object>> getTimelines(@RequestBody SearchPaginationRequest searchParams){
        return this.timelineService.getTimelines(searchParams);
    }

    @PutMapping("/{timelineId}")
    public ResponseEntity<ApiResponse<Timeline>>  updateTimeline(@PathVariable("timelineId") Long timelineId , @Valid @RequestBody TimelineValidation timelineRequest){
        return this.timelineService.updateTimeline(timelineId, timelineRequest);
    }

    // get single category
    @GetMapping("/{timelineId}")
    public ResponseEntity<ApiResponse<Timeline>>  getTimeline(@PathVariable("timelineId") Long timelineId){
        return this.timelineService.getTimeline(timelineId);
    }

    @DeleteMapping("/{timelineId}")
    public ResponseEntity<ApiResponse<?>> deleteTimeline(@PathVariable("timelineId") Long timelineId){
        return this.timelineService.deleteTimeline(timelineId);
    }
}
