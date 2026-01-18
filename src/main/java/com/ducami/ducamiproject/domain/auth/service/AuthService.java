package com.ducami.ducamiproject.domain.auth.service;

import com.ducami.ducamiproject.domain.auth.dto.request.SignupRequest;
import com.ducami.ducamiproject.domain.auth.dto.response.LoginResponse;

public interface AuthService {

    void signup(String name, String email, String password);

    void signup(SignupRequest request);

    LoginResponse login(String username, String password);

}
