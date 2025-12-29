package com.ducami.ducamiproject.domain.auth.controller;

import com.ducami.ducamiproject.domain.auth.dto.request.LoginRequest;
import com.ducami.ducamiproject.domain.auth.dto.request.SignupRequest;
import com.ducami.ducamiproject.domain.auth.dto.response.LoginResponse;
import com.ducami.ducamiproject.domain.auth.service.AuthService;
import com.ducami.ducamiproject.domain.user.domain.UserEntity;
import com.ducami.ducamiproject.global.data.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> signup(@RequestBody @Valid SignupRequest request) {
        authService.signup(request.name(), request.email(), request.password());

        return ApiResponse.created("정상적으로 가입되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest request) {
        return ApiResponse.ok(authService.login(request.email(), request.password()));
    }
}
