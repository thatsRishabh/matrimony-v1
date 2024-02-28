package com.matrimony.service;

import com.matrimony.entity.Gallery;
import com.matrimony.entity.Notification;
import com.matrimony.entity.Slider;
import com.matrimony.entity.User;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.validator.GalleryValidation;
import com.matrimony.validator.SliderValidation;
import org.springframework.http.ResponseEntity;

public interface NotificationService {
//    public ResponseEntity<ApiResponse<Notification>> createNotification(Notification notificationRequest);

    public ResponseEntity<ApiResponse<Notification>> createNotification(User user, String message);

    public ResponseEntity<ApiResponse<Object>> getNotifications(SearchPaginationRequest searchParams);

    public ResponseEntity<ApiResponse<Notification>>  getNotification(Long notificationId);
}
