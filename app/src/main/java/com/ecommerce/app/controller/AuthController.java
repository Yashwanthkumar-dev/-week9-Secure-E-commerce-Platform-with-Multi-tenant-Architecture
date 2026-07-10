package com.ecommerce.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.app.dto.Request.LoginRequest;
import com.ecommerce.app.dto.Request.RegisterRequest;
import com.ecommerce.app.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin (origins = "*")
public class AuthController {
    @Autowired
    private AuthService service;

    // ============= user registration =================
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        return service.register(request);
    }

    // ============== user login ================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        return service.login(request);
    }
}
