package com.matrimony.service;

import com.matrimony.entity.FriendRequest;
import com.matrimony.entity.Slider;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.validator.FriendRequestValidation;
import com.matrimony.validator.SliderValidation;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FriendRequestService {
    public ResponseEntity<ApiResponse<FriendRequest>> createFriendRequest(FriendRequestValidation friendRequestRequest);

    public ResponseEntity<ApiResponse<Object>> getFriendRequests(SearchPaginationRequest searchParams);

    public ResponseEntity<ApiResponse<Object>> getAcceptedFriends(SearchPaginationRequest searchParams);

//    public ResponseEntity<ApiResponse<List<FriendRequest>>> getFriendRequest(Long friendRequestId);

    public ResponseEntity<ApiResponse<FriendRequest>> updateFriendRequest(Long friendRequestId, FriendRequestValidation friendRequestRequest);

    public ResponseEntity<ApiResponse<?>> deleteFriendRequest(Long friendRequestId);
}
