package com.interplacement.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendBulkEmailRequest {

    private List<String> studentIds;
    private List<String> years; 
    private String subject;
    private String content;
    private List<MultipartFile> files;
    private List<String> companyIds;

}
