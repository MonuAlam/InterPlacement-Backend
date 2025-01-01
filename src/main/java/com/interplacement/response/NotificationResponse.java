package com.interplacement.response;

import java.time.LocalDate;

import com.interplacement.enums.NotificationStatus;
import com.interplacement.enums.ProfileStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NotificationResponse {

	private String id;
	private String title;
	private String description;
	private LocalDate date;
	private NotificationStatus status;


}
