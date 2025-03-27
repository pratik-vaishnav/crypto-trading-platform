package com.trading.auth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "username"),
           @UniqueConstraint(columnNames = "email")
       })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean emailVerified;
    
    private String verificationToken;
    
    private LocalDateTime verificationTokenExpiry;
    
    private String resetPasswordToken;
    
    private LocalDateTime resetPasswordTokenExpiry;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ApiKey> apiKeys;

    private boolean enabled = true;
    
    private LocalDateTime lastLoginDate;
    
    private String lastLoginIp;
    
    @Column(length = 2000)
    private String tradingPreferences;

    @ElementCollection
    @CollectionTable(name = "user_permissions")
    @Enumerated(EnumType.STRING)
    private Set<Permission> permissions;
}
