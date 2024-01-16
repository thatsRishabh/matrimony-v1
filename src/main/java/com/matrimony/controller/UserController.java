package com.matrimony.controller;

import com.matrimony.entity.User;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.request.UserRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.response.UserResponse;
import com.matrimony.service.UserService;
import com.matrimony.validator.UserValidation;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Log4j2
@Controller
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:9090")
public class UserController {


    @Autowired
    UserService userService;

    // for index page
    @RequestMapping("/index")
    public String homePage() {
        return "index";
    }


    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> createNewUser(@Valid @RequestBody UserValidation userRequest){
        return this.userService.createUser(userRequest);
    }

    //update
    @PutMapping("/register/{userId}")
    public ResponseEntity<ApiResponse<User>>  updateCategory(@PathVariable("userId") Long userId ,@RequestBody User userRequest){
        return this.userService.updateUser(userId, userRequest);
    }

    //get all category
    @PostMapping("/register/")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Object>> getUsers(@RequestBody SearchPaginationRequest searchParams){
        return this.userService.getUsers(searchParams);
    }

    // get single category
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<User>>  getUser(@PathVariable("userId") Long userId){
        return this.userService.getUser(userId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> deleteUser(@PathVariable("userId") Long userId){
        System.out.println("user found---------------->");
        return this.userService.deleteUser(userId);
    }


}
