package ru.nsu.mkorovkina.rubics_cube.dto;

import lombok.Data;

import java.util.List;

@Data
public class Page {
    private Pagination pagination;
    private int total;
    private List<WcaCompetition> items;
}
