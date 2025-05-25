package com.igor.hospital.presentation.controller;

import com.igor.hospital.application.service.AuthenticationService;
import com.igor.hospital.presentation.dto.LoginDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.token.Token;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody @Valid LoginDto loginDTO){
        return ResponseEntity.ok(authenticationService.autenticate(loginDTO));
    }
}
