//package com.matrimony.webSockets;
//
//public class NotificationService {
//    const socket = new SockJS('/ws');
//const stompClient = Stomp.over(socket);
//
//stompClient.connect({}, function (frame) {
//        stompClient.subscribe('/topic/user-deleted', function (message) {
//            // Handle the notification here
//            console.log('User deleted:', message.body);
//        });
//    });
//}
