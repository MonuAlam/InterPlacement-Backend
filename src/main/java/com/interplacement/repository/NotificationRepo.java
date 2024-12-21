package com.interplacement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interplacement.entity.Notification;

public interface NotificationRepo extends JpaRepository<Notification, Integer> {

}
