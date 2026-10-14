package ru.nsu.mkorovkina.rubics_cube.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
