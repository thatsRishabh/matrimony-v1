package com.matrimony.service;

import com.matrimony.entity.User;
import com.matrimony.request.LoginRequest;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.request.UserRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.response.LoginResponse;
import com.matrimony.response.UserResponse;
import com.matrimony.validator.UserValidation;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
//    public User createUser(User userRequst);

    public ResponseEntity<ApiResponse<User>> createUser(UserValidation userRequst);

    public ResponseEntity<ApiResponse<User>> updateUser(Long userId, User userRequest);

    public ResponseEntity<ApiResponse<Object>> getUsers(SearchPaginationRequest searchParams);

    public ResponseEntity<ApiResponse<User>>  getUser(Long userId);

    public ResponseEntity<ApiResponse<?>> deleteUser(Long userId);

    public ResponseEntity<ApiResponse<LoginResponse>> generateToken(LoginRequest loginRequest);

}
