package com.trading.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponse {
    private String token;
    private String type;
    private Long id;
    private String username;
    private String email;
    private String role;
}
