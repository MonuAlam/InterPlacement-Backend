package com.interplacement.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.interplacement.common.GenerateRandomPassword;
import com.interplacement.entity.Admin;
import com.interplacement.entity.Student;
import com.interplacement.enums.ProfileStatus;
import com.interplacement.enums.Role;
import com.interplacement.repository.AdminRepo;
import com.interplacement.repository.StudentRepo;
import com.interplacement.request.StudentRequest;
import com.interplacement.response.StudentResponse;
import com.interplacement.util.StudentDtoMapper;
import jakarta.annotation.PostConstruct;

@Service
public class StudentService {

	@Autowired
	private StudentRepo studentRepo;

	@Autowired
	private AdminRepo adminRepo;

	@Autowired
	private EmailService emailService;

	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	private final AtomicInteger COUNTER = new AtomicInteger(0);

	@Value("${file.upload-dir:uploads/images/}")
	private String uploadDir;

	@PostConstruct
	private void initalizeCounter() {

		Student lastStudent = studentRepo.findTopByOrderByIdDesc();

		int lastId = 0;
		if (lastStudent != null && lastStudent.getId().startsWith("IP")) {
			lastId = Integer.parseInt(lastStudent.getId().substring(2));
		}
		COUNTER.set(lastId);
	}

	public StudentResponse createStudent(String collegeId, StudentRequest request) throws IOException {

		Admin admin = adminRepo.findById(collegeId)
				.orElseThrow(() -> new RuntimeException("College not found for collegeId" + collegeId));

		if (admin.getStatus() != ProfileStatus.ACTIVE) {
			throw new RuntimeException("College InActive");
		}

		Optional<Student> existStudent = studentRepo.findByEmail(request.getEmail());

		if (existStudent.isPresent()) {
			throw new RuntimeException("Student Already Exist can't created");
		}

	    String randomPassword =GenerateRandomPassword.generateRandomPassword(8);

		
		Student student = toEntity(collegeId, request,randomPassword);

		if (request.getProfileImageBase64() != null) {

			byte[] decodedImage = Base64.getDecoder().decode(request.getProfileImageBase64());

			File uploadDirectory = new File(uploadDir);
			if (!uploadDirectory.exists()) {
				uploadDirectory.mkdirs();
			}

			String fileName = System.currentTimeMillis() + "_" + request.getProfileImageName();

			Path filePath = Paths.get(uploadDir, fileName);

			Files.write(filePath, decodedImage);

			student.setProfileImagePath("/images/" + fileName);
		}

	    Student savedStudent = studentRepo.save(student);

		
	    emailService.sendStudentPasswordEmail(savedStudent.getEmail(), savedStudent.getName(), randomPassword);

		
		return StudentDtoMapper.toResponseDto(savedStudent);
	}

	private Student toEntity(String collegeId, StudentRequest request,String randomPassword) {

		return Student.builder().id(genrateCustomId()).collegeId(collegeId).name(request.getName())
				.email(request.getEmail())
				.password(bCryptPasswordEncoder.encode(randomPassword))
				.persuingYear(request.getPersuingYear()).geneder(request.getGeneder())
				.rollNumber(request.getRollNumber()).phone(request.getPhone()).course(request.getCourse())
				.branch(request.getBranch()).year(request.getYear()).cgpa(request.getCgpa()).dob(request.getDob())
				.address(request.getAddress()).status(ProfileStatus.ACTIVE).role(Role.STUDENT).build();
	}

