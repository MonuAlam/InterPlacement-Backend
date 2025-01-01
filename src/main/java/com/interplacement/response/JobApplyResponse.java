package com.interplacement.response;

import java.time.LocalDate;

import com.interplacement.enums.JobStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class JobApplyResponse {

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
	
	private JobStatus status;

}
