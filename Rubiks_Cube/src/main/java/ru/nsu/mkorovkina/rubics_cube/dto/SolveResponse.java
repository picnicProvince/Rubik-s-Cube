package ru.nsu.mkorovkina.rubics_cube.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class SolveResponse {
    private Long id;
    private String scramble;
    private String moves;
    private Integer timeMs;
    private Instant buildDate;
}
