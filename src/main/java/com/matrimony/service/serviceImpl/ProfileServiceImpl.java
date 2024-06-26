package com.matrimony.service.serviceImpl;

import com.matrimony.entity.*;
import com.matrimony.repository.CitySelectedRepository;
import com.matrimony.repository.LanguageSelectedRepository;
import com.matrimony.repository.ProfileRepository;
import com.matrimony.repository.UserRepository;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.ProfileService;
import com.matrimony.validator.ProfileValidation;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LanguageSelectedRepository languageSelectedRepository;

    @Autowired
    CitySelectedRepository citySelectedRepository;

    //   below method will by default create the timestamp
    @PrePersist
    public void prePersist(Profile profile) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        profile.setCreatedAt(now);
        profile.setUpdatedAt(now);
    }

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public ResponseEntity<ApiResponse<Profile>> createProfile(ProfileValidation profileRequest) {
        try {
            Profile profile = new Profile();
            profile.setFatherName(profileRequest.getFatherName());
            profile.setMotherName(profileRequest.getMotherName());
            profile.setFacebookUrl(profileRequest.getFacebookUrl());


//        Generating WhatsApp URL
            String whatsAppBaseURl = "http://wa.me/";
            String whatsAppURL=whatsAppBaseURl.concat(profileRequest.getWhatsappUrl());

            profile.setWhatsappUrl(whatsAppURL);
            profile.setLinkedinUrl(profileRequest.getLinkedinUrl());
            profile.setCreatedBy(profileRequest.getCreatedBy());
            profile.setPhoto1(profileRequest.getPhoto1());
            profile.setPhoto2(profileRequest.getPhoto2());
            profile.setPhoto3(profileRequest.getPhoto3());
            profile.setAstroprofile(profileRequest.getAstroprofile());
            profile.setNakshatra(profileRequest.getNakshatra());
            profile.setRasi(profileRequest.getRasi());
            profile.setMaritalStatus(profileRequest.getMaritalStatus());
            profile.setHaveChildren(profileRequest.getHaveChildren());
            profile.setNumberOfChildren(profileRequest.getNumberOfChildren());
            profile.setWeight(profileRequest.getWeight());
            profile.setHeight(profileRequest.getHeight());
            profile.setBodyType(profileRequest.getBodyType());
            profile.setNickName(profileRequest.getNickName());
            profile.setComplexion(profileRequest.getComplexion());
            profile.setReligion(profileRequest.getReligion());
            profile.setSpecialCases(profileRequest.getSpecialCases());
            profile.setMotherTongue(profileRequest.getMotherTongue());
            profile.setCaste(profileRequest.getCaste());
            profile.setSubCaste(profileRequest.getSubCaste());
            profile.setManglik(profileRequest.getManglik());
            profile.setBodyType(profileRequest.getBodyType());
            profile.setFamilyValues(profileRequest.getFamilyValues());
            profile.setBloodGroup(profileRequest.getBloodGroup());
            profile.setAnnualIncome(profileRequest.getAnnualIncome());
            profile.setCompanyName(profileRequest.getCompanyName());
            profile.setNumberOfBrother(profileRequest.getNumberOfBrother());
            profile.setNumberOfSister(profileRequest.getNumberOfSister());
            profile.setContactPersonName(profileRequest.getContactPersonName());
            profile.setContactPersonRelationShip(profileRequest.getContactPersonRelationShip());
            profile.setContactPersonPhoneNumber(profileRequest.getContactPersonPhoneNumber());
            profile.setConvenientCallTime(profileRequest.getConvenientCallTime());
            profile.setPlaceOfBirth(profileRequest.getPlaceOfBirth());
            profile.setTimeOFBirth(profileRequest.getTimeOFBirth());
            profile.setHobbies(profileRequest.getHobbies());
            profile.setInterests(profileRequest.getInterests());
            profile.setFavoriteReads(profileRequest.getFavoriteReads());
            profile.setPreferredMovies(profileRequest.getPreferredMovies());
            profile.setSports(profileRequest.getSports());
            profile.setFavoriteCuisine(profileRequest.getFavoriteCuisine());
//            profile.setSpokenLanguages(profileRequest.getSpokenLanguages());
            profile.setEducation(profileRequest.getEducation());
            profile.setProfession(profileRequest.getProfession());
            profile.setDiet(profileRequest.getDiet());
            profile.setAboutYourself(profileRequest.getAboutYourself());
            profile.setDateOfMarriage(profileRequest.getDateOfMarriage());
            profile.setUser(profileRequest.getUser());

            Profile payload = this.profileRepository.save(profile);

            // Save Language methods
            List<Language> languages = profileRequest.getLanguage();
            for (Language languageRequest : languages) {
                LanguageSelected languageSelected= new LanguageSelected();
                languageSelected.setProfile(profile);
                languageSelected.setLanguage(languageRequest);
                languageSelectedRepository.save(languageSelected);
                System.out.println("inside the loop=======================");
            }

            // Save City methods
            List<City>  cities= profileRequest.getInterestedCities();
            for (City cityRequest : cities) {
                CitySelected citySelected= new CitySelected();
                citySelected.setProfile(profile);
                citySelected.setCity(cityRequest);
                citySelectedRepository.save(citySelected);
                System.out.println("inside the loop=======================");
            }

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

            String city = searchParams.getCity();
            int gender = searchParams.getGender();
            String minHeight = searchParams.getMinHeight();
            String maxHeight = searchParams.getMaxHeight();
            String maxWeight = searchParams.getMaxWeight();
            String minWeight = searchParams.getMinWeight();
            int minAge = searchParams.getMinAge();
            int maxAge = searchParams.getMaxAge();
            String caste = searchParams.getCaste();
            String minAnnualIncome = searchParams.getMinAnnualIncome();
            String maxAnnualIncome = searchParams.getMaxAnnualIncome();
//            String languageName = searchParams.getLanguageName();
            Integer perPageRecord = searchParams.getPer_page_record();
            List<Long> citySelectedList= searchParams.getCitySelectedList();
            List<Long> languageSelectedList= searchParams.getLanguageSelectedList();

            // Set the default value of page to 1
            Integer page = (searchParams.getPage() != null) ? searchParams.getPage() : 1;

            Page<Profile> profilePage;

            // Calculate birth dates for minAge and maxAge
            LocalDate today = LocalDate.now();
            LocalDate minBirthDate = today.minusYears(maxAge);
            LocalDate maxBirthDate = today.minusYears(minAge).plusDays(1); // Add one day to include the upper bound

            // Convert LocalDate to Date
            Date minBirthDateAsDate = Date.from(minBirthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date maxBirthDateAsDate = Date.from(maxBirthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            // Now you can use these Date objects in your query
//            return profileRepository.findByDateOfBirthBetween(minBirthDateAsDate, maxBirthDateAsDate, pageable);


            // Get the current user's data from jwt token
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserEmail = authentication.getName();
            User currentUserProfile = userRepository.findByEmailAddress(currentUserEmail);


            if (id != null) {
                Optional<Profile> categoryOptional = profileRepository.findById(id);
                if (categoryOptional.isPresent()) {
                    Profile category = categoryOptional.get();
                    profilePage = new PageImpl<>(Collections.singletonList(category));
                } else {
                    profilePage = Page.empty(); // No matching category found
                }
            }
            else if (gender != 0 && caste != null && religion != null && city != null && minHeight != null && maxHeight != null && minWeight != null && maxWeight != null) {
                profilePage = profileRepository.findByUser_GenderAndCasteContainingAndReligionContainingAndPlaceOfBirthContainingAndHeightBetweenAndWeightBetweenAndUser_IdNot(gender, caste,religion,city,minHeight, maxHeight,minWeight, maxWeight,currentUserProfile.getId(), PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else if (gender != 0 && caste != null && religion != null && city != null) {
                profilePage = profileRepository.findByUser_GenderAndCasteContainingAndReligionContainingAndPlaceOfBirthContainingAndUser_IdNot(gender, caste,religion,city,currentUserProfile.getId(), PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else if (minHeight != null && maxHeight != null && minWeight != null && maxWeight != null) {
                profilePage = profileRepository.findByHeightBetweenAndWeightBetweenAndUser_IdNot(minHeight, maxHeight,minWeight, maxWeight,currentUserProfile.getId(), PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else if (religion != null && caste != null) {
                profilePage = profileRepository.findByCasteContainingAndReligionContainingAndUser_IdNot(caste, religion, currentUserProfile.getId(),PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else if (gender != 0 && minAge != 0 && maxAge != 0) {
                profilePage = profileRepository.findByUser_GenderAndUser_DateOfBirthBetweenAndUser_IdNot(gender, minBirthDateAsDate, maxBirthDateAsDate,currentUserProfile.getId(), PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else if (gender != 0 && minHeight != null && maxHeight != null) {
                profilePage = profileRepository.findByUser_GenderAndHeightBetweenAndUser_IdNot(gender, minHeight, maxHeight,currentUserProfile.getId(), PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else if (gender != 0 && minWeight != null && maxWeight != null) {
                profilePage = profileRepository.findByUser_GenderAndWeightBetweenAndUser_IdNot(gender, minWeight, maxWeight,currentUserProfile.getId(), PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else if (gender != 0 && minAnnualIncome != null && maxAnnualIncome != null) {
                profilePage = profileRepository.findByUser_GenderAndAnnualIncomeBetweenAndUser_IdNot(gender, minAnnualIncome, maxAnnualIncome,currentUserProfile.getId(), PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else if (gender != 0 && caste != null) {
                profilePage = profileRepository.findByUser_GenderAndCasteContainingAndUser_IdNot(gender, caste,currentUserProfile.getId(), PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else if (gender != 0 && religion != null) {
                profilePage = profileRepository.findByUser_GenderAndReligionContainingAndUser_IdNot(gender, religion,currentUserProfile.getId(), PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else if (gender != 0 && city != null) {
                profilePage = profileRepository.findByUser_GenderAndPlaceOfBirthContainingAndUser_IdNot(gender, city,currentUserProfile.getId(), PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }

            else if (minAge != 0 && maxAge != 0) {
                profilePage = profileRepository.findByUser_DateOfBirthBetweenAndUser_IdNot(minBirthDateAsDate, maxBirthDateAsDate,currentUserProfile.getId(), PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else if (minHeight != null && maxHeight != null) {
                profilePage = profileRepository.findByHeightBetweenAndUser_IdNot(minHeight, maxHeight,currentUserProfile.getId(), PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else if (minWeight != null && maxWeight != null) {
                profilePage = profileRepository.findByWeightBetweenAndUser_IdNot(minWeight, maxWeight,currentUserProfile.getId(), PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else if (minAnnualIncome != null && maxAnnualIncome != null) {
                profilePage = profileRepository.findByAnnualIncomeBetweenAndUser_IdNot(minAnnualIncome, maxAnnualIncome,currentUserProfile.getId(), PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else if (religion != null) {
                profilePage = profileRepository.findByReligionContainingAndUser_IdNot(religion, currentUserProfile.getId(),PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else if (city != null) {
                profilePage = profileRepository.findByPlaceOfBirthContainingAndUser_IdNot(city,currentUserProfile.getId(), PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
//            else if (spokenLanguages != null) {
//                profilePage = profileRepository.findBySpokenLanguagesContainingAndUser_IdNot(spokenLanguages,currentUserProfile.getId(), PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
//            }
            else if (gender != 0) {
                profilePage = profileRepository.findByUser_GenderAndUser_IdNot(gender,currentUserProfile.getId(), PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }

            else if (caste != null) {
                profilePage = profileRepository.findByCasteContainingAndUser_IdNot(caste,currentUserProfile.getId(), PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else if (citySelectedList != null && languageSelectedList != null) {
                profilePage = profileRepository.findByCitySelectedList_City_IdInAndLanguageSelectedList_language_IdInAndUser_IdNot(citySelectedList, languageSelectedList ,currentUserProfile.getId(), PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else if (citySelectedList != null) {
                profilePage = profileRepository.findByCitySelectedList_City_IdInAndUser_IdNot(citySelectedList,currentUserProfile.getId(), PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else if (languageSelectedList != null) {
                profilePage = profileRepository.findByLanguageSelectedList_language_IdInAndUser_IdNot(languageSelectedList,currentUserProfile.getId(), PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }

            else {
                profilePage = profileRepository.findByIdNot(currentUserProfile.getId(),PageRequest.of(page - 1, perPageRecord,Sort.by(Sort.Order.desc("id"))));
            }

            //  Below code is when we are making any join to two tables and response sent contains array
            List<Profile> parents = profilePage.getContent();
//            List<Map<String, Object>> responseList = new ArrayList<>();
//
//            for (Profile parent : parents) {
//
//                Map<String, Object> response = new HashMap<>();
//                response.put("profile", parent);
//
//                List<LanguageSelected> children = languageSelectedRepository.findByProfileId(parent.getId());
//                List<Map<String, Object>> languageList = new ArrayList<>();
//                for (LanguageSelected language : children) {
//                    Map<String, Object> languageMap = new HashMap<>();
////                    languageMap.put("languageName", language.getLanguage());
//                    languageMap.put("languageName", language.getLanguage().getName());
//                    languageList.add(languageMap);
//
//                }
//                response.put("languageList", languageList);
//
//                List<CitySelected> citySelecteds = citySelectedRepository.findByProfileId(parent.getId());
//                List<Map<String, Object>> cityList = new ArrayList<>();
//                for (CitySelected cities : citySelecteds) {
//                    Map<String, Object> languageMap = new HashMap<>();
//                    languageMap.put("cityName", cities.getCity().getName());
//                    cityList.add(languageMap);
//
//                }
//                response.put("interestedCities", cityList);
//                responseList.add(response);
//            }

            Map<String, Object> map = Map.of(
                    "data", parents,
//                    "data", responseList,
                    "totalElements", profilePage.getTotalElements(),
                    "currentPage", page,
                    "perPageRecord", perPageRecord,
                    "totalPages", profilePage.getTotalPages()
            );
            return ResponseEntity.ok( new ApiResponse<>("success", "Data retrieved successfully", map, 200));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("An error occurred while saving data", e);
//            return e.getMessage();
            return ResponseEntity.internalServerError().body( new ApiResponse<>("error", e.getMessage(), null, 500));
        }

    }

//    below transactional annotation is must if you are deleting anything from database using PUT/POST/GET method
//    when params contains data in from of array
    @Transactional
    @Override
    public ResponseEntity<ApiResponse<Profile>> updateProfile(Long profileId, ProfileValidation profileRequest) {
        try {
            Optional<Profile> existingProfile = this.profileRepository.findById(profileId);

            if (existingProfile.isPresent()) {
                // Update the existing category with the new data
                Profile updatedProfile = existingProfile.get();

                updatedProfile.setFatherName(profileRequest.getFatherName());
                updatedProfile.setMotherName(profileRequest.getMotherName());
                updatedProfile.setFacebookUrl(profileRequest.getFacebookUrl());

                //        Generating WhatsApp URL
                String whatsAppBaseURl = "http://wa.me/";
                String whatsAppURL=whatsAppBaseURl.concat(profileRequest.getWhatsappUrl());

                updatedProfile.setWhatsappUrl(whatsAppURL);
                updatedProfile.setLinkedinUrl(profileRequest.getLinkedinUrl());
                updatedProfile.setCompanyName(profileRequest.getCompanyName());
                updatedProfile.setCreatedBy(profileRequest.getCreatedBy());
                updatedProfile.setPhoto1(profileRequest.getPhoto1());
                updatedProfile.setPhoto2(profileRequest.getPhoto2());
                updatedProfile.setPhoto3(profileRequest.getPhoto3());
                updatedProfile.setNickName(profileRequest.getNickName());
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
                updatedProfile.setContactPersonPhoneNumber(profileRequest.getContactPersonPhoneNumber());
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
//                updatedProfile.setSpokenLanguages(profileRequest.getSpokenLanguages());
                updatedProfile.setEducation(profileRequest.getEducation());
                updatedProfile.setProfession(profileRequest.getProfession());
                updatedProfile.setDiet(profileRequest.getDiet());
                updatedProfile.setAboutYourself(profileRequest.getAboutYourself());
                updatedProfile.setDateOfMarriage(profileRequest.getDateOfMarriage());
                updatedProfile.setUser(profileRequest.getUser());
                Profile payload = this.profileRepository.save(updatedProfile);

                this.languageSelectedRepository.deleteByProfileId(profileId);

                // Save Language methods
                List<Language> languages = profileRequest.getLanguage();
                for (Language languageRequest : languages) {
                    LanguageSelected languageSelected= new LanguageSelected();
                    languageSelected.setProfile(updatedProfile);
                    languageSelected.setLanguage(languageRequest);
                    languageSelectedRepository.save(languageSelected);
                    System.out.println("inside the loop=======================");
                }

                this.citySelectedRepository.deleteByProfileId(profileId);
                // Save City methods
                List<City>  cities= profileRequest.getInterestedCities();
                for (City cityRequest : cities) {
                    CitySelected citySelected= new CitySelected();
                    citySelected.setProfile(updatedProfile);
                    citySelected.setCity(cityRequest);
                    citySelectedRepository.save(citySelected);
                    System.out.println("inside the loop=======================");
                }

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
