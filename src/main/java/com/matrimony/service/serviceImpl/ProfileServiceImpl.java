package com.matrimony.service.serviceImpl;

import com.matrimony.entity.Profile;
import com.matrimony.repository.ProfileRepository;
import com.matrimony.repository.UserRepository;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.ProfileService;
import jakarta.persistence.*;
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
import java.util.*;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    UserRepository userRepository;

    //   below method will by default create the timestamp
    @PrePersist
    public void prePersist(Profile profile) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        profile.setCreatedAt(now);
        profile.setUpdatedAt(now);
    }

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public ResponseEntity<ApiResponse<Profile>> createProfile(Profile profileRequest) {
        try {
            Profile payload = this.profileRepository.save(profileRequest);
            return ResponseEntity.ok(new ApiResponse<>("success", "Profile added successfully", payload, 200));
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while saving data", e);
            return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }


    @Override
    public ResponseEntity<ApiResponse<Object>> getProfiles(SearchPaginationRequest searchParams) {

        try {
            Long id = searchParams.getId();
            String religion = searchParams.getReligion();
            Integer perPageRecord = searchParams.getPer_page_record();

            // Set the default value of page to 1
            Integer page = (searchParams.getPage() != null) ? searchParams.getPage() : 1;

            Page<Profile> categoryPage;

            if (id != null) {
                Optional<Profile> categoryOptional = profileRepository.findById(id);
                if (categoryOptional.isPresent()) {
                    Profile category = categoryOptional.get();
                    categoryPage = new PageImpl<>(Collections.singletonList(category));
                } else {
                    categoryPage = Page.empty(); // No matching category found
                }
            }
            else if (religion != null) {
                categoryPage = profileRepository.findByReligionContaining(religion, PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else {
                categoryPage = profileRepository.findAll(PageRequest.of(page - 1, perPageRecord,Sort.by(Sort.Order.desc("id"))));
            }
            //  Below code is when we are making any join to two tables
            List<Profile> parents = categoryPage.getContent();

            Map<String, Object> map = Map.of(
                    "data", parents,
//                    "data", responseList,
                    "totalElements", categoryPage.getTotalElements(),
                    "currentPage", page,
                    "perPageRecord", perPageRecord,
                    "totalPages", categoryPage.getTotalPages()
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
    public ResponseEntity<ApiResponse<Profile>> updateProfile(Long profileId, Profile profileRequest) {
        try {
            Optional<Profile> existingProfile = this.profileRepository.findById(profileId);

            if (existingProfile.isPresent()) {
                // Update the existing category with the new data
                Profile updatedProfile = existingProfile.get();

                updatedProfile.setFatherName(profileRequest.getFatherName());
                updatedProfile.setMotherName(profileRequest.getMotherName());
                updatedProfile.setFacebookUrl(profileRequest.getFacebookUrl());
                updatedProfile.setWhatsappUrl(profileRequest.getWhatsappUrl());
                updatedProfile.setLinkedinUrl(profileRequest.getLinkedinUrl());
                updatedProfile.setCreatedBy(profileRequest.getCreatedBy());
                updatedProfile.setPhoto1(profileRequest.getPhoto1());
                updatedProfile.setPhoto2(profileRequest.getPhoto2());
                updatedProfile.setPhoto3(profileRequest.getPhoto3());
                updatedProfile.setAstroprofile(profileRequest.getAstroprofile());
                updatedProfile.setNakshatra(profileRequest.getNakshatra());
                updatedProfile.setRasi(profileRequest.getRasi());
                updatedProfile.setMaritalStatus(profileRequest.getMaritalStatus());
                updatedProfile.setHaveChildren(profileRequest.getHaveChildren());
                updatedProfile.setNumberOfChildren(profileRequest.getNumberOfChildren());
                updatedProfile.setWeight(profileRequest.getWeight());
                updatedProfile.setHeight(profileRequest.getHeight());
                updatedProfile.setBodyType(profileRequest.getBodyType());
                updatedProfile.setComplexion(profileRequest.getComplexion());
                updatedProfile.setReligion(profileRequest.getReligion());
                updatedProfile.setSpecialCases(profileRequest.getSpecialCases());
                updatedProfile.setMotherTongue(profileRequest.getMotherTongue());
                updatedProfile.setCaste(profileRequest.getCaste());
                updatedProfile.setSubCaste(profileRequest.getSubCaste());
                updatedProfile.setManglik(profileRequest.getManglik());
                updatedProfile.setBodyType(profileRequest.getBodyType());
                updatedProfile.setFamilyValues(profileRequest.getFamilyValues());
                updatedProfile.setBloodGroup(profileRequest.getBloodGroup());
                updatedProfile.setAnnualIncome(profileRequest.getAnnualIncome());
                updatedProfile.setNumberOfBrother(profileRequest.getNumberOfBrother());
                updatedProfile.setNumberOfSister(profileRequest.getNumberOfSister());
                updatedProfile.setContactPersonName(profileRequest.getContactPersonName());
                updatedProfile.setContactPersonRelationShip(profileRequest.getContactPersonRelationShip());
                updatedProfile.setConvenientCallTime(profileRequest.getConvenientCallTime());
                updatedProfile.setPlaceOfBirth(profileRequest.getPlaceOfBirth());
                updatedProfile.setTimeOFBirth(profileRequest.getTimeOFBirth());
                updatedProfile.setHobbies(profileRequest.getHobbies());
                updatedProfile.setInterests(profileRequest.getInterests());
                updatedProfile.setFavoriteReads(profileRequest.getFavoriteReads());
                updatedProfile.setPreferredMovies(profileRequest.getPreferredMovies());
                updatedProfile.setSports(profileRequest.getSports());
                updatedProfile.setFavoriteCuisine(profileRequest.getFavoriteCuisine());
                updatedProfile.setSpokenLanguages(profileRequest.getSpokenLanguages());
                updatedProfile.setEducation(profileRequest.getEducation());
                updatedProfile.setProfession(profileRequest.getProfession());
                updatedProfile.setDiet(profileRequest.getDiet());
                updatedProfile.setAboutYourself(profileRequest.getAboutYourself());
                updatedProfile.setDateOfMarriage(profileRequest.getDateOfMarriage());
                Profile payload = this.profileRepository.save(updatedProfile);
                return ResponseEntity.ok(new ApiResponse<>("success", "Data updated successfully", payload, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "MenuId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while updating data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Profile>>  getProfile(Long profileId) {
        try {
            Optional<Profile> profileEntityOptional = this.profileRepository.findById(profileId);
            if (profileEntityOptional.isPresent()) {
                Profile categoryEntity = profileEntityOptional.get();
                return ResponseEntity.ok(new ApiResponse<>("success", "Data retrieved successfully", categoryEntity, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "MenuId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while retrieving data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> deleteProfile(Long profileId) {
        try {
            Optional<Profile> category = this.profileRepository.findById(profileId);
            if (category.isPresent()) {
                this.profileRepository.deleteById(profileId);
                return ResponseEntity.ok(new ApiResponse<>("success", "Data deleted successfully", null, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "MenuId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while deleting data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

}
