package com.interplacement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	public NotificationResponse createNotification(@Valid @RequestBody NotificationRequest request) {

		return notificationService.createNotification(request);

	}

	@PutMapping("/{id}")
	public NotificationResponse updateNotification(@PathVariable String id,
			@Valid @RequestBody NotificationRequest request) {
		return notificationService.updateNotification(id, request);

	}

	@GetMapping
	public List<NotificationResponse> getAllNotifications() {
		return notificationService.getAllNotifications();
	}

	@GetMapping("/{id}")
	public NotificationResponse getNotificationById(@PathVariable String id) {
		return notificationService.getByIdNotification(id);

	}

	@DeleteMapping("/{id}")
	public NotificationResponse deleteNotification(@PathVariable String id) {
		return notificationService.deleteNotification(id);

	}

	@PatchMapping("/{id}/status")
	public NotificationResponse updateStatus(@PathVariable String id, @RequestParam String status) {
		return notificationService.updateStatus(id, status);

	}

	@GetMapping("/active")
	public List<NotificationResponse> getAllActiveNotifications() {
		return notificationService.getAllActiveNotifications();

	}

	@PostMapping("/upload")
	public List<NotificationResponse> uploadNotifications(@RequestParam("file") MultipartFile file) throws Exception {

		return notificationService.saveNotificationsFromExcel(file);

	}
	
	
}
