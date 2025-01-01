package com.interplacement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.interplacement.entity.Company;
import com.interplacement.entity.Student;
import com.interplacement.enums.ProfileStatus;
import com.interplacement.repository.CompanyRepo;
import com.interplacement.repository.StudentRepo;
import com.interplacement.request.SendBulkEmailRequest;
import com.interplacement.response.SendBulkEmailResponse;
import com.interplacement.util.SendBulkEmailDtoMapper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private StudentRepo studentRepo;

	@Autowired
	private CompanyRepo companyRepo;

//    public void sendWelcomeEmail(String to, String name) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject("Welcome to InterPlacement!");
//        message.setText("Hi " + name + ",\n\nWelcome to InterPlacement! Your registration was successful. We are excited to have you onboard.\n\nBest regards,\nThe InterPlacement Team");
//        mailSender.send(message);
//    }

	public void sendWelcomeEmail(String to, String name) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setTo(to);
			helper.setSubject("Welcome to InterPlacement - Your Journey Begins Here!");
			helper.setText(buildHtmlContent(name), true);

			mailSender.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException("Failed to send email", e);
		}
	}

	private String buildHtmlContent(String name) {
		return """
				<!DOCTYPE html>
				<html>
				<head>
				    <style>
				        body {
				            font-family: Arial, sans-serif;
				            background-color: #f4f4f4;
				            margin: 0;
				            padding: 0;
				        }
				        .email-container {
				            max-width: 600px;
				            margin: 20px auto;
				            background: #ffffff;
				            padding: 20px;
				            border-radius: 8px;
				            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
				        }
				        .header {
				            text-align: center;
				            background: #0b5cff;
				            color: white;
				            padding: 10px 0;
				            border-radius: 8px 8px 0 0;
				        }
				        .content {
				            margin: 20px 0;
				            font-size: 16px;
				            color: #333;
				        }
				        .footer {
				            text-align: center;
				            font-size: 14px;
				            color: #777;
				            margin-top: 20px;
				        }
				        .button {
				            display: inline-block;
				            margin-top: 20px;
				            padding: 10px 20px;
				            font-size: 16px;
				            color: #ffffff;
				            background-color: #0b5cff;
				            text-decoration: none;
				            border-radius: 5px;
				        }
				    </style>
				</head>
				<body>
				    <div class="email-container">
				        <div class="header">
				            <h1>Welcome to InterPlacement</h1>
				        </div>
				        <div class="content">
				            <p>Hi <strong>%s</strong>,</p>
				            <p>We are thrilled to welcome you to InterPlacement! Your registration was successful, and we’re excited to have you as part of our community.</p>
				            <p>Feel free to explore our platform and discover amazing opportunities tailored for you.</p>
				            <p>If you have any questions, don't hesitate to reach out to us.</p>
				            <a href="https://inter-placement-video-application.vercel.app/" class="button">Visit InterPlacement</a>
				        </div>
				        <div class="footer">
				            <p>Thank you for joining us!</p>
				            <p>The InterPlacement Team</p>
				        </div>
				    </div>
				</body>
				</html>
				"""
				.formatted(name);
	}

	public void sendStudentPasswordEmail(String to, String name, String password) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setTo(to);
			helper.setSubject("Welcome to InterPlacement! Your Login Credentials Inside");
			helper.setText(buildStudentPasswordEmailContent(name, to, password), true);

			mailSender.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException("Failed to send email", e);
		}
	}

	private String buildStudentPasswordEmailContent(String name, String email, String password) {
		return """
				<!DOCTYPE html>
				<html>
				<head>
				    <style>
				        body {
				            font-family: Arial, sans-serif;
				            background-color: #f4f4f4;
				            margin: 0;
				            padding: 0;
				        }
				        .email-container {
				            max-width: 600px;
				            margin: 20px auto;
				            background: #ffffff;
				            padding: 20px;
				            border-radius: 8px;
				            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
				        }
				        .header {
				            text-align: center;
				            background:  #0b5cff;
				            color: white;
				            padding: 10px 0;
				            border-radius: 8px 8px 0 0;
				        }
				        .content {
				            margin: 20px 0;
				            font-size: 16px;
				            color: #333;
				        }
				        .footer {
				            text-align: center;
				            font-size: 14px;
				            color: #777;
				            margin-top: 20px;
				        }
				        .button {
				            display: inline-block;
				            margin-top: 20px;
				            padding: 10px 20px;
				            font-size: 16px;
				            color: #ffffff;
				            background-color:  #0b5cff;
				            text-decoration: none;
				            border-radius: 5px;
				        }
				    </style>
				</head>
				<body>
				    <div class="email-container">
				        <div class="header">
				            <h1>Welcome to InterPlacement</h1>
				        </div>
				        <div class="content">
				            <p>Hi <strong>%s</strong>,</p>
				            <p>Welcome to InterPlacement!</p>
				            <p>Below are your account details:</p>
				            <p><strong>Email:</strong> %s</p>
				            <p><strong>Password:</strong> %s</p>
				            <p>Please use the above credentials to log in to your account.</p>
				            <p>If you have any questions, feel free to contact us.</p>
				            <a href="https://inter-placement-video-application.vercel.app/" class="button">Log In Now</a>
				        </div>
				        <div class="footer">
				            <p>Thank you for joining us!</p>
				            <p>The InterPlacement Team</p>
				        </div>
				    </div>
				</body>
				</html>
				"""
				.formatted(name, email, password);
	}


	public SendBulkEmailResponse sendEmailsToStudents(SendBulkEmailRequest request) {
		List<Student> students;

		if (request.getStudentIds() != null && !request.getStudentIds().isEmpty()) {

			students = studentRepo.findByIdInAndStatus(request.getStudentIds(), ProfileStatus.ACTIVE);
		} else if (request.getYears() != null && !request.getYears().isEmpty()) {

			students = studentRepo.findByYearInAndStatus(request.getYears(), ProfileStatus.ACTIVE);
		} else {
			throw new IllegalArgumentException("Either studentIds or years must be provided.");
		}

		List<String> emails = students.stream().map(Student::getEmail).collect(Collectors.toList());

		List<String> failedEmails = new ArrayList<>();
		int totalEmailsSent = 0;

		for (String email : emails) {
			try {
				sendEmailToSingleRecipient(email, request.getSubject(), request.getContent(), request.getFiles());
				totalEmailsSent++;
			} catch (Exception e) {
				failedEmails.add(email);
			}
		}

		String statusMessage = failedEmails.isEmpty() ? "Emails sent successfully" : "Some emails failed to send";
		return SendBulkEmailDtoMapper.toResponseDto(totalEmailsSent, failedEmails, statusMessage);
	}

	@Async
	private void sendEmailToSingleRecipient(String email, String subject, String content, List<MultipartFile> files) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setTo(email);
			helper.setSubject(subject);

			String htmlContent = buildEmailContent(subject, content);

			helper.setText(htmlContent, true);

			if (files != null && !files.isEmpty()) {
				for (MultipartFile file : files) {
					if (!file.isEmpty()) {
						helper.addAttachment(file.getOriginalFilename(), file);
					}
				}
			}

			mailSender.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException("Failed to send email to: " + email, e);
		}
	}

	private String buildEmailContent(String subject, String content) {

		return """
				<!DOCTYPE html>
				<html>
				<head>
				    <style>
				        body {
				            font-family: Arial, sans-serif;
				            background-color: #f4f4f4;
				            margin: 0;
				            padding: 0;
				        }
				        .email-container {
				            max-width: 600px;
				            margin: 20px auto;
				            background: #ffffff;
				            padding: 20px;
				            border-radius: 8px;
				            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
				        }
				        .header {
				            text-align: center;
				            background: #0b5cff;
				            color: white;
				            padding: 10px 0;
				            border-radius: 8px 8px 0 0;
				        }
				        .content {
				            margin: 20px 0;
				            font-size: 16px;
				            color: #333;
				        }
				        .footer {
				            text-align: center;
				            font-size: 14px;
				            color: #777;
				            margin-top: 20px;
				        }
				        .button {
				            display: inline-block;
				            margin-top: 20px;
				            padding: 10px 20px;
				            font-size: 16px;
				            color: #ffffff;
				            background-color: #0b5cff;
				            text-decoration: none;
				            border-radius: 5px;
				        }
				    </style>
				</head>
				<body>
				    <div class="email-container">
				        <div class="header">
				            <h1>%s</h1>
				        </div>
				        <div class="content">
				            <p>%s</p>
				        </div>
				        <div class="footer">
				            <p>Thank you!</p>
				            <p>The InterPlacement Team</p>
				        </div>
				    </div>
				</body>
				</html>
				""".formatted(subject, content, subject);
	}

	public SendBulkEmailResponse sendEmailsToCompanies(SendBulkEmailRequest request) {
	    List<Company> companies;

	    if (request.getCompanyIds() != null && !request.getCompanyIds().isEmpty()) {

	        companies = companyRepo.findByIdInAndStatus(request.getCompanyIds(), ProfileStatus.ACTIVE);
	    
	        System.out.println(companies);
	    } else {
	        throw new IllegalArgumentException("companyIds must be provided.");
	    }

	    List<String> emails = companies.stream()
	                                   .map(Company::getEmail)
	                                   .collect(Collectors.toList());

	    List<String> failedEmails = new ArrayList<>();
	    int totalEmailsSent = 0;

	    for (String email : emails) {
	        try {
	            sendEmailToSingleRecipient(email, request.getSubject(), request.getContent(), request.getFiles());
	            totalEmailsSent++;
	        } catch (Exception e) {
	            failedEmails.add(email);
	        }
	    }

	    String statusMessage = failedEmails.isEmpty() ? "Emails sent successfully" : "Some emails failed to send";
	    return SendBulkEmailDtoMapper.toResponseDto(totalEmailsSent, failedEmails, statusMessage);
	}

}

//<form>
//<input type="text" id="subject" placeholder="Subject" />
//<textarea id="content" placeholder="Content"></textarea>
//<input type="file" id="files" multiple />
//<button type="submit">Send Emails</button>
//</form>
