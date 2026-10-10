package ru.nsu.mkorovkina.rubics_cube.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.mkorovkina.rubics_cube.model.UserModel;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}