package com.interplacement.util;


import com.interplacement.entity.JobPost;
import com.interplacement.response.JobPostResponse;

public class JobPostDtoMapper {
	
	public static JobPostResponse toResponseDto(JobPost jobPost) {
		
		return JobPostResponse.builder()
				.id(jobPost.getId())
				.companyId(jobPost.getCompanyId())
				.compName(jobPost.getCompName())
				.postedBy(jobPost.getPostedBy())
				.jobProfileName(jobPost.getJobProfileName())
				.compEmail(jobPost.getCompEmail())
				.numberOfOpening(jobPost.getNumberOfOpening())
				.experience(jobPost.getExperience())
				.skils(jobPost.getSkils())
				.qualification(jobPost.getQualification())
				.cgpa(jobPost.getCgpa())
				.salary(jobPost.getSalary())
				.phone(jobPost.getPhone())
				.address(jobPost.getAddress())
				.interviewRounds(jobPost.getInterviewRounds())
				.jobType(jobPost.getJobType())
				.shift(jobPost.getShift())
				.jobdescription(jobPost.getJobdescription())
				.dealLineFrom(jobPost.getDealLineFrom())
				.dealLineTo(jobPost.getDealLineTo())
				.postedDate(jobPost.getPostedDate())
				.compProfileImagePath(jobPost.getCompProfileImagePath())
				.status(jobPost.getStatus())
				.build();
						
	}

}
