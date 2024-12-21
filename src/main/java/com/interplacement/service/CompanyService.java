package com.interplacement.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.interplacement.entity.Company;
import com.interplacement.enums.ProfileStatus;
import com.interplacement.repository.CompanyRepo;
import com.interplacement.request.CompanyRequest;
import com.interplacement.response.CompanyResponse;
import com.interplacement.util.CompanyDtoMapper;
import jakarta.annotation.PostConstruct;
import java.util.Base64;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepo companyRepository;

	@Value("${file.upload-dir:uploads/images/}")
	private String uploadDir;

	private final AtomicInteger COUNTER = new AtomicInteger(0);

	@PostConstruct
	private void initializeCounter() {

		Company lastCompany = companyRepository.findTopByOrderByIdDesc();

		int lastId = 0;

		if (lastCompany != null && lastCompany.getId().startsWith("IP")) {
			lastId = Integer.parseInt(lastCompany.getId().substring(2));
		}

		COUNTER.set(lastId);

	}

	public CompanyResponse saveCompany(CompanyRequest companyRequest) throws IOException {

		Company company = toEntity(companyRequest);

		if (companyRequest.getProfileImageBase64() != null) {

			byte[] decodedImage = Base64.getDecoder().decode(companyRequest.getProfileImageBase64());

			File uploadDirectory = new File(uploadDir);
			if (!uploadDirectory.exists()) {
				uploadDirectory.mkdirs();
			}

			String fileName = System.currentTimeMillis() + "_" + companyRequest.getProfileImageName();

			Path filePath = Paths.get(uploadDir, fileName);

			Files.write(filePath, decodedImage);

			company.setProfileImagePath("/images/" + fileName);
		}

		return CompanyDtoMapper.toResponseDto(companyRepository.save(company));
	}

	private Company toEntity(CompanyRequest request) {
		return Company.builder().id(generateCustomId()).name(request.getName()).mobNumber(request.getMobNumber())
				.email(request.getEmail()).password(request.getPassword()).address(request.getAddress())
				.companyType(request.getCompanyType()).profileStatus(ProfileStatus.ACTIVE).build();

	}

	private String generateCustomId() {

		int currentCounter = COUNTER.incrementAndGet();

		return String.format("IP%02d", currentCounter);

	}

	public List<CompanyResponse> getAllCompanies() {

		return companyRepository.findAll().stream().map(CompanyDtoMapper::toResponseDto).toList();
	}

	public CompanyResponse getCompanyById(String id) {

		Company company = companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found"));

		return CompanyDtoMapper.toResponseDto(company);
	}

	public CompanyResponse deleteCompany(String id) {

		Company company = companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company Not found"));

		companyRepository.delete(company);

		return CompanyDtoMapper.toResponseDto(company);
	}

	public CompanyResponse updateCompany(String id, CompanyRequest companyRequest) throws IOException {
		Company existingCompany = companyRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Company not found"));

		if (companyRequest.getProfileImageBase64() != null) {
			if (existingCompany.getProfileImagePath() != null) {
				Path oldImagePath = Paths.get(uploadDir,
						existingCompany.getProfileImagePath().replaceFirst("/images/", ""));
				File oldImageFile = oldImagePath.toFile();

				if (oldImageFile.exists()) {
					oldImageFile.delete();
				}
			}

			byte[] decodedImage = Base64.getDecoder().decode(companyRequest.getProfileImageBase64());

			File uploadDirectory = new File(uploadDir);
			if (!uploadDirectory.exists()) {
				uploadDirectory.mkdirs();
			}

			String fileName = System.currentTimeMillis() + "_" + companyRequest.getProfileImageName();

			Path filePath = Paths.get(uploadDir, fileName);

			Files.write(filePath, decodedImage);

			existingCompany.setProfileImagePath("/images/" + fileName);
		}

		Company updatedCompany = updateWithBuilder(existingCompany, companyRequest);

		return CompanyDtoMapper.toResponseDto(companyRepository.save(updatedCompany));
	}

	private Company updateWithBuilder(Company company, CompanyRequest request) {
		return company.toBuilder().name(request.getName()).mobNumber(request.getMobNumber()).email(request.getEmail())
				.address(request.getAddress()).password(request.getPassword()).companyType(request.getCompanyType())
				.build();
	}

	public CompanyResponse updateStatus(String id, String status) {

		Company company = companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company Not found"));

		ProfileStatus newStatus;
		try {
			newStatus = ProfileStatus.valueOf(status.toUpperCase());
		} catch (Exception e) {

			throw new RuntimeException("Invalid Status Value");
		}

		company.setProfileStatus(newStatus);

		companyRepository.save(company);

		return CompanyDtoMapper.toResponseDto(company);
	}
}
