package com.sougata.domain.exceptions;

public class MimeTypeNotAllowedException extends RuntimeException {
    public MimeTypeNotAllowedException(String message) {
        super(message);
    }
}
