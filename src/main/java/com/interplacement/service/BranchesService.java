package com.interplacement.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.interplacement.entity.Branches;
import com.interplacement.entity.Courses;
import com.interplacement.enums.ProfileStatus;
import com.interplacement.repository.BranchesRepo;
import com.interplacement.repository.CoursesRepo;
import com.interplacement.request.BranchesRequest;
import com.interplacement.response.BranchesResponse;
import com.interplacement.util.BranchesDtoMapper;
import jakarta.annotation.PostConstruct;

@Service
public class BranchesService {

	@Autowired
	private BranchesRepo branchesRepo;

	@Autowired
	private CoursesRepo coursesRepo;

	private final AtomicInteger BRANCH_COUNTER = new AtomicInteger(0);

	@PostConstruct
	private void initializeCounter() {

		Branches lastBranch = branchesRepo.findTopByOrderByIdDesc();
		int lastBranchId = 0;
		if (lastBranch != null && lastBranch.getId().startsWith("IP")) {
			lastBranchId = Integer.parseInt(lastBranch.getId().substring(2));
		}
		BRANCH_COUNTER.set(lastBranchId);
	}

	public BranchesResponse addBranchToCourse(String courseId, BranchesRequest request) {

		Courses course = coursesRepo.findById(courseId)
				.orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));

		Branches branch = toEntity(course, request);

		Branches savedBranch = branchesRepo.save(branch);

		course.getBranches().add(savedBranch);

		coursesRepo.save(course);

		return BranchesDtoMapper.toResponseDto(savedBranch);
	}

	private Branches toEntity(Courses courses, BranchesRequest request) {

		return Branches.builder()
				.id(generateBranchId())
				.name(request.getName())
				.course(courses)
				.status(ProfileStatus.ACTIVE)
				.build();
	}

	public BranchesResponse getBranchById(String branchId) {
		Branches branch = branchesRepo.findById(branchId)
				.orElseThrow(() -> new RuntimeException("Branch not found with ID: " + branchId));
		return BranchesDtoMapper.toResponseDto(branch);
	}

	public List<BranchesResponse> getAllBranches() {
		List<Branches> branches = branchesRepo.findAll();

		return branches.stream().map(BranchesDtoMapper::toResponseDto).toList();
	}

	public BranchesResponse updateBranch(String branchId, BranchesRequest request) {
		Branches branch = branchesRepo.findById(branchId)
				.orElseThrow(() -> new RuntimeException("Branch not found with ID: " + branchId));

		Branches updatedBranch = updateWithBuilder(branch, request);

		branchesRepo.save(updatedBranch);

		return BranchesDtoMapper.toResponseDto(updatedBranch);
	}

	private Branches updateWithBuilder(Branches branches, BranchesRequest request) {

		return branches.toBuilder().name(request.getName()).build();
	}

	public BranchesResponse deleteBranch(String branchId) {
		Branches branch = branchesRepo.findById(branchId)
				.orElseThrow(() -> new RuntimeException("Branch not found with ID: " + branchId));

		Courses course = branch.getCourse();
		if (course != null) {
			course.getBranches().remove(branch);
			coursesRepo.save(course);
		}

		branchesRepo.delete(branch);

		return BranchesDtoMapper.toResponseDto(branch);
	}
	
	
	public BranchesResponse updateStatus(String id, String status) {

		Branches branch = branchesRepo.findById(id).orElseThrow(() -> new RuntimeException("Company Not found"));

		ProfileStatus newStatus;
		try {
			newStatus = ProfileStatus.valueOf(status.toUpperCase());
		} catch (Exception e) {

			throw new RuntimeException("Invalid Status Value");
		}

		branch.setStatus(newStatus);

		branchesRepo.save(branch);

		return BranchesDtoMapper.toResponseDto(branch);
	}

	private String generateBranchId() {
		int currentBranchId = BRANCH_COUNTER.incrementAndGet();
		return String.format("IP%02d", currentBranchId);
	}

}
