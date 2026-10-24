package ru.nsu.mkorovkina.rubics_cube.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.nsu.mkorovkina.rubics_cube.dto.Page;
import ru.nsu.mkorovkina.rubics_cube.dto.WcaCompetition;
import ru.nsu.mkorovkina.rubics_cube.model.Competition;
import ru.nsu.mkorovkina.rubics_cube.repository.CompetitionRepository;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WcaCompetitionService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final CompetitionRepository competitionRepository;
    private static final String BASE_URL =
            "https://raw.githubusercontent.com/robiningelbrecht/wca-rest-api/v1/competitions-page-%d.json";

    public WcaCompetitionService(RestTemplate restTemplate, ObjectMapper objectMapper,
                                 CompetitionRepository competitionRepository) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.competitionRepository = competitionRepository;
    }

    @Transactional
    public void syncUpcoming() {
        List<Competition> fetched = fetchCompetition();
        if (fetched.isEmpty()) {
            return;
        }
        Set<String> syncedIds = fetched.stream().map(Competition::getId).collect(Collectors.toSet());
        Map<String, Competition> existing = competitionRepository.findAllById(syncedIds)
                .stream()
                .collect(Collectors.toMap(Competition::getId, c -> c));
        List<Competition> toSave = new ArrayList<>(fetched.size());
        for (Competition fresh : fetched) {
            Competition target = existing.get(fresh.getId());
            if (target != null) {
                target.setName(fresh.getName());
                target.setCity(fresh.getCity());
                target.setCountry(fresh.getCountry());
                target.setFrom(fresh.getFrom());
                target.setTill(fresh.getTill());
                target.setEvents(fresh.getEvents());
                toSave.add(target);
            } else {
                toSave.add(fresh);
            }
        }
        competitionRepository.saveAll(toSave);
        competitionRepository.deleteByIdNotIn(syncedIds);
    }

    private List<Competition> fetchCompetition() {
        LocalDate today = LocalDate.now();
        List<Competition> result = new ArrayList<>();
        int pageCount = 1;
        boolean keepGoing = true;
        while (keepGoing) {
            Page page = fetchPage(pageCount);
            if (page == null || page.getItems() == null || page.getItems().isEmpty()) {
                break;
            }
            for (WcaCompetition wcaCompetition : page.getItems()) {
                if (wcaCompetition.isCanceled()) {
                    continue;
                }
                LocalDate start = LocalDate.parse(wcaCompetition.getDate().getFrom());
                if (start.isBefore(today)) {
                    keepGoing = false;
                    break;
                }
                result.add(toEntity(wcaCompetition));
            }
            pageCount++;
        }
        return result;
    }

    private Page fetchPage(int page) {
        String url = String.format(BASE_URL, page);
        try {
            String json = restTemplate.getForObject(url, String.class);
            return objectMapper.readValue(json, Page.class);
        } catch (Exception e) {
            return null;
        }
    }

    private Competition toEntity(WcaCompetition wcaCompetition) {
        Competition c = new Competition();
        c.setId(wcaCompetition.getId());
        c.setName(wcaCompetition.getName());
        c.setCity(wcaCompetition.getCity());
        c.setCountry(wcaCompetition.getCountry());
        c.setFrom(LocalDate.parse(wcaCompetition.getDate().getFrom()));
        c.setTill(LocalDate.parse(wcaCompetition.getDate().getTill()));
        c.setEvents(String.join(",", wcaCompetition.getEvents()));
        return c;
    }
}
