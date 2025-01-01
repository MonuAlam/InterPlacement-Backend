package com.interplacement.util;

import com.interplacement.entity.Branches;
import com.interplacement.response.BranchesResponse;

public class BranchesDtoMapper {
	
	public static BranchesResponse toResponseDto(Branches branches) {
		return BranchesResponse.builder()
				.id(branches.getId())
				.name(branches.getName())
				.status(branches.getStatus())
				.build();
	}
}
