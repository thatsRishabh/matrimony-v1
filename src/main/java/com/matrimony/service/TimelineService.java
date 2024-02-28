package com.matrimony.service;

import com.matrimony.entity.Slider;
import com.matrimony.entity.Timeline;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.validator.SliderValidation;
import com.matrimony.validator.TimelineValidation;
import org.springframework.http.ResponseEntity;

public interface TimelineService {
    public ResponseEntity<ApiResponse<Timeline>> createTimeline(TimelineValidation timelineRequest);

    public ResponseEntity<ApiResponse<Object>> getTimelines(SearchPaginationRequest searchParams);

    public ResponseEntity<ApiResponse<Timeline>> updateTimeline(Long timelineId, TimelineValidation timelineRequest);

    public ResponseEntity<ApiResponse<Timeline>>  getTimeline(Long timelineId);

    public ResponseEntity<ApiResponse<?>> deleteTimeline(Long timelineId);
}
