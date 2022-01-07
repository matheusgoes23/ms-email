package com.ms.email.controllers;

import com.ms.email.dtos.EmailDTO;
import com.ms.email.models.Email;
import com.ms.email.services.EmailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sending-email")
    public ResponseEntity<Email> sendingEmail(@RequestBody @Valid EmailDTO emailDTO) {
        Email email = new Email();
        BeanUtils.copyProperties(emailDTO, email);
        emailService.sendEmail(email);
        return new ResponseEntity<>(email, HttpStatus.CREATED);
    }

    @GetMapping("/emails")
    public ResponseEntity<Page<Email>> getAllEmails(Pageable pageable) {
        return ResponseEntity.ok(emailService.findAll(pageable));
    }

    @GetMapping("/emails/{emailId}")
    ResponseEntity<Email> getOneEmail(@PathVariable(value = "emailId") UUID emailId) {
        return ResponseEntity.ok(emailService.findById(emailId));
    }
}
