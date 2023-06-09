package ru.kpfu.hostel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.hostel.services.email.EmailService;

@RestController
public class EmailController {

    private final EmailService emailService;

    @Autowired
    EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    // Sending a simple Email
    /*@PostMapping("/sendMail")
    public String
    sendMail(@RequestBody EmailDetails details)
    {
        String status
            = emailService.sendSimpleMail(details);
 
        return status;
    }*/
}