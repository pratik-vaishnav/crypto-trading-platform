package com.trading.auth.repository;

import com.trading.auth.model.ApiKey;
import com.trading.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
    List<ApiKey> findByUser(User user);
    Optional<ApiKey> findByIdAndUser(Long id, User user);
    Optional<ApiKey> findByKeyId(String keyId);
    boolean existsByKeyId(String keyId);
}
