package ru.nsu.mkorovkina.rubics_cube.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.nsu.mkorovkina.rubics_cube.dto.SolveRequest;
import ru.nsu.mkorovkina.rubics_cube.dto.SolveResponse;
import ru.nsu.mkorovkina.rubics_cube.service.SolveService;

import java.util.List;

@RestController
@RequestMapping("/api/solves")
public class SolveController {
    private final SolveService solveService;

    public SolveController(SolveService solveService) {
        this.solveService = solveService;
    }

    @PostMapping
    public ResponseEntity<SolveResponse> create(Authentication authentication, @RequestBody SolveRequest solveRequest) {
        SolveResponse solveResponse = solveService.createSolve(authentication.getName(), solveRequest);
        return ResponseEntity.ok(solveResponse);
    }

    @GetMapping
    public ResponseEntity<List<SolveResponse>> getUserSolves(Authentication authentication) {
        List<SolveResponse> solves = solveService.getUserSolves(authentication.getName());
        return ResponseEntity.ok(solves);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<SolveResponse>> getAllUserSolves(Authentication authentication,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "20") int size) {
        if (page < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Номер страницы не может быть отрицательным");
        }
        if (size < 1 || size > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Размер страницы должен быть от 1 до 100");
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<SolveResponse> solves = solveService.getAllUserSolves(authentication.getName(), pageable);
        return ResponseEntity.ok(solves);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolveResponse> getSolveById(Authentication authentication, @PathVariable Long id) {
        SolveResponse solve = solveService.getSolveById(authentication.getName(), id);
        return ResponseEntity.ok(solve);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSolveById(Authentication authentication, @PathVariable Long id) {
        solveService.deleteSolveById(authentication.getName(), id);
        return ResponseEntity.noContent().build();
    }
}
