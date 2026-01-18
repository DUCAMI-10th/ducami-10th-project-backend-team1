package com.ducami.ducamiproject.domain.auth.controller;

import com.ducami.ducamiproject.domain.auth.dto.request.LoginRequest;
import com.ducami.ducamiproject.domain.auth.dto.request.RefreshRequest;
import com.ducami.ducamiproject.domain.auth.dto.request.SignupRequest;
import com.ducami.ducamiproject.domain.auth.dto.response.LoginResponse;
import com.ducami.ducamiproject.domain.auth.dto.response.RefreshResponse;
import com.ducami.ducamiproject.domain.auth.service.AuthService;
import com.ducami.ducamiproject.global.data.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> signup(@RequestBody @Valid SignupRequest request) {
        authService.signup(request);
        return ApiResponse.created("정상적으로 가입되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest request) {
        return ApiResponse.ok(authService.login(request.username(), request.password()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<RefreshResponse>> refresh(@RequestBody @Valid RefreshRequest request) {
        return ApiResponse.ok(authService.refresh(request.refreshToken()));
    }


}
