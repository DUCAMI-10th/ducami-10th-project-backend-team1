package com.ducami.ducamiproject.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    Long id;
    String accessToken;
    String refreshToken;
}
