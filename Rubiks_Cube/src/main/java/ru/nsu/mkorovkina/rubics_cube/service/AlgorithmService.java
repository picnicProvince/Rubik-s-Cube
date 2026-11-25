package ru.nsu.mkorovkina.rubics_cube.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.nsu.mkorovkina.rubics_cube.model.Algorithm;
import ru.nsu.mkorovkina.rubics_cube.repository.AlgorithmRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AlgorithmService {
    private final AlgorithmRepository algorithmRepository;

    public AlgorithmService(AlgorithmRepository algorithmRepository) {
        this.algorithmRepository = algorithmRepository;
    }

    @Transactional
    public Algorithm saveAlgorithm(Algorithm algorithm) {
        return algorithmRepository.save(algorithm);
    }

    public List<Algorithm> getAllAlgorithms() {
        return algorithmRepository.findAll();
    }

    public Algorithm getAlgorithmById(Long id) {
        Optional<Algorithm> algorithm = algorithmRepository.findById(id);
        if (algorithm.isPresent()) {
            return algorithm.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Алгоритм не найден");
        }
    }

    public List<Algorithm> getAlgorithmByCategory(String category) {
        return algorithmRepository.findByCategory(category);
    }

    @Transactional
    public void deleteAlgorithm(Long id) {
        Algorithm algorithm = getAlgorithmById(id);
        algorithmRepository.deleteUserAlgorithmLinks(id);
        algorithmRepository.delete(algorithm);
    }
}
