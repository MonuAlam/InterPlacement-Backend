package com.interplacement.entity;

import java.time.LocalDate;

import com.interplacement.enums.NotificationStatus;
import com.interplacement.enums.ProfileStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Notification {

	@Id
	private String id;

	@Column(length = 500)
	private String title;

	@Column(length = 2000)
	private String description;

	private LocalDate date;
	
	@Enumerated(EnumType.STRING)
	private NotificationStatus status;
	
}
