package ru.nsu.mkorovkina.rubics_cube.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.mkorovkina.rubics_cube.model.SolveModel;
import ru.nsu.mkorovkina.rubics_cube.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface SolveRepository extends JpaRepository<SolveModel, Long> {
    List<SolveModel> findTop100ByUserOrderByBuildDateDesc(UserModel user);
    Optional<SolveModel> findByIdAndUser(Long id, UserModel user);
}
