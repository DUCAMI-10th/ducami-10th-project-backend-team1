package com.ducami.ducamiproject.global.security.jwt.util;

import com.ducami.ducamiproject.global.data.ApiResponse;
import tools.jackson.databind.json.JsonMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ApiResponseWriter {

    private final JsonMapper mapper;

    public void write(HttpStatus status, ApiResponse<Void> apiResponse, HttpServletResponse response) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json;charset=UTF-8");
        String json = mapper.writeValueAsString(apiResponse);
        response.getWriter().write(json);
    }
}
