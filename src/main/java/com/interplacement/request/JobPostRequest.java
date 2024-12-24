package com.interplacement.request;

import java.time.LocalDate;
import com.interplacement.enums.JobType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JobPostRequest {

	@NotBlank
	private String compName;
	@NotBlank
	private String postedBy;
	@NotBlank
	private String jobProfileName;
	@NotBlank
	private String compEmail;
	@NotNull
	private Integer numberOfOpening;
	@NotBlank
	private String experience;
	@NotBlank
	private String skils;
	@NotBlank
	private String qualification;
	@NotNull
	private Double cgpa;
	@NotNull
	private Double salary;
	@NotBlank
	private String phone;
	@NotBlank
	private String address;
	@NotBlank
	private String interviewRounds;
	@NotNull
	private JobType jobType;
	@NotBlank
	private String shift;
	@NotBlank
	private String jobdescription;
	@PastOrPresent
	private LocalDate dealLineFrom;
	@FutureOrPresent
	private LocalDate dealLineTo;

	@NotBlank
	private String compProfileImagePath;
	private String profileImageBase64;
	private String profileImageName;

}
