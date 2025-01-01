package com.interplacement.util;

import com.interplacement.entity.Notification;
import com.interplacement.response.NotificationResponse;

public class NotificationDtoMapper {
	
	public static NotificationResponse toResponseDto(Notification notification) {
		
	return	NotificationResponse.builder()
		.id(notification.getId())
		.title(notification.getTitle())
		.description(notification.getDescription())
		.date(notification.getDate())
		.status(notification.getStatus())
		.build();
	}

}
