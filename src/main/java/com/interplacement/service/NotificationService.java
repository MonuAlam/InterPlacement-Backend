package com.interplacement.service;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.interplacement.entity.Notification;
import com.interplacement.enums.NotificationStatus;
import com.interplacement.repository.NotificationRepo;
import com.interplacement.request.NotificationRequest;
import com.interplacement.response.NotificationResponse;
import com.interplacement.util.NotificationDtoMapper;

import jakarta.annotation.PostConstruct;

@Service
public class NotificationService {

	@Autowired
	private NotificationRepo notificationRepo;

	private final AtomicInteger COUNTER = new AtomicInteger(0);

	@PostConstruct
	private void initializeCounter() {
		Notification lastNotification = notificationRepo.findTopByOrderByIdDesc();

		int lastId = 0;

		if (lastNotification != null && lastNotification.getId().startsWith("IP")) {
			lastId = Integer.parseInt(lastNotification.getId().substring(3));
		}
		COUNTER.set(lastId);
	}

	public NotificationResponse createNotification(NotificationRequest notificationRequest) {
		Notification notification = toEntity(notificationRequest);

		notificationRepo.save(notification);

		return NotificationDtoMapper.toResponseDto(notification);
	}

	private Notification toEntity(NotificationRequest notificationRequest) {
		return Notification.builder().id(generateCustomId()).title(notificationRequest.getTitle())
				.description(notificationRequest.getDescription()).date(LocalDate.now())
				.status(NotificationStatus.ACTIVE).build();
	}

	private String generateCustomId() {
		int currentId = COUNTER.incrementAndGet();
		return String.format("IP%02d", currentId);
	}

	public NotificationResponse updateNotification(String id, NotificationRequest notificationRequest) {
		Notification notification = notificationRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Notification not found, can't update"));

		Notification updatedNotification = updateNotificationWithBuilder(notification, notificationRequest);

		notificationRepo.save(updatedNotification);

		return NotificationDtoMapper.toResponseDto(updatedNotification);
	}

	private Notification updateNotificationWithBuilder(Notification notification,
			NotificationRequest notificationRequest) {
		return notification.toBuilder().title(notificationRequest.getTitle())
				.description(notificationRequest.getDescription()).build();
	}

	public List<NotificationResponse> getAllNotifications() {
		return notificationRepo.findAll().stream().map(NotificationDtoMapper::toResponseDto).toList();
	}

	public NotificationResponse getByIdNotification(String id) {
		Notification notification = notificationRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Notification not found"));

		return NotificationDtoMapper.toResponseDto(notification);
	}

	public NotificationResponse deleteNotification(String id) {
		Notification notification = notificationRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Notification not found, can't delete"));

		notificationRepo.delete(notification);

		return NotificationDtoMapper.toResponseDto(notification);
	}

	public NotificationResponse updateStatus(String id, String status) {
		Notification notification = notificationRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Notification not found"));

		NotificationStatus newStatus;

		try {
			newStatus = NotificationStatus.valueOf(status.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Invalid status value");
		}

		notification.setStatus(newStatus);
		notificationRepo.save(notification);

		return NotificationDtoMapper.toResponseDto(notification);
	}

	public List<NotificationResponse> getAllActiveNotifications() {
		return notificationRepo.findByStatus(NotificationStatus.ACTIVE).stream()
				.map(NotificationDtoMapper::toResponseDto).toList();
	}

	public List<NotificationResponse> saveNotificationsFromExcel(MultipartFile file) throws Exception {

		InputStream inputStream = file.getInputStream();
		Workbook workbook = WorkbookFactory.create(inputStream);
		Sheet sheet = workbook.getSheetAt(0);
		List<Notification> notifications = new ArrayList<>();

		for (Row row : sheet) {
			if (row.getRowNum() == 0)
				continue;

			String title = getCellValueAsString(row.getCell(0));
			String description = getCellValueAsString(row.getCell(1));

			if (title.isEmpty() && description.isEmpty()) {
				continue;
			}

			LocalDate date = LocalDate.now();

			Notification notification = Notification.builder().id(generateCustomId()).title(title)
					.description(description).date(date)

					.status(NotificationStatus.ACTIVE)

					.build();
			notifications.add(notification);
		}

		List<Notification> savedEntities = notificationRepo.saveAll(notifications);

		workbook.close();

		inputStream.close();

		return savedEntities.stream().map(NotificationDtoMapper::toResponseDto).toList();
	}

	private String getCellValueAsString(Cell cell) {
		if (cell != null) {
			return cell.getStringCellValue().trim();
		}
		return "";
	}

}
