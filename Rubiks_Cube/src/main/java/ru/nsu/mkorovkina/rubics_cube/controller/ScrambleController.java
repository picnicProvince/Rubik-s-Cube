package ru.nsu.mkorovkina.rubics_cube.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.mkorovkina.rubics_cube.dto.ScrambleResponse;
import ru.nsu.mkorovkina.rubics_cube.service.ScrambleService;

@RestController
@RequestMapping("/api/scramble")
@RequiredArgsConstructor
public class ScrambleController {

    private final ScrambleService scrambleService;

    @GetMapping
    public ResponseEntity<ScrambleResponse> getScramble() {
        return ResponseEntity.ok(scrambleService.getScramble());
    }
}