package ru.nsu.mkorovkina.rubics_cube.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "competitions")
public class Competition {
    @Id
    @Column(nullable = false)
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String country;
    @Column(name = "date_from", nullable = false)
    private LocalDate from;
    @Column(name = "date_till", nullable = false)
    private LocalDate till;
    @Column(nullable = false)
    private String events;
}
