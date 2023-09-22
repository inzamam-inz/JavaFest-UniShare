package com.unishare.backend.controller;

import com.unishare.backend.DTO.AuthenticationRequest;
import com.unishare.backend.DTO.AuthenticationResponse;
import com.unishare.backend.DTO.RegisterRequest;
import com.unishare.backend.service.AuthenticationService;
import com.unishare.backend.service.MailSendingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final MailSendingService mailSendingService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        boolean mailSendingStatus = mailSendingService.sendMail("bsse1113@iit.du.ac.bd", "OTP for UniShare", "Your OTP is 12345");
        if (!mailSendingStatus) {
            System.out.println("Sending email fail!");
        }
        AuthenticationResponse authenticationResponse = service.register(request);
        return ResponseEntity.ok(authenticationResponse);
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }


}

