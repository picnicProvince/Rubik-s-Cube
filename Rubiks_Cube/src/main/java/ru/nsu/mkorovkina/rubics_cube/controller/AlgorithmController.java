package ru.nsu.mkorovkina.rubics_cube.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.mkorovkina.rubics_cube.model.Algorithm;
import ru.nsu.mkorovkina.rubics_cube.service.AlgorithmService;

import java.util.List;

@RestController
@RequestMapping("/api/algorithms")
public class AlgorithmController {
    private final AlgorithmService algorithmService;

    public AlgorithmController(AlgorithmService algorithmService) {
        this.algorithmService = algorithmService;
    }

    @PostMapping
    public ResponseEntity<Algorithm> saveAlgorithm(@RequestBody Algorithm algorithm) {
        Algorithm saved = algorithmService.saveAlgorithm(algorithm);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Algorithm>> getAlgorithms() {
        List<Algorithm> algorithms = algorithmService.getAllAlgorithms();
        return ResponseEntity.ok(algorithms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Algorithm> getAlgorithmById(@PathVariable Long id) {
        Algorithm algorithm = algorithmService.getAlgorithmById(id);
        return ResponseEntity.ok(algorithm);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Algorithm>> getAlgorithmByCategory(@PathVariable String category) {
        List<Algorithm> algorithms = algorithmService.getAlgorithmByCategory(category);
        return ResponseEntity.ok(algorithms);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlgorithm(@PathVariable Long id) {
        algorithmService.deleteAlgorithm(id);
        return ResponseEntity.noContent().build();
    }
}
