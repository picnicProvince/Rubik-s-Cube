package ru.nsu.mkorovkina.rubics_cube.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.nsu.mkorovkina.rubics_cube.dto.AuthResponse;
import ru.nsu.mkorovkina.rubics_cube.model.RefreshTokenModel;
import ru.nsu.mkorovkina.rubics_cube.model.UserModel;
import ru.nsu.mkorovkina.rubics_cube.security.JwtService;

@Service
public class AuthorizationService {
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    public AuthorizationService(RefreshTokenService refreshTokenService, JwtService jwtService,
                                UserService userService,
                                PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AuthResponse registration(UserModel user) {
        UserModel savedUser = userService.save(user);
        return authResponse(savedUser);
    }

    @Transactional
    public AuthResponse login(String username, String password) {
        UserModel user = userService.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Неверный логин"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Неверный пароль");
        }
        return authResponse(user);
    }

    @Transactional
    public AuthResponse updateAccessToken(String token) {
        RefreshTokenModel refreshToken = refreshTokenService.findByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Неверный токен"));
        if (refreshTokenService.isExpired(refreshToken)) {
            refreshTokenService.deleteByToken(token);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Срок действия токена истёк");
        }
        UserModel user = refreshToken.getUser();
        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccessToken(jwtService.generateAccessToken(user));
        authResponse.setRefreshToken(token);
        authResponse.setUsername(user.getUsername());
        authResponse.setRole(user.getRole());
        return authResponse;
    }

    @Transactional
    public void logout(String token) {
        refreshTokenService.deleteByToken(token);
    }

    private AuthResponse authResponse(UserModel user) {
        AuthResponse authResponse = new AuthResponse();
        authResponse.setUsername(user.getUsername());
        authResponse.setRole(user.getRole());
        authResponse.setAccessToken(jwtService.generateAccessToken(user));
        authResponse.setRefreshToken(refreshTokenService.saveRefreshToken(user));
        return authResponse;
    }
}
