package com.interplacement.response;

import java.time.LocalDate;

import com.interplacement.enums.JobType;
import com.interplacement.enums.ProfileStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JobPostResponse {

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
	private JobType jobType;
	private String shift;
	private String jobdescription;
	private LocalDate dealLineFrom;
	private LocalDate dealLineTo;
	private String compProfileImagePath;
	private ProfileStatus status;

}
