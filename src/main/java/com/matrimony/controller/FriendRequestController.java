package com.matrimony.controller;

import com.matrimony.entity.FriendRequest;
import com.matrimony.entity.Slider;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.FriendRequestService;
import com.matrimony.validator.FriendRequestValidation;
import com.matrimony.validator.SliderValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/friend-request")
public class FriendRequestController {

    @Autowired
    private FriendRequestService friendRequestService;

    //add menu
    @PostMapping()
    public ResponseEntity<ApiResponse<FriendRequest>> addFriendRequest(@Valid @RequestBody FriendRequestValidation friendRequestRequest){
        return this.friendRequestService.createFriendRequest(friendRequestRequest);
    }

    //get all friend request received
    @PostMapping("/")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Object>> getFriendRequests(@RequestBody SearchPaginationRequest searchParams){
        return this.friendRequestService.getFriendRequests(searchParams);
    }

    //get all friend request received
    @PostMapping("/accepted")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Object>> getAcceptedFriends(@RequestBody SearchPaginationRequest searchParams){
        return this.friendRequestService.getAcceptedFriends(searchParams);
    }


    @PutMapping("/{friendRequestId}")
    public ResponseEntity<ApiResponse<FriendRequest>>  updateFriendRequest(@PathVariable("friendRequestId") Long friendRequestId , @Valid @RequestBody FriendRequestValidation friendRequestRequest){
        return this.friendRequestService.updateFriendRequest(friendRequestId, friendRequestRequest);
    }

//    // get single category
//    @GetMapping("/{friendRequestId}")
//    public ResponseEntity<ApiResponse<List<FriendRequest>>> getFriendRequest(@PathVariable("friendRequestId") Long friendRequestId){
//        return this.friendRequestService.getFriendRequest(friendRequestId);
//    }

    @DeleteMapping("/{friendRequestId}")
    public ResponseEntity<ApiResponse<?>> deleteFriendRequest(@PathVariable("friendRequestId") Long friendRequestId){
        return this.friendRequestService.deleteFriendRequest(friendRequestId);
    }
}
