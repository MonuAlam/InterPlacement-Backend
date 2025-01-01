package com.interplacement.util;

import java.util.List;

import com.interplacement.response.SendBulkEmailResponse;

public class SendBulkEmailDtoMapper {

	public static SendBulkEmailResponse toResponseDto(int totalEmailsSent,List<String> failedEmails,String statusMessage ) {
		
		return SendBulkEmailResponse.builder()
				.totalEmailsSent(totalEmailsSent)
				.failedEmails(failedEmails)
				.statusMessage(statusMessage)
				.build();
	}
}
