package ru.nsu.mkorovkina.rubics_cube.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.nsu.mkorovkina.rubics_cube.model.Competition;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RussianCompetitionService {
    private static final String BASE_URL = "https://cubingrf.org/competitions";

    private static final Map<String, Integer> MONTHS = Map.ofEntries(
            Map.entry("января", 1),
            Map.entry("февраля", 2),
            Map.entry("марта", 3),
            Map.entry("апреля", 4),
            Map.entry("мая", 5),
            Map.entry("июня", 6),
            Map.entry("июля", 7),
            Map.entry("августа", 8),
            Map.entry("сентября", 9),
            Map.entry("октября", 10),
            Map.entry("ноября", 11),
            Map.entry("декабря", 12)
    );

    private final RestTemplate restTemplate;

    public RussianCompetitionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Competition> getRussianCompetitions() {
        String html;
        try {
            html = restTemplate.getForObject(BASE_URL, String.class);
        } catch (Exception e) {
            //логер
            return List.of();
        }
        Document doc = Jsoup.parse(html);
        Elements competitions = doc.select("a.block.p-5.border");
        List<Competition> competitionList = new ArrayList<>();
        for (Element c : competitions) {
            Optional<Competition> comp = parseCompetition(c);
            if (comp.isEmpty()) {
                continue;
            }
            Competition current_comp = comp.get();
            if (current_comp.getFrom().isBefore(LocalDate.now())) {
                break;
            }
            competitionList.add(current_comp);
        }
        return competitionList;
    }

    private Optional<Competition> parseCompetition(Element elem) {
        String href = elem.attr("href");
        int index = href.lastIndexOf('/');
        String id = href.substring(index + 1);
        String name = elem.selectFirst("div.font-bold.text-lg").text().trim();
        String date = elem.selectFirst("div.text-gray-500.text-sm").text().trim();
        LocalDate[] parsedDate;
        try {
            parsedDate = parseDate(date);
        } catch (IllegalArgumentException e) {
            //добавить логер
            return Optional.empty();
        }
        LocalDate from = parsedDate[0];
        LocalDate till = parsedDate[1];
        String city = elem.selectFirst("div.text-base").text().trim();
        boolean flag = false;
        for (Element event : elem.select("i.cubing-icon")) {
            String className = event.className();
            int eventIndex = className.lastIndexOf('-');
            String eventName = className.substring(eventIndex + 1);
            if (eventName.equals("333")) {
                flag = true;
                break;
            }
        }
        if (flag) {
            Competition c = new Competition();
            c.setId(id);
            c.setName(name);
            c.setCountry("RU");
            c.setCity(city);
            c.setFrom(from);
            c.setTill(till);
            return Optional.of(c);
        } else {
            return Optional.empty();
        }
    }

    private LocalDate[] parseDate(String date) {
        String[] parts = date.split("[-\\s]+");
        int from, till, month, year;
        if (parts.length == 3) {
            from = Integer.parseInt(parts[0]);
            till = from;
            month = MONTHS.get(parts[1]);
            year = Integer.parseInt(parts[2]);
        } else if (parts.length == 4) {
            from = Integer.parseInt(parts[0]);
            till = Integer.parseInt(parts[1]);
            month = MONTHS.get(parts[2]);
            year = Integer.parseInt(parts[3]);
        } else {
            throw new IllegalArgumentException("Unsupported date format");
        }
        return new LocalDate[]{
                LocalDate.of(year, month, from),
                LocalDate.of(year, month, till)
        };
    }
}
