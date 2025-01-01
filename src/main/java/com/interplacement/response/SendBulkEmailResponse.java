package com.interplacement.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SendBulkEmailResponse {
    private int totalEmailsSent;
    private List<String> failedEmails; 

    private String statusMessage;

}
