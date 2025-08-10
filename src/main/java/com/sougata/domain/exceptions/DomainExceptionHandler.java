package com.sougata.domain.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;

@RestControllerAdvice
public class DomainExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> domainException(Exception e, HttpServletRequest request) {
        ErrorDto.ErrorDtoBuilder response = new ErrorDto.ErrorDtoBuilder();
        response.message(e.getMessage());
        response.path(request.getRequestURI());
        response.status(HttpStatus.INTERNAL_SERVER_ERROR);
        response.timestamp(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.build());
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorDto> accountExpiredException(TokenExpiredException e, HttpServletRequest request) {
        ErrorDto.ErrorDtoBuilder response = new ErrorDto.ErrorDtoBuilder();
        response.message(e.getMessage());
        response.path(request.getRequestURI());
        response.status(HttpStatus.UNAUTHORIZED);
        response.timestamp(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response.build());
    }

    @ExceptionHandler(DefaultRoleNotFoundException.class)
    public ResponseEntity<ErrorDto> defaultRoleNotFoundException(DefaultRoleNotFoundException e, HttpServletRequest request) {
        ErrorDto.ErrorDtoBuilder response = new ErrorDto.ErrorDtoBuilder();
        response.message(e.getMessage());
        response.path(request.getRequestURI());
        response.status(HttpStatus.NOT_FOUND);
        response.timestamp(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.build());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> entityNotFoundException(EntityNotFoundException e, HttpServletRequest request) {
        ErrorDto.ErrorDtoBuilder response = new ErrorDto.ErrorDtoBuilder();
        response.message(e.getMessage());
        response.path(request.getRequestURI());
        response.status(HttpStatus.NOT_FOUND);
        response.timestamp(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.build());
    }

    @ExceptionHandler(MimeTypeNotAllowedException.class)
    public ResponseEntity<ErrorDto> entityTypeNotAllowedException(MimeTypeNotAllowedException e, HttpServletRequest request) {
        ErrorDto.ErrorDtoBuilder response = new ErrorDto.ErrorDtoBuilder();
        response.message(e.getMessage());
        response.path(request.getRequestURI());
        response.status(HttpStatus.BAD_REQUEST);
        response.timestamp(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.build());
    }
}
