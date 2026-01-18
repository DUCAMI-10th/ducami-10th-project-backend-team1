package com.ducami.ducamiproject.domain.auth.service;

import com.ducami.ducamiproject.domain.auth.dto.request.SignupRequest;
import com.ducami.ducamiproject.domain.auth.dto.response.LoginResponse;
import com.ducami.ducamiproject.domain.auth.dto.response.RefreshResponse;

public interface AuthService {

    void signup(SignupRequest request);

    LoginResponse login(String username, String password);

    RefreshResponse refresh(String refreshToken);
}
