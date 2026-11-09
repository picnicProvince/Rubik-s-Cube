package ru.nsu.mkorovkina.rubics_cube.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.nsu.mkorovkina.rubics_cube.dto.SolveRequest;
import ru.nsu.mkorovkina.rubics_cube.dto.SolveResponse;
import ru.nsu.mkorovkina.rubics_cube.model.SolveModel;
import ru.nsu.mkorovkina.rubics_cube.model.UserModel;
import ru.nsu.mkorovkina.rubics_cube.repository.SolveRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SolveService {
    private final SolveRepository solveRepository;
    private final UserService userService;

    public SolveService(SolveRepository solveRepository, UserService userService) {
        this.solveRepository = solveRepository;
        this.userService = userService;
    }

    @Transactional
    public SolveResponse createSolve(String username, SolveRequest solveRequest) {
        Optional<UserModel> user = userService.findByUsername(username);
        UserModel userModel;
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Пользователь не найден");
        } else {
            userModel = user.get();
        }
        SolveModel solve = new SolveModel();
        solve.setScramble(solveRequest.getScramble());
        solve.setMoves(solveRequest.getMoves());
        solve.setBuildDate(solveRequest.getBuildDate());
        solve.setTimeMs(solveRequest.getTimeMs());
        solve.setUser(userModel);
        SolveModel saved = solveRepository.save(solve);
        return toResponse(saved);
    }

    public List<SolveResponse> getUserSolves(String username) {
        Optional<UserModel> user = userService.findByUsername(username);
        UserModel userModel;
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Пользователь не найден");
        } else {
            userModel = user.get();
        }
        return solveRepository.findTop100ByUserOrderByBuildDateDesc(userModel)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public SolveResponse getSolveById(String username, Long id) {
        Optional<UserModel> user = userService.findByUsername(username);
        UserModel userModel;
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Пользователь не найден");
        } else {
            userModel = user.get();
        }
        Optional<SolveModel> solve = solveRepository.findByIdAndUser(id, userModel);
        SolveModel solveModel;
        if (solve.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Сборка не найдена");
        } else {
            solveModel = solve.get();
        }
        return toResponse(solveModel);
    }

    @Transactional
    public void deleteSolveById(String username, Long id) {
        Optional<UserModel> user = userService.findByUsername(username);
        UserModel userModel;
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Пользователь не найден");
        } else {
            userModel = user.get();
        }
        Optional<SolveModel> solve = solveRepository.findByIdAndUser(id, userModel);
        SolveModel solveModel;
        if (solve.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Сборка не найдена");
        } else {
            solveModel = solve.get();
        }
        solveRepository.delete(solveModel);
    }

    private SolveResponse toResponse(SolveModel solve) {
        SolveResponse solveResponse = new SolveResponse();
        solveResponse.setId(solve.getId());
        solveResponse.setScramble(solve.getScramble());
        solveResponse.setMoves(solve.getMoves());
        solveResponse.setTimeMs(solve.getTimeMs());
        solveResponse.setBuildDate(solve.getBuildDate());
        return solveResponse;
    }
}
