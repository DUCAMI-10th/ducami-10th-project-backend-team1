package com.ducami.ducamiproject.domain.auth.service;

import com.ducami.ducamiproject.domain.auth.dto.response.LoginResponse;

public interface AuthService {

    void signup(String name, String email, String password);

    LoginResponse login(String email, String password);

}
