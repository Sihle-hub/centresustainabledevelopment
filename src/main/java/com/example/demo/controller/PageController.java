package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.EmailSenderService;

import jakarta.mail.MessagingException;


@Controller
public class PageController {
	
	@Autowired
	private EmailSenderService senderService;
	
	 String name;
	
	 String email;
	
	 String id;
	
	 String phone;
	
	@GetMapping("/")
	public String welcoming_page() {
		
		  return "homepage.html";
	}
	
	@GetMapping("/service")
	public String service_page() {
		
		  return "services.html";
	}
	
	@GetMapping("/apply")
	public String application() {
		
		  return "apply.html";
	}
	
	@PostMapping("/sendEmail")
	public String triggerMail(@RequestParam("id_copy") MultipartFile id_copy , @RequestParam("cv") MultipartFile cv , 
			@RequestParam("matric") MultipartFile matric , @RequestParam("hiddenName") String name ,
			@RequestParam("hiddenID") String id , @RequestParam("hiddenEmail") String email , 
			@RequestParam("hiddenPhone") String phone) throws MessagingException {

		System.out.println(name);
		
	   senderService.sendEmailWithAttachments("chabwerabanda@gmail.com",
	            "Application for Job Openings",
	            "Application for " + name + ",\n\nID Number: " + id +"n\nEmail: " + email + "\n\nPhone Number : " + phone,
	            id_copy,
	            matric,
	            cv);
		
		System.out.println("YES");
		
		
	    return "redirect:/";

	}
	
	@PostMapping("/ContactUs")
	public String triggerMail(@RequestParam("name") String name , @RequestParam("email") String email , @RequestParam("subject") String subject,
			@RequestParam("message") String message) throws MessagingException {
		
		
		senderService.sendSimpleEmail("chabwerabanda@gmail.com", subject ,
				"Name : " + name + "\nEmail : " + email + "\nMessage : " + message);

		
	    return "redirect:/";
	    
	}
	

}
