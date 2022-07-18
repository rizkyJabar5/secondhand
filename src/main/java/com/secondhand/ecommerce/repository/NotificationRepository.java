package com.secondhand.ecommerce.repository;

import com.secondhand.ecommerce.models.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("select n from Notification n where n.userId.userId=?1")
    List<Notification> findNotif(Long userId);

}