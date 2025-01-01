package com.interplacement.request;

import java.util.List;

import com.interplacement.entity.Branches;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CoursesRequest {
	
	private String name;
	
	private List<Branches> branches;

}
