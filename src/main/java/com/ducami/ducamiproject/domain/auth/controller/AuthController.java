package com.ducami.ducamiproject.domain.auth.controller;

import com.ducami.ducamiproject.infra.log.annotation.target.LogTargetId;
import com.ducami.ducamiproject.domain.auth.dto.request.LoginRequest;
import com.ducami.ducamiproject.domain.auth.dto.request.SignupRequest;
import com.ducami.ducamiproject.domain.auth.dto.response.LoginResponse;
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


    @PostMapping("/test")
    @LogTargetId("#request.email") // targetId id만 갖는 것이 아니라 String, .. 여러가지도 가질 수 있도록 그리고 이거 찾는걸 resolver에서
    public ResponseEntity<String> justTest(@RequestBody @Valid SignupRequest request) {
        authService.signup(request.name(), request.email(), request.password());
        return ResponseEntity.ok("야미");
    }

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
