package com.ecommerce.app.model;

import java.time.LocalDateTime;

import com.ecommerce.app.enums.Role;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Fist name is required to fill")
    private String firstname;
    @NotBlank(message = "Last name is required to fill")
    private String lastname;
    @NotBlank(message = "Email field is required to fill")
    @Email
    private String email;
    @NotBlank(message = "password field is required to fll")
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private TenantEntity tenant;
}
