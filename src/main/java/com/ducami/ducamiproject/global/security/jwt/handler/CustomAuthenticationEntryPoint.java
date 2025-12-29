package com.ducami.ducamiproject.global.security.jwt.handler;


import com.ducami.ducamiproject.global.data.ApiResponse;
import com.ducami.ducamiproject.global.data.ErrorResponse;
import com.ducami.ducamiproject.global.security.jwt.util.ApiResponseWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

import static com.ducami.ducamiproject.domain.auth.exception.AuthStatusCode.UNAUTHORIZED;


@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ApiResponseWriter writer;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        HttpStatus status = UNAUTHORIZED.getHttpStatus();;
        ErrorResponse errorResponse = ErrorResponse.of(UNAUTHORIZED.getCode(), UNAUTHORIZED.getMessage());
        ApiResponse<Void> apiResponse = ApiResponse.error(status, errorResponse);

        writer.write(status, apiResponse, response);

    }
}
