package com.interplacement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.interplacement.request.SendBulkEmailRequest;
import com.interplacement.response.SendBulkEmailResponse;
import com.interplacement.service.EmailService;

@RestController
@RequestMapping("/ip/bulkemails")
public class BulkEmailController {

    @Autowired
    private EmailService emailService;



//    @PostMapping("/sendToStudents")
//    public SendBulkEmailResponse sendEmailsToStudents(@RequestBody SendBulkEmailRequest request) {
//
//      return  emailService.sendEmailsToStudents(request);
//    }

    
    // Sending emails to students
    @PostMapping("/sendToStudents")
    public SendBulkEmailResponse sendEmailsToStudents(
            @RequestParam(value = "studentIds", required = false) List<String> studentIds,
            @RequestParam(value = "years", required = false) List<String> years,
            @RequestParam("subject") String subject,
            @RequestParam("content") String content,
            @RequestParam(value = "files", required = false) List<MultipartFile> files) {

        SendBulkEmailRequest request = new SendBulkEmailRequest();
        request.setStudentIds(studentIds);
        request.setYears(years);
        request.setSubject(subject);
        request.setContent(content);
        request.setFiles(files);

        return emailService.sendEmailsToStudents(request);
    }

    
    
//    @PostMapping("/sendToCompanies")
//    public SendBulkEmailResponse sendEmailsToCompanies(@RequestBody SendBulkEmailRequest request) {
//
//       return emailService.sendEmailsToCompanies(request);
//
//    }

    // Sending emails to companies
    @PostMapping("/sendToCompanies")
    public SendBulkEmailResponse sendEmailsToCompanies(
            @RequestParam(value = "companyIds", required = false) List<String> companyIds,
            @RequestParam("subject") String subject,
            @RequestParam("content") String content,
            @RequestParam(value = "files", required = false) List<MultipartFile> files) {

        SendBulkEmailRequest request = new SendBulkEmailRequest();
        request.setCompanyIds(companyIds);
        request.setSubject(subject);
        request.setContent(content);
        request.setFiles(files);

        return emailService.sendEmailsToCompanies(request);
    }
    
}
