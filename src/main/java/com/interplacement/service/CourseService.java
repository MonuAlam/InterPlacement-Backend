package com.interplacement.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.interplacement.entity.Courses;
import com.interplacement.enums.ProfileStatus;
import com.interplacement.entity.Branches;
import com.interplacement.repository.CoursesRepo;
import com.interplacement.repository.BranchesRepo;
import com.interplacement.request.CoursesRequest;
import com.interplacement.response.CoursesResponse;
import com.interplacement.util.CoursesDtoMapper;

import jakarta.annotation.PostConstruct;

@Service
public class CourseService {

	@Autowired
	private CoursesRepo coursesRepository;

	@Autowired
	private BranchesRepo branchesRepository;

	private final AtomicInteger COURSE_COUNTER = new AtomicInteger(0);
	private final AtomicInteger BRANCH_COUNTER = new AtomicInteger(0);

	@PostConstruct
	private void initializeCounter() {
		Courses lastCourse = coursesRepository.findTopByOrderByIdDesc();

		int lastCourseId = 0;
		if (lastCourse != null && lastCourse.getId().startsWith("IP")) {
			lastCourseId = Integer.parseInt(lastCourse.getId().substring(2));
		}
		COURSE_COUNTER.set(lastCourseId);

		Branches lastBranch = branchesRepository.findTopByOrderByIdDesc();
		int lastBranchId = 0;
		if (lastBranch != null && lastBranch.getId().startsWith("IP")) {
			lastBranchId = Integer.parseInt(lastBranch.getId().substring(2));
		}
		BRANCH_COUNTER.set(lastBranchId);
	}

	public CoursesResponse createCourse(CoursesRequest request) {
		boolean exists = coursesRepository.existsByName(request.getName());
		if (exists) {
			throw new IllegalArgumentException("A course with the same name already exists: " + request.getName());
		}

		Courses course = toEntity(request);

		List<Branches> branches = course.getBranches();

		if (branches != null) {
			branches.forEach(branch -> {
				branch.setId(generateBranchId());
				branch.setCourse(course);

			});
		}

		Courses savedCourse = coursesRepository.save(course);

		return CoursesDtoMapper.toResponseDto(savedCourse);
	}

	private Courses toEntity(CoursesRequest request) {
		return Courses.builder()
				.id(generateCourseId())
				.name(request.getName())
				.branches(request.getBranches())
				.status(ProfileStatus.ACTIVE)
				.build();
	}

	public List<CoursesResponse> getAllCourses() {
		List<Courses> courses = coursesRepository.findAll();
		return courses.stream().map(CoursesDtoMapper::toResponseDto).toList();
	}

	public CoursesResponse getCourseById(String id) {

		Courses course = coursesRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
		return CoursesDtoMapper.toResponseDto(course);
	}

	public CoursesResponse updateCourse(String id, CoursesRequest request) {
		Courses courseOpt = coursesRepository.findById(id).orElseThrow(() -> new RuntimeException("cource not found"));

		Courses updatedCourse = updateWithBuilderS(courseOpt, request);

		coursesRepository.save(updatedCourse);
		
		return CoursesDtoMapper.toResponseDto(updatedCourse);
	}

	private Courses updateWithBuilderS(Courses courses, CoursesRequest request) {

		return courses.toBuilder().name(request.getName()).build();
	}

	public CoursesResponse deleteCourse(String id) {
		Courses course = coursesRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
		coursesRepository.delete(course);
		return CoursesDtoMapper.toResponseDto(course);
	}
	
	public CoursesResponse updateStatus(String id, String status) {

		Courses courses = coursesRepository.findById(id).orElseThrow(() -> new RuntimeException("Company Not found"));

		ProfileStatus newStatus;
		try {
			newStatus = ProfileStatus.valueOf(status.toUpperCase());
		} catch (Exception e) {

			throw new RuntimeException("Invalid Status Value");
		}

		courses.setStatus(newStatus);

		coursesRepository.save(courses);

		return CoursesDtoMapper.toResponseDto(courses);

	}
	

	private String generateCourseId() {
		int currentId = COURSE_COUNTER.incrementAndGet();
		return String.format("IP%02d", currentId);
	}

	private String generateBranchId() {
		int currentBranchId = BRANCH_COUNTER.incrementAndGet();
		return String.format("IP%02d", currentBranchId);
	}
}
