package ru.nsu.mkorovkina.rubics_cube.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "solves")
public class SolveModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String scramble;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String moves;

    @Column(nullable = false)
    private Integer timeMs;

    @Column(nullable = false)
    private Instant buildDate;
}
