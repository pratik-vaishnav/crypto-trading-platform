package com.trading.auth.service;

import com.trading.auth.model.ApiKey;
import com.trading.auth.model.Permission;
import com.trading.auth.model.User;
import com.trading.auth.repository.ApiKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApiKeyService {
    private final ApiKeyRepository apiKeyRepository;
    private final PasswordEncoder passwordEncoder;
    private static final SecureRandom secureRandom = new SecureRandom();

    @Transactional
    public ApiKey createApiKey(User user, String description, Set<Permission> permissions, LocalDateTime expiresAt) {
        String keyId = generateKeyId();
        String keySecret = generateKeySecret();
        
        ApiKey apiKey = ApiKey.builder()
                .keyId(keyId)
                .keySecret(passwordEncoder.encode(keySecret))
                .user(user)
                .description(description)
                .permissions(permissions)
                .expiresAt(expiresAt)
                .enabled(true)
                .build();
        
        apiKeyRepository.save(apiKey);
        
        // Return the unencoded secret only once
        apiKey.setKeySecret(keySecret);
        return apiKey;
    }

    @Transactional(readOnly = true)
    public List<ApiKey> getUserApiKeys(User user) {
        return apiKeyRepository.findByUser(user);
    }

    @Transactional
    public void revokeApiKey(Long apiKeyId, User user) {
        ApiKey apiKey = apiKeyRepository.findByIdAndUser(apiKeyId, user)
                .orElseThrow(() -> new RuntimeException("API key not found"));
        apiKey.setEnabled(false);
        apiKeyRepository.save(apiKey);
    }

    @Transactional(readOnly = true)
    public boolean validateApiKey(String keyId, String keySecret) {
        return apiKeyRepository.findByKeyId(keyId)
                .filter(ApiKey::isEnabled)
                .filter(key -> !key.getExpiresAt().isBefore(LocalDateTime.now()))
                .map(key -> passwordEncoder.matches(keySecret, key.getKeySecret()))
                .orElse(false);
    }

    private String generateKeyId() {
        return "TR-" + UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }

    private String generateKeySecret() {
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
