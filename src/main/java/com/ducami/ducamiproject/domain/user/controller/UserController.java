package com.ducami.ducamiproject.domain.user.controller;

import com.ducami.ducamiproject.domain.user.dto.request.UpdateUserRequest;
import com.ducami.ducamiproject.domain.user.dto.request.UpdateUserRoleRequest;
import com.ducami.ducamiproject.domain.user.dto.response.UserInfoResponse;
import com.ducami.ducamiproject.domain.user.service.UserService;
import com.ducami.ducamiproject.global.data.ApiResponse;
import com.ducami.ducamiproject.global.security.entity.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserInfoResponse>> getUserInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ApiResponse.ok(userService.getUserInfo(userDetails.getEmail()));
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<ApiResponse<String>> updateUserRole(
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserRoleRequest request
    ) {
        userService.updateUserRole(id, request.role());
        return ApiResponse.ok("권한이 성공적으로 변경되었습니다.");
    }

    @PatchMapping("/")
    public ResponseEntity<ApiResponse<String>> updateUserInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid UpdateUserRequest request
    ) {
        userService.updateUser(userDetails.getUsername(), request);
        return ApiResponse.ok("유저정보가 성공적으로 변경되었습니다.");
    }

}
