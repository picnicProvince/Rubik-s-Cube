package ru.nsu.mkorovkina.rubics_cube.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.nsu.mkorovkina.rubics_cube.model.Algorithm;

import java.util.List;

public interface AlgorithmRepository extends JpaRepository<Algorithm, Long> {
    List<Algorithm> findByCategory(String category);

    @Modifying
    @Query(value = "DELETE FROM user_algorithms WHERE algorithm_id = :id", nativeQuery = true)
    void deleteUserAlgorithmLinks(@Param("id") Long id);
}
