package ru.nsu.mkorovkina.rubics_cube.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.mkorovkina.rubics_cube.model.RefreshTokenModel;
import ru.nsu.mkorovkina.rubics_cube.model.UserModel;
import ru.nsu.mkorovkina.rubics_cube.repository.RefreshTokenRepository;
import ru.nsu.mkorovkina.rubics_cube.security.JwtService;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, JwtService jwtService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
    }

    @Transactional
    public String saveRefreshToken(UserModel user) {
        String token = jwtService.generateRefreshToken();
        RefreshTokenModel refreshToken = new RefreshTokenModel();
        refreshToken.setRefreshToken(token);
        refreshToken.setUser(user);
        refreshToken.setExpiration(LocalDateTime.now()
                .plusNanos(jwtService.getRefreshExpirationMs() * 1_000_000));
        refreshTokenRepository.save(refreshToken);
        return token;
    }

    public Optional<RefreshTokenModel> findByToken(String token) {
        return refreshTokenRepository.findByRefreshToken(token);
    }

    public boolean isExpired(RefreshTokenModel token) {
        return token.getExpiration().isBefore(LocalDateTime.now());
    }

    @Transactional
    public void deleteByToken(String token) {
        if (refreshTokenRepository.existsByRefreshToken(token)) {
            refreshTokenRepository.deleteByRefreshToken(token);
        } else {
            throw new IllegalArgumentException("Токен не найден");
        }
    }
}
