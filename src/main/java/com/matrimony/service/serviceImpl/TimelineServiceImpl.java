package com.matrimony.service.serviceImpl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.matrimony.entity.Timeline;
import com.matrimony.repository.TimelineRepository;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.TimelineService;
import com.matrimony.validator.TimelineValidation;
import jakarta.persistence.PrePersist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TimelineServiceImpl implements TimelineService {
    @Autowired
    TimelineRepository timelineRepository;

    //    below method will by default create the timestamp
    @PrePersist
    public void prePersist(Timeline timeline) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        timeline.setCreatedAt(now);
        timeline.setUpdatedAt(now);
    }

    private final Logger log = LoggerFactory.getLogger(getClass());

    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "UTC")
    private Timestamp time;
    private String description;
    private Integer order_number;
    private String image;

    @Override
    public ResponseEntity<ApiResponse<Timeline>> createTimeline(TimelineValidation timelineRequest) {
        try {
            Timeline timeline = new Timeline();
            timeline.setTitle(timelineRequest.getTitle());
            timeline.setDescription(timelineRequest.getDescription());
            timeline.setOrder_number(timelineRequest.getOrder_number());
            timeline.setImage(timelineRequest.getImage());
            timeline.setTime(timelineRequest.getTime());

            Timeline payload = this.timelineRepository.save(timeline);
            return ResponseEntity.ok(new ApiResponse<>("success", "Data saved successfully", payload, 200));
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception here and log it
            log.error("An error occurred while saving data", e);
            return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }
    @Override
    public ResponseEntity<ApiResponse<Object>> getTimelines(SearchPaginationRequest searchParams) {

        try {
            Long id = searchParams.getId();
            String title = searchParams.getTitle();
            Integer perPageRecord = searchParams.getPer_page_record();

            // Set the default value of page to 1
            Integer page = (searchParams.getPage() != null) ? searchParams.getPage() : 1;

            Page<Timeline> timelinePage;

            if (id != null) {
                Optional<Timeline> timelineOptional = timelineRepository.findById(id);
                if (timelineOptional.isPresent()) {
                    Timeline timeline = timelineOptional.get();
                    timelinePage = new PageImpl<>(Collections.singletonList(timeline));
                } else {
                    timelinePage = Page.empty(); // No matching slider found
                }
            }
            else if (title != null) {
                timelinePage = timelineRepository.findByTitleContaining(title, PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else {
                timelinePage = timelineRepository.findAll(PageRequest.of(page - 1, perPageRecord,Sort.by(Sort.Order.desc("id"))));
            }

            List<Timeline> timelineEntities = timelinePage.getContent();


            Map<String, Object> map = Map.of(
                    "data", timelineEntities,
                    "totalElements", timelinePage.getTotalElements(),
                    "currentPage", page,
                    "perPageRecord", perPageRecord,
                    "totalPages", timelinePage.getTotalPages()
            );
            return ResponseEntity.ok( new ApiResponse<>("success", "Data retrieved successfully", map, 200));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("An error occurred while saving data", e);
//            return e.getMessage();
            return ResponseEntity.internalServerError().body( new ApiResponse<>("error", e.getMessage(), null, 500));
        }

    }

    @Override
    public ResponseEntity<ApiResponse<Timeline>> updateTimeline(Long timelineId, TimelineValidation timelineRequest) {
        try {
            Optional<Timeline> existingTimeline = this.timelineRepository.findById(timelineId);

            if (existingTimeline.isPresent()) {
                // Update the existing slider with the new data
                Timeline updatedTimeline = existingTimeline.get();
                updatedTimeline.setTitle(timelineRequest.getTitle());
                updatedTimeline.setDescription(timelineRequest.getDescription());
                updatedTimeline.setOrder_number(timelineRequest.getOrder_number());
                updatedTimeline.setImage(timelineRequest.getImage());
                updatedTimeline.setTime(timelineRequest.getTime());
                Timeline payload = this.timelineRepository.save(updatedTimeline);
                return ResponseEntity.ok(new ApiResponse<>("success", "Data updated successfully", payload, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "sliderId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while updating data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Timeline>>  getTimeline(Long timelineId) {
        try {
            Optional<Timeline> timelineEntityOptional = this.timelineRepository.findById(timelineId);

            if (timelineEntityOptional.isPresent()) {
                Timeline timelineEntity = timelineEntityOptional.get();
                return ResponseEntity.ok(new ApiResponse<>("success", "Data retrieved successfully", timelineEntity, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "sliderId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while retrieving data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> deleteTimeline(Long timelineId) {
        try {
            Optional<Timeline> timeline = this.timelineRepository.findById(timelineId);
            if (timeline.isPresent()) {
                //  CategoryEntity categoryEntity= new CategoryEntity();
                //  categoryEntity.setCid(categoryID);
                //  this.categoryRepository.delete(categoryEntity);
                // below one is easy single line code


                this.timelineRepository.deleteById(timelineId);
                return ResponseEntity.ok(new ApiResponse<>("success", "Data deleted successfully", null, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "sliderId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while deleting data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }
}
