package com.interplacement.util;

import com.interplacement.entity.JobApply;
import com.interplacement.response.JobApplyResponse;

public class JobApplyDtoMapper {
	
	public static JobApplyResponse toResponseDto(JobApply jobApply) {
		
		return JobApplyResponse.builder()
				.id(jobApply.getId())
				.studentId(jobApply.getStudentId())
				.jobPostId(jobApply.getJobPostId())
				.userName(jobApply.getUserName())
				.email(jobApply.getEmail())
				.phoneNumber(jobApply.getPhoneNumber())
				.experience(jobApply.getExperience())
				.qualification(jobApply.getQualification())
				.cgpa(jobApply.getCgpa())
				.address(jobApply.getAddress())
				.applyDate(jobApply.getApplyDate())
				.resumePath(jobApply.getResumePath())
				.status(jobApply.getStatus())
				.build();
				
	}

}
