package ru.nsu.mkorovkina.rubics_cube.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.mkorovkina.rubics_cube.dto.AuthResponse;
import ru.nsu.mkorovkina.rubics_cube.dto.LoginRequest;
import ru.nsu.mkorovkina.rubics_cube.dto.UpdateRequest;
import ru.nsu.mkorovkina.rubics_cube.model.UserModel;
import ru.nsu.mkorovkina.rubics_cube.service.AuthorizationService;

@RestController
@RequestMapping("/api/auth")
public class AuthorizationController {
    private final AuthorizationService authorizationService;

    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/registration")
    public ResponseEntity<AuthResponse>  registration(@RequestBody UserModel user) {
        AuthResponse response = authorizationService.registration(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse>  login(@RequestBody LoginRequest request) {
        AuthResponse response = authorizationService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    public ResponseEntity<AuthResponse>  update(@RequestBody UpdateRequest request) {
        AuthResponse response = authorizationService.updateAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void>  logout(@RequestBody UpdateRequest request) {
        authorizationService.logout(request.getRefreshToken());
        return ResponseEntity.noContent().build();
    }
}

