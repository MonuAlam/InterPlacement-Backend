package com.interplacement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.interplacement.entity.Notification;
import com.interplacement.repository.NotificationRepo;
import com.interplacement.request.NotificationRequest;
import com.interplacement.response.NotificationResponse;
import com.interplacement.util.NotificationDtoMapper;

@Service
public class NotificationService {

	@Autowired
	private NotificationRepo notificationRepo;

	public NotificationResponse createNotification(NotificationRequest notificationRequest) {

		Notification notification = toEntity(notificationRequest);

		notificationRepo.save(notification);

		return NotificationDtoMapper.toResponseDto(notification);
	}

	private static Notification toEntity(NotificationRequest notificationRequest) {
		return Notification.builder().title(notificationRequest.getTitle())
				.description(notificationRequest.getDescription()).date(notificationRequest.getDate()).build();
	}

	public NotificationResponse updateNotification(int id, NotificationRequest notificationRequest) {

		Notification notification = notificationRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Notification not found ,can't update"));

		Notification updatedNotification = updateNotificationWithBuilder(notification, notificationRequest);

		notificationRepo.save(updatedNotification);

		return NotificationDtoMapper.toResponseDto(updatedNotification);
	}

	private Notification updateNotificationWithBuilder(Notification notification,
			NotificationRequest notificationRequest) {
		return notification.toBuilder().title(notificationRequest.getTitle())
				.description(notificationRequest.getDescription()).date(notificationRequest.getDate()).build();
	}

	public List<NotificationResponse> getAllNotification() {

		return notificationRepo.findAll().stream().map(NotificationDtoMapper::toResponseDto).toList();
	}

	public NotificationResponse getByIdNotification(int id) {

		Notification notification = notificationRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Notification not found"));
		
		return NotificationDtoMapper.toResponseDto(notification);

	}

	public NotificationResponse deleteNotification(int id) {

		Notification notification = notificationRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Notification not found ,Can't delete"));

		notificationRepo.delete(notification);

		return NotificationDtoMapper.toResponseDto(notification);
	}

}
