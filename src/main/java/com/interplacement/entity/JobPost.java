package com.interplacement.entity;

import java.time.LocalDate;

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
public class JobPost {

	@Id
	private String id;
	private String companyId;
	private String compName;
	private String postedBy;
	private String jobProfileName;
	private String compEmail;
	private Integer numberOfOpening;
	private String experience;
	private String skils;
	private String qualification;
	private Double cgpa;
	private Double salary;
	private String phone;
	private String address;
	private String interviewRounds;

	
	
	@Enumerated(EnumType.STRING)
	private JobType jobType;
	private String shift;
	private String jobdescription;
	private LocalDate dealLineFrom;
	private LocalDate dealLineTo;
	private LocalDate postedDate;
	private String compProfileImagePath;
	private ProfileStatus status;

	
	
}
