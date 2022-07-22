package com.secondhand.ecommerce.repository;

import com.secondhand.ecommerce.models.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("select n from Notification n where n.appUser.userId=?1 order by n.createdDate desc")
    List<Notification> findNotificationUser(Long userId);

    @Query("select n from Notification n where n.product.id = ?1")
    Notification findNotificationByProductId(Long productId);

}