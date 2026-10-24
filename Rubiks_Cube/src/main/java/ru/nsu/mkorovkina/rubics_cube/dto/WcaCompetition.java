package ru.nsu.mkorovkina.rubics_cube.dto;

import lombok.Data;

import java.util.List;

@Data
public class WcaCompetition {
    private String id;
    private String name;
    private String city;
    private String country;
    private Date date;
    private boolean isCanceled;
    private List<String> events;
}
