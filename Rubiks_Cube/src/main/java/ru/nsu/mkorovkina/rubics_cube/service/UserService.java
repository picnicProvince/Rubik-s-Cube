package ru.nsu.mkorovkina.rubics_cube.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import ru.nsu.mkorovkina.rubics_cube.dto.UserRequest;
import ru.nsu.mkorovkina.rubics_cube.dto.UserResponse;
import ru.nsu.mkorovkina.rubics_cube.model.UserModel;
import ru.nsu.mkorovkina.rubics_cube.repository.RefreshTokenRepository;
import ru.nsu.mkorovkina.rubics_cube.repository.SolveRepository;
import ru.nsu.mkorovkina.rubics_cube.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final SolveRepository solveRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       RefreshTokenRepository refreshTokenRepository, SolveRepository solveRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenRepository = refreshTokenRepository;
        this.solveRepository = solveRepository;
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public UserModel findById(Long id) {
        Optional<UserModel> userModel = userRepository.findById(id);
        UserModel user;
        if (userModel.isPresent()) {
            user = userModel.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден");
        }
        return user;
    }

    public UserModel findByUsername(String username) {
        Optional<UserModel> userModel = userRepository.findByUsername(username);
        UserModel user;
        if (userModel.isPresent()) {
            user = userModel.get();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Пользователь не найден");
        }
        return user;
    }

    public UserResponse getById(Long id) {
        UserModel user = findById(id);
        return toResponse(user);
    }

    public UserResponse getMe(String username) {
        UserModel user = findByUsername(username);
        return toResponse(user);
    }

    @Transactional
    public UserModel save(@RequestBody UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Это имя уже занято");
        }
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Этот email уже занят");
        }
        UserModel user = new UserModel();
        user.setUsername(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public void deleteById(Long id) {
        UserModel user = findById(id);
        refreshTokenRepository.deleteAllByUser(user);
        solveRepository.deleteAllByUser(user);
        userRepository.delete(user);
    }

    private UserResponse toResponse(UserModel user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setRole(user.getRole());
        return userResponse;
    }
}
