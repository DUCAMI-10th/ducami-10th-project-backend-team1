package com.ducami.ducamiproject.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RefreshResponse {
    private String accessToken;
}
