package ru.nsu.mkorovkina.rubics_cube.service;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WcaCompetitionScheduler {
    private final WcaCompetitionService wcaCompetitionService;

    public WcaCompetitionScheduler(WcaCompetitionService wcaCompetitionService) {
        this.wcaCompetitionService = wcaCompetitionService;
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void scheduledSync() {
        runSafely();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void syncOnStartup() {
        runSafely();
    }

    private void runSafely() {
        try {
            wcaCompetitionService.syncUpcoming();
        } catch (Exception e) {
        }
    }
}
