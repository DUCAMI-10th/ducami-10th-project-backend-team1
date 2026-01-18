package com.ducami.ducamiproject.domain.user.exception;


import com.ducami.ducamiproject.global.exception.exception.ApplicationException;
import com.ducami.ducamiproject.global.exception.status_code.StatusCode;

public class UserException extends ApplicationException {

  public UserException(StatusCode statusCode) {
    super(statusCode);
  }

  public UserException(StatusCode statusCode, Throwable cause) {
    super(statusCode, cause);
  }

  public UserException(StatusCode statusCode, String message) {
    super(statusCode, message);
  }
}
