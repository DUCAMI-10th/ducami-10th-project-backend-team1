package com.ducami.ducamiproject.domain.auth.controller;

import com.ducami.ducamiproject.domain.auth.dto.request.LoginRequest;
import com.ducami.ducamiproject.domain.auth.dto.request.SignupRequest;
import com.ducami.ducamiproject.domain.auth.dto.response.LoginResponse;
import com.ducami.ducamiproject.domain.auth.dto.response.UserInfoResponse;
import com.ducami.ducamiproject.domain.auth.service.AuthService;
import com.ducami.ducamiproject.domain.user.domain.UserEntity;
import com.ducami.ducamiproject.global.data.ApiResponse;
import com.ducami.ducamiproject.global.security.entity.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserInfoResponse>> getUserInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ApiResponse.ok(authService.getUserInfo(userDetails.getEmail()));
    }
}
