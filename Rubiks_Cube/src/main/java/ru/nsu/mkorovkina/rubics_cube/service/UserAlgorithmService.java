package ru.nsu.mkorovkina.rubics_cube.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.mkorovkina.rubics_cube.model.Algorithm;
import ru.nsu.mkorovkina.rubics_cube.model.UserModel;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserAlgorithmService {
    private final UserService userService;
    private final AlgorithmService algorithmService;

    public UserAlgorithmService(UserService userService, AlgorithmService algorithmService) {
        this.userService = userService;
        this.algorithmService = algorithmService;
    }

    @Transactional
    public Algorithm addUserAlgorithm(String username, Long id) {
        UserModel user = userService.findByUsername(username);
        Algorithm algorithm = algorithmService.getAlgorithmById(id);
        user.getLearnedAlgorithms().add(algorithm);
        return algorithm;
    }

    @Transactional(readOnly = true)
    public Set<Algorithm> getUserAlgorithms(String username) {
        UserModel user = userService.findByUsername(username);
        return new HashSet<>(user.getLearnedAlgorithms());
    }

    @Transactional
    public void deleteUserAlgorithm(String username, Long id) {
        UserModel user = userService.findByUsername(username);
        Algorithm algorithm = algorithmService.getAlgorithmById(id);
        user.getLearnedAlgorithms().remove(algorithm);
    }
}
