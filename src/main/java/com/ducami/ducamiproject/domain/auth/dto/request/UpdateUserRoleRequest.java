package com.ducami.ducamiproject.domain.auth.dto.request;

import com.ducami.ducamiproject.domain.user.enums.UserRole;
import jakarta.validation.constraints.NotNull;

public record UpdateUserRoleRequest(
    @NotNull(message = "역할은 필수입니다.")
    UserRole role
) {}
