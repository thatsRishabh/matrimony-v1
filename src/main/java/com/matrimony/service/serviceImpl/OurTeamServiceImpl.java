package com.matrimony.service.serviceImpl;

import com.matrimony.entity.OurTeam;
import com.matrimony.repository.OurTeamRepository;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.OurTeamService;
import com.matrimony.validator.OurTeamValidation;
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
public class OurTeamServiceImpl implements OurTeamService {
    @Autowired
    OurTeamRepository ourTeamRepository;

    //    below method will by default create the timestamp
    @PrePersist
    public void prePersist(OurTeam ourTeam) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        ourTeam.setCreatedAt(now);
        ourTeam.setUpdatedAt(now);
    }

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public ResponseEntity<ApiResponse<OurTeam>> createOurTeam(OurTeamValidation ourTeamRequest) {
        try {
            OurTeam ourTeam = new OurTeam();
            ourTeam.setName(ourTeamRequest.getName());
            ourTeam.setDesignation(ourTeamRequest.getDesignation());
            ourTeam.setOrder_number(ourTeamRequest.getOrder_number());
            ourTeam.setImage(ourTeamRequest.getImage());
            ourTeam.setStatus(ourTeamRequest.getStatus());
            ourTeam.setFacebookUrl(ourTeamRequest.getFacebookUrl());
            ourTeam.setLinkedinUrl(ourTeamRequest.getLinkedinUrl());
            ourTeam.setWhatsappUrl(ourTeamRequest.getWhatsappUrl());
            OurTeam payload = this.ourTeamRepository.save(ourTeam);
            return ResponseEntity.ok(new ApiResponse<>("success", "Data saved successfully", payload, 200));
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while saving data", e);
            return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }
    @Override
    public ResponseEntity<ApiResponse<Object>> getOurTeams(SearchPaginationRequest searchParams) {

        try {
            Long id = searchParams.getId();
            String name = searchParams.getName();
            Integer perPageRecord = searchParams.getPer_page_record();

            // Set the default value of page to 1
            Integer page = (searchParams.getPage() != null) ? searchParams.getPage() : 1;

            Page<OurTeam> sliderPage;

            if (id != null) {
                Optional<OurTeam> sliderOptional = ourTeamRepository.findById(id);
                if (sliderOptional.isPresent()) {
                    OurTeam slider = sliderOptional.get();
                    sliderPage = new PageImpl<>(Collections.singletonList(slider));
                } else {
                    sliderPage = Page.empty(); // No matching slider found
                }
            }
            else if (name != null) {
                sliderPage = ourTeamRepository.findByNameContaining(name, PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else {
                sliderPage = ourTeamRepository.findAll(PageRequest.of(page - 1, perPageRecord,Sort.by(Sort.Order.desc("id"))));
            }

            List<OurTeam> sliderEntities = sliderPage.getContent();


            Map<String, Object> map = Map.of(
                    "data", sliderEntities,
                    "totalElements", sliderPage.getTotalElements(),
                    "currentPage", page,
                    "perPageRecord", perPageRecord,
                    "totalPages", sliderPage.getTotalPages()
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
    public ResponseEntity<ApiResponse<OurTeam>> updateOurTeam(Long ourTeamId, OurTeamValidation ourTeamRequest) {
        try {
            Optional<OurTeam> existingOurTeam = this.ourTeamRepository.findById(ourTeamId);

            if (existingOurTeam.isPresent()) {
                // Update the existing slider with the new data
                OurTeam updatedOurTeam= existingOurTeam.get();
                updatedOurTeam.setName(ourTeamRequest.getName());
                updatedOurTeam.setDesignation(ourTeamRequest.getDesignation());
                updatedOurTeam.setOrder_number(ourTeamRequest.getOrder_number());
                updatedOurTeam.setImage(ourTeamRequest.getImage());
                updatedOurTeam.setStatus(ourTeamRequest.getStatus());
                updatedOurTeam.setFacebookUrl(ourTeamRequest.getFacebookUrl());
                updatedOurTeam.setLinkedinUrl(ourTeamRequest.getLinkedinUrl());
                updatedOurTeam.setWhatsappUrl(ourTeamRequest.getWhatsappUrl());
                OurTeam payload = this.ourTeamRepository.save(updatedOurTeam);
                return ResponseEntity.ok(new ApiResponse<>("success", "Data updated successfully", payload, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "ourTeamId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while updating data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<OurTeam>>  getOurTeam(Long ourTeamId) {
        try {
            Optional<OurTeam> sliderEntityOptional = this.ourTeamRepository.findById(ourTeamId);

            if (sliderEntityOptional.isPresent()) {
                OurTeam sliderEntity = sliderEntityOptional.get();
                return ResponseEntity.ok(new ApiResponse<>("success", "Data retrieved successfully", sliderEntity, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "ourTeamId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while retrieving data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> deleteOurTeam(Long ourTeamId) {
        try {
            Optional<OurTeam> slider = this.ourTeamRepository.findById(ourTeamId);
            if (slider.isPresent()) {
                this.ourTeamRepository.deleteById(ourTeamId);
                return ResponseEntity.ok(new ApiResponse<>("success", "Data deleted successfully", null, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "ourTeamId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while deleting data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }
}
