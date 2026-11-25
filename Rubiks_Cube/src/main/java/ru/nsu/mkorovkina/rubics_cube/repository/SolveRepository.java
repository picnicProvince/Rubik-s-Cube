package ru.nsu.mkorovkina.rubics_cube.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.mkorovkina.rubics_cube.model.SolveModel;
import ru.nsu.mkorovkina.rubics_cube.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface SolveRepository extends JpaRepository<SolveModel, Long> {
    List<SolveModel> findTop100ByUserOrderByBuildDateDesc(UserModel user);
    Page<SolveModel> findByUserOrderByBuildDateDesc(UserModel user, Pageable pageable);
    Optional<SolveModel> findByIdAndUser(Long id, UserModel user);
    void deleteAllByUser(UserModel user);
}
