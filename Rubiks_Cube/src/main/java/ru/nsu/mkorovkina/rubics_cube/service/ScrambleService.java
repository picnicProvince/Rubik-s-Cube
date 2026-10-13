package ru.nsu.mkorovkina.rubics_cube.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.mkorovkina.rubics_cube.dto.ScrambleResponse;

@Service
@RequiredArgsConstructor
public class ScrambleService {

    private final ScrambleGenerator generator;

    public ScrambleResponse getScramble() {
        return new ScrambleResponse(generator.generate());
    }
}