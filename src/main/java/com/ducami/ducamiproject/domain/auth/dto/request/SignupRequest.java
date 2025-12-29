package com.ducami.ducamiproject.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignupRequest(
    @NotBlank(message = "이메일은 필수입니다.")
    String email,
    @NotBlank(message = "비밀번호는 필수입니다.")
    String password,
    @NotBlank(message = "이름은 필수입니다.")
    String name
    // 학번 추가

) {}
