package com.interplacement.entity;

import java.time.LocalDate;

import com.interplacement.enums.JobStatus;
import com.interplacement.enums.JobType;
import com.interplacement.enums.ProfileStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class JobApply {

	@Id
	private String id;
	private String studentId;
	private String jobPostId;
	private String userName;
	private String email;
	private String phoneNumber;
	private String experience;
	private String qualification;
	private Double cgpa;
	private String address;
	private LocalDate applyDate;
	private String resumePath;
	
	@Enumerated(EnumType.STRING)
	private JobStatus status;

}
