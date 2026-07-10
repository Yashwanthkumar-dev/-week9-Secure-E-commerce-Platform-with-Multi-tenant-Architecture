package com.ecommerce.app.oauth2;

import com.ecommerce.app.enums.Role;
import com.ecommerce.app.model.TenantEntity;
import com.ecommerce.app.model.UserEntity;
import com.ecommerce.app.repository.TenantRepository;
import com.ecommerce.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TenantRepository tenantRepo;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request)
            throws OAuth2AuthenticationException {

        OAuth2User oauthUser =
                new DefaultOAuth2UserService().loadUser(request);
        System.out.println("CustomOAuth2UserService Called");

        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");

        if (!userRepo.existsByEmail(email)) {

            TenantEntity tenant = tenantRepo.findById(1L)
                    .orElseThrow();

            UserEntity user = new UserEntity();

            user.setFirstname(name);
            user.setLastname("");
            user.setEmail(email);

            // Random password because Google user won't login with password
            user.setPassword(UUID.randomUUID().toString());

            user.setRole(Role.ROLE_CUSTOMER);
            user.setTenant(tenant);
            user.setCreatedAt(LocalDateTime.now());

            userRepo.save(user);
        }

        return oauthUser;
    }
}