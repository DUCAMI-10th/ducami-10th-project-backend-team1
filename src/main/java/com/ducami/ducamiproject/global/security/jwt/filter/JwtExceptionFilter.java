package com.ducami.ducamiproject.global.security.jwt.filter;


import com.ducami.ducamiproject.domain.auth.exception.AuthException;
import com.ducami.ducamiproject.global.data.ApiResponse;
import com.ducami.ducamiproject.global.data.ErrorResponse;
import com.ducami.ducamiproject.global.security.jwt.util.ApiResponseWriter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ApiResponseWriter writer;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        }
        catch (AuthException e) {
            handleAuthException(e.getStatusCode().getHttpStatus(), response, e);
        }
        catch (ServletException e) {
//            handleAuthException(HttpStatus.BAD_REQUEST, response, e);
        }
    }

    public void handleAuthException(
                HttpStatus status,
                HttpServletResponse response,
                AuthException ex
    ) throws IOException {

        ErrorResponse errorResponse = ErrorResponse.of(ex.getStatusCode().getCode(), ex.getMessage());
        ApiResponse<Void> apiResponse = ApiResponse.error(status, errorResponse);

        writer.write(status, apiResponse, response);
    }


}

