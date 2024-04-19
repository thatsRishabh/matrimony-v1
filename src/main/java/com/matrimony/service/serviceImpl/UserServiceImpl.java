package com.matrimony.service.serviceImpl;

import com.matrimony.authentication.CustomUserDetails;
import com.matrimony.authentication.CustomUserService;
import com.matrimony.authentication.JwtUtil;
import com.matrimony.entity.FriendRequest;
import com.matrimony.entity.Profile;
import com.matrimony.entity.User;
import com.matrimony.exception.DeactivatedUserException;
import com.matrimony.repository.FriendRequestRepository;
import com.matrimony.repository.ProfileRepository;
import com.matrimony.repository.RoleRepository;
import com.matrimony.repository.UserRepository;
import com.matrimony.request.LoginRequest;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.response.LoginResponse;
import com.matrimony.response.UserFriendRequestResponse;
import com.matrimony.service.UserService;
import com.matrimony.validator.UserValidation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

@Configuration
@Service
@Log4j2
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    FriendRequestRepository friendRequestRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserService userDetailsService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public ResponseEntity<ApiResponse<User>> createUser(UserValidation userRequest) {
       try {

           // Check if the email address is already taken
           if (userRepository.existsByEmailAddress(userRequest.getEmailAddress())) {
               return ResponseEntity.badRequest().body(new ApiResponse<>("error", "Email address already exists", null, 400));
           }

           // Validate that DateOfBirth is more than 18 years
           LocalDateTime currentDate = LocalDateTime.now();

           // Assuming getDateOfBirth() returns a Date object
           Date dateOfBirth = userRequest.getDateOfBirth();
           Instant instant = dateOfBirth.toInstant();
           ZoneId zoneId = ZoneId.systemDefault();
           LocalDateTime birthDateTime = instant.atZone(zoneId).toLocalDateTime();

           int age = Period.between(birthDateTime.toLocalDate(), currentDate.toLocalDate()).getYears();

           if (age < 18) {
               return ResponseEntity.badRequest().body(new ApiResponse<>("error", "User must be at least 18 years old", null, 400));
           }


           if (!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
               return ResponseEntity.internalServerError().body(new ApiResponse<>("error", "Both password do not match", null, 500));
           }

            User user = new User();
            user.setFullName(userRequest.getFullName());
            user.setEmailAddress(userRequest.getEmailAddress());
            user.setUserType(userRequest.getUserType());
            user.setAddress(userRequest.getAddress());
            user.setPhone(userRequest.getPhone());
            user.setStatus(1);
            user.setImagePath(userRequest.getImagePath());
            user.setGender(userRequest.getGender());
            user.setGenderIntrest(userRequest.getGenderIntrest());
            user.setSubscriptionType(userRequest.getSubscriptionType());
            user.setDateOfBirth(userRequest.getDateOfBirth());
            user.setRole(userRequest.getRole());

            String password = userRequest.getPassword();
            BCryptPasswordEncoder encoder = this.bCryptPasswordEncoder();
            String encodedPassword = encoder.encode(password);
            user.setPassword(encodedPassword);

           User payload = this.userRepository.save(user);
            return ResponseEntity.ok(new ApiResponse<>("success", "User added successfully", payload, 200));
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while saving data", e);
            return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<User>> updateUser(Long userId, User userRequest) {
        try {
            Optional<User> existingUser = this.userRepository.findById(userId);

            if (existingUser.isPresent()) {
                // Update the existing category with the new data
                User updatedUser = existingUser.get();
                updatedUser.setFullName(userRequest.getFullName());
//                updatedUser.setEmailAddress(userRequest.getEmailAddress());
                updatedUser.setUserType(userRequest.getUserType());
                updatedUser.setAddress(userRequest.getAddress());
                updatedUser.setPhone(userRequest.getPhone());
                updatedUser.setStatus(1);
                updatedUser.setImagePath(userRequest.getImagePath());
                updatedUser.setGender(userRequest.getGender());
                updatedUser.setGenderIntrest(userRequest.getGenderIntrest());
                updatedUser.setSubscriptionType(userRequest.getSubscriptionType());
                updatedUser.setDateOfBirth(userRequest.getDateOfBirth());
                updatedUser.setRole(userRequest.getRole());
//                encoding of password

//                String password = userRequest.getPassword();
//                BCryptPasswordEncoder encoder = this.bCryptPasswordEncoder();
//                String encodedPassword = encoder.encode(password);
//                updatedUser.setPassword(encodedPassword);

                // Save the updated category
                User payload = this.userRepository.save(updatedUser);

                return ResponseEntity.ok(new ApiResponse<>("success", "Data updated successfully", payload, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "userId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while updating data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> getUsers(SearchPaginationRequest searchParams) {

        try {
            Long id = searchParams.getId();
            String fullName = searchParams.getFullName();
            int gender = searchParams.getGender();
            Integer perPageRecord = searchParams.getPer_page_record();

            // Set the default value of page to 1
            Integer page = (searchParams.getPage() != null) ? searchParams.getPage() : 1;

            Page<User> userPage;


            // Get the current user's data from jwt token
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserEmail = authentication.getName();
            User currentUserProfile = userRepository.findByEmailAddress(currentUserEmail);

            if (id != null) {
                Optional<User> userOptional = userRepository.findById(id);
                if (userOptional.isPresent()) {
                    User user1 = userOptional.get();
                    userPage = new PageImpl<>(Collections.singletonList(user1));
                } else {
                    userPage = Page.empty(); // No matching category found
                }
            }
            else if (fullName != null) {
                userPage = userRepository.findByFullNameContaining(fullName, PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else if (gender != 0 ) {
                userPage = userRepository.findByGenderAndIdNot(gender,currentUserProfile.getId(), PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else {
                userPage = userRepository.findByIdNot(currentUserProfile.getId(), PageRequest.of(page - 1, perPageRecord,Sort.by(Sort.Order.desc("id"))));
            }

            List<User> userEntities = userPage.getContent();

            //  Below code is when we are making any join to two tables. This will send user data along with its friend request data
            List<UserFriendRequestResponse> responseList = new ArrayList<>();

            System.out.println("login profile--------------------" + currentUserProfile.getId());
            for (User parent : userEntities) {
                FriendRequest children = friendRequestRepository.findBySenderIdORReceiverId(parent.getId(), currentUserProfile.getId());

//      if friend request is already accepted than it will not show data of that corresponding user
                if (children != null && children.getStatus() ==1){
                    System.out.println("here---------->"+ children.getStatus());
                    UserFriendRequestResponse response = new UserFriendRequestResponse();
                    response.setUser(null);
                    response.setFriendRequest(null);
                    responseList.add(response);
                }else {
                    UserFriendRequestResponse response = new UserFriendRequestResponse();
                    response.setUser(parent);
                    response.setFriendRequest(children);
                    responseList.add(response);
                }

            }

            Map<String, Object> map = Map.of(
//                    "data", userEntities,
                    "data", responseList,
                    "totalElements", userPage.getTotalElements(),
                    "currentPage", page,
                    "perPageRecord", perPageRecord,
                    "totalPages", userPage.getTotalPages()
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
    public ResponseEntity<ApiResponse<User>> getUser(Long userId) {
        try {
            Optional<User> categoryEntityOptional = this.userRepository.findById(userId);

            if (categoryEntityOptional.isPresent()) {
                User categoryEntity = categoryEntityOptional.get();
                return ResponseEntity.ok(new ApiResponse<>("success", "Data retrieved successfully", categoryEntity, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "userId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while retrieving data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }


    @Override
    public ResponseEntity<ApiResponse<?>> deleteUser(Long userId) {
        try {
            Optional<User> category = this.userRepository.findById(userId);
            System.out.println("user found================" +category);
            if (category.isPresent()) {

                this.userRepository.deleteById(userId);

                // Send notification to WebSocket subscribers
                messagingTemplate.convertAndSend("/topic/user-deleted", "User deleted: " + userId);
                return ResponseEntity.ok(new ApiResponse<>("success", "Data deleted successfully", null, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "userId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while deleting data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }


    @Override
    public ResponseEntity<ApiResponse<LoginResponse>> generateToken(LoginRequest loginRequest) {
        String token = null;
        User user = null;

        log.info("request data : " + loginRequest.toString());
        try {
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            CustomUserDetails userDetails = this.userDetailsService.loadUserByUsername(loginRequest.getEmail());

//            saving FCM token
            User userFCMToken = userRepository.findByEmailAddress(loginRequest.getEmail());
            userFCMToken.setFcmToken(loginRequest.getFcmToken());
            userRepository.save(userFCMToken);

            token = jwtUtil.generateToken(userDetails);
            user = userRepository.findByEmailAddress(loginRequest.getEmail());
            Optional<Profile> profileEntityOptional = this.profileRepository.findByUserId(user.getId());
            log.info("user found ....");
            return ResponseEntity.ok(new ApiResponse<LoginResponse>("success", "Login successful",
                    new LoginResponse(token, user.getCreationDate(), profileEntityOptional, user),  200));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("exception thrown Exception handled ");
            if (e instanceof DeactivatedUserException) {
                log.error("DeactivatedUserException thrown DeactivatedUserException handled ");
                return ResponseEntity
                        .ofNullable(new ApiResponse<LoginResponse>("Failed", "token not generated", null, 444));
            }

        }
        return ResponseEntity.ofNullable(new ApiResponse<LoginResponse>("Failed", "token not generated", null, 444));

    }

}
