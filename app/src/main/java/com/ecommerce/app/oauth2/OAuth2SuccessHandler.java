package com.ecommerce.app.oauth2;

import com.ecommerce.app.enums.Role;
import com.ecommerce.app.model.TenantEntity;
import com.ecommerce.app.model.UserEntity;
import com.ecommerce.app.repository.TenantRepository;
import com.ecommerce.app.repository.UserRepository;
import com.ecommerce.app.security.Jwt.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TenantRepository tenantRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        String email = authentication.getName();
        System.out.println("OAuth2SuccessHandler Called");
        System.out.println(authentication.getName());
        UserEntity user = userRepo.findByEmail(email)
                .orElseGet(() -> {

                    TenantEntity tenant = tenantRepo.findById(1L)
                            .orElseThrow(() -> new RuntimeException("Default tenant not found"));

                    UserEntity newUser = new UserEntity();
                    newUser.setEmail(email);
                    newUser.setFirstname(email);
                    newUser.setPassword("");
                    newUser.setRole(Role.ROLE_CUSTOMER);
                    newUser.setTenant(tenant);

                    return userRepo.save(newUser);
                });

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getTenant().getId()
        );

        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);

        response.addCookie(cookie);

        response.sendRedirect("http://localhost:5173/");
    }
}