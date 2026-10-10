package ru.nsu.mkorovkina.rubics_cube.dto;

import lombok.Data;

@Data
public class AuthResponse {
    String username;
    String role;
    String accessToken;
    String refreshToken;
}
