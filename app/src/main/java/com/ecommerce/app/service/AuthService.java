package com.ecommerce.app.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

import com.ecommerce.app.model.TenantEntity;
import com.ecommerce.app.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.app.dto.Request.LoginRequest;
import com.ecommerce.app.dto.Request.RegisterRequest;
import com.ecommerce.app.enums.Role;
import com.ecommerce.app.model.UserEntity;
import com.ecommerce.app.repository.UserRepository;
import com.ecommerce.app.security.Jwt.JwtUtil;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil util;

    @Autowired
    private TenantRepository tenantRepo;

    // ============= user registration =================
    public ResponseEntity<?> register(RegisterRequest request) {
        if (userRepo.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User email already exists");
        }
        TenantEntity tenant = tenantRepo.findById(request.getTenantId()).orElseThrow(
                () -> new RuntimeException(
                        "Tenant was not found in this id" + request.getTenantId()
                )
        );
        UserEntity newUser = new UserEntity();
        newUser.setFirstname(request.getFirstname());
        newUser.setLastname(request.getLastname());
        newUser.setEmail(request.getEmail());
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setTenant(tenant);
        if (request.getRole() == null) {
            newUser.setRole(Role.ROLE_CUSTOMER);
        } else {
            newUser.setRole(request.getRole());
        }

        userRepo.save(newUser);
        return ResponseEntity.ok("user registered successfully");

    }

    // ============== user login ================
    public ResponseEntity<?> login(LoginRequest request) {
        UserEntity user = userRepo.findByEmail(request.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("user was not register"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invaild email or password");
        }
        String token = util.generateToken(user.getEmail(),
                user.getTenant().getId());

        ResponseCookie cookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .secure(false)
                .maxAge(Duration.ofDays(1))
                .path("/")
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of(
                        "message", "Login successfull"
                ));
    }

}
