package ru.nsu.mkorovkina.rubics_cube.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String password;
    private String email;
}
