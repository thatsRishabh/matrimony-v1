package com.matrimony.controller;


import com.matrimony.entity.Gallery;
import com.matrimony.entity.Notification;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.GalleryService;
import com.matrimony.service.NotificationService;
import com.matrimony.validator.GalleryValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationsController {
    @Autowired
    private NotificationService notificationService;

//    //add menu
//    @PostMapping()
//    public ResponseEntity<ApiResponse<Gallery>> addGallery(@Valid @RequestBody GalleryValidation galleryRequest){
//        return this.galleryService.createGallery(galleryRequest);
//    }

    //get all category
    @PostMapping("/")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Object>> getNotifications(@RequestBody SearchPaginationRequest searchParams){
        return this.notificationService.getNotifications(searchParams);
    }

    // get single category
    @GetMapping("/{notificationId}")
    public ResponseEntity<ApiResponse<Notification>> getNotification(@PathVariable("notificationId") Long notificationId){
        return this.notificationService.getNotification(notificationId);
    }
}
