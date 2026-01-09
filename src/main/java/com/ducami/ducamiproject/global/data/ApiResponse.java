package com.ducami.ducamiproject.global.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
    int status,
    T data,
    ErrorResponse error
) {
  public static <T> ResponseEntity<ApiResponse<T>> of(T data, HttpStatus status) {
    return ResponseEntity.status(status).body(new ApiResponse<>(status.value(), data, null));
  }

  public static <T> ResponseEntity<ApiResponse<T>> ok(T data) {
    return of(data, HttpStatus.OK);
  }

  public static <T> ResponseEntity<ApiResponse<T>> created(T data) {
    return of(data, HttpStatus.CREATED);
  }

  public static ApiResponse<Void> error(HttpStatus status, String code, String message) {
    return new ApiResponse<>(status.value(), null, ErrorResponse.of(code, message));
  }

  public static ApiResponse<Void> error(HttpStatus status, String code, String message, Map<String, String> details) {
    return new ApiResponse<>(status.value(), null, ErrorResponse.of(code, message, details));
  }

  public static ApiResponse<Void> error(HttpStatus status, ErrorResponse error) {
    return new ApiResponse<>(status.value(), null, error);
  }
}

