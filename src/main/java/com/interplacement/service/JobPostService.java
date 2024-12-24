package com.interplacement.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.interplacement.entity.JobPost;
import com.interplacement.enums.ProfileStatus;
import com.interplacement.repository.CompanyRepo;
import com.interplacement.repository.JobPostRepo;
import com.interplacement.request.JobPostRequest;
import com.interplacement.response.JobPostResponse;
import com.interplacement.util.JobPostDtoMapper;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

@Service
public class JobPostService {

	@Autowired
	private JobPostRepo jobPostRepo;

	@Autowired
	private CompanyRepo companyRepo;

	@Value("${file.upload:upload/images/}")
	private String uploadDir;

	private final AtomicInteger COUNTER = new AtomicInteger(0);

	@PostConstruct
	private void initializeCounter() {

		JobPost lastJobPost = jobPostRepo.findTopByOrderByIdDesc();

		int lastId = 0;

		if (lastJobPost != null && lastJobPost.getId().startsWith("IP")) {

			lastId = Integer.parseInt(lastJobPost.getId().substring(2));
		}

		COUNTER.set(lastId);
	}

	private String generateCustomId() {

		int currentId = COUNTER.incrementAndGet();

		return String.format("IP%02d", currentId);
	}

	public JobPostResponse createJobPost(String companyId, @Valid JobPostRequest request) throws IOException {

		companyRepo.findById(companyId).orElseThrow(() -> new RuntimeException("Company not found"));

		JobPost jobPost = toEntity(companyId, request);

		if (request.getProfileImageBase64() != null) {

			byte[] decodeImage = Base64.getDecoder().decode(request.getProfileImageBase64());

			File uploadDirectory = new File(uploadDir);
			if (!uploadDirectory.exists()) {
				uploadDirectory.mkdirs();
			}

			String fileName = System.currentTimeMillis() + "_" + request.getProfileImageName();

			Path filePath = Paths.get(uploadDir, fileName);

			Files.write(filePath, decodeImage);

			jobPost.setCompProfileImagePath("/images/" + fileName);
		}

		return JobPostDtoMapper.toResponseDto(jobPostRepo.save(jobPost));
	}

	private JobPost toEntity(String companyId, JobPostRequest request) {

		return JobPost.builder().id(generateCustomId()).companyId(companyId).compName(request.getCompName())
				.postedBy(request.getPostedBy()).jobProfileName(request.getJobProfileName())
				.compEmail(request.getCompEmail()).numberOfOpening(request.getNumberOfOpening())
				.experience(request.getExperience()).skils(request.getSkils()).qualification(request.getQualification())
				.cgpa(request.getCgpa()).salary(request.getSalary()).phone(request.getPhone())
				.address(request.getAddress()).interviewRounds(request.getInterviewRounds())
				.jobType(request.getJobType()).shift(request.getShift()).jobdescription(request.getJobdescription())
				.dealLineFrom(request.getDealLineFrom()).dealLineTo(request.getDealLineTo())
				.compProfileImagePath(request.getCompProfileImagePath()).status(ProfileStatus.ACTIVE).build();
	}

	public List<JobPostResponse> getAllJobPostByComp(String companyId) {

		companyRepo.findById(companyId).orElseThrow(() -> new RuntimeException("Company not found"));

		return jobPostRepo.findAll().stream().map(JobPostDtoMapper::toResponseDto).toList();
	}

	public JobPostResponse getJobPostById(String id) {

		JobPost jobPost = jobPostRepo.findById(id).orElseThrow(() -> new RuntimeException("Job Post not found"));

		return JobPostDtoMapper.toResponseDto(jobPost);
	}

	public JobPostResponse updateJobPost(String id, JobPostRequest request) throws IOException {

		JobPost jobPost = jobPostRepo.findById(id).orElseThrow(() -> new RuntimeException("Job Post Not Found"));

		if (request.getProfileImageBase64() != null) {

			if (jobPost.getCompProfileImagePath() != null) {
				Path oldImagePath = Paths.get(uploadDir,
						jobPost.getCompProfileImagePath().replaceFirst("/images/", ""));

				File oldImageFile = oldImagePath.toFile();

				if (oldImageFile.exists()) {
					oldImageFile.delete();
				}

				byte[] decodedImage = Base64.getDecoder().decode(request.getProfileImageBase64());

				File uploadDirectory = new File(uploadDir);
				if (!uploadDirectory.exists()) {

					uploadDirectory.mkdirs();
				}

				String fileName = System.currentTimeMillis() + "_" + request.getProfileImageName();

				Path filePaths = Paths.get(uploadDir, fileName);

				Files.write(filePaths, decodedImage);

				jobPost.setCompProfileImagePath("/images/" + fileName);

			}

		}
		JobPost updatedJobPost = updateWithBuilder(jobPost, request);

		return JobPostDtoMapper.toResponseDto(jobPostRepo.save(updatedJobPost));
	}

	private JobPost updateWithBuilder(JobPost jobPost, JobPostRequest request) {

		return jobPost.toBuilder().compName(request.getCompName()).postedBy(request.getPostedBy())
				.jobProfileName(request.getJobProfileName()).compEmail(request.getCompEmail())
				.numberOfOpening(request.getNumberOfOpening()).experience(request.getExperience())
				.skils(request.getSkils()).qualification(request.getQualification()).cgpa(request.getCgpa())
				.salary(request.getSalary()).phone(request.getPhone()).address(request.getAddress())
				.interviewRounds(request.getInterviewRounds()).jobType(request.getJobType()).shift(request.getShift())
				.jobdescription(request.getJobdescription()).dealLineFrom(request.getDealLineFrom())
				.dealLineTo(request.getDealLineTo()).compProfileImagePath(request.getCompProfileImagePath()).build();
	}

	public JobPostResponse updateStatus(String id, String status) {

		JobPost jobPost = jobPostRepo.findById(id).orElseThrow(() -> new RuntimeException("Job Post Not Found"));

		ProfileStatus newStatus;

		try {

			newStatus = ProfileStatus.valueOf(status.toUpperCase());

		} catch (Exception e) {
			throw new RuntimeException("Invalid Status Value");
		}

		jobPost.setStatus(newStatus);
		
		return JobPostDtoMapper.toResponseDto(jobPostRepo.save(jobPost));
	}

	public JobPostResponse deleteJobPost(String id) {

		JobPost jobPost=jobPostRepo.findById(id).orElseThrow(()->new RuntimeException("Job Post Not Found"));
		
		jobPostRepo.delete(jobPost);

		return JobPostDtoMapper.toResponseDto(jobPost);
	}
}
