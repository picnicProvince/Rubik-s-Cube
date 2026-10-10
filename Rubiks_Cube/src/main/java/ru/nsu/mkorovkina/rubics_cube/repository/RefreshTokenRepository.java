package ru.nsu.mkorovkina.rubics_cube.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.mkorovkina.rubics_cube.model.RefreshTokenModel;
import ru.nsu.mkorovkina.rubics_cube.model.UserModel;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenModel, Long> {
    Optional<RefreshTokenModel> findByRefreshToken(String token);
    boolean existsByRefreshToken(String token);
    void deleteByRefreshToken(String token);
}