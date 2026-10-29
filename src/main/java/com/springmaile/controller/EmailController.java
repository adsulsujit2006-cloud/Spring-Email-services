package com.springmaile.controller;

import com.springmaile.dto.request.MailRequest;
import com.springmaile.services.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class EmailController {

    private final Email email;

    @PostMapping("/send")
    public ResponseEntity<?> sendMail(@RequestBody MailRequest request) {
        System.out.println("Hell");
        Map<String, String> response = new HashMap<>();

        if (request.getTo() == null || request.getTo().trim().isEmpty()
                || request.getName() == null || request.getName().trim().isEmpty()) {
            response.put("status", "error");
            response.put("message", "Both 'to' and 'name' are required");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            email.sendMail(request.getTo().trim(), request.getName().trim());
            response.put("status", "success");
            response.put("message", "Welcome mail sent to " + request.getTo());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to send mail: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}