package com.matrimony.repository;

import com.matrimony.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    @Query(value = "SELECT * FROM friend_request_entity WHERE sender_id_user_id = :senderId", nativeQuery = true)
    List<FriendRequest> findBySenderId(@Param("senderId") long senderId);

    @Query(value = "SELECT * FROM friend_request_entity WHERE receiver_id_user_id = :receiverId AND sender_id_user_id = :userId ", nativeQuery = true)
    FriendRequest findBySenderIdReceiverId(@Param("receiverId") long receiverId, @Param("userId") long userId);

    @Query(value = "SELECT * FROM friend_request_entity WHERE receiver_id_user_id = :receiverId AND sender_id_user_id = :userId OR receiver_id_user_id = :userId AND sender_id_user_id = :receiverId", nativeQuery = true)
    FriendRequest findBySenderIdORReceiverId(@Param("receiverId") long receiverId, @Param("userId") long userId);

    @Query(value = "SELECT * FROM friend_request_entity WHERE receiver_id_user_id = :receiverId", nativeQuery = true)
    public Page<FriendRequest> findByReceiverId(@Param("receiverId") long receiverId, Pageable pageable);


    @Query(value = "SELECT * FROM friend_request_entity WHERE sender_id_user_id = :userId AND status = :status ", nativeQuery = true)
    public Page<FriendRequest> findBySenderIdStatusId( @Param("userId") long userId, @Param("status") long status,  Pageable pageable);

}

