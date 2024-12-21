package com.interplacement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interplacement.request.NotificationRequest;
import com.interplacement.response.NotificationResponse;
import com.interplacement.service.NotificationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/ip/notifi")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	@PostMapping
	public NotificationResponse createNotification(@Valid @RequestBody NotificationRequest notificationRequest) {

		return notificationService.createNotification(notificationRequest);
	}

	@PutMapping("/{notificationId}")
	public NotificationResponse updateNotification(@PathVariable String notificationId,
		@Valid	@RequestBody NotificationRequest notificationRequest) {

		return notificationService.updateNotification(Integer.parseInt(notificationId), notificationRequest);
	}

	@GetMapping
	public List<NotificationResponse> getAllNotification() {
		return notificationService.getAllNotification();
	}

	@GetMapping("/{notificationId}")
	public NotificationResponse getByIdNotification(@PathVariable String notificationId) {
		return notificationService.getByIdNotification(Integer.parseInt(notificationId));
	}
	
	@DeleteMapping("/{notificationId}")
	public NotificationResponse deleteNotification(@PathVariable String notificationId) {
		return notificationService.deleteNotification(Integer.parseInt(notificationId));
	}
	
	

}
