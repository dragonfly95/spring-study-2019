package com.haseong.demo.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class ApiFailedException extends RuntimeException {
    private HttpStatus httpStatus;

    private ApiFailedException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public static ApiFailedException from(String message) {
        return new ApiFailedException(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public static ApiFailedException of(HttpStatus httpStatus, String message) {
        return new ApiFailedException(httpStatus, message);
    }
}
