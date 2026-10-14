package ru.nsu.mkorovkina.rubics_cube.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private String username;
    private String role;
    private String accessToken;
    private String refreshToken;
}
