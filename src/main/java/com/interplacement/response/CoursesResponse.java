package com.interplacement.response;

import java.util.List;

import com.interplacement.entity.Branches;
import com.interplacement.enums.ProfileStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CoursesResponse {

	private String id;

	private String name;

	private List<Branches> branches;
 
	@Enumerated(EnumType.STRING)
    private ProfileStatus status;
}
