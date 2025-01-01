package com.interplacement.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.interplacement.entity.JobApply;
import com.interplacement.enums.JobStatus;
import com.interplacement.repository.JobApplyRepo;
import com.interplacement.request.JobApplyRequest;
import com.interplacement.response.JobApplyResponse;
import com.interplacement.util.JobApplyDtoMapper;

import jakarta.annotation.PostConstruct;

@Service
public class JobApplyService {

	

    @Autowired
    private JobApplyRepo jobApplyRepo;

    @Value("${file.upload:upload/resumes/}")
    private String uploadDir;

    private final AtomicInteger COUNTER = new AtomicInteger(0);

    @PostConstruct
    private void initializeCounter() {
        JobApply lastApplication = jobApplyRepo.findTopByOrderByIdDesc();
        int lastId = 0;

        if (lastApplication != null && lastApplication.getId().startsWith("IP")) {
            lastId = Integer.parseInt(lastApplication.getId().substring(2));
        }

        COUNTER.set(lastId);
    }

    private String generateCustomId() {
        int currentId = COUNTER.incrementAndGet();
        return String.format("IP%02d", currentId);
    }

    public JobApplyResponse createJobApply(String studentId, String jobPostId,  JobApplyRequest request) throws IOException {
        JobApply jobApply = toEntity(studentId, jobPostId, request);

        if (request.getResumeBase64() != null) {
            byte[] decodedResume = Base64.getDecoder().decode(request.getResumeBase64());

            File uploadDirectory = new File(uploadDir);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }

            String fileName = System.currentTimeMillis() + "_" + request.getResumeName();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.write(filePath, decodedResume);

            jobApply.setResumePath("/resumes/" + fileName);
        }

        return JobApplyDtoMapper.toResponseDto(jobApplyRepo.save(jobApply));
    }

    private JobApply toEntity(String studentId, String jobPostId, JobApplyRequest request) {
        return JobApply.builder()
                .id(generateCustomId())
                .studentId(studentId)
                .jobPostId(jobPostId)
                .userName(request.getUserName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .experience(request.getExperience())
                .qualification(request.getQualification())
                .cgpa(request.getCgpa())
                .address(request.getAddress())
                .applyDate(LocalDate.now())
                .resumePath(request.getResumePath())
                .status(JobStatus.OPEN)
                .build();
    }

    public List<JobApplyResponse> getAllApplications() {
       
    	return jobApplyRepo.findAll().stream().map(JobApplyDtoMapper::toResponseDto).toList();
    }

    public JobApplyResponse getApplicationById(String id) {
        JobApply jobApply = jobApplyRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        return JobApplyDtoMapper.toResponseDto(jobApply);
    }

    public JobApplyResponse updateApplication(String id, JobApplyRequest request) throws IOException {
        JobApply jobApply = jobApplyRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if (request.getResumeBase64() != null) {
            if (jobApply.getResumePath() != null) {
                Path oldResumePath = Paths.get(uploadDir, jobApply.getResumePath().replaceFirst("/resumes/", ""));
                File oldResumeFile = oldResumePath.toFile();

                if (oldResumeFile.exists()) {
                    oldResumeFile.delete();
                }

                byte[] decodedResume = Base64.getDecoder().decode(request.getResumeBase64());
                File uploadDirectory = new File(uploadDir);
                if (!uploadDirectory.exists()) {
                    uploadDirectory.mkdirs();
                }

                String fileName = System.currentTimeMillis() + "_" + request.getResumeName();
                Path filePath = Paths.get(uploadDir, fileName);
                Files.write(filePath, decodedResume);

                jobApply.setResumePath("/resumes/" + fileName);
            }
        }

        jobApply = updateWithBuilder(jobApply, request);
        
        return JobApplyDtoMapper.toResponseDto(jobApplyRepo.save(jobApply));
    }

    private JobApply updateWithBuilder(JobApply jobApply, JobApplyRequest request) {
        return jobApply.toBuilder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .experience(request.getExperience())
                .qualification(request.getQualification())
                .cgpa(request.getCgpa())
                .address(request.getAddress())
                .build();
    }

    public JobApplyResponse deleteApplication(String id) {
        JobApply jobApply = jobApplyRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
       
        jobApplyRepo.delete(jobApply);
        
        return JobApplyDtoMapper.toResponseDto(jobApply);
    }
    
    public JobApplyResponse updateStatus(String id, String status) {
        JobApply jobApply = jobApplyRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        JobStatus newStatus;
        try {
            newStatus = JobStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Invalid status value. Allowed values are: " + List.of(JobStatus.values()));
        }

        jobApply.setStatus(newStatus);

        return JobApplyDtoMapper.toResponseDto(jobApplyRepo.save(jobApply));
    }


	
}
