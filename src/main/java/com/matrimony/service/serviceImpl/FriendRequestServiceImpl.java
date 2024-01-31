package com.matrimony.service.serviceImpl;

import com.matrimony.entity.FriendRequest;
import com.matrimony.entity.Profile;
import com.matrimony.entity.Slider;
import com.matrimony.entity.User;
import com.matrimony.firebaseNotifications.FirebaseMessagingService;
import com.matrimony.repository.FriendRequestRepository;
import com.matrimony.repository.ProfileRepository;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.response.UserFriendRequestProfileResponse;
import com.matrimony.response.UserFriendRequestResponse;
import com.matrimony.service.FriendRequestService;
import com.matrimony.validator.FriendRequestValidation;
import com.matrimony.validator.SliderValidation;
import jakarta.persistence.PrePersist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class FriendRequestServiceImpl  implements FriendRequestService {

    @Autowired
    FriendRequestRepository friendRequestRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    FirebaseMessagingService firebaseMessagingService;

    //    below method will by default create the timestamp
    @PrePersist
    public void prePersist(FriendRequest friendRequest) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        friendRequest.setCreatedAt(now);
        friendRequest.setUpdatedAt(now);
    }
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public ResponseEntity<ApiResponse<FriendRequest>> createFriendRequest(FriendRequestValidation friendRequestRequest) {
        try {

            if (friendRequestRepository.findBySenderIdReceiverId(friendRequestRequest.getReceiver_id().getId(),friendRequestRequest.getSender_id().getId()) != null) {
                return ResponseEntity.badRequest().body(new ApiResponse<>("error", "Friend request for this profile already exists", null, 400));
            }

            FriendRequest friendRequest = new FriendRequest();
            friendRequest.setSender_id(friendRequestRequest.getSender_id());
            friendRequest.setReceiver_id(friendRequestRequest.getReceiver_id());
            friendRequest.setStatus(friendRequestRequest.getStatus());
            FriendRequest payload = this.friendRequestRepository.save(friendRequest);

//            sending friend request notification
            firebaseMessagingService.sendFriendRequestNotification(friendRequestRequest);


            return ResponseEntity.ok(new ApiResponse<>("success", "Data saved successfully", payload, 200));
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while saving data", e);
            return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<FriendRequest>> updateFriendRequest(Long sliderId, FriendRequestValidation friendRequestRequest) {
        try {
            Optional<FriendRequest> existingFriendRequest = this.friendRequestRepository.findById(sliderId);

            if (existingFriendRequest.isPresent()) {
                // Update the existing FriendRequest with the new data
                FriendRequest updatedFriendRequest = existingFriendRequest.get();
                updatedFriendRequest.setSender_id(friendRequestRequest.getSender_id());
                updatedFriendRequest.setReceiver_id(friendRequestRequest.getReceiver_id());
                updatedFriendRequest.setStatus(friendRequestRequest.getStatus());
                FriendRequest payload = this.friendRequestRepository.save(updatedFriendRequest);

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
    public ResponseEntity<ApiResponse<List<FriendRequest>>> getFriendRequest(Long friendRequestId) {
        try {
            List<FriendRequest> friendRequests = this.friendRequestRepository.findBySenderId(friendRequestId);

            if (friendRequests.isEmpty()) {
                // Friend request with the given ID does not exist
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "Friend request not found", null, 404));
            }

            return ResponseEntity.ok(new ApiResponse<>("success", "Data retrieved successfully", friendRequests, 200));
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while retrieving data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> getReceivedFriendRequests(SearchPaginationRequest searchParams) {

        try {
            Long userId = searchParams.getUserId();
            Integer perPageRecord = searchParams.getPer_page_record();

// Set the default value of page to 1
            Integer page = (searchParams.getPage() != null) ? searchParams.getPage() : 1;

            Page<FriendRequest> sliderPage = friendRequestRepository.findByReceiverId(
                    userId,
                    PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id")))
            );

            List<FriendRequest> sliderEntities = sliderPage.getContent();


//            List<User> userEntities = userPage.getContent();

            //  Below code is when we are making any join to two tables
            List<UserFriendRequestProfileResponse> responseList = new ArrayList<>();

            for (FriendRequest parent : sliderEntities) {
                Optional<Profile> children = profileRepository.findByUserId(parent.getSender_id().getId());
                UserFriendRequestProfileResponse response = new UserFriendRequestProfileResponse();
                response.setFriendRequest(parent);
                response.setProfile(children);
                responseList.add(response);
            }

            Map<String, Object> map = Map.of(
                    "data", responseList,
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
    public ResponseEntity<ApiResponse<?>> deleteFriendRequest(Long friendRequestId) {
        try {
            Optional<FriendRequest> slider = this.friendRequestRepository.findById(friendRequestId);
            if (slider.isPresent()) {
                //  CategoryEntity categoryEntity= new CategoryEntity();
                //  categoryEntity.setCid(categoryID);
                //  this.categoryRepository.delete(categoryEntity);
                // below one is easy single line code

                this.friendRequestRepository.deleteById(friendRequestId);
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
