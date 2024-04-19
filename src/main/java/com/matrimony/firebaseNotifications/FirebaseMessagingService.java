package com.matrimony.firebaseNotifications;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.matrimony.entity.User;
import com.matrimony.repository.NotificationRepository;
import com.matrimony.repository.UserRepository;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.NotificationService;
import com.matrimony.validator.FriendRequestValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FirebaseMessagingService {

    @Autowired
    FirebaseMessaging firebaseMessaging;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    private NotificationService notificationService;

    public String sendNotificationByToken(NotificationMessage notificationMessage){

        Notification notification= Notification
                .builder()
                .setTitle(notificationMessage.getTitle())
                .setBody(notificationMessage.getBody())
                .setImage(notificationMessage.getImage())
                .build();

        Message message =Message
                .builder()
                .setToken(notificationMessage.getRecipientToken())
                .setNotification(notification)
                .putAllData(notificationMessage.getData())
                .build();


      try {
        firebaseMessaging.send(message);
        return "Success Sending Notification";
      }catch (FirebaseMessagingException e){
          e.printStackTrace();
          return "error sending notification";
      }
    }

    public void sendFriendRequestNotification(FriendRequestValidation friendRequestRequest){

        Optional<User> friendRequestReceiver = userRepository.findById(Long.valueOf(friendRequestRequest.getReceiver_id().getId()));

        System.out.println("------------------------fcm"+ friendRequestReceiver.get().getFcmToken());

        if (friendRequestReceiver.get().getFcmToken() != null) {
            Optional<User> friendRequestSender = userRepository.findById(Long.valueOf(friendRequestRequest.getSender_id().getId()));

            Notification notification= Notification
                    .builder()
                    .setTitle("You have a friend Request from " +friendRequestSender.get().getFullName())
                    .build();

            System.out.println("------------------------friendRequestRequest.getReceiver_id()"+ friendRequestRequest.getReceiver_id());
            System.out.println("------------------------name   "+ friendRequestSender.get().getFullName());
//            saving the notification in the database
            notificationService.createNotification(friendRequestRequest.getReceiver_id(),"You have a friend Request from " +friendRequestSender.get().getFullName());

            Message message =Message
                    .builder()
                    .setToken(friendRequestReceiver.get().getFcmToken())
                    .setNotification(notification)
//                .putAllData(notificationMessage.getData())
                    .build();


            try {
                firebaseMessaging.send(message);
                System.out.println("Success Sending Notification");
//            return "Success Sending Notification";
            }catch (FirebaseMessagingException e){
                e.printStackTrace();
                System.out.println("error sending notification");
//            return "error sending notification";
            }

        }

    }
}
