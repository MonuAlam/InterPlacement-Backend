package com.interplacement.request;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class JobApplyRequest {

	private String userName;
	private String email;
	private String phoneNumber;
	private String experience;
	private String qualification;
	private Double cgpa;
	private String address;
	private String resumePath;

	private String resumeBase64;
	private String resumeName;
	
	
}
