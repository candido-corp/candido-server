package com.candido.server.controller;

import com.candido.server.service.email.EmailService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/docker/test")
public class ControllerTest {

    @Autowired
    EmailService emailService;

    @Value("${application.email.no-reply}")
    private String noReply;

    @Value("${application.name}")
    private String applicationName;

    @GetMapping
    public ResponseEntity<String> testDocker() {
        // TODO: Rimuovi questo endpoint
        return ResponseEntity.ok("It works.");
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class TestEmail {
        String content;
        String subject;
    }

    @PostMapping("/email/{email}")
    public ResponseEntity<Void> testEmail(
            @PathVariable("email") String email,
            @RequestBody TestEmail testEmail
    ) {
        emailService.sendSimpleMessage(noReply, applicationName, email, testEmail.subject, testEmail.content);
        return ResponseEntity.noContent().build();
    }


}
