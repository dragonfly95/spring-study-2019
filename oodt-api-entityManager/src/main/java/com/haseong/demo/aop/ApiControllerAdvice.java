package com.haseong.demo.aop;

import com.haseong.demo.exception.ApiFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(ApiFailedException.class)
    public String handleException(ApiFailedException ex, HttpServletResponse response) {
        log.warn("ApiFailedException", ex);
        response.setStatus(ex.getHttpStatus().value());
        return ex.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex) {
        log.error("Exception", ex);
        return ex.getMessage();
    }
}
