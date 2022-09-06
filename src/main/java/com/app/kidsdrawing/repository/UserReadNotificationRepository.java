package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.UserReadNotification;
@Repository
public interface UserReadNotificationRepository extends JpaRepository <UserReadNotification, Long>{
    boolean existsByUserId(Long id);
    boolean existsByNotificationId(Long id);
    Optional<UserReadNotification> findByNotificationId(Long id);
    List<UserReadNotification> findByUserId(Long id);
    void deleteById(Long id);
}