	public StudentResponse registerStudent(StudentRequest request) throws IOException {

		if (request.getCollegeId() == null) {
			throw new RuntimeException("collegeId required");
		}

		Admin admin = adminRepo.findById(request.getCollegeId())
				.orElseThrow(() -> new RuntimeException("College not found for collegeId" + request.getCollegeId()));
		if (admin.getStatus() != ProfileStatus.ACTIVE) {
			throw new RuntimeException("College InActive");
		}

		Optional<Student> existStudent = studentRepo.findByEmail(request.getEmail());

		if (existStudent.isPresent()) {
			throw new RuntimeException("Student Already Exist can't created");
		}

		Student student = toEntityReg(request);

		if (request.getProfileImageBase64() != null) {

			byte[] decodedImage = Base64.getDecoder().decode(request.getProfileImageBase64());

			File uploadDirectory = new File(uploadDir);
			if (!uploadDirectory.exists()) {
				uploadDirectory.mkdirs();
			}

			String fileName = System.currentTimeMillis() + "_" + request.getProfileImageName();

			Path filePath = Paths.get(uploadDir, fileName);

			Files.write(filePath, decodedImage);

			student.setProfileImagePath("/images/" + fileName);
		}

		Student savedStudent = studentRepo.save(student);

		emailService.sendWelcomeEmail(savedStudent.getEmail(), savedStudent.getName());

		return StudentDtoMapper.toResponseDto(savedStudent);
	}

	private Student toEntityReg(StudentRequest request) {

		return Student.builder().id(genrateCustomId()).collegeId(request.getCollegeId()).name(request.getName())
				.email(request.getEmail()).password(bCryptPasswordEncoder.encode(request.getPassword()))
				.persuingYear(request.getPersuingYear()).geneder(request.getGeneder())
				.rollNumber(request.getRollNumber()).phone(request.getPhone()).course(request.getCourse())
				.branch(request.getBranch()).year(request.getYear()).cgpa(request.getCgpa()).dob(request.getDob())
				.address(request.getAddress()).status(ProfileStatus.INACTIVE).role(Role.STUDENT).build();
	}

	private String genrateCustomId() {

		int currentId = COUNTER.incrementAndGet();

		return String.format("IP%02d", currentId);

	}

	public List<StudentResponse> getAllStudent() {

		return studentRepo.findAll().stream().map(StudentDtoMapper::toResponseDto).toList();
	}

	public StudentResponse getStudentById(String id) {

		Student student = studentRepo.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));

		return StudentDtoMapper.toResponseDto(student);
	}

	public StudentResponse deleteStudent(String id) {

		Student student = studentRepo.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));

		studentRepo.delete(student);

		return StudentDtoMapper.toResponseDto(student);
	}

	public StudentResponse updateStatus(String id, String status) {

		Student student = studentRepo.findById(id).orElseThrow(() -> new RuntimeException("Student Not found"));

		ProfileStatus newStatus;
		try {
			newStatus = ProfileStatus.valueOf(status.toUpperCase());
		} catch (Exception e) {
			throw new RuntimeException("Invalid Value");
		}
		student.setStatus(newStatus);

		studentRepo.save(student);

		return StudentDtoMapper.toResponseDto(student);
	}

	public StudentResponse updateStudent(String id, StudentRequest request) throws IOException {

		Student student = studentRepo.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));

		if (request.getProfileImageBase64() != null) {

			if (student.getProfileImagePath() != null) {

				Path oldImagePath = Paths.get(uploadDir, student.getProfileImagePath().replaceFirst("/images/", ""));

				File oldImageFile = oldImagePath.toFile();

				if (oldImageFile.exists()) {
					oldImageFile.delete();
				}
			}

			byte[] decodeImage = Base64.getDecoder().decode(request.getProfileImageBase64());

			File uploadDirectory = new File(uploadDir);

			if (!uploadDirectory.exists()) {
				uploadDirectory.mkdirs();
			}

			String fileName = System.currentTimeMillis() + "_" + request.getProfileImageName();

			Path filePath = Paths.get(uploadDir, fileName);

			Files.write(filePath, decodeImage);

			student.setProfileImagePath("/images/" + fileName);

		}
		Student updatedStudent = updateWithBuilder(student, request);

		return StudentDtoMapper.toResponseDto(studentRepo.save(updatedStudent));
	}

	private Student updateWithBuilder(Student student, StudentRequest request) {

		return student.toBuilder().name(request.getName()).email(request.getEmail())
				.password(bCryptPasswordEncoder.encode(request.getPassword())).persuingYear(request.getPersuingYear())
				.geneder(request.getGeneder()).phone(request.getPhone()).rollNumber(request.getRollNumber())
				.course(request.getCourse()).branch(request.getBranch()).year(request.getYear()).cgpa(request.getCgpa())
				.dob(request.getDob()).address(request.getAddress()).build();
	}
}
