package com.interplacement.service;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interplacement.repository.JobPostRepo;

@Service
public class JobPostService {

	@Autowired
	private JobPostRepo jobPostRepo;

	private final AtomicInteger COUNTER = new AtomicInteger(0);
	
	private void initializeCounter() {
		
	}

	private String generateCustomId() {
		
		int currentId=COUNTER.incrementAndGet();
		
		return String.format("IP%02d", currentId);
	}
}
