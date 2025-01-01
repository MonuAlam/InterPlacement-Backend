package com.interplacement.response;



import com.interplacement.enums.ProfileStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BranchesResponse {

	private String id;

	private String name;

    private ProfileStatus status;


}
