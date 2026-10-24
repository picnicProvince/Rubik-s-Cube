package ru.nsu.mkorovkina.rubics_cube.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.mkorovkina.rubics_cube.model.Competition;
import ru.nsu.mkorovkina.rubics_cube.repository.CompetitionRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/competitions")
public class WcaCompetitionController {
    private final CompetitionRepository competitionRepository;

    public WcaCompetitionController(CompetitionRepository competitionRepository) {
        this.competitionRepository = competitionRepository;
    }

    @GetMapping("/upcoming")
    public List<Competition> upcoming(@RequestParam(required = false) String country) {
        LocalDate today = LocalDate.now();
        if (country != null && !country.isBlank()) {
            return competitionRepository
                    .findByCountryAndFromGreaterThanEqualOrderByFromAsc(country, today);
        }
        return competitionRepository.findByFromGreaterThanEqualOrderByFromAsc(today);
    }
}
