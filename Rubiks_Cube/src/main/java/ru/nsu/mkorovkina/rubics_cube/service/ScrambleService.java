package ru.nsu.mkorovkina.rubics_cube.service;

import org.springframework.stereotype.Service;
import ru.nsu.mkorovkina.rubics_cube.dto.ScrambleResponse;

@Service
public class ScrambleService {
    private final ScrambleGenerator generator;

    public ScrambleService(ScrambleGenerator generator) {
        this.generator = generator;
    }

    public ScrambleResponse getScramble() {
        ScrambleResponse scrambleResponse = new ScrambleResponse();
        scrambleResponse.setScramble(generator.generate());
        return scrambleResponse;
    }
}