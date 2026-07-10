package com.ecommerce.app.security.Service;

import com.ecommerce.app.model.UserEntity;
import com.ecommerce.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("user was not found in this email"));
        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(
                        List.of(
                                new SimpleGrantedAuthority(user.getRole().name())
                        )
                )
                .build();
    }
}
