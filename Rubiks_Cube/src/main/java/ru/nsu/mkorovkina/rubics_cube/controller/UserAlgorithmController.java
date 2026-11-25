package ru.nsu.mkorovkina.rubics_cube.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.nsu.mkorovkina.rubics_cube.model.Algorithm;
import ru.nsu.mkorovkina.rubics_cube.service.UserAlgorithmService;

import java.util.Set;

@RestController
@RequestMapping("/api/user-algorithms")
public class UserAlgorithmController {
    private final UserAlgorithmService userAlgorithmService;

    public UserAlgorithmController(UserAlgorithmService userAlgorithmService) {
        this.userAlgorithmService = userAlgorithmService;
    }

    @GetMapping
    public ResponseEntity<Set<Algorithm>> getUserAlgorithms(Authentication authentication) {
        Set<Algorithm> learnedAlgorithms = userAlgorithmService.getUserAlgorithms(
                authentication.getName());
        return ResponseEntity.ok(learnedAlgorithms);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Algorithm> addUserAlgorithm(Authentication authentication,
                                                      @PathVariable Long id) {
        Algorithm algorithm = userAlgorithmService.addUserAlgorithm(authentication.getName(), id);
        return ResponseEntity.ok(algorithm);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserAlgorithm(Authentication authentication,
                                                    @PathVariable Long id) {
        userAlgorithmService.deleteUserAlgorithm(authentication.getName(), id);
        return ResponseEntity.noContent().build();
    }
}
