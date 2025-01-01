package com.interplacement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interplacement.entity.Notification;
import com.interplacement.enums.NotificationStatus;

public interface NotificationRepo extends JpaRepository<Notification, String> {

	Notification findTopByOrderByIdDesc();

	Optional<Notification> findByStatus(NotificationStatus active);

}
