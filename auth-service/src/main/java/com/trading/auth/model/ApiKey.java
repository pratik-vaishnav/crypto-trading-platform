package com.trading.auth.model;

import jakarta.persistence.*;
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
@Table(name = "api_keys")
public class ApiKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String keyId;

    @Column(nullable = false)
    private String keySecret;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;

    private String ipWhitelist;

    @Builder.Default
    private boolean enabled = true;

    @ElementCollection
    @CollectionTable(name = "api_key_permissions")
    @Enumerated(EnumType.STRING)
    private Set<Permission> permissions;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
