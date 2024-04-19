package com.matrimony.repository;

import com.matrimony.entity.FriendRequest;
import com.matrimony.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(value = "SELECT * FROM notification_entity WHERE user_id_user_id = :userId", nativeQuery = true)
    public Page<Notification> findByUserId(@Param("userId") long userId, Pageable pageable);
}
