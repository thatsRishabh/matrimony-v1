package com.matrimony.service.serviceImpl;

import com.matrimony.entity.Gallery;
import com.matrimony.entity.Notification;
import com.matrimony.entity.User;
import com.matrimony.repository.GalleryRepository;
import com.matrimony.repository.NotificationRepository;
import com.matrimony.repository.UserRepository;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.NotificationService;
import com.matrimony.validator.GalleryValidation;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    UserRepository userRepository;

    //    below method will by default create the timestamp
    @PrePersist
    public void prePersist(Notification notification) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        notification.setCreatedAt(now);
        notification.setUpdatedAt(now);
    }

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public ResponseEntity<ApiResponse<Notification>> createNotification(User user, String message) {
        try {
            Notification notification= new Notification();
            notification.setUserId(user);
            notification.setMessage(message);
            Notification payload = this.notificationRepository.save(notification);
            return ResponseEntity.ok(new ApiResponse<>("success", "Data saved successfully", payload, 200));
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception here and log it
            log.error("An error occurred while saving data", e);
            return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }
    @Override
    public ResponseEntity<ApiResponse<Object>> getNotifications(SearchPaginationRequest searchParams) {

        try {
            Long id = searchParams.getId();
            Integer perPageRecord = searchParams.getPer_page_record();

            // Set the default value of page to 1
            Integer page = (searchParams.getPage() != null) ? searchParams.getPage() : 1;

            Page<Notification> galleryPage;

            // Get the current user's data from jwt token
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserEmail = authentication.getName();
            User currentUserProfile = userRepository.findByEmailAddress(currentUserEmail);

            if (id != null) {
                Optional<Notification> galleryOptional = notificationRepository.findById(id);
                if (galleryOptional.isPresent()) {
                    Notification gallery = galleryOptional.get();
                    galleryPage = new PageImpl<>(Collections.singletonList(gallery));
                } else {
                    galleryPage = Page.empty(); // No matching slider found
                }
            }
            else {

                galleryPage = notificationRepository.findByUserId(currentUserProfile.getId(),PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));

//                galleryPage = notificationRepository.findAll(PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }

            List<Notification> galleryEntities = galleryPage.getContent();


            Map<String, Object> map = Map.of(
                    "data", galleryEntities,
                    "totalElements", galleryPage.getTotalElements(),
                    "currentPage", page,
                    "perPageRecord", perPageRecord,
                    "totalPages", galleryPage.getTotalPages()
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
    public ResponseEntity<ApiResponse<Notification>>  getNotification(Long notificationId) {
        try {
            Optional<Notification> notificationEntityOptional = this.notificationRepository.findById(notificationId);

            if (notificationEntityOptional.isPresent()) {
                Notification notificationEntity = notificationEntityOptional.get();
                return ResponseEntity.ok(new ApiResponse<>("success", "Data retrieved successfully", notificationEntity, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "sliderId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while retrieving data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

}
