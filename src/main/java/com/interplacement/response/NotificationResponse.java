package com.interplacement.response;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NotificationResponse {

	private int id;
	private String title;
	private String description;
	private LocalDate date;

}
