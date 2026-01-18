package com.ducami.ducamiproject.domain.user.dto.request;


import jakarta.validation.constraints.Email;

public record UpdateUserRequest(
   @Email(message = "이메일 형식이 아닙니다.")
   String email,
   Integer generation,
   String studentId
) {}
